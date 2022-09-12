package edu.lernia.labb4;

import java.io.File;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class TollFeeCalculator {

    public TollFeeCalculator(String inputFile) {
        try {
            Scanner sc = new Scanner(new File(inputFile));
            String[] dateStrings = sc.nextLine().split(", ");
            LocalDateTime[] dates = new LocalDateTime[dateStrings.length];
            //En bugg eftersom bara 9/10 skrivs ut kolla så att båda strings är lika långa
            for(int i = 0; i < dates.length; i++) {
                dates[i] = LocalDateTime.parse(dateStrings[i], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            }
            System.out.println("The total fee for the inputfile is " + getTotalFeeCost(dates));
        } catch(IOException e) {
            System.err.println("Could not read file " + new File(inputFile).getAbsolutePath());
        }
    }

    public static int getTotalFeeCost(LocalDateTime[] dates) {
        int totalFee = 0;
        boolean hasPayed = false; 
        LocalDateTime intervalStart = dates[0];
        for(LocalDateTime date: dates) {
            System.out.println(date.toString());
            long diffInMinutes = intervalStart.until(date, ChronoUnit.MINUTES);
            if(intervalStart.equals(date) || diffInMinutes > 60) {
                totalFee += getTollFeePerPassing(date);
                intervalStart = date;
                hasPayed = false; 
            } else {
                if(getTollFeePerPassing(date) > getTollFeePerPassing(intervalStart) && !hasPayed) {
                    totalFee += (getTollFeePerPassing(date) - getTollFeePerPassing(intervalStart)); 
                    hasPayed = true; 
                }
                
            }
        }
        return Math.min(totalFee, 60);
        //Ska vara Math.min inte max, för annars returnar den aldrig mindre än 60
    }

    public static int getTollFeePerPassing(LocalDateTime date) {
        if (isTollFreeDate(date)) return 0;
        int hour = date.getHour();
        int minute = date.getMinute();
        if (hour == 6 && minute >= 0 && minute <= 29) return 8;
        else if (hour == 6 && minute >= 30 && minute <= 59) return 13;
        else if (hour == 7 && minute >= 0 && minute <= 59) return 18;
        else if (hour == 8 && minute >= 0 && minute <= 29) return 13;
        else if (hour >= 8 && hour <= 14 && minute >= 30 && minute <= 59) return 8;
        else if (hour == 15 && minute >= 0 && minute <= 29) return 13;
        else if (hour == 15 && minute >= 0 || hour == 16 && minute <= 59) return 18;
        else if (hour == 17 && minute >= 0 && minute <= 59) return 13;
        else if (hour == 18 && minute >= 0 && minute <= 29) return 8;
        else return 0;
    }

    public static boolean isTollFreeDate(LocalDateTime date) {
        DayOfWeek dayoOfWeek = DayOfWeek.from(date);
        if(dayoOfWeek.toString().equals("SATURDAY") || dayoOfWeek.toString().equals("SUNDAY") || date.getMonth().getValue() == 7) { 
            return true;
        }
        else {
            return false;
        }
    } //En bugg där det returnerade 6 eller 7 istället för en boolean

    public static void main(String[] args) {
        new TollFeeCalculator("src/test/resources/Lab4.txt");
    }
}
