package model.service;

import lombok.Getter;
import model.MenuItem;

import java.util.ArrayList;
import java.util.List;
@Getter
public class FoodAndBeverage {
    private final List<MenuItem> menuItems = new ArrayList<>();
    public FoodAndBeverage(){
        addMenu("Nasi Goreng", 15000);
        addMenu("Mie Goreng", 13000);
        addMenu("Nasi + Ayam", 18000);
        addMenu("Es Teh Manis", 3000);
        addMenu("Es Jeruk", 5000);
    }

    public void addMenu(String name, int price){
        menuItems.add(new MenuItem(name, price));
    }
}
