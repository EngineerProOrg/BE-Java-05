package lecture.junit;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

class CalculatorTest {
    private static Calculator calculator;

    @BeforeAll
    public static void beforeAll() {
        System.out.println("Run before all test cases");
        calculator = new Calculator();
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("Run after all test cases");
        calculator = null;
    }

    @BeforeEach
    public void beforeEach() {
        System.out.println("Run before each test case");
    }

    @AfterEach
    public void afterEach() {
        System.out.println("Run after each test case");
    }

    @Test
    void testSum_TwoIntegers_ReturnSumOfThem() {
        // Arrange
        int a = 1;
        int b = 2;
        // Act
        int returnedSum = calculator.sum(a, b);
        // Assert
        assertEquals(3, returnedSum);
    }

    @Test
    void testSubtract_TwoIntegers_ReturnSubtractionOfThem() {
        // Arrange
        int a = 1;
        int b = 2;
        // Act
        int returnedSubtraction = calculator.subtract(a, b);
        // Assert
        assertEquals(-1, returnedSubtraction);
    }
}
