package com.CarSaleWebsite.Kolesa.Models.Patterns;

import com.CarSaleWebsite.Kolesa.Models.utils.Food;

public interface FoodBuilder {


    FoodBuilder withPrice(double price);
    FoodBuilder withName(String name);
    FoodBuilder withCategory(String name);
    FoodBuilder withImageUrl(String imageUrl);
    FoodBuilder withDescription(String description);
    FoodBuilder withSize(String size);

    Food build();
}
