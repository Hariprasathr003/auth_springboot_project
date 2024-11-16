package com.demo.token.service;

import java.util.Optional;

import com.demo.token.model.TopicsReadStatus;

public interface TopicsReadStatusService

{
	/**To save or update a view status 
 	 * 
	 * @param topicsReadStatus
	 * @return TopicsReadStatus
	 */
	TopicsReadStatus saveStatus(TopicsReadStatus topicsReadStatus);
	
	/**To get all view Status
	 * 
	 * @param usersUuid
	 * @return TopicsReadStatus
	 */

	Optional<TopicsReadStatus> getReadStatusByUsersUuid(String usersUuid);

	/**
	 * 
	 * @param topicsUuid
	 * @return
	 */
	//Optional<TopicsReadStatus> getReadStatusByTopicsUuid(String topicsUuid);

	Optional<TopicsReadStatus>getReadStatusByTopicsUuidAndUserUuid(String topicsUuid, String usersUuid);
}
