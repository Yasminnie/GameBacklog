package yazzyyas.gamebacklog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class AddGameActivity extends AppCompatActivity {
    Spinner spinner;
    FloatingActionButton fabSaveGame;

    EditText title;
    EditText platform;
    EditText notes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addgame);

        spinner = findViewById(R.id.status_spinner);
        title = findViewById(R.id.titleTV);
        platform = findViewById(R.id.platformTV);
        notes = findViewById(R.id.notesTV);

        title.setText(getIntent().getStringExtra("title"));
        platform.setText(getIntent().getStringExtra("platform"));
        notes.setText(getIntent().getStringExtra("notes"));

        for (int i = 0; i < spinner.getAdapter().getCount() - 1; i++) {
            if (spinner.getItemAtPosition(i) == getIntent().getStringExtra("status")) {
                spinner.setSelection(i);
                break;
            }
        }

        final long gameID = getIntent().getLongExtra("id", -1l);

        fabSaveGame = findViewById(R.id.fabSaveGame);
        fabSaveGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("id", gameID);
                intent.putExtra("title", title.getText().toString());
                intent.putExtra("platform", platform.getText().toString());
                intent.putExtra("notes", notes.getText().toString());

                intent.putExtra("status", spinner.getSelectedItem().toString());
                setResult(Activity.RESULT_OK, intent);

                finish();
            }
        });
    }
}
