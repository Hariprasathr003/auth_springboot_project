package com.demo.token.model;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Topics {
	/**
	 * It is an auto generated value for each Topic
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * It is an unique id for each Topic to identify
	 */
	@Column(nullable = false, unique = true, updatable = false)
	private String uuid = UUID.randomUUID().toString();

	/**
	 * Topic's Name
	 */
	@NotNull(message = "Topic's name cannot be null")
	private String name;

	/**
	 * Topic's Description
	 */
	@NotNull(message = "Description cannot be null")
	@Column(name = "description", columnDefinition = "TEXT")
	private String description;

	/**
	 * A topics can
	 */
	@OneToMany(mappedBy = "topics")
	private Set<TopicsReadStatus> readStatuses;

	/**
	 * Topics belongs to category's uuid
	 */
	@ManyToOne
	@JoinColumn(name = "category_uuid", referencedColumnName = "uuid", updatable = true ,nullable = false)
	private Category category;

	/**
	 * To identify the time of creation
	 */
	@CreatedDate
	@Column(name="created_at",nullable = false, updatable = false)
	private LocalDateTime createdAt;

	/**
	 * To identify the time of updatedAt
	 */
	@LastModifiedDate
	@Column(name="updated_at",nullable = false)
	private LocalDateTime updatedAt;

	/**
	 * To identify the ,who created the topics
	 */
	@CreatedBy
	@Column(name="created_by",nullable = false)
	private String createdBy;

	/**
	 * To identify,who updates the topics entity
	 */
	@LastModifiedBy
	@Column(name="updated_by",nullable = false)
	private String updatedBy;

	/**
	 *
	 * To identify the count of updated made on topics
	 */
	@Version
	private Long version;

	/**
	 * Used to represent a user is active or not
	 */

	@Column(name="is_active",nullable = false)
	private boolean isActive;

}
