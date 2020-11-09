package com.CarSaleWebsite.Kolesa.Controllers;

import com.CarSaleWebsite.Kolesa.Models.utils.Order;
import com.CarSaleWebsite.Kolesa.Models.utils.OrderFood;
import com.CarSaleWebsite.Kolesa.Repositories.DiningTableTrackRepository;
import com.CarSaleWebsite.Kolesa.Repositories.OrderFoodRepository;
import com.CarSaleWebsite.Kolesa.Repositories.OrderRepository;
import com.CarSaleWebsite.Kolesa.Services.interfaces.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;


@Controller
public class OrderController {
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final OrderFoodRepository orderFoodRepository;
    private final DiningTableTrackRepository diningTableTrackRepository;
    public OrderController(OrderService orderService, OrderRepository orderRepository, OrderFoodRepository orderFoodRepository, DiningTableTrackRepository diningTableTrackRepository) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
        this.orderFoodRepository = orderFoodRepository;
        this.diningTableTrackRepository = diningTableTrackRepository;
    }

    @GetMapping("api/orders")
    public String list(Principal principal, Model model) {
        Iterable<Order> orders = orderService.getMyOrder(principal.getName());
        if (orders == null) {
            return "redirect:/catalog";
        } else {
            model.addAttribute("orders", orders);
        }
        return "order-page";

    }
    @PostMapping("/api/orders/remove/{order}")
    public String deleteOrder(Principal principal, @PathVariable(name = "order") Long id){
        Order deletable=orderRepository.findByIDAndUserUsername(id,principal.getName());

        if(deletable.getStatus().equals("WAITING") || deletable.getStatus().equals("WITHCASH") || deletable.getStatus().equals("WITHWAITER")){
            List<OrderFood> orderFoods=orderFoodRepository.findAllByOrderID(id);
            orderFoodRepository.deleteAll(orderFoods);
            orderRepository.delete(deletable);


        }

        return "redirect:/api/orders";
    }

}
