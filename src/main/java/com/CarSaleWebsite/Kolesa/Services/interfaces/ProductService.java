package com.CarSaleWebsite.Kolesa.Services.interfaces;

import com.CarSaleWebsite.Kolesa.Models.utils.Food;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Validated
public interface ProductService {

    @NotNull Iterable<Food> getAllProducts();

    Food getProduct(@Min(value = 1L, message = "Invalid product ID.") long id);

    Food save(Food product);

    Food getProduct(String name);
}