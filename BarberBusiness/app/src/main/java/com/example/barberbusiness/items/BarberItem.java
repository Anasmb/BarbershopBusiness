package com.example.barberbusiness.items;

public class BarberItem {

    private int barberID;
    private String barberName;
    private String experience;
    private String nationality;

    public BarberItem(int barberID, String barberName, String experience, String nationality) {
        this.barberID = barberID;
        this.barberName = barberName;
        this.experience = experience;
        this.nationality = nationality;
    }

    public int getBarberID() {
        return barberID;
    }

    public String getBarberName() {
        return barberName;
    }

    public String getExperience() {
        return experience;
    }

    public String getNationality() {
        return nationality;
    }
}
