package com.demo.token.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for descriptions.
 * <p>
 * This class is used to transfer description information, typically used for
 * various purposes where a description needs to be encapsulated in a DTO.
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DescriptionDTO {
	/**
	 * Name of the topic
	 */
	private String topic_Name;

	/**
	 * Description of the topic
	 */
	private String description;

}
