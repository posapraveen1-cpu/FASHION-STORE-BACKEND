package com.fashionstore.service;

import com.fashionstore.model.Category;
import com.fashionstore.repository.CategoryRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final CategoryRepository categories;
    public CategoryService(CategoryRepository categories) { this.categories = categories; }
    public List<Category> findAll() { return categories.findAll(); }
    public Category getOrCreate(String name) {
        String normalized = name.trim().toLowerCase();
        return categories.findByNameIgnoreCase(normalized).orElseGet(() -> categories.save(new Category(normalized)));
    }
}
