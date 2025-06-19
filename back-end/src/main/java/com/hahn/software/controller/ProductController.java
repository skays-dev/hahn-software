package com.hahn.software.controller;

import com.hahn.software.criteria.ProductCriteria;
import com.hahn.software.dto.ProductDto;
import com.hahn.software.exception.FunctionalException;
import com.hahn.software.exception.HahnException;
import com.hahn.software.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(value = "/api/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping()
    public ResponseEntity<?> findProductsByCriteria(
            @RequestParam(name = "id", required = false) Long id,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate
    ) throws HahnException {
        ProductCriteria productCriteria = new ProductCriteria();
        productCriteria.setId(id);
        productCriteria.setName(name);
        productCriteria.setStartDate(startDate);
        productCriteria.setEndDate(endDate);
        return ResponseEntity.ok(productService.findProductsByCriteria(productCriteria));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findProductById(@PathVariable Long id) throws HahnException {
        return ResponseEntity.ok(productService.findProductById(id));
    }

    @PostMapping()
    public ResponseEntity<?> persistProduct(@Valid @RequestBody ProductDto productDto) throws HahnException {
        return ResponseEntity.ok(productService.persistProduct(productDto));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDto productDto) throws HahnException {
        return ResponseEntity.ok(productService.updateProduct(id, productDto));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable Long id) throws HahnException {
        return ResponseEntity.ok(productService.deleteProductById(id));
    }
}
