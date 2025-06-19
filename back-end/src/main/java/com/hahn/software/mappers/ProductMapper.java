package com.hahn.software.mappers;

import com.hahn.software.dao.entity.Product;
import com.hahn.software.dto.ProductDto;
import com.hahn.software.mappers.Component.AbstractDtoMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper extends AbstractDtoMapper<ProductDto, Product> {

}