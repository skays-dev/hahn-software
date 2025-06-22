package com.hahn.software.service;

import com.hahn.software.criteria.ProductCriteria;
import com.hahn.software.dto.ProductDto;
import com.hahn.software.dto.ResponseDto;
import com.hahn.software.exception.HahnException;

import java.util.List;


public interface ProductService {
    List<ProductDto> findProducts() throws HahnException;

    ProductDto findProductById(Long id) throws HahnException;

    ProductDto persistProduct(ProductDto productDto) throws HahnException;

    ProductDto updateProduct(Long id, ProductDto productDto) throws HahnException;

    ResponseDto deleteProductById(Long id) throws HahnException;
}
