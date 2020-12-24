package ir.shariaty.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ir.shariaty.notes.Model.Note;

public class NoteItemActivity extends AppCompatActivity {

    Animation rotateOpen;
    Animation rotateClose;
    Animation fromBottom;
    Animation toBottom;

    private Boolean clicked = false;

    FloatingActionButton menuFAB;
    FloatingActionButton deleteFAB;
    FloatingActionButton editFAB;
    TextView title;
    TextView date;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_item);

        rotateOpen = AnimationUtils.loadAnimation(this,R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(this,R.anim.rotate_close_anim);
        fromBottom = AnimationUtils.loadAnimation(this,R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(this,R.anim.to_bottom_anim);

        menuFAB = findViewById(R.id.fab_menu);
        deleteFAB = findViewById(R.id.fab_delete);
        editFAB = findViewById(R.id.fab_edit);

        title = (TextView) findViewById(R.id.item_title);
        date = (TextView) findViewById(R.id.item_date);
        text = (TextView) findViewById(R.id.item_note);

        if (getIntent().hasExtra("selected_note")){

            Note note = getIntent().getParcelableExtra("selected_note");
            title.setText(note.getTitle());
            date.setText(note.getDate());
            text.setText(note.getDescription());
        }

        menuFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMenuButtonClick();


            }
        });

        deleteFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        editFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //end


    }

    private void onMenuButtonClick() {
        setVisibility(clicked);
        setAnimation(clicked);
        setClickable(clicked);
        if(!clicked)
            clicked= true;
        else
            clicked = false;


    }

    private void setVisibility(boolean clicked) {

        if(!clicked){
            editFAB.setVisibility(View.VISIBLE);
            deleteFAB.setVisibility(View.VISIBLE);
        }
        else {
            editFAB.setVisibility(View.INVISIBLE);
            deleteFAB.setVisibility(View.INVISIBLE);
        }
    }

    private void setAnimation(boolean clicked) {
        if(!clicked){
            editFAB.startAnimation(fromBottom);
            deleteFAB.startAnimation(fromBottom);
            menuFAB.startAnimation(rotateOpen);
        }
        else {
            editFAB.startAnimation(toBottom);
            deleteFAB.startAnimation(toBottom);
            menuFAB.startAnimation(rotateClose);
        }
    }

    private void setClickable(boolean clicked){
        if(!clicked){
            editFAB.setClickable(true);
            deleteFAB.setClickable(true);
        }
        else {
            editFAB.setClickable(false);
            deleteFAB.setClickable(false);
        }
    }
}
