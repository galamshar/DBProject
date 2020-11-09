package com.CarSaleWebsite.Kolesa.Services;

import com.CarSaleWebsite.Kolesa.Models.utils.OrderFood;
import com.CarSaleWebsite.Kolesa.Repositories.OrderFoodRepository;
import com.CarSaleWebsite.Kolesa.Services.interfaces.OrderProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderProductServiceImpl implements OrderProductService {

    private final OrderFoodRepository orderProductRepository;

    public OrderProductServiceImpl(OrderFoodRepository orderProductRepository) {
        this.orderProductRepository = orderProductRepository;
    }

    @Override
    public OrderFood create(OrderFood orderProduct) {
        return this.orderProductRepository.save(orderProduct);
    }
}