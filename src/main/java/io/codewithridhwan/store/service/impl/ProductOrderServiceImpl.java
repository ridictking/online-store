package io.codewithridhwan.store.service.impl;

import io.codewithridhwan.store.service.ProductOrderService;
import io.codewithridhwan.store.domain.ProductOrder;
import io.codewithridhwan.store.repository.ProductOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProductOrder}.
 */
@Service
@Transactional
public class ProductOrderServiceImpl implements ProductOrderService {

    private final Logger log = LoggerFactory.getLogger(ProductOrderServiceImpl.class);

    private final ProductOrderRepository productOrderRepository;

    public ProductOrderServiceImpl(ProductOrderRepository productOrderRepository) {
        this.productOrderRepository = productOrderRepository;
    }

    @Override
    public ProductOrder save(ProductOrder productOrder) {
        log.debug("Request to save ProductOrder : {}", productOrder);
        return productOrderRepository.save(productOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductOrder> findAll(Pageable pageable) {
        log.debug("Request to get all ProductOrders");
        return productOrderRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ProductOrder> findOne(Long id) {
        log.debug("Request to get ProductOrder : {}", id);
        return productOrderRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductOrder : {}", id);
        productOrderRepository.deleteById(id);
    }
}
