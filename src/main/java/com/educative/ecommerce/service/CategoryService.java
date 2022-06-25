package com.educative.ecommerce.service;

import com.educative.ecommerce.model.Category;
import com.educative.ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Transactional(readOnly = true)
	public List<Category> listCategories() {
		return categoryRepository.findAll();
	}

	@Transactional(readOnly = false)
	public void createCategory(Category category) {
		categoryRepository.save(category);
	}

	@Transactional(readOnly = true)
	public Category readCategory(String categoryName) {
		return categoryRepository.findByCategoryName(categoryName);
	}

	@Transactional(readOnly = true)
	public Optional<Category> readCategory(Integer categoryId) {
		return categoryRepository.findById(categoryId);
	}

	@Transactional(readOnly = false)
	public void updateCategory(Integer categoryID, Category newCategory) {
		Category category = categoryRepository.findById(categoryID).get();
		category.setCategoryName(newCategory.getCategoryName());
		category.setDescription(newCategory.getDescription());
		category.setImageUrl(newCategory.getImageUrl());
		categoryRepository.save(category);
	}
}
