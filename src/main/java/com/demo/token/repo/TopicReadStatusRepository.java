package com.demo.token.repo;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.token.model.TopicsReadStatus;

public interface TopicReadStatusRepository extends JpaRepository<TopicsReadStatus, Long>{
	
	/**
	 * To find a users by uuid
	 * @param usersUuid
	 * @return TopicsReadStatus
	 */
    Optional<TopicsReadStatus> findByUsersUuid(String usersUuid);
    
    /**
     * To find a topics uuid
     * @param topicsUuid
     * @return TopicsReadStatus
     */
    Optional<TopicsReadStatus> findByTopicsUuid(String topicsUuid);
    
    /**
     * Finds a {@link TopicsReadStatus} by the given topic UUID and user UUID.
     *
     * @param topicsUuid The UUID of the topic for which the read status is being queried.
     * @param usersUuid The UUID of the user whose read status is being queried.
     * @return An {@link Optional} containing the {@link TopicsReadStatus} if found, otherwise an empty {@link Optional}.
     */
    Optional<TopicsReadStatus> findByTopicsUuidAndUsersUuid(String topicsUuid, String usersUuid);

}
