package model;

import lombok.Getter;

import java.util.LinkedHashMap;

@Getter
public class Order {
    private final LinkedHashMap<MenuItem, Integer> orders;
    private int totalQty;
    private int totalPrice;
    private MenuItem item;
    public Order(){
        orders = new LinkedHashMap<>();
    }
    public void addOrder(       int qty){
        orders.merge(item, qty, Integer::sum);
        this.totalQty += qty;
        this.totalPrice += qty * item.getPrice();
    }

    public void selectOrder(MenuItem item){
        this.item = item;
    }

    public void editOrderQty(int qty){
        this.totalPrice += item.getPrice() * (qty - orders.get(item));
        this.totalQty += qty - orders.get(item);
        orders.put(item, qty);
    }

    public void deleteOrder(MenuItem item){
        this.totalPrice -= item.getPrice() * orders.get(item);
        this.totalQty -= orders.get(item);
        orders.remove(item);
    }
}
