package com.hahn.software.criteria;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ProductCriteria {
    private Long id;

    private String name;

    private String description;

    private String price;

    private String quantity;

    private LocalDateTime dateCreation;

    private Long userCreation;

    private LocalDateTime dateUpdate;

    private Long userUpdate;

    private LocalDate startDate;

    private LocalDate endDate;
}
