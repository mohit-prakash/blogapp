package com.mps.blogapp.service.serviceimpl;

import com.mps.blogapp.dto.CategoryDto;
import com.mps.blogapp.entity.Category;
import com.mps.blogapp.exception.ResourceNotFoundException;
import com.mps.blogapp.repository.CategoryRepository;
import com.mps.blogapp.service.ICategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    private CategoryRepository catRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        Category savedCategory = catRepo.save(category);
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long catId) {
        Category category = catRepo.findById(catId).orElseThrow(() -> new ResourceNotFoundException("Category not found with this id " + catId));
        category.setCatTitle(categoryDto.getCatTitle());
        category.setCatDescription(categoryDto.getCatDescription());
        catRepo.save(category);
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public void removeCategory(Long catId) {
        Category category = catRepo.findById(catId).orElseThrow(() -> new ResourceNotFoundException("Category not found with this id " + catId));
        catRepo.delete(category);
    }

    @Override
    public CategoryDto getOneCategory(Long catId) {
        Category category = catRepo.findById(catId).orElseThrow(() -> new ResourceNotFoundException("Category not found with this id " + catId));
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = catRepo.findAll();
        List<CategoryDto> categoryDtos = categories.stream().map(category -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
        return categoryDtos;
    }
}
