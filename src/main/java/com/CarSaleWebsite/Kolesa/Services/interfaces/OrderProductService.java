package com.CarSaleWebsite.Kolesa.Services.interfaces;

import com.CarSaleWebsite.Kolesa.Models.utils.OrderFood;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
public interface OrderProductService {

    OrderFood create(@NotNull(message = "The products for order cannot be null.") @Valid OrderFood orderProduct);
}