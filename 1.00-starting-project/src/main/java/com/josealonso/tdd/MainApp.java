package com.josealonso.tdd;

public class MainApp {

    public static void main(String[] args) {
        System.out.println("\nFizzBuzz Numbers:\n");
        for (int i = 1; i <= 100 ; i++) {
            System.out.println(FizzBuzz.compute(i));
        }
    }
}