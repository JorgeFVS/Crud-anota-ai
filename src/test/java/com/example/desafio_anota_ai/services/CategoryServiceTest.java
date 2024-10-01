package com.example.desafio_anota_ai.services;

import com.example.desafio_anota_ai.domain.category.Category;
import com.example.desafio_anota_ai.domain.category.CategoryDTO;
import com.example.desafio_anota_ai.domain.category.exceptions.CategoryNotFoundException;
import com.example.desafio_anota_ai.repositories.CategoryRepository;
import com.example.desafio_anota_ai.utils.MockCategories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static com.example.desafio_anota_ai.utils.MockCategories.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class CategoryServiceTest {

    @Mock
    CategoryRepository repository;

    @Autowired
    @InjectMocks
    CategoryService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void insertSuccess() {
        final var input = new CategoryDTO("title", "description", "ownerId");
        final var category = new Category(input);

        given(repository.save(category)).willReturn(category);

        final var actual = service.insert(input);

        assertEquals(category, actual);

    }

    @Test
    void updateNonexistentCategory() {
        assertThrows(CategoryNotFoundException.class,
                () -> service.update("id", null));
    }

    @Test
    @DisplayName("should return a Category list on getAll")
    void getAllSuccess() {
        when(repository.findAll()).thenReturn(mockCategoryList());

        List<Category> result = this.service.getAll();

        assertNotNull(result);
        assertEquals(result.get(0).getOwnerId(), OWNER_ID);
        assertEquals(result.get(0).getTitle(), TITLE);
        assertEquals(result.get(0).getDescription(), DESCRIPTION);
    }

    @Test
    @DisplayName("Should get a Category by Id")
    void getById() {
        Category category = mockCategoryEntity();
        when(repository.findById(CATEGORY_ID)).thenReturn(Optional.of(category));

        Optional<Category> result = this.service.getById(CATEGORY_ID);

        assertNotNull(result);
        Mockito.verify(repository, times(1)).findById(CATEGORY_ID);
    }

    @Test
    @DisplayName("should delete a Category when exists")
    void deleteSuccess() {
        Category category = mockCategoryEntity();
        when(repository.findById(CATEGORY_ID)).thenReturn(Optional.of(category));

        this.service.delete(CATEGORY_ID);

        Mockito.verify(repository, times(1)).delete(category);
    }

    @Test
    @DisplayName("should throw exception when Category not exists")
    void deleteError() {
        when(repository.findById(CATEGORY_ID)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> {
            this.service.delete(CATEGORY_ID);
        });
    }
}