import java.util.*;

public class Main {

    // ✅ Method 1: calculate single item total
    public static double calculateItemTotal(double price, int quantity) {
        return price * quantity;
    }

    // ✅ Method 2: calculate cart total
    public static double calculateCartTotal(double[] prices, int[] quantities) {
        double total = 0;
        for (int i = 0; i < prices.length; i++) {
            total += calculateItemTotal(prices[i], quantities[i]);
        }
        return total;
    }

    // ✅ Method 3: get locale (testable!)
    public static Locale getLocale(int choice) {
        return switch (choice) {
            case 2 -> new Locale("fi", "FI");
            case 3 -> new Locale("sv", "SE");
            case 4 -> new Locale("ja", "JP");
            default -> new Locale("en", "US");
        };
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Select language: 1=EN, 2=FI, 3=SV, 4=JP");
        int choice = scanner.nextInt();

        Locale locale = getLocale(choice);

        ResourceBundle bundle = ResourceBundle.getBundle("MessagesBundle", locale);

        System.out.println(bundle.getString("enter_items"));
        int items = scanner.nextInt();

        double[] prices = new double[items];
        int[] quantities = new int[items];

        for (int i = 0; i < items; i++) {
            System.out.println(bundle.getString("enter_price"));
            prices[i] = scanner.nextDouble();

            System.out.println(bundle.getString("enter_quantity"));
            quantities[i] = scanner.nextInt();
        }

        double total = calculateCartTotal(prices, quantities);

        System.out.println(bundle.getString("total_cost") + total);
    }
}