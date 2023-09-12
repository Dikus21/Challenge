import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Command {
    private AppState state;
    private FoodBeverage food;
    private final LinkedHashMap<FoodBeverage, Integer> order = new LinkedHashMap<>();
    private int totalPrice;
    private int totalQty;

    Command() {
        mainMenu();
    }
    public boolean online(){
        return state != AppState.EXIT;
    }
    public void menu(String input) throws IOException {
        if(!numberChecker(input)){
            if (state == AppState.QTY_INCREASE || state == AppState.QTY_DECREASE) System.out.print("Qty => ");
            else System.out.print("=> ");
            return;
        }
        int num = Integer.parseInt(input);
        switch (state){
            case STANDBY:
                setState(num);
                break;
            case QTY_INCREASE:
                foodQtyInc(num);
                break;
            case FINALIZATION:
                orderFinalization(num);
                break;
            case EDIT:
                editChoice(num);
                break;
            case QTY_DECREASE:
                foodQtyDec(num);
                break;

        }
    }
    public void setState(int num){
        if(num > 0 && num <= FoodBeverage.values().length){
            state = AppState.QTY_INCREASE;
            foodChoice(num);
        } else if (num == 99){
            state = AppState.FINALIZATION;
            printOrder();
        } else if (num == 0){
            state = AppState.EXIT;
        } else {
            System.out.println("Unidentified input!");
            System.out.print("=> ");
        }
    }
    public boolean numberChecker(String input){
        try {
            if(Integer.parseInt(input) >= 0)return true;
            else {
                System.out.println("Negative value input!");
                return false;
            }
        } catch (NumberFormatException nfe){
            System.out.println("Unrecognized input");
            return false;
        }
    }
    public void orderFinalization(int num) throws IOException {
        switch (num){
            case 1:
                printReceipt();
                printReceiptToText();
                state = AppState.EXIT;
                break;
            case 2:
                edit();
                state = AppState.EDIT;
                break;
            case 3:
                mainMenu();
                state = AppState.STANDBY;
                break;
            case 0:
                state = AppState.EXIT;
                break;
            default:
                System.out.println("Unrecognized input!");
                break;
        }
    }
    public void printReceiptToText() throws IOException {
        PrintStream stream = new PrintStream("reciept.txt");
        System.setOut(stream);
        printReceipt();
    }
    public void printReceipt(){
        System.out.printf("==========================%n" +
                "BinarFud%n" +
                "==========================%n" +
                "%n" +
                "Terima kasih sudah memesan%n" +
                "di BinarFud%n" +
                "%n" +
                "Dibawah ini adalah pesanan anda%n");
        int numbering = 1;
        for(Map.Entry<FoodBeverage, Integer> listOrder : order.entrySet()){
            int qty = listOrder.getValue();
            String price = priceToString(listOrder.getKey().getPrice() * qty);
            String name = listOrder.getKey().getName();
            System.out.printf("%n%d. %-15s %3d  %10s", numbering, name, qty, price);
            numbering++;
        }
        System.out.printf("%n----------------------------------+%n" +
                "SubTotal %-9s %3d  %10s%n", "", totalQty, priceToString(totalPrice));
        int ppn = totalPrice/10;
        System.out.printf("PPN 10%% %-16s%10s%n", "", priceToString(ppn));
        System.out.printf("----------------------------------+%n" +
                "Total %-18s%10s%n", "", priceToString(totalPrice+ppn));
        System.out.printf("%n" +
                "Pembayaran : BinarCash%n" +
                "%n" +
                "==========================%n" +
                "Simpan struk ini sebagai%n" +
                "bukti pembayaran%n" +
                "==========================%n");
    }
    public void printOrder(){
        System.out.printf("==========================%n" +
                "Konfirmasi & Pembayaran%n" +
                "==========================%n");
        for(Map.Entry<FoodBeverage, Integer> listOrder : order.entrySet()){
            int qty = listOrder.getValue();
            String price = priceToString(listOrder.getKey().getPrice() * qty);
            String name = listOrder.getKey().getName();
            System.out.printf("%n%-15s %3d   %10s", name, qty, price);
        }
        System.out.printf("%n---------------------------------+%n" +
                "SubTotal %-6s %3d   %10s%n", "", totalQty, priceToString(totalPrice));
        int ppn = totalPrice/10;
        System.out.printf("PPN 10%%  %-13s%10s%n", "", priceToString(ppn));
        System.out.printf("---------------------------------+%n" +
                "Total %-16s%10s%n", "", priceToString(totalPrice+ppn));
        System.out.printf("%n" +
                "1. Konfirmasi dan Bayar%n" +
                "2. Edit order%n" +
                "3. Kembali kemenu utama%n" +
                "0. Keluar aplikasi%n" +
                "=> ");
    }
    public void mainMenu() {
        state = AppState.STANDBY;
        System.out.printf("==========================%n" +
                        "Selamat datang di BinarFud%n" +
                        "==========================%n" +
                        "%n" +
                        "Menu Utama :%n");
        for (FoodBeverage foodList : FoodBeverage.values()){
            String price = priceToString(foodList.getPrice());
            System.out.printf("%d. %-12s | %s%n", foodList.ordinal()+1, foodList.getName(), price);
        }
        System.out.printf("99. Pesan dan Bayar%n" +
                        "0. Keluar Aplikasi%n" +
                        "=> ");
    }

    public void foodChoice(int num){
        if(num > FoodBeverage.values().length || num < 0){
            System.out.println("Unregister menu!");
            System.out.print("=> ");
            return;
        } else if (num == 0){
            mainMenu();
            return;
        }
        state = AppState.QTY_INCREASE;
        food = FoodBeverage.values()[num - 1];
        String price = priceToString(food.getPrice());
        System.out.printf("==========================%n" +
                "Berapa jumlah pesanan anda%n" +
                "==========================%n" +
                "%n" +
                "%-12s | %s%n" +
                "(input 0 untuk kembali)%n" +
                "%n" +
                "Qty => ", food.getName(), price);
    }
    public void foodQtyInc(int qty){
        if (qty > 0){
            order.merge(food, qty, Integer::sum);
            totalPrice += food.getPrice() * qty;
            totalQty += qty;
        }
        mainMenu();
    }
    public String priceToString(int price){
        String result = String.valueOf(price);
        int thousand = result.length() - 3;
        while (thousand > 0){
            result = result.substring(0, thousand) + '.' + result.substring(thousand);
            thousand -= 3;
        }
        return result;
    }
    public void edit(){
        System.out.printf("==========================%n" +
                "Pilih pesanan yang diubah :%n" +
                "==========================%n");
        int numbering = 1;
        for(Map.Entry<FoodBeverage, Integer> listOrder : order.entrySet()){
            int qty = listOrder.getValue();
            String price = priceToString(listOrder.getKey().getPrice() * qty);
            String name = listOrder.getKey().getName();
            System.out.printf("%n%d. %-15s %3d  %10s", numbering, name, qty, price);
            numbering++;
        }
        System.out.printf("%n----------------------------------+%n" +
                "SubTotal %-9s %3d  %10s%n", "", totalQty, priceToString(totalPrice));
        int ppn = totalPrice/10;
        System.out.printf("PPN 10%% %-16s%10s%n", "", priceToString(ppn));
        System.out.printf("----------------------------------+%n" +
                "Total %-18s%10s%n", "", priceToString(totalPrice+ppn));
        System.out.printf("%n" +
                "0. Kembali ke pembayaran%n" +
                "=> ");
    }
    public void editChoice(int num){
        if(num == 0){
            state = AppState.FINALIZATION;
            printOrder();
            return;
        }
        Set<FoodBeverage> keySet = order.keySet();
        FoodBeverage[] keyArray = keySet.toArray(new FoodBeverage[0]);
        food = keyArray[num-1];
        state = AppState.QTY_DECREASE;
        System.out.printf("Masukan jumlah yang diinginkan%n" +
                "Qty=> ");
    }
    public void foodQtyDec(int qty){
        totalPrice += food.getPrice() * (qty - order.get(food));
        totalQty += qty - order.get(food);
        if(qty == 0){
            order.remove(food);
        } else {
            order.put(food, qty);
        }
        edit();
        state = AppState.EDIT;
    }

    /*public void foodMenu() {
        System.out.printf("==========================%n" +
                "Menu makanan di BinarFud%n" +
                "==========================%n" +
                "%n" +
                "Silahkan pilih makanan :%n");
        for (FoodBeverage foodList : FoodBeverage.values()){
            String price = priceToString(foodList.getPrice());
            System.out.printf("%d. %-12s | %s%n", foodList.ordinal()+1, foodList.getName(), price);
        }
        System.out.printf("0. Kembali kemenu utama%n" +
                "%n=> ");
    }*/
}
