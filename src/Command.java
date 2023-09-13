import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        mainMenuTemplate();
    }

    public boolean online() {
        return state != AppState.EXIT;
    }

    public void menu(String input) throws IOException {
        if (!numberChecker(input)) {
            if (state == AppState.QTY_INCREASE || state == AppState.QTY_EDIT) System.out.print("Qty => ");
            else System.out.print("=> ");
            return;
        }
        int num = Integer.parseInt(input);
        switch (state) {
            case STANDBY:
                mainMenu(num);
                break;
            case QTY_INCREASE:
                foodQtyInc(num);
                break;
            case FINALIZATION:
                orderFinalizationMenu(num);
                break;
            case EDIT:
                editOrder(num);
                break;
            case QTY_EDIT:
                foodQtyEdit(num);
                break;

        }
    }

    private void mainMenu(int num) {
        if (num > 0 && num <= FoodBeverage.values().length) {
            state = AppState.QTY_INCREASE;
            foodOrder(num);
        } else if (num == 99) {
            if (orderIsEmpty()) return;
            state = AppState.FINALIZATION;
            printOrderTemplate();
        } else if (num == 0) {
            state = AppState.EXIT;
        } else {
            System.out.println("Unidentified input!");
            System.out.print("=> ");
        }
    }

    private void orderFinalizationMenu(int num) throws IOException {
        switch (num) {
            case 1:
                printReceiptTemplate();
                printReceiptToText();
                state = AppState.EXIT;
                break;
            case 2:
                editTemplate();
                state = AppState.EDIT;
                break;
            case 3:
                mainMenuTemplate();
                state = AppState.STANDBY;
                break;
            case 0:
                state = AppState.EXIT;
                break;
            default:
                System.out.println("Unrecognized input!");
                System.out.print("=> ");
                break;
        }
    }

    private void mainMenuTemplate() {
        state = AppState.STANDBY;
        System.out.printf(
                "==========================%n" +
                        "Selamat datang di BinarFud%n" +
                        "==========================%n" +
                        "%n" +
                        "Menu Utama :%n"
        );
        for (FoodBeverage foodList : FoodBeverage.values()) {
            String price = priceToString(foodList.getPrice());
            System.out.printf("%d. %-12s | %s%n", foodList.ordinal() + 1, foodList.getName(), price);
        }
        System.out.printf(
                "99. Pesan dan Bayar%n" +
                        "0. Keluar Aplikasi%n" +
                        "=> "
        );
    }

    private void printReceiptTemplate() {
        int ppn = totalPrice / 10;
        DateTimeFormatter dtfDay = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter dtfTime = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime currentTime = LocalDateTime.now();
        System.out.printf(
                "==========================%n" +
                "BinarFud%n" +
                "==========================%n" +
                "%n" +
                "Terima kasih sudah memesan%n" +
                "di BinarFud%n" +
                "%n" +
                "Dibawah ini adalah pesanan anda%n");
        for (Map.Entry<FoodBeverage, Integer> listOrder : order.entrySet()) {
            int qty = listOrder.getValue();
            String price = priceToString(listOrder.getKey().getPrice() * qty);
            String name = listOrder.getKey().getName();
            System.out.printf("%n%-15s %3d   %10s", name, qty, price);
        }
        System.out.printf("%n---------------------------------+%n");
        System.out.printf("SubTotal %-6s %3d   %10s%n", "", totalQty, priceToString(totalPrice));
        System.out.printf("PPN 10%%  %-13s%10s%n", "", priceToString(ppn));
        System.out.printf("---------------------------------+%n");
        System.out.printf("Total %-16s%10s%n", "", priceToString(totalPrice + ppn));
        System.out.printf("%n" +
                "Pembayaran : BinarCash%n" +
                dtfDay.format(currentTime) +
                "%n" + dtfTime.format(currentTime) +
                "%n%n" +
                "==========================%n" +
                "Simpan struk ini sebagai%n" +
                "bukti pembayaran%n" +
                "==========================%n");
    }

    private void printReceiptToText() throws IOException {
        PrintStream stream = new PrintStream("reciept.txt");
        System.setOut(stream);
        printReceiptTemplate();
    }

    private void printOrderTemplate() {
        int ppn = totalPrice / 10;
        System.out.printf(
                "==========================%n" +
                        "Konfirmasi & Pembayaran%n" +
                        "==========================%n"
        );
        for (Map.Entry<FoodBeverage, Integer> listOrder : order.entrySet()) {
            int qty = listOrder.getValue();
            String price = priceToString(listOrder.getKey().getPrice() * qty);
            String name = listOrder.getKey().getName();
            System.out.printf("%n%-15s %3d   %10s", name, qty, price);
        }
        System.out.printf("%n---------------------------------+%n");
        System.out.printf("SubTotal %-6s %3d   %10s%n", "", totalQty, priceToString(totalPrice));
        System.out.printf("PPN 10%%  %-13s%10s%n", "", priceToString(ppn));
        System.out.printf("---------------------------------+%n");
        System.out.printf("Total %-16s%10s%n", "", priceToString(totalPrice + ppn));
        System.out.printf(
                "%n" +
                        "1. Konfirmasi dan Bayar%n" +
                        "2. Edit order%n" +
                        "3. Kembali kemenu utama%n" +
                        "0. Keluar aplikasi%n" +
                        "=> "
        );
    }

    private void foodOrder(int num) {
        if (num > FoodBeverage.values().length || num < 0) {
            System.out.println("Unregister menu!");
            System.out.print("=> ");
            return;
        } else if (num == 0) {
            mainMenuTemplate();
            return;
        }
        state = AppState.QTY_INCREASE;
        food = FoodBeverage.values()[num - 1];
        String price = priceToString(food.getPrice());
        System.out.printf(
                "==========================%n" +
                        "Berapa jumlah pesanan anda%n" +
                        "==========================%n" +
                        "%n" +
                        "%-12s | %s%n" +
                        "(input 0 untuk kembali)%n" +
                        "%n" +
                        "Qty => ", food.getName(), price
        );
    }

    private void editTemplate() {
        int ppn = totalPrice / 10;
        System.out.printf(
                "==========================%n" +
                        "Daftar pesanan :%n" +
                        "==========================%n");
        int numbering = 1;
        for (Map.Entry<FoodBeverage, Integer> listOrder : order.entrySet()) {
            int qty = listOrder.getValue();
            String price = priceToString(listOrder.getKey().getPrice() * qty);
            String name = listOrder.getKey().getName();
            System.out.printf("%n%d. %-15s %3d  %10s", numbering, name, qty, price);
            numbering++;
        }
        System.out.printf("%n----------------------------------+%n");
        System.out.printf("SubTotal %-9s %3d  %10s%n", "", totalQty, priceToString(totalPrice));
        System.out.printf("PPN 10%% %-16s%10s%n", "", priceToString(ppn));
        System.out.printf("----------------------------------+%n");
        System.out.printf("Total %-18s%10s%n", "", priceToString(totalPrice + ppn));
        System.out.printf("%n" +
                "0. Kembali ke pembayaran%n" +
                "Pilih pesanan yang ingin diubah%n" +
                "=> ");
    }

    private void editOrder(int num) {
        if (num == 0) {
            state = AppState.FINALIZATION;
            printOrderTemplate();
            return;
        } else if (num > order.size()) {
            System.out.println("Unrecognized input!");
            System.out.print("=> ");
            return;
        }
        Set<FoodBeverage> keySet = order.keySet();
        FoodBeverage[] keyArray = keySet.toArray(new FoodBeverage[0]);
        food = keyArray[num - 1];
        state = AppState.QTY_EDIT;
        System.out.printf(
                "Masukan jumlah yang diinginkan%n" +
                "Qty=> "
        );
    }

    private void foodQtyInc(int qty) {
        if (qty > 0) {
            order.merge(food, qty, Integer::sum);
            totalPrice += food.getPrice() * qty;
            totalQty += qty;
        }
        mainMenuTemplate();
    }

    private void foodQtyEdit(int qty) {
        totalPrice += food.getPrice() * (qty - order.get(food));
        totalQty += qty - order.get(food);
        if (qty == 0) {
            order.remove(food);
        } else {
            order.put(food, qty);
        }
        if (orderIsEmpty()) {
            mainMenuTemplate();
            return;
        }
        editTemplate();
        state = AppState.EDIT;
    }

    private String priceToString(int price) {
        String result = String.valueOf(price);
        int thousand = result.length() - 3;
        while (thousand > 0) {
            result = result.substring(0, thousand) + '.' + result.substring(thousand);
            thousand -= 3;
        }
        return result;
    }

    public boolean numberChecker(String input) {
        try {
            if (Integer.parseInt(input) >= 0) return true;
            else {
                System.out.println("Negative value input!");
                return false;
            }
        } catch (NumberFormatException nfe) {
            System.out.println("Unrecognized input");
            return false;
        }
    }

    private boolean orderIsEmpty() {
        if (totalQty == 0) {
            System.out.println("No order listed!");
            System.out.print("=> ");
            return true;
        }
        return false;
    }
}
