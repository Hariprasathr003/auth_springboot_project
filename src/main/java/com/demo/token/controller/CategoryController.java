package com.demo.token.controller;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.token.dto.CatregoryDTO;
import com.demo.token.model.Category;
import com.demo.token.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	/**
	 * @param categoryService The service that handles the business logic for
	 *                        category. Dependency Injection is used here to ensure
	 *                        the controller can access the service methods.
	 */
	@Autowired
	private CategoryService categoryService;

	/**
	 * To create new category
	 * 
	 * @param category
	 * @return category name
	 */
	@PostMapping("/addCategories")
	public ResponseEntity<?> addCategory(@Valid @RequestBody Category category) {
		try {
			CatregoryDTO newcategories = categoryService.addCategory(category);
			return ResponseEntity.ok(newcategories);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error saving category: " + e.getMessage());
		}
	}

	/**
	 * To get all categories
	 * 
	 * @param category
	 * @return All saved category
	 */

	@GetMapping("/getAllCategories")
	public ResponseEntity<?> getAllCategories() {
	    Set<CatregoryDTO> categoryList = categoryService.getAllCategories();
	    
	    if (categoryList.isEmpty()) {
	        // Return a 404 Not Found status with a meaningful message
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body("No categories found.");
	    }
	    
	    // Return a 200 OK status with the list of categories
	    return ResponseEntity.ok(categoryList);
	}

	/**CatregoryDTO
	 * To get category by its uuid
	 * 
	 * @param categoryUuid
	 * @return saved category
	 */
	@GetMapping("/getByUuid/{categoryUuid}")
	public ResponseEntity<?> getCategoryById(@PathVariable String categoryUuid) {
		Optional<CatregoryDTO> category = categoryService.getCategoryByUuid(categoryUuid);

		if (category.isPresent()) {
			return ResponseEntity.ok(category.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("category not with uuid"); 
		}
	}

	/**
	 * To update a existing category
	 * 
	 * @param categoryUuid
	 * @param category
	 * @return updated category
	 */
	@PutMapping("/updateById/{categoryUuid}")
	public ResponseEntity<?> updateCategoryById(@PathVariable String categoryUuid,
			@Valid @RequestBody Category category) {
		Optional<Category> existingCategory = categoryService.updateCategoryByUuid(categoryUuid, category);

		if (existingCategory.isPresent()) {
			return ResponseEntity.ok(existingCategory.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("category not found with uuid");
		}
	}

	/**
	 * To drop a category
	 * 
	 * @param categoryUuid
	 * @return success message
	 */
	@DeleteMapping("/deleteBy/{categoryUuid}")
	public ResponseEntity<String> deleteCategoryById(@PathVariable String categoryUuid) {
		try {
			boolean deletedCategory = categoryService.deleteCategoryByUuid(categoryUuid);
			if (deletedCategory) {
				return ResponseEntity.ok( "Category deleted successfully");
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found with uuid");
			}
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while deleting the category");
		}
	}
}
