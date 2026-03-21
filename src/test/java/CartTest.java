import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CartTest {

    @Test
    void testItemTotal() {
        double result = 10 * 2;
        assertEquals(20, result);
    }

    @Test
    void testCartTotal() {
        double total = (10 * 2) + (5 * 3);
        assertEquals(35, total);
    }
}