package com.demo.token.service;

import java.util.Optional;
import java.util.Set;

import com.demo.token.dto.CatregoryDTO;
import com.demo.token.model.Category;

public interface CategoryService {
	/**
	 * To add category
	 * @param category
	 * @return category
	 */
	CatregoryDTO addCategory(Category category);
	
	/**
	 * To get All categories
	 * @return CatregoryDTO
	 */
	Set<CatregoryDTO> getAllCategories();

	/**
	 * To get category by uuid
	 * @param categoryUuid
	 * @return CatregoryDTO
	 */
	Optional<CatregoryDTO> getCategoryByUuid(String categoryUuid);
	/**
	 * To update a cetegory by its uuid
	 * @param categoryUuid
	 * @param category
	 * @return Category
	 */
	Optional<Category> updateCategoryByUuid(String categoryUuid, Category category);
	/**
	 * To drop a category
	 * @param uuid
	 * @return boolean value
	 */
	boolean deleteCategoryByUuid(String uuid);
	/**
	 * To find a category 
	 * @param categoryUuid
	 * @return Category
	 */
	Optional<Category> findByUuid(String categoryUuid);
}
