package com.aplikasi.challenge.utils;

import com.aplikasi.challenge.view.Formatting;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RTEHandler {
    private final Formatting formatting;

    public RTEHandler() {
        formatting = new Formatting();
    }

    public boolean numberFilter(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException nfe) {
            formatting.borderLine();
            System.out.println("Non-Number Input!");
            formatting.borderLine();
            return false;
        }
    }

    public boolean negativeValueFilter(int input) {
        if (input < 0) {
            formatting.borderLine();
            System.out.println("Negative Value Input!");
            formatting.borderLine();
            return false;
        } else return true;
    }

    public boolean foodNameFilter(String input) {
        String regex = "^([A-Z][a-z]+)(\\s+\\+\\s+[A-Z][a-z]+|\\s+[A-Z][a-z]+)*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (!matcher.find()) {
            formatting.borderLine();
            System.out.println("Wrong name format");
            System.out.println("Character only and + symbol");
            System.out.println("in between words");
            formatting.borderLine();
            return false;
        } else return true;
    }
}
