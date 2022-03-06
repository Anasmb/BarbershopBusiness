package com.example.barberbusiness;

public class ServiceItem {

    private String serviceName;
    private String duration;
    private double price;

    public ServiceItem(String serviceName, String duration, double price) {
        this.serviceName = serviceName;
        this.duration = duration;
        this.price = price;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getDuration() {
        return duration;
    }

    public double getPrice() {
        return price;
    }

}
