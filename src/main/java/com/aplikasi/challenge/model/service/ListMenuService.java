package com.aplikasi.challenge.model.service;

import com.aplikasi.challenge.model.MenuItem;
import com.aplikasi.challenge.utils.RTEHandler;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class ListMenuService {
    private final List<MenuItem> menuItems = new ArrayList<>();
    RTEHandler rte = new RTEHandler();

    public ListMenuService() {
        addMenu("Nasi Goreng", 15000);
        addMenu("Mie Goreng", 13000);
        addMenu("Nasi + Ayam", 18000);
        addMenu("Es Teh Manis", 3000);
        addMenu("Es Jeruk", 5000);
    }

    public void addMenu(String name, Integer price) {
        MenuItem item = new MenuItem(name, price);
        Optional<String> itemName = item.getNameOptional();
        Optional<Integer> itemPrice = item.getPriceOptional();
        if (itemName.isPresent() && itemPrice.isPresent()){
            if (rte.foodNameFilter(name) && rte.negativeValueFilter(price))menuItems.add(item);
        } else {
            System.out.println("Null Value!");
        }
    }
}
