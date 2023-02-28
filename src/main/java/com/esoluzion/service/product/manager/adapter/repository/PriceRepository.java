package com.esoluzion.service.product.manager.adapter.repository;

import com.esoluzion.service.product.manager.adapter.dto.PricesDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PriceRepository extends CrudRepository<PricesDTO, PricesDTO> {

    @Query(value = "SELECT * FROM PRICES t WHERE t.brand_id= :brand and t.product_id= :product",
            nativeQuery = true)
    List<PricesDTO> findPricesList(
            @Param("brand") Integer brand,
            @Param("product") Integer product);
}