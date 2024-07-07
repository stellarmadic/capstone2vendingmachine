package com.techelevator;

public class Candy extends VendingItem {

    public Candy(String name, String slot, double price) {
        super(name, slot, price);
    }

    public String returnMessage(){
        String message = "Munch Munch, Mmm Mmm Good!";
        return message;
    }
}
