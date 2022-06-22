package com.example.barberbusiness.items;

public class GalleryItem {

    private int imageID;
    private String image;

    public GalleryItem(int imageID, String image) {
        this.imageID = imageID;
        this.image = image;
    }

    public int getImageID() {
        return imageID;
    }

    public String getImage() {
        return image;
    }
}
