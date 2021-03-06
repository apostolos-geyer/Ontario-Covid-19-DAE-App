/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jginfosci.covid19.dae;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Simple utility class for getting current date and time.
 * @author paulgeyer
 */
public class DateAndTime {

    /**
     * Format of the {@link LocalDateTime}.
     * <pre>Format yyyy-MM-dd HH:mm</pre>
     */
    public static final DateTimeFormatter lastUpdatedFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final DateTimeFormatter displayFormat = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy");
    public static final DateTimeFormatter dataFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     *
     * @return The current time.
     * @see LocalDateTime#now()
     */
    public static String lastUpdateTime() {
        LocalDateTime dt = LocalDateTime.now();
        String now = lastUpdatedFormat.format(dt);
        return now;
    }

    public static String dispDate() {
        LocalDateTime dt = LocalDateTime.now();
        String now = displayFormat.format(dt);
        return now;
    }
    
    public static String dataDate() {
        LocalDateTime dt = LocalDateTime.now();
        String now = dataFormat.format(dt);
        return now;
    }
    
}
