package com.techelevator.view;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.techelevator.VendingMachineCLI.getCurrentDateAndTime;
import static com.techelevator.VendingMachineCLI.makeChange;
import static org.junit.Assert.assertEquals;

public class VendingMachineCLITest {

    @Test
    public void testMakeChange_ValidAmount_CorrectChange() {
        // Arrange
        double amount = 1.67;
        int expectedQuarters = 6;
        int expectedDimes = 1;
        int expectedNickels = 1;
        int expectedPennies = 2;

        // Act
        String changeOutput = makeChange(amount);

        // Assert
        String expectedOutput = String.format("%d Quarters %d Dimes %d Nickels %d Pennies", expectedQuarters, expectedDimes, expectedNickels, expectedPennies);
        assertEquals(expectedOutput, changeOutput);
    }

    @Test
    public void test_Current_Date_and_Time()    {
        
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
        currentDateTime.format(formatter);
        String expectedOutput = currentDateTime.format(formatter);

        getCurrentDateAndTime();
        String output = getCurrentDateAndTime();

        Assert.assertEquals(expectedOutput, output);
    }
    
}


