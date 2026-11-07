import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {

    Calculator calculator = new Calculator();

    @Test
    void testEvenPositiveNumber() {
        assertTrue(calculator.isEven(8));
    }

    @Test
    void testOddPositiveNumber() {
        assertFalse(calculator.isEven(7));
    }

    @Test
    void testEvenNegativeNumber() {
        assertTrue(calculator.isEven(-4));
    }

    @Test
    void testZeroIsEven() {
        assertTrue(calculator.isEven(0));
    }
}