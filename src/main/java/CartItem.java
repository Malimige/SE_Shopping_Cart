public class CartItem {
    private int itemNumber;
    private double price;
    private int quantity;

    public CartItem(int itemNumber, double price, int quantity) {
        this.itemNumber = itemNumber;
        this.price = price;
        this.quantity = quantity;
    }

    public int getItemNumber() {
        return itemNumber;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getSubtotal() {
        return price * quantity;
    }
}