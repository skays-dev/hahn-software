package com.hahn.software.service.impl;

import com.hahn.software.criteria.ProductCriteria;
import com.hahn.software.dao.entity.Product;
import com.hahn.software.dao.repository.ProductRepository;
import com.hahn.software.dto.ProductDto;
import com.hahn.software.dto.ResponseDto;
import com.hahn.software.exception.FunctionalException;
import com.hahn.software.exception.HahnException;
import com.hahn.software.mappers.ProductMapper;
import com.hahn.software.service.ProductService;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public List<ProductDto> findProductsByCriteria(ProductCriteria productCriteria) throws HahnException {
        try {
            List<Product> productList = productRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
                List<Predicate> predicateList = new ArrayList<>();
                if (productCriteria.getId() != null) {
                    predicateList.add(criteriaBuilder.equal(root.get("id"), productCriteria.getId()));
                }
                if (productCriteria.getName() != null) {
                    predicateList.add(criteriaBuilder.equal(root.get("name"), productCriteria.getName()));
                }
                if (productCriteria.getStartDate() != null) {
                    predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dateCreation"), productCriteria.getStartDate().atTime(LocalTime.MIN)));
                }
                if (productCriteria.getEndDate() != null) {
                    predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("dateCreation"), productCriteria.getEndDate().atTime(LocalTime.MAX)));
                }

                return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
            });
            return ProductMapper.MAPPER.toDtos(productList);
        } catch (Exception e) {
            throw new HahnException("Error in class ProductService while executing this method (findProductsByCriteria) productCriteria (" + productCriteria + ")", new RuntimeException(e));
        }
    }

    @Override
    public ProductDto findProductById(Long id) throws HahnException {
        try {
            Optional<Product> product = productRepository.findById(id);
            if (product.isPresent()) {
               return ProductMapper.MAPPER.toDto(product.get());
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
            productDto.setUserCreation(null);

            return ProductMapper.MAPPER.toDto(productRepository.save(ProductMapper.MAPPER.toEntity(productDto)));
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

            return ProductMapper.MAPPER.toDto(productRepository.save(ProductMapper.MAPPER.toEntity(productDto)));
        } catch (FunctionalException e) {
            throw e;
        } catch (Exception e) {
            throw new HahnException("Error in class ProductService while executing this method (updateProduct) productDto (" + productDto + ")", new RuntimeException(e));
        }
    }


    @Override
    public ResponseDto deleteProductById(Long id) throws HahnException {
        try {
            findProductById(id);

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
