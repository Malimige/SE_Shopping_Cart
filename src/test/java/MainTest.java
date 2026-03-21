import org.junit.jupiter.api.Test;
import java.util.Locale;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void testItemTotal() {
        assertEquals(20, Main.calculateItemTotal(10, 2));
    }

    @Test
    void testCartTotal() {
        double[] prices = {10, 5};
        int[] quantities = {2, 3};
        assertEquals(35, Main.calculateCartTotal(prices, quantities));
    }

    @Test
    void testZeroQuantity() {
        assertEquals(0, Main.calculateItemTotal(10, 0));
    }

    @Test
    void testZeroPrice() {
        assertEquals(0, Main.calculateItemTotal(0, 5));
    }

    @Test
    void testSingleItemCart() {
        double[] prices = {10};
        int[] quantities = {2};
        assertEquals(20, Main.calculateCartTotal(prices, quantities));
    }

    @Test
    void testEmptyCart() {
        double[] prices = {};
        int[] quantities = {};
        assertEquals(0, Main.calculateCartTotal(prices, quantities));
    }

    @Test
    void testLocaleEnglish() {
        Locale locale = Main.getLocale(1);
        assertEquals("en", locale.getLanguage());
    }

    @Test
    void testLocaleFinnish() {
        assertEquals("fi", Main.getLocale(2).getLanguage());
    }

    @Test
    void testLocaleSwedish() {
        assertEquals("sv", Main.getLocale(3).getLanguage());
    }

    @Test
    void testLocaleJapanese() {
        assertEquals("ja", Main.getLocale(4).getLanguage());
    }

    @Test
    void testDefaultLocale() {
        assertEquals("en", Main.getLocale(99).getLanguage());
    }

    // ? VERY IMPORTANT (this increases coverage a lot)
    @Test
    void testMainExecution() {
        String input = "1\n1\n10\n2\n";
        System.setIn(new java.io.ByteArrayInputStream(input.getBytes()));
        Main.main(new String[]{});
    }

}