package com.techelevator;

public abstract class VendingItem {
    private String name;
    private String slot;
    private double price;
    private int inventoryCount;

    public VendingItem(String name, String slot, double price){
        this.name = name;
        this.slot = slot;
        this.price = price;
        inventoryCount = 5;

    }

    public String returnMessage(){
        String message = "";
        return message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getInventoryCount() {
        return inventoryCount;
    }

    public void setInventoryCount(int inventoryCount) {
        this.inventoryCount = inventoryCount;
    }
}
