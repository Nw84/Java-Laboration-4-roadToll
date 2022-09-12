package edu.lernia.labb4;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

public class TollFeeCalculatorTest {
    @Test
    void inputAndOutputShouldBeSameLength() {
        String dates [] = new String [2];
        dates[0] = "2020-06-30T10:25";
        dates[1] = "2020-06-30T10:25";
    }

    @Test
    void PassingSeveralTollStationsWithin60MinutesIsTaxedOnlyOnce() {
        LocalDateTime dates [] = new LocalDateTime[3];
        dates[0] = LocalDateTime.of(2022, 05, 12, 15, 28, 40);
        dates[1] = LocalDateTime.of(2022, 05, 12, 15, 55, 40);
        dates[2] = LocalDateTime.of(2022, 05, 12, 15, 59, 40);

        assertEquals(18, TollFeeCalculator.getTotalFeeCost(dates));
        
    }

    @Test
    void julyIsTollFreeButDecemberIsNot () {
        LocalDateTime july = LocalDateTime.of(2022, 07, 12, 14, 30, 40);

        LocalDateTime december = LocalDateTime.of(2021, 12, 15, 14, 30, 40);

        
        assertTrue(TollFeeCalculator.isTollFreeDate(july));
        assertFalse(TollFeeCalculator.isTollFreeDate(december));
    }

    @Test
    void SaturdayAndSundayAreTollFreeButMondayIsNot () {
        LocalDateTime saturday = LocalDateTime.of(2021, 06, 26, 10, 30, 40);
        LocalDateTime sunday = LocalDateTime.of(2021, 06, 27, 10, 30, 40);

        LocalDateTime monday = LocalDateTime.of(2021, 06, 28, 10, 30, 40);

        assertTrue(TollFeeCalculator.isTollFreeDate(saturday));
        assertTrue(TollFeeCalculator.isTollFreeDate(sunday));
        assertFalse(TollFeeCalculator.isTollFreeDate(monday));
    }
}
