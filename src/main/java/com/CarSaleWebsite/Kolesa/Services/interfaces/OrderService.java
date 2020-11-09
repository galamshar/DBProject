package com.CarSaleWebsite.Kolesa.Services.interfaces;

import com.CarSaleWebsite.Kolesa.Models.utils.Order;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public interface OrderService {

    @NotNull Iterable<Order> getAllOrders();

    Order create(@NotNull(message = "The order cannot be null.") @Valid Order order);

    void update(@NotNull(message = "The order cannot be null.") @Valid Order order);

    List<Order> getMyOrder(String username);
}