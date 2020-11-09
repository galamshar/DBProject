package com.CarSaleWebsite.Kolesa.Repositories;

import com.CarSaleWebsite.Kolesa.Models.utils.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderRepository extends CrudRepository<Order,Long> {

    List<Order> findByUserUsername(String user_username);
    Order findByIDAndUserUsername(Long ID, String user_username);
    Order findByID(Long ID);
    @Query("select count(o.ID) from Order o where o.user.username=?1 and o.status='WAITING'")
    int findCountofOrderByUsername(String username);
    @Query("select o from Order o where o.user.username=?1 and o.status='PAID'")
    List<Order> findOrdersByUsername(String username);
    @Query("select o from Order o where o.status='PAID' or o.status='WITHWAITER' or o.status='WITHCASH'")
    List<Order> findGeneralOrders();

}
