package com.CarSaleWebsite.Kolesa.Controllers;

import com.CarSaleWebsite.Kolesa.DTO.ChargeRequest;
import com.CarSaleWebsite.Kolesa.Models.utils.Order;
import com.CarSaleWebsite.Kolesa.Repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class CheckoutController {

    @Value("${STRIPE_PUBLIC_KEY}")
    private String stripePublicKey;

    @Autowired
    private final OrderRepository orderRepository;

    public CheckoutController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @RequestMapping("/checkout/{order_id}")
    public String checkout(Model model, @PathVariable(name = "order_id") Long order_id, Principal principal) {

        Order check = orderRepository.findByIDAndUserUsername(order_id,principal.getName());
        if (check == null) {
            return "redirect:/";
        }
        int price = (int) (check.getTotalOrderPrice() / 1);

        model.addAttribute("amount", price); // in cents
        model.addAttribute("orderid",check.getID());
        model.addAttribute("stripePublicKey", stripePublicKey);
        model.addAttribute("currency", ChargeRequest.Currency.KZT);
        return "checkout";
    }
}