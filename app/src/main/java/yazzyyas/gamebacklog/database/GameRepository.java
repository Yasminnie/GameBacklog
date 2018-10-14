package yazzyyas.gamebacklog.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import yazzyyas.gamebacklog.Game;

public class GameRepository {

    private GameDao gameDao;
    private LiveData<List<Game>> allGames;

    GameRepository( Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        gameDao = db.gameDao();
    }

    LiveData<List<Game>> getAllGames() {
        return allGames;
    }

    public void insert(Game game) {
        new insertAsyncTask(gameDao).execute(game);
    }

    private static class insertAsyncTask extends AsyncTask<Game, Void, Void> {

        private GameDao mAsyncTaskDao;

        insertAsyncTask(GameDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Game... games) {
            mAsyncTaskDao.insertGames(games[0]);
            return null;
        }
    }
}
