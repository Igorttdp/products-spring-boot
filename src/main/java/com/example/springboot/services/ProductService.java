package com.example.springboot.services;

import com.example.springboot.controllers.ProductController;
import com.example.springboot.dtos.ProductRecordDto;
import com.example.springboot.dtos.ProductRecordUpdateDto;
import com.example.springboot.models.ProductModel;
import com.example.springboot.repositories.ProductRepository;
import com.example.springboot.services.impl.ProductServiceImpl;
import com.example.springboot.util.AppUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ProductService implements ProductServiceImpl {
    @Autowired
    ProductRepository productRepository;

    public ProductModel saveProduct(ProductRecordDto productRecordDto) {
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return productRepository.save(productModel);
    }

    public List<ProductModel> getAllProducts() {
        List<ProductModel> productsList = productRepository.findAll();

        if (!productsList.isEmpty()) {
            for (ProductModel product : productsList) {
                UUID id = product.getIdProduct();
                product.add(linkTo(methodOn(ProductController.class).getOneProduct(id)).withSelfRel());
            }
        }

        return productsList;
    }

    public ProductModel getOneProduct(UUID id) {
        Optional<ProductModel> product = productRepository.findById(id);

        if (product.isEmpty()) {
            return null;
        }

        product.get().add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("Products List"));

        return product.get();
    }

    public ProductModel updateProduct(UUID id, ProductRecordUpdateDto ProductRecordUpdateDto) {
        Optional<ProductModel> product = productRepository.findById(id);
        if (product.isEmpty()) {
            return null;
        }

        var productModel = product.get();
        AppUtils.copyNonNullProperties(ProductRecordUpdateDto, productModel);
        return productRepository.save(productModel);
    }

    public boolean deleteProduct(@PathVariable(value = "id") UUID id) {
        Optional<ProductModel> product = productRepository.findById(id);
        if (product.isEmpty()) {
            return false;
        }

        productRepository.delete(product.get());
        return true;
    }
}
