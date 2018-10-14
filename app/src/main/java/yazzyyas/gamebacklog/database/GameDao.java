package yazzyyas.gamebacklog.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import yazzyyas.gamebacklog.Game;

@Dao

public interface GameDao {
    @Query("SELECT * FROM game")
    LiveData<List<Game>> getAllGames();

    @Insert
    void insertGames(Game games);


    @Delete
    void deleteGames(Game games);


    @Update
    void updateGames(Game games);
}
