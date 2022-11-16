package com.josealonso.tdd;

public class FizzBuzz {
    public static String compute(int i) {
        String result = "";

        if (i % 3 == 0) {
            result = "Fizz";
        }
        if (i % 5 == 0) {
            result += "Buzz";
        } else if (!(i % 3 == 0) && !(i % 5 == 0)) {
            // result = String.valueOf(i);
            result = Integer.toString(i);
        }
        return result;
    }

}
