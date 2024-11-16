package com.demo.token.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.token.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	/**
	 * To find a category by its uuid
	 * @param uuid
	 * @return category details
	 */
	Optional<Category> findByUuid(String uuid);

	/**
	 * To check a whether category present or not
	 * @param uuid
	 * @return true or false
	 */
	boolean existsByUuid(String uuid);

	/**
	 * @return list of IsaActive(True) users
	 */
	List<Category> findByIsActiveTrue();

}
