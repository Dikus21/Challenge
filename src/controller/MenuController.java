package controller;

import model.AppState;
import model.MenuItem;
import model.Order;
import model.service.FoodAndBeverage;
import utils.RTEHandler;
import view.MenuView;
import view.RecieptView;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuController {
    MenuView menuView;
    AppState state;
    FoodAndBeverage listMenu;
    Order orders;

    public MenuController(FoodAndBeverage listMenu, Order orders, MenuView menuView, AppState state){
        this.state = state;
        this.listMenu = listMenu;
        this.orders = orders;
        this.menuView = menuView;
        menuView.displayMainMenu(this.listMenu.getMenuItems());
    }

    public void takeOrder() {
        Scanner sc = new Scanner(System.in);
        while (online()){
            setState(sc.nextLine());
        }
        menuView.displayExit();
    }
    public boolean online(){
        return state != AppState.EXIT;
    }

    public void mainMenu(int input){
        if (input > 0 && input <= listMenu.getMenuItems().size()) {
            menuView.displayFoodOrder(listMenu.getMenuItems().get(input-1));
            orders.selectOrder(listMenu.getMenuItems().get(input-1));
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
            menuView.displayError();
            System.out.print("=> ");
        }

    }

    public void setState(String input) {
        RTEHandler rte = new RTEHandler();
        if (!rte.numberChecker(input)) {
            if (state == AppState.QTY_INCREASE || state == AppState.QTY_EDIT) System.out.print("Qty => ");
            else System.out.print("=> ");
            return;
        }
        int inputNumber = Integer.parseInt(input);
        switch (state){
            case STANDBY:
                mainMenu(inputNumber);
                break;
            case QTY_INCREASE:
                if (inputNumber > 0) orders.addOrder(inputNumber);
                menuView.displayMainMenu(listMenu.getMenuItems());
                state = AppState.STANDBY;
                break;
            case EDIT:
                if (inputNumber == 0){
                    mainMenu(99);
                    return;
                } else if (inputNumber > orders.getOrders().size()) {
                    menuView.displayError();
                    System.out.print("=> ");
                    return;
                }
                List<MenuItem> keySet = new ArrayList<>(orders.getOrders().keySet());
                orders.selectOrder(keySet.get(inputNumber-1));
                menuView.displayEditQty();
                state = AppState.QTY_EDIT;
                break;
            case QTY_EDIT:
                if (inputNumber == 0) orders.deleteOrder(orders.getItem());
                else orders.editOrderQty(inputNumber);
                if (orderIsEmpty()) paymentMenu(3);
                else paymentMenu(2);
                break;
            default:
                paymentMenu(inputNumber);
        }
    }

    public void paymentMenu(int input) {
        switch (input){
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
                menuView.displayError();
        }
    }

    public boolean orderIsEmpty(){
        return orders.getTotalQty() == 0;
    }
}
