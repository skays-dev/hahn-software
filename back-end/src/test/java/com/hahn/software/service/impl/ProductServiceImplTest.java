package com.hahn.software.service.impl;

import com.hahn.software.dao.entity.Product;
import com.hahn.software.dao.repository.ProductRepository;
import com.hahn.software.dto.ProductDto;
import com.hahn.software.dto.ResponseDto;
import com.hahn.software.exception.FunctionalException;
import com.hahn.software.exception.HahnException;
import com.hahn.software.mappers.ProductMapper;
import com.hahn.software.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@SpringBootTest
public class ProductServiceImplTest {
    @Mock
    private  ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    ProductService productService = new ProductServiceImpl();


    @DisplayName("Find Products - Success")
    @Test
    void test_FindProducts_Success() throws HahnException {
        // Arrange
        List<Product> products = List.of(getProduct());
        List<ProductDto> productList  = List.of(getProductDto());

        when(productRepository.findAll()).thenReturn(products);
        when(productMapper.toDtos(products)).thenReturn(productList);

        // Act
        List<ProductDto> result = productService.findProducts();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Product For Test Junit Test", result.get(0).getName());

        verify(productRepository, times(1)).findAll();
        verify(productMapper, times(1)).toDtos(products);
    }

    @DisplayName("Find Products - Exception Scenario")
    @Test
    void test_FindProducts_ThrowsException() {
        // Arrange
        when(productRepository.findAll()).thenThrow(new RuntimeException("DB failure"));

        // Act + Assert
        HahnException ex = assertThrows(HahnException.class, () -> productService.findProducts());
        assertTrue(ex.getMessage().contains("Error in class ProductService while executing this method"));
        assertInstanceOf(RuntimeException.class, ex.getCause());

        verify(productRepository, times(1)).findAll();
    }


    @DisplayName("Get Product - Success Scenario")
    @Test
    void testWhen_Product_Success() throws HahnException {
        // Arrange
        Product product = getProduct();
        ProductDto productDto = getProductDto();

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productMapper.toDto(product)).thenReturn(productDto);

        // Act
        ProductDto productResponse = productService.findProductById(1L);

        // Assert
        verify(productRepository, times(1)).findById(1L);
        verify(productMapper, times(1)).toDto(product);

