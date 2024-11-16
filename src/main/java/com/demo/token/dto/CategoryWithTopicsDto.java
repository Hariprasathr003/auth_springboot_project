package com.demo.token.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for categories with their associated topics.
 * <p>
 * This class is used to transfer category information along with a list of
 * topics that belong to the category. It encapsulates the details of the
 * category and its related topics.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryWithTopicsDto {

	/**
	 * Unique identifier for the category.
	 */
	private String categoryUuid;

	/**
	 * Name of the category.
	 */
	private String categoryName;

	/**
	 * List of topics associated with the category.
	 */
	private List<TopicWithUuidDto> topics;

}
