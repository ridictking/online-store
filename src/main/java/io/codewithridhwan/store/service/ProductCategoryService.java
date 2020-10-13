package io.codewithridhwan.store.service;

import io.codewithridhwan.store.domain.ProductCategory;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ProductCategory}.
 */
public interface ProductCategoryService {

    /**
     * Save a productCategory.
     *
     * @param productCategory the entity to save.
     * @return the persisted entity.
     */
    ProductCategory save(ProductCategory productCategory);

    /**
     * Get all the productCategories.
     *
     * @return the list of entities.
     */
    List<ProductCategory> findAll();


    /**
     * Get the "id" productCategory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductCategory> findOne(Long id);

    /**
     * Delete the "id" productCategory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
