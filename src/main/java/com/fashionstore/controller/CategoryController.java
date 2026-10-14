package com.fashionstore.controller;

import com.fashionstore.model.Category;
import com.fashionstore.service.CategoryService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categories;
    public CategoryController(CategoryService categories) { this.categories = categories; }
    @GetMapping public List<Category> findAll() { return categories.findAll(); }
}
