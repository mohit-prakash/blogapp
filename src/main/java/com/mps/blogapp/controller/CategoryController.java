package com.mps.blogapp.controller;

import com.mps.blogapp.dto.ApiResponse;
import com.mps.blogapp.dto.CategoryDto;
import com.mps.blogapp.exception.ResourceNotFoundException;
import com.mps.blogapp.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v2")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;
    @PostMapping("/add")
    public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto addedCategoryDto = categoryService.addCategory(categoryDto);
        return new ResponseEntity<>(addedCategoryDto, HttpStatus.CREATED);
    }

    @PutMapping("/update/{catId}")
    public ResponseEntity<ApiResponse> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Long catId){
        try{
            categoryService.updateCategory(categoryDto, catId);
            return new ResponseEntity<>(new ApiResponse("Category "+catId+" updated!!",true),HttpStatus.CREATED);
        }catch (ResourceNotFoundException rnfe){
            return new ResponseEntity<>(new ApiResponse(rnfe.getMessage(), false),HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/remove/{catId}")
    public ResponseEntity<ApiResponse> removeCategory(@PathVariable Long catId){
        try {
            categoryService.removeCategory(catId);
            return new ResponseEntity<>(new ApiResponse("Category "+catId+" removed!!",true),HttpStatus.OK);
        } catch (ResourceNotFoundException rnfe){
            return new ResponseEntity<>(new ApiResponse(rnfe.getMessage(), false),HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/get/{catId}")
    public ResponseEntity<?> getCategory(@PathVariable Long catId){
        try {
            CategoryDto categoryDto = categoryService.getOneCategory(catId);
            return new ResponseEntity<>(categoryDto,HttpStatus.OK);
        } catch (ResourceNotFoundException rnfe){
            return new ResponseEntity<>(new ApiResponse(rnfe.getMessage(), false),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getall")
    public ResponseEntity<List<CategoryDto>> getCategories(){
        return new ResponseEntity<>(categoryService.getAllCategories(),HttpStatus.OK);
    }
}