        assertNotNull(productResponse);
        assertEquals(product.getId(), productResponse.getId());
        assertEquals(product.getName(), productResponse.getName());
        assertEquals(product.getPrice(), productResponse.getPrice());
    }

    @DisplayName("Get Product - Unexpected Exception Scenario")
    @Test
    void testWhen_FindProduct_ThrowsRuntimeException() {
        // Arrange
        when(productRepository.findById(anyLong()))
                .thenThrow(new RuntimeException("Simulated DB error"));

        // Act + Assert
        HahnException exception = assertThrows(HahnException.class, () -> productService.findProductById(1L));

        // Assert: exception message contains fallback error message
        assertTrue(exception.getMessage().contains("Error in class ProductService while executing this method"));

        assertInstanceOf(RuntimeException.class, exception.getCause());
        assertTrue(exception.getCause().getMessage().contains("Simulated DB error"));

        // Verify
        verify(productRepository, times(1)).findById(1L);
    }




    @DisplayName("Persist Product - Success scenario")
    @Test
    void test_When_Persist_Product_Success() throws HahnException {
        // Arrange
        ProductDto productDto = getProductDto();
        Product product = getProduct();
        Product savedProduct = getProduct();
        savedProduct.setId(1L);

        ProductDto expectedDto = getProductDto();
        expectedDto.setId(1L);

        when(productMapper.toEntity(any(ProductDto.class))).thenReturn(product);
        when(productRepository.save(product)).thenReturn(savedProduct);
        when(productMapper.toDto(savedProduct)).thenReturn(expectedDto);

        // Act
        ProductDto result = productService.persistProduct(productDto);

        // Assert
        verify(productRepository, times(1)).save(product);
        verify(productMapper).toEntity(productDto);
        verify(productMapper).toDto(savedProduct);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(product.getName(), result.getName());
    }

    @DisplayName("Persist Product - Failed scenario")
    @Test
    void test_When_Persist_Product_Failed() {
        // Arrange
        ProductDto productDto = getProductDto();
        Product product = getProduct();

        when(productMapper.toEntity(any(ProductDto.class))).thenReturn(product);
        when(productRepository.save(product)).thenThrow(new RuntimeException("DB error"));

        // Act + Assert
        HahnException exception = assertThrows(HahnException.class,
                () -> productService.persistProduct(productDto));

        // Assert message content (optional)
        assertTrue(exception.getMessage().contains("Error in class ProductService while executing this method"));

        // Verify
        verify(productMapper).toEntity(productDto);
        verify(productRepository).save(product);
    }


    @DisplayName("Update Product - Success Scenario")
    @Test
    void test_UpdateProduct_Success() throws HahnException {
        Long id = 1L;
        ProductDto inputDto = getProductDto();

        Product productEntity = getProduct();
        productEntity.setId(id);
        productEntity.setDateCreation(LocalDateTime.of(2024, 1, 1, 10, 0));

        ProductDto oldDto = getProductDto();
        oldDto.setId(id);
        oldDto.setDateCreation(productEntity.getDateCreation());
        oldDto.setUserCreation(10L);

        Product savedEntity = getProduct();
        savedEntity.setId(id);

        ProductDto resultDto = getProductDto();
        resultDto.setId(id);

        when(productRepository.findById(id)).thenReturn(Optional.of(productEntity));
        when(productMapper.toDto(productEntity)).thenReturn(oldDto);

        when(productMapper.toEntity(any(ProductDto.class))).thenReturn(productEntity);
        when(productRepository.save(productEntity)).thenReturn(savedEntity);
        when(productMapper.toDto(savedEntity)).thenReturn(resultDto);

        // Act
        ProductDto actualResult = productService.updateProduct(id, inputDto);

        // Assert
        assertNotNull(actualResult);
        assertEquals(id, actualResult.getId());

        verify(productRepository, times(1)).save(productEntity);
        verify(productMapper).toEntity(any(ProductDto.class));
        verify(productMapper).toDto(savedEntity);
    }


    @DisplayName("Update Product - Failed Scenario")
    @Test
    void test_UpdateProduct_FailureScenarios() throws HahnException {
        Long notFoundId = 100L;
        Long runtimeErrorId = 1L;
        ProductDto inputDto = getProductDto();
        Product productEntity = getProduct();

        when(productRepository.findById(notFoundId)).thenReturn(Optional.empty());

        FunctionalException notFoundException = assertThrows(FunctionalException.class,
                () -> productService.updateProduct(notFoundId, inputDto));
        assertEquals("The product is not found with id 100", notFoundException.getMessage());
        verify(productRepository, times(1)).findById(notFoundId);

        reset(productRepository, productMapper);

        when(productRepository.findById(runtimeErrorId)).thenReturn(Optional.of(productEntity));
        when(productMapper.toDto(productEntity)).thenReturn(getProductDto());

        when(productMapper.toEntity(any(ProductDto.class)))
                .thenThrow(new RuntimeException("Simulated failure"));

        HahnException runtimeException = assertThrows(HahnException.class,
                () -> productService.updateProduct(runtimeErrorId, inputDto));
        assertTrue(runtimeException.getMessage().contains("updateProduct"));
        assertInstanceOf(RuntimeException.class, runtimeException.getCause());

        verify(productRepository, times(1)).findById(runtimeErrorId);
    }

    @DisplayName("Delete Product - Success Scenario")
    @Test
    void test_DeleteProductById_Success() throws HahnException {
        Long id = 1L;

        when(productRepository.checkProductIsExistById(id)).thenReturn(1L);
        doNothing().when(productRepository).deleteById(id);

        ResponseDto response = productService.deleteProductById(id);

        assertNotNull(response);
        assertEquals("Deleted Success", response.getMessage());

        verify(productRepository, times(1)).checkProductIsExistById(id);
        verify(productRepository, times(1)).deleteById(id);
    }

    @DisplayName("Delete Product - Failure Scenarios (Not Found & Unexpected Exception)")
    @Test
    void test_DeleteProductById_FailureScenarios() {
        Long notFoundId = 100L;
        Long errorId = 1L;

        when(productRepository.checkProductIsExistById(notFoundId)).thenReturn(0L);

        FunctionalException notFoundException = assertThrows(FunctionalException.class,
                () -> productService.deleteProductById(notFoundId));
        assertEquals("The product is not found with id 100", notFoundException.getMessage());
        verify(productRepository, times(1)).checkProductIsExistById(notFoundId);
        verify(productRepository, never()).deleteById(anyLong());

        reset(productRepository);

        when(productRepository.checkProductIsExistById(errorId))
                .thenThrow(new RuntimeException("Simulated DB failure"));

        HahnException hahnException = assertThrows(HahnException.class,
                () -> productService.deleteProductById(errorId));
        assertTrue(hahnException.getMessage().contains("deleteProductById"));
        assertInstanceOf(RuntimeException.class, hahnException.getCause());
        assertTrue(hahnException.getCause().getMessage().contains("Simulated DB failure"));

        verify(productRepository, times(1)).checkProductIsExistById(errorId);
        verify(productRepository, never()).deleteById(anyLong());
    }



    private ProductDto getProductDto() {
        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setName("Product For Test Junit Test");
        productDto.setPrice(3.2);
        productDto.setQuantity(3L);
        productDto.setDateCreation(LocalDateTime.now());
        return productDto;
    }


    private Product getProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product For Test Junit Test");
        product.setPrice(3.2);
        product.setQuantity(3L);
        product.setDateCreation(LocalDateTime.now());
        return product;
    }
}