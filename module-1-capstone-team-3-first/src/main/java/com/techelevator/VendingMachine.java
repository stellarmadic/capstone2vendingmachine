package com.techelevator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class VendingMachine {
    private List<VendingItem> inventory;
    public VendingMachine(File inputFile){
        this.inventory = new ArrayList<VendingItem>();
        try {
            Scanner inputScanner = new Scanner(inputFile);
            while (inputScanner.hasNextLine()) {
                String lineInput = inputScanner.nextLine();
                String [] items = lineInput.split("\\|" );
                //System.out.printf("%s  %-20s  %4.2f 5 \n",items[0],items[1], Double.parseDouble(items[2]));
                //String productItems = String.format("%s  %-20s  %4.2f 5",items[0],items[1], Double.parseDouble(items[2]));
                //System.out.println(productItems);

                // make a chip
                if (items[3].equals("Chip")) {
                    //call chip constructor
                    Chip chipName = new Chip(items[1],items[0], Double.parseDouble(items[2]));
                    //System.out.println("made a chip: " + chipName.getName());
                    inventory.add(chipName);
                }
                // make a candy
                else if (items[3].equals("Candy")) {
                    //call chip constructor
                    Candy candyName = new Candy(items[1],items[0], Double.parseDouble(items[2]));
                    //System.out.println("made a candy: " + candyName.getName());
                    inventory.add(candyName);
                }

                // make a drink
                else if (items[3].equals("Drink")) {
                    //call drink constructor
                    Drink drinkName = new Drink(items[1],items[0], Double.parseDouble(items[2]));
                    //System.out.println("made a drink: " + drinkName.getName());
                    inventory.add(drinkName);
                }

                //make a gum
                if (items[3].equals("Gum")) {
                    //call gum constructor
                    Gum gumName = new Gum(items[1],items[0], Double.parseDouble(items[2]));
                    //System.out.println("made a gum: " + gumName.getName());
                    inventory.add(gumName);
                }

            }

        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }


    public List<VendingItem> getInventory() {
        return inventory;
    }
    public List<VendingItem> getUpdatedInventory() {
        return new ArrayList<>(inventory);
    }


    public void dispense(VendingItem vendingItem) {
        vendingItem.setInventoryCount(vendingItem.getInventoryCount() - 1);
        String message = vendingItem.returnMessage();
        System.out.println(message);
    }

}
