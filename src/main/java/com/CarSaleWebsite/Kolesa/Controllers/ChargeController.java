package com.CarSaleWebsite.Kolesa.Controllers;

import com.CarSaleWebsite.Kolesa.DTO.ChargeRequest;
import com.CarSaleWebsite.Kolesa.Models.utils.Order;
import com.CarSaleWebsite.Kolesa.Models.utils.enums.OrderStatus;
import com.CarSaleWebsite.Kolesa.Repositories.OrderRepository;
import com.CarSaleWebsite.Kolesa.Services.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.naming.AuthenticationException;

@Controller
public class ChargeController {

    @Autowired
    private StripeService paymentsService;
    @Autowired
    private final OrderRepository orderRepository;

    public ChargeController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @PostMapping("/charge/{order}")
    public String charge(@PathVariable(name = "order") Long order_id, ChargeRequest chargeRequest, Model model)
            throws StripeException {
        chargeRequest.setCurrency(ChargeRequest.Currency.KZT);
        Charge charge = null;
        try {
            charge = paymentsService.charge(chargeRequest);

            Order paid=orderRepository.findByID(order_id);
            paid.setStatus(OrderStatus.PAID.name());
            chargeRequest.setDescription("Example charge"+paid.toString());
            orderRepository.save(paid);

        } catch (AuthenticationException e) {
            e.printStackTrace();
        }

        model.addAttribute("id", charge != null ? charge.getId() :null);
        model.addAttribute("status", charge != null ? charge.getStatus() : null);
        model.addAttribute("chargeId", charge != null ? charge.getId() :null);
        model.addAttribute("balance_transaction", charge != null ? charge.getBalanceTransaction() : null);
        return "result";
    }

    @ExceptionHandler(StripeException.class)
    public String handleError(Model model, StripeException ex) {
        model.addAttribute("error", ex.getMessage());
        return "result";
    }
}
