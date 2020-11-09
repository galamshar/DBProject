package com.CarSaleWebsite.Kolesa.DTO;

public class AjaxResponseBody {
    String message="nothing";
    String result="nothing";

    public AjaxResponseBody(String message) {
        this.message = message;
    }

    public AjaxResponseBody() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
