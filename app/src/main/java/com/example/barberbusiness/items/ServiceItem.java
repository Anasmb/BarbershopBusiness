package com.example.barberbusiness.items;

public class ServiceItem {

    private int serviceID;
    private String serviceName;
    private double price;

    public ServiceItem(int serviceID , String serviceName, double price) {
        this.serviceID = serviceID;
        this.serviceName = serviceName;
        this.price = price;
    }

    public int getServiceID() {
        return serviceID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getPrice() {
        return price;
    }

}
