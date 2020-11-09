package com.CarSaleWebsite.Kolesa.Controllers;

import com.CarSaleWebsite.Kolesa.DTO.AjaxResponseBody;
import com.CarSaleWebsite.Kolesa.DTO.OrderDto;
import com.CarSaleWebsite.Kolesa.DTO.OrderProductDto;
import com.CarSaleWebsite.Kolesa.DTO.OrderTypeDto;
import com.CarSaleWebsite.Kolesa.Functions.ValidationExistence;
import com.CarSaleWebsite.Kolesa.Functions.interfaces.ValidateExistence;
import com.CarSaleWebsite.Kolesa.Models.utils.DiningTableTrack;
import com.CarSaleWebsite.Kolesa.Models.utils.DiningTables;
import com.CarSaleWebsite.Kolesa.Models.utils.Order;
import com.CarSaleWebsite.Kolesa.Models.utils.OrderFood;
import com.CarSaleWebsite.Kolesa.Models.utils.enums.OrderStatus;
import com.CarSaleWebsite.Kolesa.Repositories.*;
import com.CarSaleWebsite.Kolesa.Services.OrderProductServiceImpl;
import com.CarSaleWebsite.Kolesa.Services.OrderServiceImpl;
import com.CarSaleWebsite.Kolesa.Services.interfaces.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TestContoller {
    private final OrderProductServiceImpl orderProductService;
    private final ProductService productService;
    private final UsersRepository usersRepository;
    private final OrderServiceImpl orderService;
    private final OrderFoodRepository orderFoodRepository;
    private final OrderRepository orderRepository;
    private final DiningTablesRepository diningTablesRepository;
    private final DiningTableTrackRepository diningTableTrackRepository;

    public TestContoller(OrderProductServiceImpl orderProductService, ProductService productService, UsersRepository usersRepository, OrderServiceImpl orderService, OrderFoodRepository orderFoodRepository, OrderRepository orderRepository, DiningTablesRepository diningTablesRepository, DiningTableTrackRepository diningTableTrackRepository) {
        this.orderProductService = orderProductService;
        this.productService = productService;
        this.usersRepository = usersRepository;
        this.orderService = orderService;
        this.orderFoodRepository = orderFoodRepository;
        this.orderRepository = orderRepository;
        this.diningTablesRepository = diningTablesRepository;
        this.diningTableTrackRepository = diningTableTrackRepository;
    }

    @PostMapping("/paytype")
    public ResponseEntity<?> chooseType(@RequestBody OrderTypeDto orderTypeDto, Errors errors, Principal principal) {
        AjaxResponseBody result = new AjaxResponseBody();

        if (errors.hasErrors()) {
            result.setMessage(errors.getAllErrors()
                    .stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(",")));
            return ResponseEntity.badRequest().body(result);
        }
        if(orderTypeDto==null){
            return ResponseEntity.badRequest().body("Order is null");

        }
        if (principal.getName().isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }
        Order order = orderRepository.findByID(orderTypeDto.getOrderDto().getId());
        if (order == null) {
            return ResponseEntity.badRequest().body("No such an order");
        }

        if (orderTypeDto.getType().equals(OrderStatus.WITHCASH.name())) {
            order.setStatus(OrderStatus.WITHCASH.name());
            orderRepository.save(order);
        }else if(orderTypeDto.getType().equals(OrderStatus.WITHWAITER.name())){
            order.setStatus(OrderStatus.WITHWAITER.name());
            orderRepository.save(order);//3-6-9-12-15
        }else {
            return ResponseEntity.badRequest().body("The error occured");
        }

        return ResponseEntity.ok("Accepted "+order.getID());
    }

    @GetMapping("/test")
    public String test() throws JsonProcessingException {
        OrderTypeDto orderTypeDto = new OrderTypeDto();
        orderTypeDto.setOrderDto(new OrderDto((long) 1));
        orderTypeDto.setType("WITHWAITER");


        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(orderTypeDto);
    }


    @PostMapping("/api/test")
    public ResponseEntity<?> create(@RequestBody PurchaseController.OrderForm form, Errors errors, Principal principal, @RequestParam(name = "chair",defaultValue = "1",required = false) int chair) throws JsonProcessingException {
        System.out.println("Table is"+chair);
        AjaxResponseBody result = new AjaxResponseBody();

        if (errors.hasErrors()) {

            result.setMessage(errors.getAllErrors()
                    .stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(",")));

            return ResponseEntity.badRequest().body(result);

        }
        if (orderRepository.findCountofOrderByUsername(principal.getName()) >= 2) {

            return ResponseEntity.badRequest().body("Firstly pay the waiting orders");
        }

        if (principal.getName().isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }
        List<OrderProductDto> formDtos = form.getProductOrders();
        if (!formDtos.isEmpty()) {
            result.setMessage("success");
        } else {
            result.setMessage("error");
            return ResponseEntity.badRequest().body(result);
        }

        ValidateExistence validateExistence = new ValidationExistence();
        validateExistence.validateExistence(formDtos, productService);

        Order order = new Order();
        order.setStatus(OrderStatus.WAITING.name());
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
        if(chair>0){
            DiningTables diningTables=diningTablesRepository.findByID((long) chair);
            diningTableTrackRepository.save(new DiningTableTrack(diningTables,order));
        }
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonString = mapper.writeValueAsString(formDtos);
//        result.setResult(jsonString);

        return ResponseEntity.ok(result);
    }


}
