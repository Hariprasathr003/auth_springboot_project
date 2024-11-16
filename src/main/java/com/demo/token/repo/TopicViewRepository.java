package com.demo.token.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.demo.token.model.TopicSummaryView;

@Repository
public interface TopicViewRepository extends JpaRepository<TopicSummaryView, Long> {

	 // @Query("SELECT t FROM TopicSummaryView t")
	 // List<TopicSummaryView> getAlltopics();
	  
		@Query(value = "SELECT * FROM topic_summary_view", nativeQuery = true)
		List<TopicSummaryView> getAlltopics();

		List<TopicSummaryView> findAll(); // Fetches all records from the view

}
