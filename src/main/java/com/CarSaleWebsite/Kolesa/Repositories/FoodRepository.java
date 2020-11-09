package com.CarSaleWebsite.Kolesa.Repositories;

import com.CarSaleWebsite.Kolesa.Models.utils.Food;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends CrudRepository<Food,Long> {
     Food findFoodByName(String name);
     Food findFoodByCategoryAndName(String category,String name);
     Boolean existsByCategoryAndName(String category,String name);
     Boolean existsByName(String name);
     Boolean existsByID(long id);
     Food findByID(Long id);
     List<Food> findByName(String name);
     @Query("select distinct category from Food order by category")
     List<String> findAllCategories();


}
