package com.example.ru_pizzeria;

public class OrderManager {

    private static int orderCounter = 1;

    public static int getNextOrderNumber() {
        return orderCounter++;
    }
}
