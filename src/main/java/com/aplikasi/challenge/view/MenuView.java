package com.aplikasi.challenge.view;

import com.aplikasi.challenge.model.MenuItem;
import com.aplikasi.challenge.model.service.OrderService;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MenuView {
    private final Formatting format = new Formatting();

    public void displayMainMenu(List<MenuItem> menuItems) {
        format.borderLine();
        System.out.println("Selamat datang di BinarFud");
        format.borderLine();
        System.out.printf("%nMenu Utama :%n");
        menuItems.forEach(item -> {
            String price = format.priceToString(item.getPrice());
            System.out.printf("%d. %-12s | %s%n",
                    menuItems.indexOf(item) + 1, item.getName(), price);
        });
        System.out.printf(
                "99. Pesan dan Bayar%n" +
                        "0. Keluar Aplikasi%n" +
                        "=> "
        );
    }

    public void displayPaymentMenu(OrderService orders) {
        int ppn = orders.getTotalPrice() / 10;
        format.borderLine();
        System.out.println("Konfirmasi & Pembayaran");
        format.borderLine();
        orders.getOrders().forEach((itemOrder, qty) -> {
            String price = format.priceToString(itemOrder.getPrice() * qty);
            String name = itemOrder.getName();
            System.out.printf("%n%-15s %3d   %10s", name, qty, price);
        });
        System.out.printf("%n---------------------------------+%n");
        System.out.printf("SubTotal %-6s %3d   %10s%n", "", orders.getTotalQty(), format.priceToString(orders.getTotalPrice()));
        System.out.printf("PPN 10%%  %-13s%10s%n", "", format.priceToString(ppn));
        System.out.printf("---------------------------------+%n");
        System.out.printf("Total %-16s%10s%n", "", format.priceToString(orders.getTotalPrice() + ppn));
        System.out.printf(
                "%n" +
                        "1. Konfirmasi dan Bayar%n" +
                        "2. Edit order%n" +
                        "3. Kembali kemenu utama%n" +
                        "0. Keluar aplikasi%n" +
                        "=> "
        );
    }

    public void displayFoodOrder(MenuItem food) {
        String price = format.priceToString(food.getPrice());
        format.borderLine();
        System.out.println("Tambah jumlah pesanan anda");
        format.borderLine();
        System.out.printf(
                "%n" +
                        "%-12s | %s%n" +
                        "(input 0 untuk kembali)%n" +
                        "%n" +
                        "Qty => ", food.getName(), price
        );
    }

    public void displayEditOrder(OrderService orders) {
        int ppn = orders.getTotalPrice() / 10;
        format.borderLine();
        System.out.println("Daftar pesanan :");
        format.borderLine();
        AtomicInteger numbering = new AtomicInteger(1);
        orders.getOrders().forEach((orderItem, qty) -> {
            String price = format.priceToString(orderItem.getPrice() * qty);
            String name = orderItem.getName();
            System.out.printf("%n%d. %-15s %3d  %10s", numbering.getAndIncrement(), name, qty, price);
        });
        System.out.printf("%n---------------------------------+%n");
        System.out.printf("SubTotal %-9s %3d  %10s%n", "", orders.getTotalQty(), format.priceToString(orders.getTotalPrice()));
        System.out.printf("PPN 10%% %-16s%10s%n", "", format.priceToString(ppn));
        System.out.printf("---------------------------------+%n");
        System.out.printf("Total %-18s%10s%n", "", format.priceToString(orders.getTotalPrice() + ppn));
        System.out.printf("%n" +
                "0. Kembali ke pembayaran%n" +
                "Pilih pesanan yang ingin diubah%n" +
                "=> ");
    }

    public void displayEditQty() {
        System.out.printf(
                "Masukan jumlah yang diinginkan%n" +
                        "0 Untuk hapus pesanan%n" +
                        "Qty=> "
        );
    }

    public void displayError() {
        format.borderLine();
        System.out.println("Unrecognized input");
        format.borderLine();
    }

    public void displayNegativeNumber() {
        format.borderLine();
        System.out.println("Negative Value Input!");
        format.borderLine();
    }

    public void displayNoOrder() {
        format.borderLine();
        System.out.println("Minimal 1 jumlah pesanan!");
        format.borderLine();
    }

    public void displayExit() {
        format.borderLine();
        System.out.println("Terimakasih dan sampai");
        System.out.println("jumpa!");
        format.borderLine();
    }
}
