package com.demo.token.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for category details.
 * <p>
 * This class is used to transfer category information, including UUID, name,
 * and the creator of the category.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class CatregoryDTO {

	/**
	 * Unique identifier for the category.
	 */
	private String uuid;

	/**
	 * Name of the category.
	 */
	private String name;

	/**
	 * The username or identifier of the creator of the category.
	 */
	private String createdBY;

}
