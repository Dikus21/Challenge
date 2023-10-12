package com.aplikasi.challenge;

import com.aplikasi.challenge.model.service.ListMenuService;
import com.aplikasi.challenge.model.service.OrderService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChallengeServiceTest {
    private ListMenuService listMenu;
    private OrderService orders;

    @BeforeEach
    public void setUp() {
        listMenu = new ListMenuService();
        orders = new OrderService();
    }

    @Test
    @DisplayName("Test Tambah Menu")
    void testAddMenu() {
        listMenu.addMenu("Ayam Geprek", 12000);
        assertEquals("Ayam Geprek", listMenu.getMenuItems().get(listMenu.getMenuItems().size()-1).getName());
        assertEquals(12000, listMenu.getMenuItems().get(listMenu.getMenuItems().size()-1).getPrice());
        assertEquals(6, listMenu.getMenuItems().size());
        listMenu.addMenu(null, 12000);
        assertEquals(6, listMenu.getMenuItems().size());
        listMenu.addMenu("123ayam !2goreng", 13000);
        assertEquals(6, listMenu.getMenuItems().size());
        listMenu.addMenu("Ayam Goreng", -20000);
        assertEquals(6, listMenu.getMenuItems().size());
        listMenu.addMenu("Ayam Opor + Nasi", 23000);
        assertEquals("Ayam Opor + Nasi", listMenu.getMenuItems().get(listMenu.getMenuItems().size()-1).getName());
        assertEquals(23000, listMenu.getMenuItems().get(listMenu.getMenuItems().size()-1).getPrice());
        assertEquals(7, listMenu.getMenuItems().size());
        System.out.println("Test Passed");
    }

    @Test
    @DisplayName("Test Tambah Order")
    void testAddOrder() {
        orders.selectOrder(listMenu.getMenuItems().get(0));
        orders.addOrder(5);
        assertEquals(1, orders.getOrders().size());
        orders.addOrder(7);
        assertEquals(12, orders.getOrders().get(listMenu.getMenuItems().get(0)));
        orders.addOrder(-5);
        assertEquals(12, orders.getOrders().get(listMenu.getMenuItems().get(0)));
        orders.selectOrder(listMenu.getMenuItems().get(1));
        orders.addOrder(-5);
        assertEquals(1, orders.getOrders().size());
        orders.addOrder(3);
        assertEquals(2, orders.getOrders().size());
        System.out.println("Test Passed");
    }

    @Test
    @DisplayName("Test Hapus Order")
    void testDeleteOrder() {
        orders.selectOrder(listMenu.getMenuItems().get(0));
        orders.addOrder(10);
        assertEquals(1, orders.getOrders().size());
        orders.deleteOrder(listMenu.getMenuItems().get(1));
        assertEquals(1, orders.getOrders().size());
        orders.deleteOrder(listMenu.getMenuItems().get(0));
        assertEquals(0, orders.getOrders().size());
        System.out.println("Test Passed");
    }

    @Test
    @DisplayName("Test Select Order")
    void testSelectOrder() {
        orders.selectOrder(listMenu.getMenuItems().get(0));
        assertEquals(listMenu.getMenuItems().get(0), orders.getItem());
        System.out.println("Test Passed");
    }

    @Test
    @DisplayName("Test Total Qty")
    void testTotalQtyOrder() {
        orders.selectOrder(listMenu.getMenuItems().get(0));
        orders.addOrder(5);
        orders.selectOrder(listMenu.getMenuItems().get(1));
        orders.addOrder(10);
        assertEquals(15, orders.getTotalQty());
        System.out.println("Test Passed");
    }

    @Test
    @DisplayName("Test Total Harga")
    void testTotalPriceOrder() {
        orders.selectOrder(listMenu.getMenuItems().get(0));
        orders.addOrder(5);
        orders.selectOrder(listMenu.getMenuItems().get(1));
        orders.addOrder(3);
        assertEquals(114000, orders.getTotalPrice());
        System.out.println("Test Passed");
    }

    @AfterEach
    public void shutDown() {
        listMenu = null;
        orders = null;
    }
}
