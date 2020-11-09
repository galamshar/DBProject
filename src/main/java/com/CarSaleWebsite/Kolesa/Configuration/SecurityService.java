package com.CarSaleWebsite.Kolesa.Configuration;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}