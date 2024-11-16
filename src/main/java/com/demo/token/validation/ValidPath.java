package com.demo.token.validation;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

@Component
public class ValidPath {

	// Declare the validPath set as an instance variable
	private Set<String> validPath;

	// Constructor to initialize the valid paths
	public ValidPath() {
		validPath = new HashSet<>();

		// Add all Admin related paths to the set
		
		validPath.add("/api/user/login");
		validPath.add("/api/user/register");
		validPath.add("/api/user/delete/{Uuid}");
		validPath.add("/api/user/update/{uuid}");
		validPath.add("/api/user/getAllUsers");
		validPath.add("/api/user/getAllActiveUsers");
		validPath.add("/api/user/getAllInActiveUsers");
		validPath.add("/api/user/getAllTrainees");
		validPath.add("/api/user/getAllAttendees");
		validPath.add("/api/user/getUserBy/{Uuid}");
		validPath.add("/api/user/search/{name}");
		validPath.add("/send");

		// Add category-related paths
		validPath.add("/api/categories/addCategories");
		validPath.add("/api/categories/getAllCategories");
		validPath.add("/api/categories/getByUuid/{categoryUuid}");
		validPath.add("/api/categories/updateById/{categoryUuid}");
		validPath.add("/api/categories/deleteBy/{categoryUuid}");

		// Add topic-related paths
		validPath.add("/api/topics/addtopics/{categoryUuid}");
		validPath.add("/api/topics/getallTopics");
		validPath.add("/api/topics/getallTopicsBy/{categoryUuid}");
		validPath.add("/api/topics/updateTopic/{topicsUuid}");
		validPath.add("/api/topics/viewDescription/{topicsUuid}");
		validPath.add("/api/topics/deleteBy/{topicsUuid}");

		// Other paths
		validPath.add("/api/topic-view");
	}

	private AntPathMatcher pathMatcher = new AntPathMatcher();

	// Method to check if a given path is valid
	public boolean isValid(String path) {

		// Return true if the path is in the set, false otherwise
		// return validPath.contains(path);
		return validPath.stream().anyMatch(pattern -> pathMatcher.match(pattern, path));
	}

	public static void main(String[] args) {
		// Create an instance of ValidPath
		// ValidPath pathValidator = new ValidPath();

		// Test paths
		// String testPath1 = "/api/user/login";
		// String testPath2 = "/api/user/invalidPath";

		// Check if the paths are valid
		// System.out.println("Is valid path (" + testPath1 + "): " +
		// pathValidator.isValid(testPath1)); // Should print true
		// System.out.println("Is valid path (" + testPath2 + "): " +
		// pathValidator.isValid(testPath2)); // Should print false
	}
}
