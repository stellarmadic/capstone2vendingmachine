package com.techelevator.view;

import com.techelevator.Drink;
import org.junit.Assert;
import org.junit.Test;

public class DrinkTest {
    @Test
    public void test_Correct_Drink_message() {


        Drink drink = new Drink("", "", 0);

        String expectedMessage = "Glug Glug, Chug Chug!";

        String message = drink.returnMessage();

        Assert.assertEquals(expectedMessage, message);
    }
}