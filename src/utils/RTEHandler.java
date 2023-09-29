package utils;

import view.Formatting;

public class RTEHandler{
    private final Formatting formatting;
    public RTEHandler(){
        formatting = new Formatting();
    }
    public boolean numberChecker(String input){
        try {
            if (Integer.parseInt(input) >= 0) return true;
            else {
                formatting.borderLine();
                System.out.println("Negative value input!");
                formatting.borderLine();
                return false;
            }
        } catch (NumberFormatException nfe) {
            formatting.borderLine();
            System.out.println("Non-Number Input!");
            formatting.borderLine();
            return false;
        }
    }
}
