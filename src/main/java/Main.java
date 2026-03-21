import java.util.*;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Select language: 1=EN, 2=FI, 3=SV, 4=JP");
        int choice = scanner.nextInt();

        Locale locale = switch (choice) {
            case 2 -> new Locale("fi", "FI");
            case 3 -> new Locale("sv", "SE");
            case 4 -> new Locale("ja", "JP");
            default -> new Locale("en", "US");
        };

        ResourceBundle bundle = ResourceBundle.getBundle("MessagesBundle", locale);

        System.out.println(bundle.getString("enter_items"));
        int items = scanner.nextInt();

        double total = 0;

        for (int i = 0; i < items; i++) {
            System.out.println(bundle.getString("enter_price"));
            double price = scanner.nextDouble();

            System.out.println(bundle.getString("enter_quantity"));
            int qty = scanner.nextInt();

            total += price * qty;
        }

        System.out.println(bundle.getString("total_cost") + total);
    }
}