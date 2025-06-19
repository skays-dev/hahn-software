package com.hahn.software.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductDto {
    private Long id;

    @NotEmpty(message = "Name should be not empty")
    @Size(min = 10, max = 255)
    private String name;

    @Size(min = 10, max = 255)
    private String description;

    @NotEmpty(message = "Price should be not empty")
    private Double price;

    @NotEmpty(message = "Quantity should be not empty")
    private Long quantity;

    private LocalDateTime dateCreation;

    private Long userCreation;

    private LocalDateTime dateUpdate;

    private Long userUpdate;
}
