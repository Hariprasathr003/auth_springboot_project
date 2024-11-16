package com.demo.token.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.token.model.TopicsReadStatus;
import com.demo.token.repo.TopicReadStatusRepository;
import com.demo.token.service.TopicsReadStatusService;

@Service
public class TopicsReadStatusImpl implements TopicsReadStatusService {
	
	/**
	 * TopicReadStatusRepository is a Spring Data JPA repository interface 
	 * for managing TopicReadStatus entities in the database. 
	 * It provides methods for performing CRUD operations related to 
	 * tracking the read status of topics by users.
	 */
	@Autowired
	private TopicReadStatusRepository topicReadStatusRepository;

	@Override
	public TopicsReadStatus saveStatus(TopicsReadStatus topicsReadStatus) {
		return topicReadStatusRepository.save(topicsReadStatus);
	}

	@Override
	public Optional<TopicsReadStatus> getReadStatusByUsersUuid(String usersUuid) {
		return topicReadStatusRepository.findByUsersUuid(usersUuid);
	}
	
	@Override
	public Optional<TopicsReadStatus> getReadStatusByTopicsUuidAndUserUuid(String topicsUuid, String usersUuid) {
		return topicReadStatusRepository.findByTopicsUuidAndUsersUuid(topicsUuid, usersUuid);
	}

}
