package com.CarSaleWebsite.Kolesa.DTO;

import com.CarSaleWebsite.Kolesa.Models.Usr;

public class UserDto {
    private Long user_id;

    public UserDto(Long user_id) {
        this.user_id = user_id;
    }

    public UserDto() {
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
    public UserDto(Usr user){
        this.user_id=user.getId();
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "user_id=" + user_id +
                '}';
    }
}
