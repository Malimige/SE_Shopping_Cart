import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Controller {

    @FXML
    private ComboBox<String> languageBox;

    @FXML
    private TextField itemsField; // ✅ NEW

    @FXML
    private TextField priceField;

    @FXML
    private TextField quantityField;

    @FXML
    private Button calculateButton;

    @FXML
    private Label resultLabel;

    private ResourceBundle bundle;

    @FXML
    public void initialize() {
        languageBox.getItems().addAll("EN", "FI", "SV", "JP", "AR");
        languageBox.setValue("EN");

        loadLanguage("EN");
    }

    @FXML
    public void handleLanguageChange() {
        loadLanguage(languageBox.getValue());
    }

    // 🔥 UTF-8 FIXED LOADING
    private void loadLanguage(String lang) {
        try {
            Locale locale = Main.getLocale(lang);

            String fileName = "MessagesBundle_" + locale.getLanguage() + "_" + locale.getCountry() + ".properties";

            InputStreamReader reader = new InputStreamReader(
                    getClass().getClassLoader().getResourceAsStream(fileName),
                    StandardCharsets.UTF_8
            );

            bundle = new PropertyResourceBundle(reader);

            // Apply to UI
            itemsField.setPromptText(bundle.getString("enter_items")); // 
            priceField.setPromptText(bundle.getString("enter_price"));
            quantityField.setPromptText(bundle.getString("enter_quantity"));
            calculateButton.setText(bundle.getString("calculate"));
            resultLabel.setText(bundle.getString("total_cost"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleCalculate() {
        try {
            int items = Integer.parseInt(itemsField.getText()); // ✅ NEW

            double total = 0;

            // 🔥 LOOP for multiple items
            for (int i = 0; i < items; i++) {

                double price = Double.parseDouble(priceField.getText());
                int quantity = Integer.parseInt(quantityField.getText());

                total += Main.calculateItemTotal(price, quantity);
            }

            resultLabel.setText(bundle.getString("total_cost") + " " + total);

        } catch (Exception e) {
            resultLabel.setText("Invalid input!");
        }
    }
}