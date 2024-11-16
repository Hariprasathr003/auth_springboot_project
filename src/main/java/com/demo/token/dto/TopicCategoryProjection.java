package com.demo.token.dto;

/**
 * Projection interface for retrieving topic and category information.
 * <p>
 * This interface defines methods to retrieve specific pieces of information
 * about a topic and its associated category. It is used for projections in
 * queries where only a subset of fields is needed.
 */
public interface TopicCategoryProjection {

	/**
	 * Gets the name of the topic.
	 *
	 * @return the name of the topic
	 */
	String getTopicName();

	/**
	 * Gets the unique identifier (UUID) of the topic.
	 * <p>
	 * Ensure that this method returns the UUID or other identifier used to uniquely
	 * identify the topic.
	 *
	 * @return the unique identifier of the topic
	 */
	String getTopicUuid();

	/**
	 * Gets the name of the category associated with the topic.
	 *
	 * @return the name of the category
	 */
	String getCategoryName();
}
