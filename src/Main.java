import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        Command app = new Command();
        while (app.online()){
            app.menu(sc.nextLine());
        }
    }
}