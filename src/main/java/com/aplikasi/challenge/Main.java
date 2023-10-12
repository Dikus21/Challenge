package com.aplikasi.challenge;


import com.aplikasi.challenge.controller.MenuController;
import com.aplikasi.challenge.model.AppState;
import com.aplikasi.challenge.model.service.ListMenuService;
import com.aplikasi.challenge.model.service.OrderService;
import com.aplikasi.challenge.view.MenuView;

public class Main {
    public static void main(String[] args) {
        MenuView menuView = new MenuView();
        OrderService orders = new OrderService();
        ListMenuService listMenu = new ListMenuService();
        AppState state = AppState.STANDBY;
        MenuController controller = new MenuController(listMenu, orders, menuView, state);
        controller.takeOrder();
    }
}