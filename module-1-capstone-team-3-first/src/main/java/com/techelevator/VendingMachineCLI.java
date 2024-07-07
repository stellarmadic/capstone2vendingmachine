package com.techelevator;

import com.techelevator.view.VendingMenu;

import javax.lang.model.element.NestingKind;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class VendingMachineCLI {

    private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
    private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
    private static final String MAIN_MENU_OPTION_EXIT = "Exit";
    private static final String MAIN_MENU_SECRET_OPTION = "*Sales Report";

    private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
    private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
    private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";

    private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT, MAIN_MENU_SECRET_OPTION};
    private static final String[] PURCHASE_MENU_OPTIONS = {PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FINISH_TRANSACTION};

    private VendingMenu menu;

    private VendingMachine vendingMachine;

    public VendingMachineCLI(VendingMenu menu) {
        this.menu = menu;
        this.vendingMachine = new VendingMachine(new File("vendingmachine.csv"));
    }

    public void run() throws IOException {
        boolean running = true;
        //   List<VendingItem> localInventory = vendingMachine.getInventory();
        List<VendingItem> inventory = vendingMachine.getInventory();
        while (running) {

            String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

            // A switch statement could also be used here.  Your choice.
//            if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {

            if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
                for (VendingItem item : inventory) {
                    if (item.getInventoryCount() == 0) {
                        System.out.printf("%s  %-20s $%4.2f SOLD OUT \n", item.getSlot(), item.getName(), item.getPrice());
                    } else {
                        System.out.printf("%s  %-20s $%4.2f %d \n", item.getSlot(), item.getName(), item.getPrice(), item.getInventoryCount());
                    }

                }
            } else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
                double money = 0;
                while (running) {
                    System.out.printf("Current Balance is $%.2f", money);
                    String choice2 = (String)
                            menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);


                    if (choice2.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {
                        Scanner userInput = new Scanner(System.in);
                        System.out.println("Please enter money in full dollar amounts (ie. 1.00)");

                        try {
                            double addedMoney = Double.parseDouble(userInput.nextLine());
                            if (addedMoney <= 0) {
                                System.out.println("You did not enter a valid amount, please enter a positive dollar amount.");
                            } else {
                                money = addedMoney + money;
                                makeLog(String.format("%s FEED MONEY $%.2f $%.2f",getCurrentDateAndTime(),addedMoney, money));
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input, please enter a positive dollar amount.");
                        }





                    } else if (choice2.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {
                        for (VendingItem item : inventory) {
                            if (item.getInventoryCount() == 0) {
                                System.out.printf("%s  %-20s $%4.2f SOLD OUT \n", item.getSlot(), item.getName(), item.getPrice());
                            } else {
                                System.out.printf("%s  %-20s $%4.2f %d \n", item.getSlot(), item.getName(), item.getPrice(), item.getInventoryCount());
                            }
                        }
                        Scanner userInput = new Scanner(System.in);
                        System.out.println("Please enter a slot from A1 to D4:");
                        String userSelectedSlot = userInput.nextLine();
                        //match slot to inventory options
                        List<String> slotNames = new ArrayList<>();
                        for (VendingItem item : vendingMachine.getInventory()) {



                            slotNames.add(item.getSlot());
                        }

                        if (slotNames.contains(userSelectedSlot)) {

                            VendingItem currentItem = null;
                            for (VendingItem item : inventory) {
                                if (item.getSlot().equals(userSelectedSlot)) {
                                    currentItem = item;
                                }
                            }

                            if (currentItem.getInventoryCount() < 1) {
                                System.out.println("Sorry, this item is sold out!");

                            } else if (currentItem.getPrice() > money) {
                                System.out.println("Sorry, you don't have enough for that item.");
                            } else {
                                vendingMachine.dispense(currentItem);
                                money = money - currentItem.getPrice();
                                inventory = vendingMachine.getUpdatedInventory();
                                makeLog(String.format("%s %s %s $%.2f $%.2f",getCurrentDateAndTime(),currentItem.getName(),currentItem.getSlot(),currentItem.getPrice(),money));

                            }

                        } else {
                            System.out.println("That slot does not exist.");
                        }


                    } else if (choice2.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION)) {
                        makeLog(String.format("%s GIVE CHANGE $%.2f $0.00",getCurrentDateAndTime(),money));
                        System.out.println(String.format("Thank you - your change is $%.2f.",money));
                        System.out.println(makeChange(money));
                        break;

                    }
                }

            } else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
                break;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        VendingMenu menu = new VendingMenu(System.in, System.out);
        VendingMachineCLI cli = new VendingMachineCLI(menu);
        VendingMachine vendingMachine = new VendingMachine(new File("vendingmachine.csv"));
        makeLogFile();
        cli.run();
    }

    public static String makeChange (double money) {
        int amountInCents = (int) Math.round(money * 100);

        int numberOfQuarters = (int) (amountInCents / 25);
        double amountOfChangeLeft = (amountInCents - (numberOfQuarters * 25));
        double numberOfDimes = Math.floor(amountOfChangeLeft / 10);
        amountOfChangeLeft = (amountOfChangeLeft - (numberOfDimes * 10));
        double numberOfNickels = (double) Math.floor(amountOfChangeLeft / 5);
        amountOfChangeLeft = (amountOfChangeLeft - (numberOfNickels * 5));
        double numberOfPennies = (double) Math.floor(amountOfChangeLeft / 1);
        return (String.format("%d Quarters %.0f Dimes %.0f Nickels %.0f Pennies", numberOfQuarters, numberOfDimes, numberOfNickels, numberOfPennies));
    }

    public static void makeLogFile() throws IOException {
        File newFile = new File("log.txt");
        String message = "This is our vending machine log from this session.";
        PrintWriter writer = null;
        if (newFile.exists()) {
            newFile.delete();
            newFile.createNewFile();
            writer = new PrintWriter(new FileWriter(newFile));

        }
        else {
            writer = new PrintWriter(new FileWriter(newFile, true));
        }
        writer.append(message);
        writer.flush();
        writer.close();

    }
    public static void makeLog(String logMessage) throws IOException {
        File newFile = new File("log.txt");

        PrintWriter writer = null;


        if (newFile.exists()) {
            writer = new PrintWriter(new FileWriter(newFile, true));


        }
        else {
            writer = new PrintWriter(new FileWriter(newFile, true));
        }
        writer.append("\n" + logMessage);
        writer.flush();
        writer.close();


    }

    public static String getCurrentDateAndTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
        return currentDateTime.format(formatter);
    }

//    public static void makeSalesReport() throws IOException {
//
//       String fileName = ("SalesLog.txt");
//        File newFile = new File (fileName);
//      //  System.out.println(fileName);
//
//        String message = "Appreciate\nElevate\nParticipate";
//        PrintWriter writer = new PrintWriter(newFile);
//        writer.print(message);
//        writer.flush();
//        writer.close();
//
//
//      //  try( PrintWriter writer = new
//        //        PrintWriter(newFile )){
//      //      writer.print(message);
////        }
//




 //   }


}



