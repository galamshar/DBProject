package com.CarSaleWebsite.Kolesa.DTO;

public class OrderProductDto {

private FoodDto product;
private Integer quantity;

    public FoodDto getProduct() {
        return product;
    }

    public void setProduct(FoodDto product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
//Json format of OrderProductDto
// {"product":{"ID":1,"name":"Whopper","description":null,"price":0,"size":"M","image_url":null,"category":null,"id":1},"quantity":3}
