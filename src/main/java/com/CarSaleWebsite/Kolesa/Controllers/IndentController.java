package com.CarSaleWebsite.Kolesa.Controllers;

import com.CarSaleWebsite.Kolesa.Models.utils.Order;
import com.CarSaleWebsite.Kolesa.Models.utils.enums.OrderStatus;
import com.CarSaleWebsite.Kolesa.Repositories.OrderFoodRepository;
import com.CarSaleWebsite.Kolesa.Repositories.OrderRepository;
import com.CarSaleWebsite.Kolesa.Services.interfaces.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/indent")
public class IndentController {
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final OrderFoodRepository orderFoodRepository;

    public IndentController(OrderService orderService, OrderRepository orderRepository, OrderFoodRepository orderFoodRepository) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
        this.orderFoodRepository = orderFoodRepository;
    }

    @RequestMapping(value = "/general", method = RequestMethod.GET)
    public String getGeneralOrders(Model model) {
        List<Order> orderList = orderRepository.findGeneralOrders();
        model.addAttribute("orders", orderList);
        return "indent-page";
    }

    @RequestMapping(value = "/general", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> getDone(@RequestParam Integer order_id, HttpServletRequest request, HttpServletResponse response, Model model) {
        Order order=orderRepository.findByID(Long.valueOf(order_id));
        order.setStatus(OrderStatus.DONE.name());
        orderRepository.save(order);
        return ResponseEntity.ok("Success Beka is MOnster");
    }

}
