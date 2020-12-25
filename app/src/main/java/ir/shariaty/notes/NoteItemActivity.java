package ir.shariaty.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

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
    String noteId;

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

        final Note note1 = getIntent().getParcelableExtra("selected_note");
        if (getIntent().hasExtra("selected_note")){

            Note note = getIntent().getParcelableExtra("selected_note");
            title.setText(note.getTitle());
            date.setText(note.getDate());
            text.setText(note.getDescription());
            noteId=note.getNoteID();
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

                FirebaseDatabase.getInstance().getReference()
                        .child("Notes").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(noteId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(NoteItemActivity.this, "note deleted!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(NoteItemActivity.this,"note doesn't delete", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        editFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }

    private void onMenuButtonClick() {
        setVisibility(clicked);
        setAnimation(clicked);
        setClickable(clicked);
        clicked= !clicked;


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
