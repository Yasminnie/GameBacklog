package yazzyyas.gamebacklog;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import butterknife.BindView;

public class AddGameActivity extends AppCompatActivity {

    @BindView(R.id.status_spinner)
    Spinner spinner;

    @BindView(R.id.fabSaveGame)
    FloatingActionButton fabSaveGame;

    EditText title = findViewById(R.id.titleTV);
    EditText platform = findViewById(R.id.platformTV);
    EditText notes = findViewById(R.id.notesTV);

    public final static int TASK_GET_ALL_GAMES = 0;
    public final static int TASK_DELETE_GAME = 1;
    public final static int TASK_UPDATE_GAME = 2;
    public final static int TASK_INSERT_GAME = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addgame);
        Spinner();


        fabSaveGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Game newGame = new Game(title.getText().toString(), platform.getText().toString(), notes.getText().toString());

                new MainActivity.GameAsyncTask(TASK_INSERT_GAME).execute(newGame);
                Intent intent = new Intent(getApplicationContext() , AddGameActivity.class);
                startActivity(intent);


                finish();
            }
        });
    }

    private void Spinner() {
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.status_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }
}
