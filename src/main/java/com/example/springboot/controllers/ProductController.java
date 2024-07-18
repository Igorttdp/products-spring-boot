package com.example.springboot.controllers;

import com.example.springboot.dtos.ProductRecordDto;
import com.example.springboot.dtos.ProductRecordUpdateDto;
import com.example.springboot.models.ProductModel;
import com.example.springboot.services.ProductService;
import com.example.springboot.util.ApiError;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/products")
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto) {
        var result = productService.saveProduct(productRecordDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        var result = productService.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getOneProduct(@PathVariable(value = "id") UUID id) {
        var result = productService.getOneProduct(id);

        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiError(HttpStatus.NOT_FOUND, "Product not found."));
        }

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PutMapping("products/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value = "id") UUID id,
                                                @RequestBody @Valid ProductRecordUpdateDto ProductRecordUpdateDto) {
        var result = productService.updateProduct(id, ProductRecordUpdateDto);

        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiError(HttpStatus.NOT_FOUND, "Product not found."));
        }

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") UUID id) {
        var result = productService.deleteProduct(id);

        if (!result) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiError(HttpStatus.NOT_FOUND, "Product not found."));
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);

    }
}
