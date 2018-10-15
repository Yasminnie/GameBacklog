package yazzyyas.gamebacklog;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class Converters {

    @TypeConverter
    public Date fromTimeStamp(Long value) {
        if (value == null) {
            return null;
        } else {
            return new Date(value);
        }
    }

    @TypeConverter
    public Long dateToTimeStamp(Date date) {
        return date.getTime();
    }
}
