package com.CarSaleWebsite.Kolesa.Controllers;

import com.CarSaleWebsite.Kolesa.Functions.StringConfigurerFunctions;
import com.CarSaleWebsite.Kolesa.Models.Patterns.FoodBuilder;
import com.CarSaleWebsite.Kolesa.Models.Patterns.FoodBuilderImpl;
import com.CarSaleWebsite.Kolesa.Models.utils.DiningTables;
import com.CarSaleWebsite.Kolesa.Models.utils.Food;
import com.CarSaleWebsite.Kolesa.Repositories.DiningTablesRepository;
import com.CarSaleWebsite.Kolesa.Repositories.FoodRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class FoodController {
    private final FoodRepository foodRepository;
    private final DiningTablesRepository diningTablesRepository;

    public FoodController(FoodRepository foodRepository, DiningTablesRepository diningTablesRepository) {
        this.foodRepository = foodRepository;
        this.diningTablesRepository = diningTablesRepository;
    }

    @GetMapping("/order/{id}")
    public String qrCodeGenerator(Model model, @PathVariable("id") Long id) {
        if (!foodRepository.existsByID(id)) {
            return "redirect:/";
        }
        Food food = foodRepository.findByID(id);
        model.addAttribute("food", food.toString());
        return "QrCodeGen";
    }

    @GetMapping("/food/add")
    public String addFood() {
        return "add-food";
    }

    @PostMapping("/food/add")
    public String addFoodAction(@RequestParam String category,
                                @RequestParam String nme,
                                @RequestParam String description,
                                @RequestParam long price,
                                @RequestParam String size,
                                @RequestParam String image) {
        FoodBuilder builder = new FoodBuilderImpl();

        foodRepository.save( builder
                .withCategory(category)
                .withName(StringConfigurerFunctions.replaceWhiteSpaceWithMinus(nme))
                .withDescription(description)
                .withPrice(price)
                .withSize(size)
                .withImageUrl(image)
                .build());

        return "redirect:/";

    }

    @GetMapping("/{category}/{name}")
    public String detailedViewFood(Model model,
                                   @PathVariable(value = "category") String category,
                                   @PathVariable(value = "name") String name) {
        if (!foodRepository.existsByCategoryAndName(category, name)) {
            return "redirect:/";
        }
        Food food = foodRepository.findFoodByCategoryAndName(category, name);
        model.addAttribute("food", food);

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);

        model.addAttribute("date", strDate);

        return "detailed-view-food";

    }
//
//    @GetMapping("/catalog")
//    public String catalogPage(Model model) {
//        Iterable<Food> foodList = foodRepository.findAll();
//        List<String> categories = foodRepository.findAllCategories();
//        List<String> colors = StringConfigurerFunctions.allColorsinBootstrap();
//
//        model.addAttribute("foodList", foodList);
//
//
//        model.addAttribute("categories", categories);
//        model.addAttribute("colors", colors);
//
//
//        return "catalog-page";
//
//    }

    @GetMapping("/catalog")
    public String catalogPagewithChair(@RequestParam(name = "chair", required = false, defaultValue = "0") Long chair_id, Model model) {
        Iterable<Food> foodList = foodRepository.findAll();
        List<String> categories = foodRepository.findAllCategories();
        List<String> colors = StringConfigurerFunctions.allColorsinBootstrap();

        model.addAttribute("foodList", foodList);

        DiningTables diningTables = diningTablesRepository.findByID(chair_id);
        if (diningTables != null) {
            model.addAttribute("chair", diningTables);
        }
        model.addAttribute("categories", categories);
        model.addAttribute("colors", colors);


        return "catalog-page";

    }

    @GetMapping("/food/edit/{id}")
    public String editFood(Model model, @PathVariable(value = "id") Long id) {
        if (!foodRepository.existsById(id)) {
            return "redirect:/";
        }
        Food food = foodRepository.findByID(id);
        model.addAttribute("food", food);


        return "edit-food";
    }

    @PostMapping("/food/edit/{id}")
    public String editFoodAction(@PathVariable(value = "id") Long id, @RequestParam String category,
                                 @RequestParam String nme,
                                 @RequestParam String description,
                                 @RequestParam long price,
                                 @RequestParam String size,
                                 @RequestParam String image) {

        Food food = foodRepository.findByID(id);
        food.setName(nme);
        food.setCategory(category);
        food.setDescription(description);
        food.setPrice(price);
        food.setImage_url(image);
        food.setSize(size);

        foodRepository.save(food);
        return "redirect:/";

    }

    @PostMapping("/food/remove/{id}")
    public String deleteFood(Model model, @PathVariable(value = "id") Long id) {
        if (foodRepository.existsById(id)) {
            Food food = foodRepository.findByID(id);
            foodRepository.delete(food);
        }
        return "redirect:/";
    }

}
