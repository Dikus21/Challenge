package com.aplikasi.challenge.view;

public class Formatting {
    public String priceToString(int price) {
        String result = String.valueOf(price);
        int thousand = result.length() - 3;
        while (thousand > 0) {
            result = result.substring(0, thousand) + '.' + result.substring(thousand);
            thousand -= 3;
        }
        return result;
    }

    public void borderLine() {
        System.out.println("==========================");
    }

}
