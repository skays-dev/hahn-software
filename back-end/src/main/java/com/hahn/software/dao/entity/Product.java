package com.hahn.software.dao.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PRODUCT", catalog = "hahn_software")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PRICE",  nullable = false)
    private String price;

    @Column(name = "QUANTITY", nullable = false)
    private String quantity;

    @Column(name = "DATE_CREATION" , nullable = false)
    private LocalDateTime dateCreation;

    @Column(name = "USER_CREATION")
    private Long userCreation;

    @Column(name = "DATE_UPDATE" , nullable = false)
    private LocalDateTime dateUpdate;

    @Column(name = "USER_UPDATE")
    private Long userUpdate;
}
