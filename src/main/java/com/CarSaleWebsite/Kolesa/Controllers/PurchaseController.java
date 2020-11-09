package com.CarSaleWebsite.Kolesa.Controllers;

import com.CarSaleWebsite.Kolesa.DTO.OrderProductDto;
import com.CarSaleWebsite.Kolesa.Models.utils.Order;
import com.CarSaleWebsite.Kolesa.Models.utils.OrderFood;
import com.CarSaleWebsite.Kolesa.Models.utils.enums.OrderStatus;
import com.CarSaleWebsite.Kolesa.Repositories.OrderFoodRepository;
import com.CarSaleWebsite.Kolesa.Repositories.UsersRepository;
import com.CarSaleWebsite.Kolesa.Services.interfaces.OrderProductService;
import com.CarSaleWebsite.Kolesa.Services.interfaces.OrderService;
import com.CarSaleWebsite.Kolesa.Services.interfaces.ProductService;
import com.CarSaleWebsite.Kolesa.Exceptions.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class PurchaseController {

    private final ProductService productService;
    private final OrderService orderService;
    private final OrderProductService orderProductService;
    private final UsersRepository usersRepository;
    private final OrderFoodRepository orderFoodRepository;

    public PurchaseController(ProductService productService, OrderService orderService, OrderProductService orderProductService, UsersRepository usersRepository, OrderFoodRepository orderFoodRepository) {
        this.productService = productService;
        this.orderService = orderService;
        this.orderProductService = orderProductService;
        this.usersRepository = usersRepository;
        this.orderFoodRepository = orderFoodRepository;
    }



    @PostMapping("/api/orders")
    public ResponseEntity<Order> create(@RequestBody OrderForm form,Principal principal) {
        List<OrderProductDto> formDtos = form.getProductOrders();
        validateProductsExistence(formDtos);
        Order order = new Order();
        order.setStatus(OrderStatus.PAID.name());
        order.setUser(usersRepository.findByUsername(principal.getName()));
        order = this.orderService.create(order);

        List<OrderFood> orderProducts = new ArrayList<>();
        for (OrderProductDto dto : formDtos) {
            orderProducts
                    .add(orderProductService
                            .create(new OrderFood(
                                    order, productService.getProduct(
                                            dto.getProduct().getName()), dto.getQuantity())));
        }

        order.setOrderProducts(orderProducts);
        this.orderService.update(order);
        orderProducts.forEach(orderFoodRepository::save);

        String uri = ServletUriComponentsBuilder
                .fromCurrentServletMapping()
                .path("/orderlist/{id}")
                .buildAndExpand(order.getID())
                .toString();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", uri);

        return new ResponseEntity<>(order, headers, HttpStatus.CREATED);
    }

    private void validateProductsExistence(List<OrderProductDto> orderProducts) {
        List<OrderProductDto> list = orderProducts
                .stream()
                .filter(op -> Objects.isNull(productService.getProduct(op.getProduct().getName()))).collect(Collectors.toList());




        if (!CollectionUtils.isEmpty(list)) {
            throw new ResourceNotFoundException("Product not found");
        }
    }

    public static class OrderForm {

        private List<OrderProductDto> productOrders;

        public List<OrderProductDto> getProductOrders() {
            return productOrders;
        }

        public void setProductOrders(List<OrderProductDto> productOrders) {
            this.productOrders = productOrders;
        }
    }
}