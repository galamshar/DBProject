package com.CarSaleWebsite.Kolesa.Services;

import com.CarSaleWebsite.Kolesa.Models.utils.Food;
import com.CarSaleWebsite.Kolesa.Repositories.FoodRepository;
import com.CarSaleWebsite.Kolesa.Services.interfaces.ProductService;
import com.CarSaleWebsite.Kolesa.Exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final FoodRepository productRepository;

    public ProductServiceImpl(FoodRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Iterable<Food> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Food getProduct(long id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    @Override
    public Food save(Food product) {
        return productRepository.save(product);
    }

    @Override
    public Food getProduct(String name) {
        return productRepository.findFoodByName(name);
    }
}