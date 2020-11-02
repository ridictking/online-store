package io.codewithridhwan.store.repository;

import io.codewithridhwan.store.domain.ProductOrder;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProductOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {
    Page<ProductOrder> findAllByCustomerUserLogin(String login, Pageable pageable);
    ProductOrder findOneByIdAndCustomerUserLogin(Long id, String login);
}
