package com.aplikasi.challenge.controller;

import com.aplikasi.challenge.model.AppState;
import com.aplikasi.challenge.model.MenuItem;
import com.aplikasi.challenge.model.service.ListMenuService;
import com.aplikasi.challenge.model.service.OrderService;
import com.aplikasi.challenge.utils.RTEHandler;
import com.aplikasi.challenge.view.MenuView;
import com.aplikasi.challenge.view.RecieptView;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuController {
    private final MenuView menuView;
    private @Getter @Setter AppState state;
    private final ListMenuService listMenu;
    private final OrderService orders;

    public MenuController(ListMenuService listMenu, OrderService orders, MenuView menuView, AppState state) {
        this.state = state;
        this.listMenu = listMenu;
        this.orders = orders;
        this.menuView = menuView;
    }

    public void takeOrder() {
        menuView.displayMainMenu(this.listMenu.getMenuItems());
        Scanner sc = new Scanner(System.in);
        while (online()) {
            String input = sc.nextLine();
            RTEHandler rte = new RTEHandler();
            if (!rte.numberFilter(input)) {
                if (state == AppState.QTY_INCREASE || state == AppState.QTY_EDIT) System.out.print("Qty => ");
                else System.out.print("=> ");
            } else appState(Integer.parseInt(input));
        }
        menuView.displayExit();
    }

    public boolean online() {
        return state != AppState.EXIT;
    }

    public void mainMenu(int input) {
        if (input > 0 && input <= listMenu.getMenuItems().size()) {
            menuView.displayFoodOrder(listMenu.getMenuItems().get(input - 1));
            orders.selectOrder(listMenu.getMenuItems().get(input - 1));
            state = AppState.QTY_INCREASE;
        } else if (input == 99) {
            if (orders.getOrders().isEmpty()) {
                menuView.displayNoOrder();
                System.out.print("=> ");
                return;
            }
            menuView.displayPaymentMenu(orders);
            state = AppState.FINALIZATION;
        } else if (input == 0) {
            state = AppState.EXIT;
        } else {
            if (input < 0) menuView.displayNegativeNumber();
            else menuView.displayError();
            System.out.print("=> ");
        }

    }

    public void appState(int input) {
        switch (state) {
            case STANDBY:
                mainMenu(input);
                break;
            case QTY_INCREASE:
                if (orders.addOrder(input)){
                    menuView.displayMainMenu(listMenu.getMenuItems());
                    state = AppState.STANDBY;
                } else System.out.print("Qty => ");
                break;
            case EDIT:
                if (input == 0) {
                    mainMenu(99);
                } else if (input > orders.getOrders().size()) {
                    menuView.displayError();
                    System.out.print("=> ");
                } else if (input < 0) {
                    menuView.displayNegativeNumber();
                    System.out.print("=> ");
                } else {
                    List<MenuItem> keySet = new ArrayList<>(orders.getOrders().keySet());
                    orders.selectOrder(keySet.get(input - 1));
                    menuView.displayEditQty();
                    state = AppState.QTY_EDIT;
                }
                break;
            case QTY_EDIT:
                if (input == 0) orders.deleteOrder(orders.getItem());
                else if (!orders.editOrderQty(input)) {
                    System.out.print("Qty=> ");
                    return;
                }
                if (orders.getTotalQty() == 0) paymentMenu(3);
                else paymentMenu(2);
                break;
            default:
                paymentMenu(input);
        }
    }

    public void paymentMenu(int input) {
        switch (input) {
            case 1:
                RecieptView recieptView = new RecieptView();
                recieptView.showReciept(orders);
                recieptView.printReceipt(orders);
                state = AppState.EXIT;
                break;
            case 2:
                menuView.displayEditOrder(orders);
                state = AppState.EDIT;
                break;
            case 3:
                menuView.displayMainMenu(listMenu.getMenuItems());
                state = AppState.STANDBY;
                break;
            case 0:
                state = AppState.EXIT;
                break;
            default:
                if (input < 0) menuView.displayNegativeNumber();
                else menuView.displayError();
                System.out.print("=> ");
        }
    }
}
