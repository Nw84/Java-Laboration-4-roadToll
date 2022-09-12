package edu.lernia.labb4;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.Scanner;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

public class TollFeeCalculatorTest {

    private PrintStream originalSystemOut;
    private ByteArrayOutputStream systemOutContent;

    @BeforeEach
    void redirectSystemOutStream() {

        originalSystemOut = System.out;

        // given
        systemOutContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOutContent));
    }

    @AfterEach
    void restoreSystemOutStream() {
        System.setOut(originalSystemOut);
    }


    @Test
    void inputAndOutputShouldBeSameLength() {
        TollFeeCalculator.main(new String[] {"src/test/resources/Lab4.txt"});
        
        assertEquals(219, systemOutContent.toString().length());
        //18 x 10 rows + 39; 
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
    void testingEveryTimeForCorrectResponse () {
        LocalDateTime dates [] = new LocalDateTime[2];
        dates[0] = LocalDateTime.of(2022, 05, 12, 21, 30, 40);
        dates[1] = LocalDateTime.of(2022, 05, 12, 21, 52, 40);
        

        assertEquals(0, TollFeeCalculator.getTotalFeeCost(dates));
        
    }

    @Test 
    void maximumCostShouldBe60 () {
        LocalDateTime dates [] = new LocalDateTime[5];
        dates[0] = LocalDateTime.of(2022, 05, 12, 6, 38, 40);
        dates[1] = LocalDateTime.of(2022, 05, 12, 7, 55, 40);
        dates[2] = LocalDateTime.of(2022, 05, 12, 15, 10, 40);
        dates[3] = LocalDateTime.of(2022, 05, 12, 16, 15, 40);
        dates[4] = LocalDateTime.of(2022, 05, 12, 17, 45, 40);

        assertEquals(60, TollFeeCalculator.getTotalFeeCost(dates));
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
