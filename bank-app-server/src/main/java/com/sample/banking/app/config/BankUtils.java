package com.sample.banking.app.config;

import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class BankUtils {

    Random random = new Random();

    public static long generateNumber(List<Long> existingNumbers) {

        long newNumber;
        do {
            newNumber = Math.abs((long) (Math.random()*Math.pow(10,10)));
        } while (existingNumbers.contains(newNumber));

        return newNumber;
    }

    public static boolean isValidPassword(String password)
    {

        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";

        Pattern p = Pattern.compile(regex);

        if (password == null) {
            return false;
        }

        Matcher m = p.matcher(password);
        return m.matches();
    }


}
