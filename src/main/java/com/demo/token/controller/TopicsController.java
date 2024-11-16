package com.demo.token.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.token.dto.CategoryWithTopicsDto;
import com.demo.token.dto.DescriptionDTO;
import com.demo.token.dto.TopicsDTO;
import com.demo.token.exception.ResourceNotFoundException;
import com.demo.token.model.Topics;
import com.demo.token.service.TopicsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/topics")
public class TopicsController {

	/** 
	 * @param topicsService The service that handles the business logic for topics.
	 *                      Dependency Injection is used here to ensure the
	 *                      controller can access the service methods.
	 */
	@Autowired
	private final TopicsService topicsService;

	public TopicsController(TopicsService topicsService) {
		super();
		this.topicsService = topicsService;
	}
	/**
	 * To add new Topics based on category uuid
	 * 
	 * @param categoryUuid
	 * @param topicRequest Topics details
	 * @return saved topic's name with message
	 */
	@PostMapping("/addtopics/{categoryUuid}")
	public ResponseEntity<?> addTopic( @PathVariable String categoryUuid,@Valid @RequestBody Topics topicRequest) {
		try {
			TopicsDTO topic = topicsService.addTopics(categoryUuid, topicRequest.getName(), topicRequest.getDescription());
			return ResponseEntity.ok(topic);
		}
		catch(IllegalArgumentException e)
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Error adding topic: " + e.getMessage());
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error adding topic: " + e.getMessage());
		}
	}

	/**
	 * To get all topics based on category's uuid
	 * 
	 * @param categoryUuid
	 * @return Saved topics
	 */

	@GetMapping("/getallTopicsBy/{categoryUuid}")
	public ResponseEntity<?> getTopicsWithCategory(@PathVariable String categoryUuid) {
		try {
			CategoryWithTopicsDto categoryWithTopics = topicsService.getTopicsWithCategory(categoryUuid);
			return new ResponseEntity<>(categoryWithTopics, HttpStatus.OK);
		} catch (ResourceNotFoundException e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}
	}

	/**
	 * To view all topics
	 * 
	 * @return All saved topics details
	 */
	@GetMapping("/getallTopics")
	public List<TopicsDTO> getAllTopics() {
		return topicsService.getAllTopics();

	}

	/**
	 * To update a topic
	 * 
	 * @param topic's       Uuid
	 * @param updateRequest topic details
	 * @return updated topic details
	 */
	@PutMapping("/updateTopic/{topicsUuid}")
	public ResponseEntity<?> updateTopic(@PathVariable("topicsUuid") String topicsUuid,
			@Valid @RequestBody Topics updateRequest) {
		try {
			Optional<String> updatedTopic = topicsService.updateTopics(topicsUuid, updateRequest);
			return ResponseEntity.ok("Updated Sucessfully " + updatedTopic);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
		}
	}

	/**
	 * To read topics description
	 * 
	 * @param topicsUuid
	 * @param token
	 * @return
	 */
	@GetMapping("/viewDescription/{topicsUuid}")
	public ResponseEntity<?> getDescriptionAndMarkAsRead(@PathVariable("topicsUuid") String topicsUuid,
			@RequestHeader("Authorization") String token) {

		// Remove "Bearer " prefix from token if present
		if (token.startsWith("Bearer ")) {
			token = token.substring(7);
		}

		Optional<DescriptionDTO> description = topicsService.findAndMarkDescriptionAsReadByTopicsUuid(topicsUuid,
				token);

		if (description.isPresent()) {
			return ResponseEntity.ok(description.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("description not found with UUID: "+topicsUuid);
		}
	}
	/**
	 * 
	 * @param topicsUuid
	 * @return
	 */
	@DeleteMapping("/deleteBy/{topicsUuid}")
	public ResponseEntity<String> deleteCategoryById(@PathVariable String topicsUuid) {
		try {
			boolean deletedTopics = topicsService.deleteTopicsByUuid(topicsUuid);
			if (deletedTopics) {
				return ResponseEntity.ok("Topics deleted successfully");
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Topics not found with uuid");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while deleting the category");
		}
	}
}