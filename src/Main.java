import controller.MenuController;
import model.AppState;
import model.Order;
import model.service.FoodAndBeverage;
import view.MenuView;


public class Main {
    public static void main(String[] args) {
        MenuView menuView = new MenuView();
        Order orders = new Order();
        FoodAndBeverage listMenu = new FoodAndBeverage();
        AppState state = AppState.STANDBY;
        MenuController controller = new MenuController(listMenu, orders, menuView, state);
        controller.takeOrder();
    }
}