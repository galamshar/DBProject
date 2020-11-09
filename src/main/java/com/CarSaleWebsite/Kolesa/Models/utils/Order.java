package com.CarSaleWebsite.Kolesa.Models.utils;


import com.CarSaleWebsite.Kolesa.Models.Usr;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateCreated;

    private String status;

    @JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy = "order")
    private List<OrderFood> orderProducts = new ArrayList<>();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Usr user;


    @Transient
    public Double getTotalOrderPrice() {
        double sum = 0D;
        List<OrderFood> orderProducts = getOrderProducts();
        for (OrderFood op : orderProducts) {
            sum += op.getTotalPrice();
        }
        return sum;
    }

    @Transient
    public int getNumberOfProducts() {
        return this.orderProducts.size();
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderFood> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderFood> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public Long getID() {
        return ID;
    }

    @Transient
    public Usr getUser() {
        return user;
    }


    public void setUser(Usr user) {
        this.user = user;
    }

    public String getOrderProductsString() {
        StringBuilder empty= new StringBuilder();
        for (OrderFood food : orderProducts) {
         empty.append(food.toString());
        }
        return empty.toString();
    }

    @Override
    public String toString() {
        return "Order{" +
                "order_id=" + ID +
                ", dateCreated=" + dateCreated +
                ", status='" + status + '\'' +
                '}';
    }


}
