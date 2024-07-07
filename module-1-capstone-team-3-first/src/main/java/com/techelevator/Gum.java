package com.techelevator;

public class Gum extends VendingItem{
    public Gum(String name, String slot, double price) {
        super(name, slot, price);
    }

    public String getMessage(){
        String message = "Chew Chew, Pop!";
        return message;
    }
}
