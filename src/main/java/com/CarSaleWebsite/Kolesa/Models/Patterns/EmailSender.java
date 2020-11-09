package com.CarSaleWebsite.Kolesa.Models.Patterns;

public interface EmailSender {
    void send(String receiverEmail, String subject, String body);
}
