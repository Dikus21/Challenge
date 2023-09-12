public enum FoodBeverage {

    NASI_GORENG(15000, "Nasi Goreng"),
    MIE_GORENG(13000, "Mie Goreng"),
    NASI_AYAM(18000, "Nasi + Ayam"),
    ES_TEH_MANIS(3000, "Es Teh Manis"),
    ES_JERUK(5000, "Es Jeruk");
    private final int price;
    private final String name;

    FoodBeverage(int price, String name){
        this.price = price;
        this.name = name;
    }
    public String getPrices(){
        return name;
    }
    public int getPrice(){
        return price;
    }
    public String getName(){
        return name;
    }
}
