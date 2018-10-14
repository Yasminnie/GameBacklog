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

public class AddGameActivity extends AppCompatActivity {

    Spinner spinner;
    FloatingActionButton fabSaveGame;

    EditText title;
    EditText platform;
    EditText notes;

    public final static int TASK_GET_ALL_GAMES = 0;
    public final static int TASK_DELETE_GAME = 1;
    public final static int TASK_UPDATE_GAME = 2;
    public final static int TASK_INSERT_GAME = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addgame);

        spinner = findViewById(R.id.status_spinner);
        spinner();

        title = findViewById(R.id.titleTV);
        platform = findViewById(R.id.platformTV);
        notes = findViewById(R.id.notesTV);

        fabSaveGame = findViewById(R.id.fabSaveGame);
        fabSaveGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Game newGame = new Game(title.getText().toString(), platform.getText().toString(), notes.getText().toString());

                new MainActivity.GameAsyncTask(TASK_INSERT_GAME).execute(newGame);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

                finish();
            }
        });
    }

    private void spinner() {
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.status_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }
}