package com.CarSaleWebsite.Kolesa.Models.utils;

import javax.persistence.*;

@Entity
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long ID;

    @Column(nullable = false,unique = true)
    public String name;

    @Column(columnDefinition = "text")
    public String description;

    public long price=0;

    public String size="M";

    public String image_url;

    public String category;


    public Food(String name, String description, long price, String size, String image_url, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.size = size;
        this.image_url = image_url;
        this.category = category;
    }

    public Food() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getID() {
        return ID;
    }


    @Override
    public String toString() {
        return "Food{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", size='" + size + '\'' +
                ", image_url='" + image_url + '\'' +
                ", category='" + category + '\'' +
                '}';
    }

}
