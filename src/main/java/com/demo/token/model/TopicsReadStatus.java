package com.demo.token.model;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.demo.token.model.Users.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
public class TopicsReadStatus {
	/**
	 * It is an auto generated value for each user
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * TopicsReadStatus belongs to topics_id
	 */
	@ManyToOne
	@JoinColumn(name = "topics_id")
	private Topics topics;

	/**
	 * TopicsReadStatus belongs to users_id who read the topic's description
	 */
	@ManyToOne
	@JoinColumn(name = "users_id")
	private Users users;

	/**
	 * user name to, who reads the topics
	 */
	@JoinColumn(name = "user_name")
	private String userName;

	/**
	 * To identify the role of user who reads the topics
	 */
	@Enumerated(EnumType.STRING)
	private Role role;

	/**
	 * To identify the time of read
	 */
	@JoinColumn(name = "read_at")
	private LocalDateTime readAt;

	/**
	 * To identify the topics easily by its uuid
	 */
	@JoinColumn(name = "topics_uuid")
	private String topicsUuid;

	/**
	 * To identify the User easily by user's uuid
	 */
	@JoinColumn(name = "users_uuid")
	private String usersUuid;
	
	/**
	 * To identify the count of reads By ATTENDEE only
	 */
	@Version
	private int version;

}
