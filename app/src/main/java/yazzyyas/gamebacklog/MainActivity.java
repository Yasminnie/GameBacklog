package yazzyyas.gamebacklog;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static yazzyyas.gamebacklog.MainActivity.TASK_DELETE_GAME;
import static yazzyyas.gamebacklog.MainActivity.TASK_INSERT_GAME;
import static yazzyyas.gamebacklog.MainActivity.TASK_UPDATE_GAME;
import static yazzyyas.gamebacklog.MainActivity.db;

public class MainActivity extends AppCompatActivity {

    public final static int TASK_GET_ALL_GAMES = 0;
    public final static int TASK_DELETE_GAME = 1;
    public final static int TASK_UPDATE_GAME = 2;
    public final static int TASK_INSERT_GAME = 3;
    public List<Game> games = new ArrayList<>();
    private GameAdapter gameAdapter;
    static AppDatabase db;

    @BindView(R.id.fabAddGame)
    FloatingActionButton fabAddGame;

    @BindView(R.id.gameRecyclerView)
    RecyclerView gameRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = AppDatabase.getInstance(this);
        new GameAsyncTask(TASK_GET_ALL_GAMES).execute();
        games = db.gameDao().getAllGames();

        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL);
        gameRecyclerView.setLayoutManager(layoutManager);

        fabAddGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GameAsyncTask(TASK_INSERT_GAME).execute(newGame);
                Intent intent = new Intent(MainActivity.this, AddGameActivity.class);
                startActivity(intent);
            }
        });

        /*
            Add a touch helper to the RecyclerView to recognize when a user swipes.
            An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
            and uses callbacks to signal when a user is performing these actions.
         */

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder
                            target) {
                        return false;
                    }

                    //Called when a user swipes left or right on a ViewHolder
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        int position = (viewHolder.getAdapterPosition());
                        if (swipeDir == ItemTouchHelper.LEFT) {
                            new GameAsyncTask(TASK_DELETE_GAME).execute(games.get(position));
                            games.remove(position);
                            Toast.makeText(MainActivity.this, "Links swipen", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Rechts swipen", Toast.LENGTH_SHORT).show();
                        }
//                        gameAdapter.notifyItemChanged(position);
                        gameAdapter.swapList(games);
                    }
                };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(gameRecyclerView);

    }

    public void onGameDbUpdated(List list) {
        games = list;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1234) {
                Bundle bundle = data.getExtras();
                Game intentGame = (Game) bundle.getSerializable("portal");
                games.add(intentGame);

                Game updatedGame = data.getParcelableExtra(MainActivity.EXTRA_REMINDER);
                games.set(mModifyPosition, updatedGame);
                updateUI();
                new GameAsyncTask(TASK_UPDATE_REMINDER).execute(updatedGame);
            }
        }
    }
}

public class GameAsyncTask extends AsyncTask<Game, Void, List> {

    private int taskCode;

    public GameAsyncTask(int taskCode) {
        this.taskCode = taskCode;
    }

    @Override
    protected List doInBackground(Game... games) {
        switch (taskCode) {
            case TASK_DELETE_GAME:
                db.gameDao().deleteGames(games[0]);
                break;
            case TASK_UPDATE_GAME:
                db.gameDao().updateGames(games[0]);
                break;
            case TASK_INSERT_GAME:
                db.gameDao().insertGames(games[0]);
                break;
        }

        //To return a new list with the updated data, we get all the data from the database again.
        return db.gameDao().getAllGames();
    }


    @Override
    protected void onPostExecute(List list) {
        super.onPostExecute(list);
        onGameDbUpdated(list);
    }
}

