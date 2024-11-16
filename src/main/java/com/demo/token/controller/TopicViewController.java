package com.demo.token.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.token.model.TopicSummaryView;
import com.demo.token.service.TopicsViewService;

@RestController 
@RequestMapping("/api/topic-view")
public class TopicViewController {

	/**
	 * @param categoryService The service that handles the business logic for
	 *                        topicsViewService. Dependency Injection is used here to ensure
	 *                        the controller can access the service methods.
	 */
	@Autowired
	private TopicsViewService topicsViewService;
	
	/**
	 * 
	 * @return To get summary of view 
	 */
	@GetMapping
    public List<TopicSummaryView> getAllTopics() {
        return topicsViewService.getAllTopics();
    }
}
