import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class CartService {

    public void saveCart(List<CartItem> cartItems, String language) {
        String insertCartRecord = "INSERT INTO cart_records (total_items, total_cost, language) VALUES (?, ?, ?)";
        String insertCartItem = "INSERT INTO cart_items (cart_record_id, item_number, price, quantity, subtotal) VALUES (?, ?, ?, ?, ?)";

        int totalItems = 0;
        double totalCost = 0.0;

        for (CartItem item : cartItems) {
            totalItems += item.getQuantity();
            totalCost += item.getSubtotal();
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            int cartRecordId;

            try (PreparedStatement recordStmt = conn.prepareStatement(insertCartRecord, PreparedStatement.RETURN_GENERATED_KEYS)) {
                recordStmt.setInt(1, totalItems);
                recordStmt.setDouble(2, totalCost);
                recordStmt.setString(3, language);
                recordStmt.executeUpdate();

                try (ResultSet rs = recordStmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        cartRecordId = rs.getInt(1);
                    } else {
                        throw new Exception("Failed to retrieve cart record ID.");
                    }
                }
            }

            try (PreparedStatement itemStmt = conn.prepareStatement(insertCartItem)) {
                for (CartItem item : cartItems) {
                    itemStmt.setInt(1, cartRecordId);
                    itemStmt.setInt(2, item.getItemNumber());
                    itemStmt.setDouble(3, item.getPrice());
                    itemStmt.setInt(4, item.getQuantity());
                    itemStmt.setDouble(5, item.getSubtotal());
                    itemStmt.addBatch();
                }
                itemStmt.executeBatch();
            }

            conn.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}