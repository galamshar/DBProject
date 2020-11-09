package com.CarSaleWebsite.Kolesa.Models.Patterns;

/**
 * Implementation that does nothing.
 *
 * "Null Object" pattern approach.
 */
public class NullEmailSender implements EmailSender {
    @Override
    public void send(String receiverEmail, String subject, String body) {

    }
}
