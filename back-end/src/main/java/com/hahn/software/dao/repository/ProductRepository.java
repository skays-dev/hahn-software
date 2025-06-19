package com.hahn.software.dao.repository;

import com.hahn.software.dao.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Query(value = " SELECT COUNT(p.id) FROM Product p " +
            " WHERE p.id = :id ")
    Long checkProductIsExistById(@Param("id") Long id);
}
