package yazzyyas.gamebacklog;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
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

import yazzyyas.gamebacklog.database.AppDatabase;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    static AppDatabase db;
    static final int REQUEST_CODE = 1234;
    public static final String EXTRA_GAME = "Games";

    public final static int TASK_GET_ALL_GAMES = 0;
    public final static int TASK_DELETE_GAME = 1;
    public final static int TASK_UPDATE_GAME = 2;
    public final static int TASK_INSERT_GAME = 3;

    public List<Game> games = new ArrayList<>();
    RecyclerView gameRecyclerView;
    private MyRecyclerViewAdapter gameAdapter;

    FloatingActionButton fabAddGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fabAddGame = findViewById(R.id.fabAddGame);
        fabAddGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddGameActivity.class);
                startActivityForResult(intent, TASK_INSERT_GAME);
            }
        });

        db = AppDatabase.getInstance(this);
        GameRepository gameRepository = new GameRepository(this);
        gameRepository.insert(new Game("hey", "testje", "alles goed?"));

        new GameAsyncTask(TASK_GET_ALL_GAMES).execute();
        db.gameDao().insertGames(new Game("ab", "cd", "asdf"));

        games.add(new Game("a", "b", "c"));
        games.add(new Game("d", "e", "f"));

        this.gameAdapter = new MyRecyclerViewAdapter(this, games);

        gameRecyclerView = findViewById(R.id.gameRecyclerView);
        gameRecyclerView.setAdapter(gameAdapter);

        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL);
        gameRecyclerView.setLayoutManager(layoutManager);

        updateUI();

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder
                            target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        int position = (viewHolder.getAdapterPosition());
                        new GameAsyncTask(TASK_DELETE_GAME).execute(games.get(position));
                        Toast.makeText(MainActivity.this, "swipen testt", Toast.LENGTH_SHORT).show();
                    }
                };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(gameRecyclerView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case TASK_INSERT_GAME:
                    Game updatedGame = data.getParcelableExtra(MainActivity.EXTRA_GAME);
                    new MainActivity.GameAsyncTask(TASK_UPDATE_GAME).execute(updatedGame);
                    break;
                case TASK_UPDATE_GAME:
                    Game insertGame = data.getParcelableExtra(MainActivity.EXTRA_GAME);
                    new MainActivity.GameAsyncTask(TASK_INSERT_GAME).execute(insertGame);
                    break;
            }
        }
    }

    public void onGameDbUpdated(List list) {
        games = list;
        updateUI();
    }

    @Override
    public void onItemClick(View view, int position) {
    }

    private void updateUI() {
        if (gameAdapter == null) {
            gameAdapter = new MyRecyclerViewAdapter(this, games);
            gameRecyclerView.setAdapter(gameAdapter);
        } else {
            gameAdapter.swapList(games);
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
            return db.gameDao().getAllGames().getValue();
        }

        @Override
        protected void onPostExecute(List list) {
            super.onPostExecute(list);
            onGameDbUpdated(list);
        }
    }
}
