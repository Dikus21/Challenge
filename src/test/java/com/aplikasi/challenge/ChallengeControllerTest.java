package com.aplikasi.challenge;

import com.aplikasi.challenge.controller.MenuController;
import com.aplikasi.challenge.model.AppState;
import com.aplikasi.challenge.model.service.ListMenuService;
import com.aplikasi.challenge.model.service.OrderService;
import com.aplikasi.challenge.view.MenuView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChallengeControllerTest {
    private MenuController controller;
    private MenuView menuView;
    private OrderService orders;
    private ListMenuService listMenu;
    private AppState state;

    @BeforeEach
    public void setUp() {
        menuView = new MenuView();
        orders = new OrderService();
        listMenu = new ListMenuService();
        state = AppState.STANDBY;
        controller = new MenuController(listMenu, orders, menuView, state);
    }

    @Test
    @DisplayName("Test Main Menu")
    void testMainMenu() {
        //State awal STANDBY
        //Input 1-98 diberikan untuk menu yang ada
        //Nomor input yang dapat diterima berdasarkan menu yang tersedia
        controller.mainMenu(1);
        assertEquals(AppState.QTY_INCREASE, controller.getState());

        controller.setState(AppState.STANDBY);
        controller.mainMenu(5);
        assertEquals(AppState.QTY_INCREASE, controller.getState());

        controller.setState(AppState.STANDBY);
        controller.mainMenu(7);
        assertEquals(AppState.STANDBY, controller.getState());

        controller.setState(AppState.STANDBY);
        controller.mainMenu(-8);
        assertEquals(AppState.STANDBY, controller.getState());
        //Tanpa ada pesanan yang dibuat
        controller.setState(AppState.STANDBY);
        controller.mainMenu(99);
        assertEquals(AppState.STANDBY, controller.getState());
        //Menu tidak bisa pindah ke mode payment atau state FINALIZATION

        //Dengan ada pesanan yang dibuat
        orders.selectOrder(listMenu.getMenuItems().get(0));
        orders.addOrder(5);
        state = AppState.STANDBY;
        controller = new MenuController(listMenu, orders, menuView, state);
        controller.mainMenu(99);
        assertEquals(AppState.FINALIZATION, controller.getState());
        //Menu berindah ke mode payment atau state FINALIZATION

        controller.setState(AppState.STANDBY);
        controller.mainMenu(0);
        assertEquals(AppState.EXIT, controller.getState());

        System.out.println("Test Passed");
    }

    @Test
    @DisplayName("Test Payment Menu")
    void testPaymentMenu() {
        //State awal FINALIZATION
        controller.setState(AppState.FINALIZATION);
        controller.paymentMenu(1);
        assertEquals(AppState.EXIT, controller.getState());

        controller.setState(AppState.FINALIZATION);
        controller.paymentMenu(2);
        assertEquals(AppState.EDIT, controller.getState());

        controller.setState(AppState.FINALIZATION);
        controller.paymentMenu(3);
        assertEquals(AppState.STANDBY, controller.getState());

        controller.setState(AppState.FINALIZATION);
        controller.paymentMenu(90);
        assertEquals(AppState.FINALIZATION, controller.getState());

        controller.setState(AppState.FINALIZATION);
        controller.paymentMenu(-10);
        assertEquals(AppState.FINALIZATION, controller.getState());

        controller.setState(AppState.FINALIZATION);
        controller.paymentMenu(0);
        assertEquals(AppState.EXIT, controller.getState());

        System.out.println("Test Passed");
    }

    @Test
    @DisplayName("Test State Aplikasi")
    void testAppState() {
        //State STANDBY maka input masuk ke method mainMenu
        controller.appState(1);
        assertEquals(AppState.QTY_INCREASE, controller.getState());
        controller.setState(AppState.STANDBY);
        controller.appState(-2);
        assertEquals(AppState.STANDBY, controller.getState());

        //State QTY_INCREASE
        controller.setState(AppState.QTY_INCREASE);
        controller.appState(5);
        assertEquals(AppState.STANDBY, controller.getState());

        controller.setState(AppState.QTY_INCREASE);
        controller.appState(-7);
        assertEquals(AppState.QTY_INCREASE, controller.getState());

        //State EDIT
        controller.setState(AppState.EDIT);
        controller.appState(0);
        assertEquals(AppState.FINALIZATION, controller.getState());

        controller.setState(AppState.EDIT);
        controller.appState(1);
        assertEquals(AppState.QTY_EDIT, controller.getState());

        controller.setState(AppState.EDIT);
        controller.appState(3);
        assertEquals(AppState.EDIT, controller.getState());

        controller.setState(AppState.EDIT);
        controller.appState(-4);
        assertEquals(AppState.EDIT,controller.getState());

        //State QTY_EDIT
        controller.setState(AppState.QTY_EDIT);
        controller.appState(5);
        assertEquals(AppState.EDIT, controller.getState());

        controller.setState(AppState.QTY_EDIT);
        controller.appState(-5);
        assertEquals(AppState.QTY_EDIT, controller.getState());

        //State FINALIZATION akan masuk ke paymenMenu method
        controller.setState(AppState.FINALIZATION);
        controller.appState(1);
        assertEquals(AppState.EXIT, controller.getState());

        controller.setState(AppState.FINALIZATION);
        controller.appState(-4);
        assertEquals(AppState.FINALIZATION, controller.getState());
    }

    public void shutDown() {
        listMenu = null;
        orders = null;
        menuView = null;
        controller = null;
    }

}
