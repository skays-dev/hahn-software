package com.hahn.software.controller;

import com.hahn.software.dto.ProductDto;
import com.hahn.software.exception.HahnException;
import com.hahn.software.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    // http://localhost:8080/api/products GET
    @GetMapping()
    public ResponseEntity<?> findProducts(
    ) throws HahnException {
        return ResponseEntity.ok(productService.findProducts());
    }

    // http://localhost:8080/api/products/{id} GET
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findProductById(@PathVariable Long id) throws HahnException {
        return ResponseEntity.ok(productService.findProductById(id));
    }

    // http://localhost:8080/api/products POST
    @PostMapping()
    public ResponseEntity<?> persistProduct(@Valid @RequestBody ProductDto productDto) throws HahnException {
        return ResponseEntity.ok(productService.persistProduct(productDto));
    }

    // http://localhost:8080/api/products PUT
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDto productDto) throws HahnException {
        return ResponseEntity.ok(productService.updateProduct(id, productDto));
    }

    // http://localhost:8080/api/products DELETE
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable Long id) throws HahnException {
        return ResponseEntity.ok(productService.deleteProductById(id));
    }
}
