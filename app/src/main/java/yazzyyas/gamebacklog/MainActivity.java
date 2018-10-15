package yazzyyas.gamebacklog;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import yazzyyas.gamebacklog.database.AppDatabase;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    static AppDatabase db;
    public final static int TASK_GET_ALL_GAMES = 0;
    public final static int TASK_DELETE_GAME = 1;
    public final static int TASK_UPDATE_GAME = 2;
    public final static int TASK_INSERT_GAME = 3;

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
        this.gameAdapter = new MyRecyclerViewAdapter(this, new ArrayList<Game>(), this);

        new GameAsyncTask(TASK_GET_ALL_GAMES, gameAdapter).execute();

        gameRecyclerView = findViewById(R.id.gameRecyclerView);
        gameRecyclerView.setAdapter(gameAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        gameRecyclerView.setLayoutManager(layoutManager);
        gameRecyclerView.setHasFixedSize(true);

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

                        try {
                            Game gamePosition = new GameAsyncTask(TASK_GET_ALL_GAMES, gameAdapter).execute().get().get(position);
                            new GameAsyncTask(TASK_DELETE_GAME, gameAdapter).execute(gamePosition);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        gameAdapter.notifyItemRemoved(position);
                    }
                };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(gameRecyclerView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case TASK_INSERT_GAME: // data in intent omzetten naar een game object
                    Game insertGame = new Game(null, data.getStringExtra("title"), data.getStringExtra("platform"), data.getStringExtra("notes"), new Date());
                    new GameAsyncTask(TASK_INSERT_GAME, gameAdapter).execute(insertGame);
                    break;
                case TASK_UPDATE_GAME:
                    Game updateGame = new Game(data.getLongExtra("id", 0), data.getStringExtra("title"), data.getStringExtra("platform"), data.getStringExtra("notes"), new Date());
                    new GameAsyncTask(TASK_UPDATE_GAME, gameAdapter).execute(updateGame);
                    break;
            }
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        try {
            Game gamePosition = new GameAsyncTask(TASK_GET_ALL_GAMES, gameAdapter).execute().get().get(position);
            Intent intent = new Intent(MainActivity.this, AddGameActivity.class);

            intent.putExtra("id", gamePosition.getId());
            intent.putExtra("title", gamePosition.getTitle());
            intent.putExtra("platform", gamePosition.getPlatform());
            intent.putExtra("notes", gamePosition.getNotes());
            intent.putExtra("status", gamePosition.getStatus());

            startActivityForResult(intent, TASK_UPDATE_GAME);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
