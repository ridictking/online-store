package io.codewithridhwan.store.service.impl;

import io.codewithridhwan.store.service.ShipmentService;
import io.codewithridhwan.store.domain.Shipment;
import io.codewithridhwan.store.repository.ShipmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Shipment}.
 */
@Service
@Transactional
public class ShipmentServiceImpl implements ShipmentService {

    private final Logger log = LoggerFactory.getLogger(ShipmentServiceImpl.class);

    private final ShipmentRepository shipmentRepository;

    public ShipmentServiceImpl(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @Override
    public Shipment save(Shipment shipment) {
        log.debug("Request to save Shipment : {}", shipment);
        return shipmentRepository.save(shipment);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Shipment> findAll(Pageable pageable) {
        log.debug("Request to get all Shipments");
        return shipmentRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Shipment> findOne(Long id) {
        log.debug("Request to get Shipment : {}", id);
        return shipmentRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Shipment : {}", id);
        shipmentRepository.deleteById(id);
    }
}
