package edu.lernia.labb4;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TollFeeCalculatorTest {

    private PrintStream originalSystemOut;
    private ByteArrayOutputStream systemOutContent;

    @BeforeEach
    void redirectSystemOutStream() {

        originalSystemOut = System.out;

        systemOutContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOutContent));
    }

    @AfterEach
    void restoreSystemOutStream() {
        System.setOut(originalSystemOut);
    }


    @Test
    void inputAndOutputShouldBeSameLength() throws Exception {
        TollFeeCalculator.main(new String[] {"src/test/resources/Lab4.txt"});
        String line = "";
        int counter = 0;  
        FileReader file = new FileReader("src/test/resources/Lab4.txt");  
        BufferedReader br = new BufferedReader(file);  
    
        while((line = br.readLine()) != null) {  
            String words[] = line.split(", ");  
            counter += words.length;  
        }  
            br.close();
            assertEquals(counter * 18 + 39, systemOutContent.toString().length());
            //18chars x 10 rows + 39 for the last line; 
            //So if the bugg LocalDateTime[] dates = new LocalDateTime[dateStrings.length];
            //whould come back would get 201 back instead of 219
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
    void PassingTwoTollStationsDuring60minutesAndOneShortAfterReturnsCorrectResult() {
        LocalDateTime dates [] = new LocalDateTime[3];
        dates[0] = LocalDateTime.of(2022, 05, 12, 15, 28, 40);
        dates[1] = LocalDateTime.of(2022, 05, 12, 15, 55, 40);
        dates[2] = LocalDateTime.of(2022, 05, 12, 16, 29, 40);

        assertEquals(36, TollFeeCalculator.getTotalFeeCost(dates));
        //Returns 18 for the 15:55 toll and 18 for the 16:29 
    }

    @Test
    void testingEveryTimeWindowForCorrectResponse () {
        LocalDateTime time1 = LocalDateTime.of(2022, 05, 12, 6, 29, 40);
        LocalDateTime time2 = LocalDateTime.of(2022, 05, 12, 6, 57, 40);
        LocalDateTime time3 = LocalDateTime.of(2022, 05, 12, 7, 55, 40);
        LocalDateTime time4 = LocalDateTime.of(2022, 05, 12, 8, 20, 40);
        LocalDateTime time5 = LocalDateTime.of(2022, 05, 12, 11, 30, 40);
        LocalDateTime time6 = LocalDateTime.of(2022, 05, 12, 15, 15, 40);
        LocalDateTime time7 = LocalDateTime.of(2022, 05, 12, 16, 15, 40);
        LocalDateTime time8 = LocalDateTime.of(2022, 05, 12, 17, 20, 40);
        LocalDateTime time9 = LocalDateTime.of(2022, 05, 12, 18, 15, 40);
        LocalDateTime time10 = LocalDateTime.of(2022, 05, 12, 21, 15, 40);

        assertEquals(8, TollFeeCalculator.getTollFeePerPassing(time1));
        assertEquals(13, TollFeeCalculator.getTollFeePerPassing(time2));
        assertEquals(18, TollFeeCalculator.getTollFeePerPassing(time3));
        assertEquals(13, TollFeeCalculator.getTollFeePerPassing(time4));
        assertEquals(8, TollFeeCalculator.getTollFeePerPassing(time5));
        assertEquals(13, TollFeeCalculator.getTollFeePerPassing(time6));
        assertEquals(18, TollFeeCalculator.getTollFeePerPassing(time7));
        assertEquals(13, TollFeeCalculator.getTollFeePerPassing(time8));
        assertEquals(8, TollFeeCalculator.getTollFeePerPassing(time9));
        assertEquals(0, TollFeeCalculator.getTollFeePerPassing(time10));
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
        //total sum is 85, but it should return max (60)
    }

    @Test 
    void shouldReturnTotalValueIfITOtalValueIsLessTHen60 () {
        LocalDateTime dates [] = new LocalDateTime[3];
        dates[0] = LocalDateTime.of(2022, 05, 12, 6, 38, 40);
        dates[1] = LocalDateTime.of(2022, 05, 12, 7, 55, 40);
        dates[2] = LocalDateTime.of(2022, 05, 12, 15, 10, 40);

        assertEquals(44, TollFeeCalculator.getTotalFeeCost(dates));
        //total sum of the 3 dates is 44, so it should return 44
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
