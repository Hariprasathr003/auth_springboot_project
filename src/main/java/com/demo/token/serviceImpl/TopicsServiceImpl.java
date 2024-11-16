package com.demo.token.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demo.token.dto.CategoryWithTopicsDto;
import com.demo.token.dto.DescriptionDTO;
import com.demo.token.dto.TopicCategoryProjection;
import com.demo.token.dto.TopicWithUuidDto;
import com.demo.token.dto.TopicsDTO;
import com.demo.token.exception.ResourceNotFoundException;
import com.demo.token.exception.TopicNotFoundException;
import com.demo.token.model.Category;
import com.demo.token.model.Topics;
import com.demo.token.model.TopicsReadStatus;
import com.demo.token.model.Users;
import com.demo.token.model.Users.Role;
import com.demo.token.repo.TopicsRepository;
import com.demo.token.service.TopicsService;
import com.demo.token.service.UsersService;
import com.demo.token.serviceutility.UtilService;

@Service
public class TopicsServiceImpl implements TopicsService {

	/**
	 * UtilService is a service class that provides various utility methods that can
	 * be utilized by different components, promoting code * reusability and
	 * separation of concerns.
	 */
	@Autowired
	private UtilService utilService;

	/**
	 * UsersService is responsible for managing user-related operations This service
	 * interacts with the user repository and also handle business logic related to
	 * user management, ensuring that user data is correctly processed and
	 * maintained.
	 */
	@Autowired
	private UsersService usersService;

	/**
	 * Repository for performing CRUD operations on Topics. Used to interact with
	 * the database for managing topics-related data.
	 */
	@Autowired
	private TopicsRepository topicsRepository;

	public TopicsDTO convertsToDTO(Topics topics) {
		// return new TopicsDTO(topics.getUuid(), topics.getName(),
		// topics.getCreatedBy());
		utilService.getModelMapper().typeMap(Topics.class, TopicsDTO.class).addMappings(mapper -> {
			mapper.map(Topics::getName, TopicsDTO::setTopic_Name);
		});
		return utilService.getModelMapper().map(topics, TopicsDTO.class);
	}

	public DescriptionDTO convertsDescriptionToDTO(Topics topics) {
		utilService.getModelMapper().typeMap(Topics.class, DescriptionDTO.class).addMappings(mapper -> {
			mapper.map(Topics::getName, DescriptionDTO::setTopic_Name);
			mapper.map(Topics::getDescription, DescriptionDTO::setDescription); 
		});
		return utilService.getModelMapper().map(topics, DescriptionDTO.class);
	}

	public TopicsDTO addTopics(String categoryUuid, String name, String description) {
		Optional<Category> category = utilService.getCategoryService().findByUuid(categoryUuid);
		if (category.isPresent()) {
			Category categories = category.get();
			Topics topics = new Topics();
			topics.setName(name);
			topics.setDescription(description);
			topics.setCategory(categories);
			topics.setActive(true);
			Topics savedtopics = topicsRepository.save(topics);
			return convertsToDTO(savedtopics);
		} else {
			throw new IllegalArgumentException("Category not found with UUID: " + categoryUuid);
		}
	}

	@Override
	public List<TopicsDTO> getAllTopics() {
		List<TopicsDTO> Extopics = topicsRepository.findByIsActiveTrue().stream().map(this::convertsToDTO)
				.collect(Collectors.toList());
		if (Extopics.isEmpty()) {
			throw new IllegalArgumentException("Topcs not found ");
		}
		return Extopics;
	}

	@Override
	public CategoryWithTopicsDto getTopicsWithCategory(String categoryUuid) {
		List<TopicCategoryProjection> results = topicsRepository.findByCategoryUuid(categoryUuid);

		if (results.isEmpty()) {
			throw new ResourceNotFoundException("Topic not found with this category UUID " + categoryUuid);
		}

		String categoryName = results.get(0).getCategoryName();

		List<TopicWithUuidDto> topics = results.stream()
				.map(projection -> new TopicWithUuidDto(projection.getTopicName(), projection.getTopicUuid()))
				.collect(Collectors.toList());

		return new CategoryWithTopicsDto(categoryUuid, categoryName, topics);
	}

	@Override
	public Optional<String> updateTopics(String topicsUuid, Topics request) {
		Optional<Topics> existingTopic = topicsRepository.findByUuid(topicsUuid);

		if (existingTopic.isPresent()) {
			Topics existingTopicname = existingTopic.get();
			//existingTopicname.setCategory(request.getCategory());
			existingTopicname.setName(request.getName());
			existingTopicname.setDescription(request.getDescription());
			topicsRepository.save(existingTopicname);
			return Optional.of(existingTopicname.getName());
		} else {
			throw new ResourceNotFoundException("Topic not found with UUID " + topicsUuid);
		}
	}

	@Override
	public Optional<DescriptionDTO> findAndMarkDescriptionAsReadByTopicsUuid(String uuid, String token) {
		Optional<Topics> description = topicsRepository.findDescriptionByUuid(uuid);
		
		if (description.isPresent()) {
			// Extract the username from the JWT token
			String username = utilService.getJwtService().extractUserName(token);
			// Find the user by username, throw an exception if not found
			Users users = usersService.findByUserName(username)
					.orElseThrow(() -> new UsernameNotFoundException("Username not found"));

			// Find the topic by UUID, throw an exception if not found
			Topics topic = topicsRepository.findByUuid(uuid)
					.orElseThrow(() -> new TopicNotFoundException("Topic not found"));

			// Check if the role of the user is ATTENDEE
			if (users.getRole().equals(Role.ATTENDEE)) {
				// Check if there's an existing read status for this user and topic
				Optional<TopicsReadStatus> existingReadStatus = utilService.getTopicsReadStatusService()
						.getReadStatusByTopicsUuidAndUserUuid(uuid, users.getUuid());

				if (existingReadStatus.isPresent()) {
					// Update the existing read status with the new read time
					TopicsReadStatus readStatus = existingReadStatus.get();
					readStatus.setReadAt(LocalDateTime.now());
					utilService.getTopicsReadStatusService().saveStatus(readStatus); // Save the updated read status
				} else {
					// Create a new read status
					TopicsReadStatus readStatus = new TopicsReadStatus();
					readStatus.setTopicsUuid(uuid); 						// Set the topic UUID
					readStatus.setTopics(topic); 							// Set the topic entity
					readStatus.setUsersUuid(users.getUuid()); 				// Set the user UUID
					readStatus.setUserName(users.getUserName()); 			// Set the username
					readStatus.setRole(users.getRole()); 					// Set the user role
					readStatus.setReadAt(LocalDateTime.now()); 				// Set the current timestamp
					readStatus.setUsers(users);
					readStatus.setVersion(readStatus.getVersion() + 1);
					utilService.getTopicsReadStatusService().saveStatus(readStatus); // Save the new read status
				}
			}

			return Optional.of(convertsDescriptionToDTO(topic));
		}
		// Return an empty Optional if no description was found
		return Optional.empty();
	}

	@Override
	public boolean deleteTopicsByUuid(String uuid) {

		if (topicsRepository.existsByUuid(uuid)) {
			Topics deletedTopicsId = topicsRepository.findByUuid(uuid)
					.orElseThrow(() -> new RuntimeException("Topics not found"));
			if (deletedTopicsId.isActive())
				deletedTopicsId.setActive(false);
			topicsRepository.save(deletedTopicsId);
			return true;
		} else {
			return false;
		}
	}

}
