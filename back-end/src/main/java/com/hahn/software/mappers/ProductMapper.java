package com.hahn.software.mappers;

import com.hahn.software.dao.entity.Product;
import com.hahn.software.dto.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductMapper {

    ProductMapper MAPPER = Mappers.getMapper(ProductMapper.class);

    ProductDto toDto(Product product);


    Product toEntity(ProductDto productDto);


    List<ProductDto> toDtos(List<Product> product);


    List<Product> toEntities(List<ProductDto> productDto);

}

//ProductMapper.MAPPER.toDto