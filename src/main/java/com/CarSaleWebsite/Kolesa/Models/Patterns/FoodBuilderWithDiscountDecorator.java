package com.CarSaleWebsite.Kolesa.Models.Patterns;

import com.CarSaleWebsite.Kolesa.Models.utils.Food;

public class FoodBuilderWithDiscountDecorator implements FoodBuilder {
    private static final double MIN_DISCOUNT = 0.0;
    private static final double MAX_DISCOUNT = 1.0;

    private final FoodBuilder inner;
    private final double discount;

    public FoodBuilderWithDiscountDecorator(FoodBuilder inner, double discount) {
        if (discount < MIN_DISCOUNT) {
            throw new IllegalArgumentException("Discount can't be less than " + MIN_DISCOUNT);
        }

        if (discount > MAX_DISCOUNT) {
            throw new IllegalArgumentException("Discount can't be greater than " + MAX_DISCOUNT);
        }

        this.inner = inner;
        this.discount = discount;
    }


    @Override
    public FoodBuilder withPrice(double price) {
        return inner.withPrice(price - (price * discount));
    }

    @Override
    public FoodBuilder withName(String name) {
        return inner.withName(name);
    }

    @Override
    public FoodBuilder withCategory(String name) {
        return inner.withCategory(name);
    }

    @Override
    public FoodBuilder withImageUrl(String imageUrl) {
        return inner.withImageUrl(imageUrl);
    }

    @Override
    public FoodBuilder withDescription(String description) {
        return inner.withDescription(description);
    }

    @Override
    public FoodBuilder withSize(String size) {
        return inner.withSize(size);
    }

    @Override
    public Food build() {
        return inner.build();
    }
}
