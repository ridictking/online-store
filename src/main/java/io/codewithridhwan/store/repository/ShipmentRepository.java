package io.codewithridhwan.store.repository;

import io.codewithridhwan.store.domain.Invoice;
import io.codewithridhwan.store.domain.Shipment;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;

/**
 * Spring Data  repository for the Shipment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    //Page<Invoice> findAllByOrderCustomerUserLogin(String login, Pageable pageable);
}
