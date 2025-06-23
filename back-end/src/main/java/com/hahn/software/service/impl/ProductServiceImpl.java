package com.hahn.software.service.impl;

import com.hahn.software.dao.entity.Product;
import com.hahn.software.dao.repository.ProductRepository;
import com.hahn.software.dto.ProductDto;
import com.hahn.software.dto.ResponseDto;
import com.hahn.software.exception.FunctionalException;
import com.hahn.software.exception.HahnException;
import com.hahn.software.mappers.ProductMapper;
import com.hahn.software.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<ProductDto> findProducts() throws HahnException {
        try {
            return productMapper.toDtos(productRepository.findAll());
        } catch (Exception e) {
            throw new HahnException("Error in class ProductService while executing this method (findProducts)", new RuntimeException(e));
        }
    }

    @Override
    public ProductDto findProductById(Long id) throws HahnException {
        try {
            Optional<Product> product = productRepository.findById(id);
            System.out.println("1");
            System.out.println(product);
            if (product.isPresent()) {
               return productMapper.toDto(product.get());
            } else {
                throw new FunctionalException("The product is not found with id " + id);
            }
        } catch (FunctionalException e) {
            throw e;
        } catch (Exception e) {
            throw new HahnException("Error in class ProductService while executing this method (findProductById)", new RuntimeException(e));
        }
    }

    @Override
    public ProductDto persistProduct(ProductDto productDto) throws HahnException {
        try {
            productDto.setId(null);
            productDto.setDateCreation(LocalDateTime.now());

            return productMapper.toDto(productRepository.save(productMapper.toEntity(productDto)));
        } catch (Exception e) {
            throw new HahnException("Error in class ProductService while executing this method (persistProduct) productDto (" + productDto + ")", new RuntimeException(e));
        }
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) throws HahnException {
        try {
            ProductDto productOld = findProductById(id);
            productDto.setId(productOld.getId());
            productDto.setDateCreation(productOld.getDateCreation());
            productDto.setUserCreation(productOld.getUserCreation());
            productDto.setDateUpdate(LocalDateTime.now());
            productDto.setUserUpdate(null);

            return productMapper.toDto(productRepository.save(productMapper.toEntity(productDto)));
        } catch (FunctionalException e) {
            throw e;
        } catch (Exception e) {
            throw new HahnException("Error in class ProductService while executing this method (updateProduct) productDto (" + productDto + ")", new RuntimeException(e));
        }
    }

    @Override
    public ResponseDto deleteProductById(Long id) throws HahnException {
        try {
            Long check = productRepository.checkProductIsExistById(id);

            if (check != 1) {
                throw new FunctionalException("The product is not found with id " + id);
            }

            productRepository.deleteById(id);

            ResponseDto responseDto = new ResponseDto();
            responseDto.setMessage("Deleted Success");

            return responseDto;
        } catch (FunctionalException e) {
            throw e;
        } catch (Exception e) {
            throw new HahnException("Error in class ProductService while executing this method (deleteProductById) id (" + id + ")", new RuntimeException(e));
        }
    }
}
