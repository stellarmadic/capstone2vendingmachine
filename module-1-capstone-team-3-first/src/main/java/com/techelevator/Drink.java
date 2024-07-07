package com.techelevator;

public class Drink extends VendingItem{
    public Drink(String name, String slot, double price) {
        super(name, slot, price);
    }

    public String returnMessage() {
        String message = "Glug Glug, Chug Chug!";
        return message;
    }
}
