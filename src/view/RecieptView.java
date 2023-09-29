package view;

import lombok.SneakyThrows;
import model.MenuItem;
import model.Order;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class RecieptView {
    private Formatting format = new Formatting();
    public void showReciept(Order orders){
        int ppn = orders.getTotalPrice()/10;
        DateTimeFormatter dtfDay = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter dtfTime = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime currentTime = LocalDateTime.now();
        format.borderLine();
        System.out.println("BinarFud");
        format.borderLine();
        System.out.printf(
                "%n" +
                "Terima kasih sudah memesan%n" +
                "di BinarFud%n" +
                "%n" +
                "Dibawah ini adalah pesanan anda%n");
        for (Map.Entry<MenuItem, Integer> orderItem : orders.getOrders().entrySet()){
            int qty = orderItem.getValue();
            String price = format.priceToString(orderItem.getKey().getPrice() * qty);
            String name = orderItem.getKey().getName();
            System.out.printf("%n%-15s %3d   %10s", name, qty, price);
        }
        System.out.printf("%n---------------------------------+%n");
        System.out.printf("SubTotal %-6s %3d   %10s%n", "", orders.getTotalQty(), format.priceToString(orders.getTotalPrice()));
        System.out.printf("PPN 10%%  %-13s%10s%n", "", format.priceToString(ppn));
        System.out.printf("---------------------------------+%n");
        System.out.printf("Total %-16s%10s%n", "", format.priceToString(orders.getTotalPrice() + ppn));
        System.out.printf("%n" +
                "Pembayaran : BinarCash%n" +
                dtfDay.format(currentTime) +
                "%n" + dtfTime.format(currentTime) +
                "%n%n");
        format.borderLine();
        System.out.println("Simpan struk ini sebagai");
        System.out.println("bukti pembayaran");
        format.borderLine();
    }

    @SneakyThrows
    public void printReceipt(Order orders) {
        PrintStream originalOut = new PrintStream(System.out);
        PrintStream stream = new PrintStream("reciept.csv");
        System.setOut(stream);
        showReciept(orders);
        System.setOut(originalOut);
    }
}
