package pl.dkobylarz.garage_system_api.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DateTimeStampConverter {

    public static Date dateOf(long timestamp){
        Timestamp ts = new Timestamp(timestamp);
        return new Date(ts.getTime());
    }

    public static LocalDateTime localDateTimeOf(long timestmap){
        return new Timestamp(timestmap).toLocalDateTime();
    }
}
