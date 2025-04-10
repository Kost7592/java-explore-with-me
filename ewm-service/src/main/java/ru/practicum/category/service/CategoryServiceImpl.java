package ru.practicum.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.error.exception.CategoryNotFoundException;

import java.util.List;

/**
 * Реализация сервисного интерфейса для работы с категориями.
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional
    @Override
    public CategoryDto createCategory(NewCategoryDto newCategoryDto) {
        Category category = categoryRepository.save(Category.builder()
                .name(newCategoryDto.getName())
                .build());
        return CategoryMapper.categoryDto(category);
    }

    @Transactional
    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer catId) {
        Category category = categoryRepository.findById(catId).orElseThrow(CategoryNotFoundException::new);
        category.setName(categoryDto.getName());
        categoryDto.setId(category.getId());
        return categoryDto;
    }

    @Transactional
    @Override
    public void deleteCategory(Integer catId) {
        categoryRepository.findById(catId).orElseThrow(CategoryNotFoundException::new);
        categoryRepository.deleteById(catId);
    }

    @Override
    public CategoryDto getCategoryById(Integer catId) {
        Category category = categoryRepository.findById(catId).orElseThrow(CategoryNotFoundException::new);
        return CategoryMapper.categoryDto(category);
    }

    @Override
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        List<Category> allCategories = categoryRepository.findAll();
        return allCategories.stream()
                .skip(from)
                .limit(size)
                .map(CategoryMapper::categoryDto)
                .toList();
    }
}
