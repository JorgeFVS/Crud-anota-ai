package com.example.desafio_anota_ai.utils;

import com.example.desafio_anota_ai.domain.category.Category;
import com.example.desafio_anota_ai.domain.category.CategoryDTO;

import java.util.ArrayList;
import java.util.List;

public class MockCategories {
    public static String TITLE = "Titulo de teste";
    public static String DESCRIPTION = "descricao de teste";
    public static String OWNER_ID = "111";
    public static String CATEGORY_ID = "222";

    public static CategoryDTO mockCategoryDTO() {
        return new CategoryDTO(TITLE, DESCRIPTION, OWNER_ID);
    }

    public static Category mockCategoryEntity() {
        return new Category(mockCategoryDTO());
    }

    public static List<Category> mockCategoryList() {
        Category category = mockCategoryEntity();
        List<Category> listCategories = new ArrayList<>();
        listCategories.add(category);
        return listCategories;
    }
}
