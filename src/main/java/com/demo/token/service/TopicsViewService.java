package com.demo.token.service;

import java.util.List;

import com.demo.token.model.TopicSummaryView;

public interface TopicsViewService {
	
	/**
	 * Retrieves a list of all topics.
	 *
	 * This method returns a summary view of all topics available in the system. 
	 * Each topic is represented by a {@link TopicSummaryView} object, which includes 
	 * relevant details about the topic.
	 *
	 * @return a list of {@link TopicSummaryView} objects, representing all topics.
	 */
	List<TopicSummaryView> getAllTopics();
}
