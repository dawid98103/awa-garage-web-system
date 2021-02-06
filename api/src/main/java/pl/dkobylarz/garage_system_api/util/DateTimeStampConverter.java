package pl.dkobylarz.garage_system_api.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateTimeStampConverter {

    public static Date dateOf(long timestamp) {
        Timestamp ts = new Timestamp(timestamp);
        return new Date(ts.getTime());
    }

    public static LocalDateTime localDateTimeOf(long timestmap) {
        return new Timestamp(timestmap).toLocalDateTime();
    }
}
