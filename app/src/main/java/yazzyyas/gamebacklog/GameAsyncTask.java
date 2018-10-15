package yazzyyas.gamebacklog;

import android.os.AsyncTask;
import java.util.List;
import static yazzyyas.gamebacklog.MainActivity.TASK_DELETE_GAME;
import static yazzyyas.gamebacklog.MainActivity.db;

public class GameAsyncTask extends AsyncTask<Game, Void, List<Game>> {

    public final static int TASK_UPDATE_GAME = 2;
    public final static int TASK_INSERT_GAME = 3;

    int taskcode;
    MyRecyclerViewAdapter adapter;

    public GameAsyncTask(int taskcode, MyRecyclerViewAdapter adapter) {
        this.taskcode = taskcode;
        this.adapter = adapter;
    }

    @Override
    protected List<Game> doInBackground(Game... games) {
        switch (taskcode) {
            case TASK_INSERT_GAME:
                db.gameDao().insertGames(games[0]);
                break;
            case TASK_UPDATE_GAME:
                db.gameDao().updateGames(games[0]);
                break;
            case TASK_DELETE_GAME:
                db.gameDao().deleteGames(games[0]);
                break;
        }

        //To return a new list with the updated data, we get all the data from the database again.
        return (List) db.gameDao().getAllGames();
    }

    @Override
    protected void onPostExecute(List<Game> games) {
        adapter.setmData(games);
    }
}
