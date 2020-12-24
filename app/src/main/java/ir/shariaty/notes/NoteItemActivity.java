package ir.shariaty.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import ir.shariaty.notes.Model.Note;

public class NoteItemActivity extends AppCompatActivity {

    TextView title;
    TextView date;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_item);

        title = (TextView) findViewById(R.id.item_title);
        date = (TextView) findViewById(R.id.item_date);
        text = (TextView) findViewById(R.id.item_note);

        if (getIntent().hasExtra("selected_note")){

            Note note = getIntent().getParcelableExtra("selected_note");
            title.setText(note.getTitle());
            date.setText(note.getDate());
            text.setText(note.getDescription());
        }


    }
}
