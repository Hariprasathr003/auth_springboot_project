package com.demo.token.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.token.dto.TopicCategoryProjection;
import com.demo.token.model.Topics;

@Repository
public interface TopicsRepository extends JpaRepository<Topics, Long> {
	/**
	 * To find a topics names and topic's uuid based on category uuid and
	 * 
	 * @param categoryUuid
	 * @return List of TopicCategoryProjection
	 */

	@Query("SELECT t.name AS topicName, t.uuid AS topicUuid, c.name AS categoryName "
			+ "FROM Topics t JOIN Category c ON t.category.id = c.id " + "WHERE c.uuid = :categoryUuid")
	List<TopicCategoryProjection> findByCategoryUuid(String categoryUuid);

	/**
	 * 
	 * @param Topics's uuid
	 * @return Description of the topics based on uuid
	 */

	//@Query("SELECT t.description FROM Topics t WHERE t.uuid = :uuid")
	Optional<Topics> findDescriptionByUuid(@Param("uuid")String uuid);

	/**
	 * To find a Topic
	 * 
	 * @param topicsUuid
	 * @return Topics
	 */
	Optional<Topics> findByUuid(String uuid);

	/**
	 * Checks if a record exists with the given UUID.
	 *
	 * @param uuid The UUID of the record to check for existence.
	 * @return {@code true} if a record with the specified UUID exists,
	 *         {@code false} otherwise.
	 */
	boolean existsByUuid(String uuid);

	/**
	 * To find All Topics
	 * 
	 * @return List of Topics By IsActive is true
	 */
	List<Topics> findByIsActiveTrue();

	// @Query("SELECT t FROM TopicSummaryView t")
	// @Query(value = "SELECT * FROM topic_summary_view", nativeQuery = true)
	// List<TopicSummaryView> getAlltopics();

}
