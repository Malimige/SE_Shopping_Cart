import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Controller {

    @FXML
    private ComboBox<String> languageBox;

    @FXML
    private Button confirmButton;

    @FXML
    private Label selectLanguageLabel;

    @FXML
    private Label itemNumberLabel;

    @FXML
    private TextField itemNumberField;

    @FXML
    private Label priceLabel;

    @FXML
    private TextField priceField;

    @FXML
    private Label quantityLabel;

    @FXML
    private TextField quantityField;

    @FXML
    private Button addItemButton;

    @FXML
    private Button calculateTotalButton;

    @FXML
    private Label totalLabel;

    @FXML
    private TextArea cartDisplayArea;

    private final LocalizationService localizationService = new LocalizationService();
    private final CartService cartService = new CartService();
    private final List<CartItem> cartItems = new ArrayList<>();

    private Map<String, String> localizedTexts;
    private String selectedLanguageCode = "en";

    @FXML
    public void initialize() {
        languageBox.getItems().addAll("English", "Finnish", "Swedish", "Japanese", "Arabic");
        languageBox.setValue("English");
        loadLanguage("en");
    }

    @FXML
    public void handleLanguageChange() {
        String selectedLanguage = languageBox.getValue();
        selectedLanguageCode = getLanguageCode(selectedLanguage);
        loadLanguage(selectedLanguageCode);
    }

    @FXML
    public void handleConfirmLanguage() {
        totalLabel.setText(
                localizedTexts.getOrDefault("language_confirmed", "Language confirmed") +
                        ": " +
                        languageBox.getValue()
        );
    }

    @FXML
    public void handleAddItem() {
        try {
            int itemNumber = Integer.parseInt(itemNumberField.getText().trim());
            double price = Double.parseDouble(priceField.getText().trim());
            int quantity = Integer.parseInt(quantityField.getText().trim());

            CartItem item = new CartItem(itemNumber, price, quantity);
            cartItems.add(item);

            cartDisplayArea.appendText(
                    localizedTexts.getOrDefault("item_number", "Item Number") + ": " + item.getItemNumber() + ", " +
                            localizedTexts.getOrDefault("price", "Price") + ": " + item.getPrice() + ", " +
                            localizedTexts.getOrDefault("quantity", "Quantity") + ": " + item.getQuantity() + ", " +
                            localizedTexts.getOrDefault("subtotal", "Subtotal") + ": " + item.getSubtotal() + "\n"
            );

            itemNumberField.clear();
            priceField.clear();
            quantityField.clear();

        } catch (Exception e) {
            totalLabel.setText(
                    localizedTexts.getOrDefault("error_invalid_input", "Invalid input!")
            );
        }
    }

    @FXML
    public void handleCalculateTotal() {
        try {
            if (cartItems.isEmpty()) {
                totalLabel.setText(
                        localizedTexts.getOrDefault("error_no_items", "Please add items first!")
                );
                return;
            }

            int totalItems = 0;
            double totalCost = 0.0;

            for (CartItem item : cartItems) {
                totalItems += item.getQuantity();
                totalCost += item.getSubtotal();
            }

            totalLabel.setText(
                    localizedTexts.getOrDefault("total_items", "Total Items") + ": " + totalItems + " | " +
                            localizedTexts.getOrDefault("total_cost", "Total Cost") + ": " + totalCost
            );

            cartService.saveCart(cartItems, selectedLanguageCode);

            // Clear only the in-memory cart to avoid duplicate DB inserts.
            // Keep the text area content visible for screenshots and demo.
            cartItems.clear();

        } catch (Exception e) {
            totalLabel.setText(
                    localizedTexts.getOrDefault("error_calculation", "Error in calculation!")
            );
            e.printStackTrace();
        }
    }

    private String getLanguageCode(String displayName) {
        return switch (displayName) {
            case "Finnish" -> "fi";
            case "Swedish" -> "sv";
            case "Japanese" -> "jp";
            case "Arabic" -> "ar";
            default -> "en";
        };
    }

    private void loadLanguage(String lang) {
        localizedTexts = localizationService.getLocalizedStrings(lang);
        updateUIWithLanguage();
    }

    private void updateUIWithLanguage() {
        selectLanguageLabel.setText(
                localizedTexts.getOrDefault("select_language", "Select the language:")
        );
        confirmButton.setText(
                localizedTexts.getOrDefault("confirm_language", "Confirm Language")
        );
        itemNumberLabel.setText(
                localizedTexts.getOrDefault("item_number", "Item Number")
        );
        itemNumberField.setPromptText(
                localizedTexts.getOrDefault("item_number", "Item Number")
        );
        priceLabel.setText(
                localizedTexts.getOrDefault("price", "Price")
        );
        priceField.setPromptText(
                localizedTexts.getOrDefault("price", "Price")
        );
        quantityLabel.setText(
                localizedTexts.getOrDefault("quantity", "Quantity")
        );
        quantityField.setPromptText(
                localizedTexts.getOrDefault("quantity", "Quantity")
        );
        addItemButton.setText(
                localizedTexts.getOrDefault("add_item", "Add Item")
        );
        calculateTotalButton.setText(
                localizedTexts.getOrDefault("calculate_total", "Calculate Total")
        );
        totalLabel.setText(
                localizedTexts.getOrDefault("total", "Total")
        );
    }
}