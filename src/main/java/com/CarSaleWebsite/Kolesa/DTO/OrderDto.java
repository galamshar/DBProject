package com.CarSaleWebsite.Kolesa.DTO;

public class OrderDto {
    private Long id;

    public OrderDto(Long id) {
        this.id = id;
    }

    public OrderDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "id=" + id +
                '}';
    }
}
