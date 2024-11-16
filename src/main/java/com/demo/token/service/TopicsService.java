package com.demo.token.service;

import java.util.List;
import java.util.Optional;

import com.demo.token.dto.CategoryWithTopicsDto;
import com.demo.token.dto.DescriptionDTO;
import com.demo.token.dto.TopicsDTO;
import com.demo.token.model.Topics;

public interface TopicsService {

	/**
	 * add the topics
	 * 
	 * @param categoryUuid categoryUuid
	 * @param name         category name
	 * @param description  category description
	 * @return topics
	 */
	TopicsDTO addTopics(String categoryUuid, String name, String description);

	/**
	 * To converts entity to DTO
	 * @param topics
	 * @return TopicsDTo
	 */
	TopicsDTO convertsToDTO(Topics topics);
	
	/**
	 * To get all topics from DB
	 * @return List of Topics
	 */
	List<TopicsDTO> getAllTopics();
	
	/**
	 * To update a existing topics by toipcs uuid
	 * @param topicsUuid
	 * @param topics
	 * @return Updated topics details
	 */
	Optional<String> updateTopics(String topicsUuid, Topics topics);
	/**
	 * To find a description and from topics entity
	 * @param topicsUuid
	 * @param token
	 * @return DescriptionDTO
	 */
	Optional<DescriptionDTO> findAndMarkDescriptionAsReadByTopicsUuid(String topicsUuid, String token);

	/**
	 * To get all Topics by category uuid
	 * @param categoryUuid
	 * @return CategoryWithTopicsDto
	 */
	CategoryWithTopicsDto getTopicsWithCategory(String categoryUuid);

	
	/**
	 * Deletes a topic based on its unique identifier (UUID).
	 *
	 * @param uuid the unique identifier of the topic to be deleted
	 * @return true if the topic was successfully deleted, false otherwise
	 */
	boolean deleteTopicsByUuid(String uuid);


}
