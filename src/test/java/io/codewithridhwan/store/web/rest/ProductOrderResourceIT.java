package io.codewithridhwan.store.web.rest;

import io.codewithridhwan.store.StoreApp;
import io.codewithridhwan.store.domain.ProductOrder;
import io.codewithridhwan.store.domain.Customer;
import io.codewithridhwan.store.repository.ProductOrderRepository;
import io.codewithridhwan.store.service.ProductOrderService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.codewithridhwan.store.domain.enumeration.OrderStatus;
/**
 * Integration tests for the {@link ProductOrderResource} REST controller.
 */
@SpringBootTest(classes = StoreApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProductOrderResourceIT {

    private static final Instant DEFAULT_PLACE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PLACE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final OrderStatus DEFAULT_STATUS = OrderStatus.COMPLETED;
    private static final OrderStatus UPDATED_STATUS = OrderStatus.PENDING;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private ProductOrderRepository productOrderRepository;

    @Autowired
    private ProductOrderService productOrderService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductOrderMockMvc;

    private ProductOrder productOrder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductOrder createEntity(EntityManager em) {
        ProductOrder productOrder = new ProductOrder()
            .placeDate(DEFAULT_PLACE_DATE)
            .status(DEFAULT_STATUS)
            .code(DEFAULT_CODE);
        // Add required entity
        Customer customer;
        if (TestUtil.findAll(em, Customer.class).isEmpty()) {
            customer = CustomerResourceIT.createEntity(em);
            em.persist(customer);
            em.flush();
        } else {
            customer = TestUtil.findAll(em, Customer.class).get(0);
        }
        productOrder.setCustomer(customer);
        return productOrder;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductOrder createUpdatedEntity(EntityManager em) {
        ProductOrder productOrder = new ProductOrder()
            .placeDate(UPDATED_PLACE_DATE)
            .status(UPDATED_STATUS)
            .code(UPDATED_CODE);
        // Add required entity
        Customer customer;
        if (TestUtil.findAll(em, Customer.class).isEmpty()) {
            customer = CustomerResourceIT.createUpdatedEntity(em);
            em.persist(customer);
            em.flush();
        } else {
            customer = TestUtil.findAll(em, Customer.class).get(0);
        }
        productOrder.setCustomer(customer);
        return productOrder;
    }

    @BeforeEach
    public void initTest() {
        productOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductOrder() throws Exception {
        int databaseSizeBeforeCreate = productOrderRepository.findAll().size();
        // Create the ProductOrder
        restProductOrderMockMvc.perform(post("/api/product-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productOrder)))
            .andExpect(status().isCreated());

        // Validate the ProductOrder in the database
        List<ProductOrder> productOrderList = productOrderRepository.findAll();
        assertThat(productOrderList).hasSize(databaseSizeBeforeCreate + 1);
        ProductOrder testProductOrder = productOrderList.get(productOrderList.size() - 1);
        assertThat(testProductOrder.getPlaceDate()).isEqualTo(DEFAULT_PLACE_DATE);
        assertThat(testProductOrder.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProductOrder.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createProductOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productOrderRepository.findAll().size();

        // Create the ProductOrder with an existing ID
        productOrder.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductOrderMockMvc.perform(post("/api/product-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productOrder)))
            .andExpect(status().isBadRequest());

        // Validate the ProductOrder in the database
        List<ProductOrder> productOrderList = productOrderRepository.findAll();
        assertThat(productOrderList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPlaceDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = productOrderRepository.findAll().size();
        // set the field null
        productOrder.setPlaceDate(null);

        // Create the ProductOrder, which fails.


        restProductOrderMockMvc.perform(post("/api/product-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productOrder)))
            .andExpect(status().isBadRequest());

        List<ProductOrder> productOrderList = productOrderRepository.findAll();
        assertThat(productOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = productOrderRepository.findAll().size();
        // set the field null
        productOrder.setStatus(null);

        // Create the ProductOrder, which fails.


        restProductOrderMockMvc.perform(post("/api/product-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productOrder)))
            .andExpect(status().isBadRequest());

        List<ProductOrder> productOrderList = productOrderRepository.findAll();
        assertThat(productOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = productOrderRepository.findAll().size();
        // set the field null
        productOrder.setCode(null);

        // Create the ProductOrder, which fails.


        restProductOrderMockMvc.perform(post("/api/product-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productOrder)))
            .andExpect(status().isBadRequest());

        List<ProductOrder> productOrderList = productOrderRepository.findAll();
        assertThat(productOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductOrders() throws Exception {
        // Initialize the database
        productOrderRepository.saveAndFlush(productOrder);

        // Get all the productOrderList
        restProductOrderMockMvc.perform(get("/api/product-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].placeDate").value(hasItem(DEFAULT_PLACE_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }
    
    @Test
    @Transactional
    public void getProductOrder() throws Exception {
        // Initialize the database
        productOrderRepository.saveAndFlush(productOrder);

        // Get the productOrder
        restProductOrderMockMvc.perform(get("/api/product-orders/{id}", productOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productOrder.getId().intValue()))
            .andExpect(jsonPath("$.placeDate").value(DEFAULT_PLACE_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }
    @Test
    @Transactional
    public void getNonExistingProductOrder() throws Exception {
        // Get the productOrder
        restProductOrderMockMvc.perform(get("/api/product-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductOrder() throws Exception {
        // Initialize the database
        productOrderService.save(productOrder);

        int databaseSizeBeforeUpdate = productOrderRepository.findAll().size();

        // Update the productOrder
        ProductOrder updatedProductOrder = productOrderRepository.findById(productOrder.getId()).get();
        // Disconnect from session so that the updates on updatedProductOrder are not directly saved in db
        em.detach(updatedProductOrder);
        updatedProductOrder
            .placeDate(UPDATED_PLACE_DATE)
            .status(UPDATED_STATUS)
            .code(UPDATED_CODE);

        restProductOrderMockMvc.perform(put("/api/product-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductOrder)))
            .andExpect(status().isOk());

        // Validate the ProductOrder in the database
        List<ProductOrder> productOrderList = productOrderRepository.findAll();
        assertThat(productOrderList).hasSize(databaseSizeBeforeUpdate);
        ProductOrder testProductOrder = productOrderList.get(productOrderList.size() - 1);
        assertThat(testProductOrder.getPlaceDate()).isEqualTo(UPDATED_PLACE_DATE);
        assertThat(testProductOrder.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProductOrder.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingProductOrder() throws Exception {
        int databaseSizeBeforeUpdate = productOrderRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductOrderMockMvc.perform(put("/api/product-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productOrder)))
            .andExpect(status().isBadRequest());

        // Validate the ProductOrder in the database
        List<ProductOrder> productOrderList = productOrderRepository.findAll();
        assertThat(productOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductOrder() throws Exception {
        // Initialize the database
        productOrderService.save(productOrder);

        int databaseSizeBeforeDelete = productOrderRepository.findAll().size();

        // Delete the productOrder
        restProductOrderMockMvc.perform(delete("/api/product-orders/{id}", productOrder.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductOrder> productOrderList = productOrderRepository.findAll();
        assertThat(productOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
