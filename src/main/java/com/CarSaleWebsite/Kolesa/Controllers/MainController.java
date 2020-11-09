package com.CarSaleWebsite.Kolesa.Controllers;


import com.CarSaleWebsite.Kolesa.Configuration.SecurityServiceImpl;
import com.CarSaleWebsite.Kolesa.Models.utils.Food;
import com.CarSaleWebsite.Kolesa.Models.utils.Order;
import com.CarSaleWebsite.Kolesa.Models.Usr;
import com.CarSaleWebsite.Kolesa.Repositories.FoodRepository;
import com.CarSaleWebsite.Kolesa.Repositories.OrderRepository;
import com.CarSaleWebsite.Kolesa.Repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class MainController {

    @Autowired
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final FoodRepository foodRepository;
    private final OrderRepository orderRepository;
    private SecurityServiceImpl securityService;

    public MainController(UsersRepository usersRepository, PasswordEncoder passwordEncoder, FoodRepository foodRepository, OrderRepository orderRepository) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.foodRepository = foodRepository;
        this.orderRepository = orderRepository;

    }

    @GetMapping("/")
    public String MainPage(Model model) {
        Iterable<Food> foods = foodRepository.findAll();
        model.addAttribute("foods", foods);
        return "main-page";
    }

    @GetMapping("/login")
    public String LoginPage() {
        return "login-page";
    }

    @GetMapping("/users")
    public String users(Model model) {
        Iterable<Usr> users = usersRepository.findAll();
        model.addAttribute("users", users);
        return "users-page";
    }

    @GetMapping("/profile")
    public String profilePage(Model model, Principal principal) {
        Usr auth = usersRepository.findByUsername(principal.getName());
        String role = auth.getRoles();
        model.addAttribute("authuser", principal.getName());

        List<Order> myOrders = orderRepository.findOrdersByUsername(principal.getName());
        DoubleSummaryStatistics doubleSummaryStatistics = myOrders.stream().collect(Collectors.summarizingDouble(Order::getTotalOrderPrice));


        model.addAttribute("statistics",doubleSummaryStatistics);
        model.addAttribute("role", role);
        return "profile-page";
    }

    @GetMapping("/users/add")
    public String addUserPage() {
        return "add-user";
    }

    @PostMapping("/users/add")
    public String addUserAction(@RequestParam String txtUsername,
                                @RequestParam String txtPassword,
                                @RequestParam String role,
                                @RequestParam String permission) {
        Usr user = new Usr(txtUsername, passwordEncoder.encode(txtPassword), role, permission);
        usersRepository.save(user);
//        securityService.autoLogin(user.getUsername(),user.getPassword());

        return "redirect:/catalog";
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "about-page";
    }



}


