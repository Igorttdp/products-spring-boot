package com.example.springboot.services.impl;

import com.example.springboot.dtos.ProductRecordDto;
import com.example.springboot.dtos.ProductRecordUpdateDto;
import com.example.springboot.models.ProductModel;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@Service
public interface ProductServiceImpl {
    ProductModel saveProduct(ProductRecordDto productRecordDto);

    List<ProductModel> getAllProducts();

    ProductModel getOneProduct(UUID id);

    ProductModel updateProduct(UUID id, ProductRecordUpdateDto productRecordUpdateDto);

    boolean deleteProduct(@PathVariable(value = "id") UUID id);

}
