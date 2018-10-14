package yazzyyas.gamebacklog;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;
import java.util.List;
import yazzyyas.gamebacklog.database.AppDatabase;
import yazzyyas.gamebacklog.database.GameDao;

public class GameRepository {

    private GameDao gameDao;
    private LiveData<List<Game>> allGames;

    GameRepository( Context context ) {
        AppDatabase db = AppDatabase.getInstance(context);
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
