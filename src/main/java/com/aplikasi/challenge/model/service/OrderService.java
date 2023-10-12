package com.aplikasi.challenge.model.service;

import com.aplikasi.challenge.model.MenuItem;
import com.aplikasi.challenge.utils.RTEHandler;
import lombok.Getter;

import java.util.LinkedHashMap;
@Getter
public class OrderService {
    private final LinkedHashMap<MenuItem, Integer> orders;
    private MenuItem item;
    private final RTEHandler rte;

    public OrderService() {
        orders = new LinkedHashMap<>();
        rte = new RTEHandler();
    }

    public boolean addOrder(int qty) {
        if (rte.negativeValueFilter(qty)) {
            orders.merge(item, qty, Integer::sum);
            return true;
        } else return false;
    }

    public void selectOrder(MenuItem item) {
        this.item = item;
    }

    public boolean editOrderQty(int qty) {
        if (rte.negativeValueFilter(qty)) {
            orders.put(item, qty);
            return true;
        } return false;
    }

    public void deleteOrder(MenuItem item) {
        boolean deleted = orders.keySet().removeIf(key -> key == item);
        if (!deleted) System.out.println("Item Not Found!");
        else orders.remove(item);
    }

    public int getTotalPrice() {
        return orders.entrySet()
                .stream()
                .mapToInt(order -> order.getKey().getPrice() * order.getValue())
                .sum();
    }

    public int getTotalQty() {
        return orders.values()
                .stream().mapToInt(Integer::intValue).sum();
    }

}
