package com.demo.token.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for topics with UUID.
 * <p>
 * This class is used to transfer topic information that includes the topic name and its unique identifier (UUID).
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class TopicWithUuidDto {

    /**
     * The name of the topic.
     */
    private String topicName;

    /**
     * The unique identifier (UUID) of the topic.
     */
    private String topicUuid;

   }
