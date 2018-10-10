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
    @Query("SELECT * FROM GameBacklog")
    public LiveData<List<Game>> getAllGames();

    @Insert
    public void insertGames(Game games);


    @Delete
    public void deleteGames(Game games);


    @Update
    public void updateGames(Game games);
}
