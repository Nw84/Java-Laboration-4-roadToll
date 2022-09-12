package edu.lernia.labb4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

public class TollFeeCalculatorTest {
    @Test
    void testGetTollFeePerPassing() {

    }

    @Test
    void testGetTotalFeeCost() {

    }

    @Test
    void julyIsTollFreeButDecemberIsNot () {
        var tollFeeCalculator = new TollFeeCalculator("src/test/resources/Lab4.txt");
        LocalDateTime july = LocalDateTime.of(2022, 07, 12, 14, 30, 40);

        LocalDateTime december = LocalDateTime.of(2021, 12, 15, 14, 30, 40);

        
        assertTrue(tollFeeCalculator.isTollFreeDate(july));
        assertFalse(tollFeeCalculator.isTollFreeDate(december));
    }

    @Test
    void SaturdayAndSundayAreTollFreeButMondayIsNot () {
        var tollFeeCalculator = new TollFeeCalculator("src/test/resources/Lab4.txt");
        LocalDateTime saturday = LocalDateTime.of(2021, 06, 26, 10, 30, 40);
        LocalDateTime sunday = LocalDateTime.of(2021, 06, 27, 10, 30, 40);

        LocalDateTime monday = LocalDateTime.of(2021, 06, 28, 10, 30, 40);

        assertTrue(tollFeeCalculator.isTollFreeDate(saturday));
        assertTrue(tollFeeCalculator.isTollFreeDate(sunday));
        assertFalse(tollFeeCalculator.isTollFreeDate(monday));
    }
}
