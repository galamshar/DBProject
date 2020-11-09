package com.CarSaleWebsite.Kolesa.Services;

import com.CarSaleWebsite.Kolesa.Models.Patterns.EmailSender;
import com.CarSaleWebsite.Kolesa.Models.Patterns.NullEmailSender;
import com.CarSaleWebsite.Kolesa.Models.utils.Order;
import com.CarSaleWebsite.Kolesa.Repositories.OrderRepository;
import com.CarSaleWebsite.Kolesa.Services.interfaces.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final EmailSender sender = new NullEmailSender();
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Iterable<Order> getAllOrders() {
        return this.orderRepository.findAll();
    }

    @Override
    public Order create(Order order) {
        order.setDateCreated(LocalDate.now());
//        sender.send(order.getUser().getUsername(), "ORDER", "You have ordered products for " + order.getTotalOrderPrice());
        return this.orderRepository.save(order);
    }

    @Override
    public void update(Order order) {
        this.orderRepository.save(order);
    }

    @Override
    public List<Order> getMyOrder(String username) {
       return this.orderRepository.findByUserUsername(username);
    }

}