package com.example.barberbusiness.items;

public class AppointmentItem {

    private String appointmentID;
    private String customer;
    private String barber;
    private String price;
    private String dateTime;
    private String serviceAt;
    private String address;
    private String services;
    private String status;

    public AppointmentItem(String appointmentID , String customer,String barber ,String price, String dateTime, String serviceAt, String address, String services ,String status) {
        this.appointmentID = appointmentID;
        this.customer = customer;
        this.barber = barber;
        this.price = price;
        this.dateTime = dateTime;
        this.services = services;
        this.address = address;
        this.serviceAt = serviceAt;
        this.status = status;
    }

    public String getAppointmentID() {
        return appointmentID;
    }

    public String getCustomer() {
        return customer;
    }

    public String getPrice() {
        return price;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getServiceAt() {
        return serviceAt;
    }

    public String getAddress() {
        return address;
    }

    public String getServices() {
        return services;
    }

    public String getStatus() {
        return status;
    }

    public String getBarber() {
        return barber;
    }

}