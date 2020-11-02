package io.codewithridhwan.store.service.impl;

import io.codewithridhwan.store.security.AuthoritiesConstants;
import io.codewithridhwan.store.security.SecurityUtils;
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
        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){
            return productOrderRepository.findAll(pageable);
        }else{
            return productOrderRepository.findAllByCustomerUserLogin(SecurityUtils.getCurrentUserLogin().get(), pageable);
        }
    }


    @Transactional(readOnly = true)
    public ProductOrder findOne(Long id) {
        log.debug("Request to get ProductOrder : {}", id);
        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){
            return productOrderRepository.findById(id).get();
        }else{
            return productOrderRepository.findOneByIdAndCustomerUserLogin(id,SecurityUtils.getCurrentUserLogin().get());
        }
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductOrder : {}", id);
        productOrderRepository.deleteById(id);
    }
}
