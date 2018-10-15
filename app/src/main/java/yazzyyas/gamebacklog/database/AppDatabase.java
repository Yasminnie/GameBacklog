package yazzyyas.gamebacklog.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import yazzyyas.gamebacklog.Converters;
import yazzyyas.gamebacklog.Game;

@Database(entities = {Game.class}, version = 1)
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {

    public abstract GameDao gameDao();

    private final static String NAME_DATABASE = "GameBacklog";
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context, AppDatabase.class, NAME_DATABASE).allowMainThreadQueries().build();
        }
        return sInstance;
    }
}
