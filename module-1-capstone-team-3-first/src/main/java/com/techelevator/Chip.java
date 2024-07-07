package com.techelevator;

public class Chip extends VendingItem{
    public Chip(String name, String slot, double price) {
        super(name, slot, price);
    }

    public String returnMessage(){
        String message = "Crunch Crunch, It's Yummy!";
        return message;
    }
}
