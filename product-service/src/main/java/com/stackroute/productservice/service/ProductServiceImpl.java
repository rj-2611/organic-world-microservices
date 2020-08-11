package com.stackroute.productservice.service;

import com.stackroute.productservice.errorhandler.exception.ProductExistsException;
import com.stackroute.productservice.errorhandler.exception.ProductNotFoundException;
import com.stackroute.productservice.model.Product;
import com.stackroute.productservice.repository.ProductRedisRepo;
import com.stackroute.productservice.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    public static final String NOT_AVAILABLE_IN_CACHE = "Retrieving products from database - category not available in cache";
    public static final String PRODUCT_NOT_FOUND = "Product not found";
    public static final String PRODUCT_ALREADY_EXISTS = "Product with given id already exists";
    /**
     * **TODO**
     * Create a slf4j Logger for logging messages to standard output
     */
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;

    @Autowired
    private ProductRedisRepo productRedisRepo;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * This method adds a new product to the database.
     * It throws ProductExistsException If the product already exists
     * <p>
     * * TODO **
     * The new product saved should be added to cache with name `products` and
     * the key for the cache should be the id of the product
     * The cached products belonging to the category of saved product should be removed from cache `categoryProducts`
     *
     * @param product
     * @throws ProductExistsException
     */

    @Override
    public Product addNewProduct(Product product) throws ProductExistsException {
     // ** TODO ** log info message NOT_AVAILABLE_IN_CACHE defined above
        logger.info(NOT_AVAILABLE_IN_CACHE);
        if (productRepository.findById(product.getId()).isPresent()) {
            // ** TODO ** log error message here
            logger.info(PRODUCT_ALREADY_EXISTS);
            throw new ProductExistsException(PRODUCT_ALREADY_EXISTS);
        }
        productRedisRepo.saveProduct(product);
        productRedisRepo.deleteCategory(product.getCategory());
        return productRepository.save(product);
    }

    /**
     * This method gets an existing product from the database given a product id.
     * It throws ProductNotFoundException, If the product is not found in database
     * <p>
     * * TODO **
     * The product retrieved should be added to cache with name `products` with key as
     * id of the product
     */

    @Override
    public Product fetchProductByProductId(String productId) throws ProductNotFoundException {
        // ** TODO ** log info message NOT_AVAILABLE_IN_CACHE defined above
        logger.info(NOT_AVAILABLE_IN_CACHE);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND));
        productRedisRepo.saveProduct(product);
        return product;
    }

    /**
     * This method returns a list of products for a given product category.
     * <p>
     * * TODO **
     * The list of products retrieved should be added to cache with name `product-category` with key
     * as `category` of the products
     */
    @Override
    public List<Product> fetchProductsByCategory(String category) {
        // **TODO** log below info message
        //  'Retrieving products from database - products of category not available in cache'
        List<Product> product = productRepository.findByCategoryIgnoreCase(category);;
        productRedisRepo.saveCategory(category,product);
        return product;
    }

    /**
     * This method updates an existing product given a product id and product object. It throws
     * ProductNotFoundException, If the product already exists
     * <p>
     * * TODO **
     * The product updated should be added to cache with name `products` with key as
     * id of the product
     * The cached products belonging to the category of updated product should be
     * removed from cache `categoryProducts`
     */
    @Override
    public Product updateProduct(Product product, String productId) throws ProductNotFoundException {
        // ** TODO ** log info message NOT_AVAILABLE_IN_CACHE defined above
        if (!productRepository.existsById(productId)) {
            logger.info(NOT_AVAILABLE_IN_CACHE);
            throw new ProductNotFoundException(PRODUCT_NOT_FOUND);
        }
        productRedisRepo.saveProduct(product);
        productRedisRepo.deleteCategory(product.getCategory());
        return productRepository.save(product);
    }

    /**
     * This method removes an existing product given a product id . It throws
     * ProductNotFoundException If the product is not found in database
     * <p>
     * * TODO **
     * The product updated should be added to cache with name `products` with key as
     * productId of the product
     */
    @Override
    public Product removeProductById(String productId) throws ProductNotFoundException {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> {
                    // ** TODO ** log error message here
                    logger.info(PRODUCT_NOT_FOUND);
                    return new ProductNotFoundException(PRODUCT_NOT_FOUND);

                });
        productRepository.delete(existingProduct);
        return existingProduct;
    }
}