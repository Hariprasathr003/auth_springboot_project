package com.demo.token.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for topics.
 * <p>
 * This class is used to transfer topic information including UUID, topic name,
 * and creator details.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class TopicsDTO {

	/**
	 * Unique identifier for the topic.
	 */
	private String uuid;

	/**
	 * Name of the topic.
	 */
	private String Topic_Name;

	/**
	 * The username or identifier of the creator of the topic.
	 */
	private String createdBy;

}
