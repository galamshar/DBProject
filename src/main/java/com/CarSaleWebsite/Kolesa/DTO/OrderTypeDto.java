package com.CarSaleWebsite.Kolesa.DTO;

public class OrderTypeDto {
    public OrderDto orderDto;

    public String type;

    public OrderTypeDto() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public OrderDto getOrderDto() {
        return orderDto;
    }

    public void setOrderDto(OrderDto orderDto) {
        this.orderDto = orderDto;
    }

    public OrderTypeDto(OrderDto orderDto, String type) {
        this.orderDto = orderDto;
        this.type = type;
    }

    @Override
    public String toString() {
        return "OrderTypeDto{" +
                "orderDto=" + orderDto +
                ", type='" + type + '\'' +
                '}';
    }
}
