package com.CarSaleWebsite.Kolesa.Models.utils;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class DiningTableTrack {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long track_id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "table_id", nullable = false)
    public DiningTables diningTables_id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    public Order order_id;

    public DiningTableTrack(DiningTables diningTables_id, Order order_id) {
        this.diningTables_id = diningTables_id;
        this.order_id = order_id;
    }

    public DiningTableTrack() {

    }

    public Long getTrack_id() {
        return track_id;
    }


    public DiningTables getDiningTables_id() {
        return diningTables_id;
    }

    public void setDiningTables_id(DiningTables diningTables_id) {
        this.diningTables_id = diningTables_id;
    }

    public Order getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Order order_id) {
        this.order_id = order_id;
    }
}
