package com.example.desafio_anota_ai.services;

import com.example.desafio_anota_ai.domain.product.ProductDTO;
import com.example.desafio_anota_ai.domain.category.Category;
import com.example.desafio_anota_ai.domain.category.CategoryDTO;
import com.example.desafio_anota_ai.domain.category.exceptions.CategoryNotFoundException;
import com.example.desafio_anota_ai.domain.product.Product;
import com.example.desafio_anota_ai.domain.product.exceptions.ProductNotFoundException;
import com.example.desafio_anota_ai.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private CategoryService categoryService;
    private ProductRepository repository;

    public ProductService(ProductRepository repository, CategoryService categoryService) {
        this.repository = repository;
        this.categoryService = categoryService;
    }

    public Product insert(ProductDTO productData) {
        this.categoryService.getById(productData.categoryId())
                .orElseThrow(CategoryNotFoundException::new);
        Product newProduct = new Product(productData);

        this.repository.save(newProduct);
        return newProduct;
    }

    public List<Product> getAll() {
        return this.repository.findAll();
    }

    public Product update(String id, ProductDTO productData) {
        Product product = this.repository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        if (productData.categoryId() != null) {
            this.categoryService.getById(productData.categoryId())
                    .orElseThrow(ProductNotFoundException::new);
            product.setCategory(productData.categoryId());
        }

        if (!productData.title().isEmpty()) product.setTitle(productData.title());
        if (!productData.description().isEmpty()) product.setDescription(productData.description());
        if (!(productData.price() == null)) product.setPrice(productData.price());

        this.repository.save(product);

        return product;
    }

    public void delete(String id) {
        Product product = this.repository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
        this.repository.delete(product);
    }
}
