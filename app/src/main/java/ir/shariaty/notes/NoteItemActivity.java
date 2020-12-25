package ir.shariaty.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import ir.shariaty.notes.Model.Note;
import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

public class NoteItemActivity extends AppCompatActivity {


    Animation rotateOpen;
    Animation rotateClose;
    Animation fromBottom;
    Animation toBottom;

    private Boolean clicked = false;

    FloatingActionButton menuFAB;
    FloatingActionButton deleteFAB;
    FloatingActionButton editFAB;

    EditText editTitle;
    EditText editNote;
    Toolbar toolbar;

    TextView title;
    TextView date;
    TextView text;
    String noteId;


    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_item);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        rotateOpen = AnimationUtils.loadAnimation(this,R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(this,R.anim.rotate_close_anim);
        fromBottom = AnimationUtils.loadAnimation(this,R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(this,R.anim.to_bottom_anim);

        menuFAB = findViewById(R.id.fab_menu);
        deleteFAB = findViewById(R.id.fab_delete);
        editFAB = findViewById(R.id.fab_edit);

        toolbar =  findViewById(R.id.edit_toolbar);


        title =  findViewById(R.id.item_title);
        date =  findViewById(R.id.item_date);
        text =  findViewById(R.id.item_note);

        editTitle = findViewById(R.id.edit_title);
        editNote = findViewById(R.id.edit_note);

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
                        .child("Notes").child(noteId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(NoteItemActivity.this, "note deleted!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(NoteItemActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
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

                setEditVisibility();
                setSupportActionBar(toolbar);
            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        PersianDate pDate = new PersianDate();
        PersianDateFormat pdFormater = new PersianDateFormat("Y/m/d  H:i");

        String noteTitle = editTitle.getText().toString();
        String noteDate = pdFormater.format(pDate);
        String noteText = editNote.getText().toString();

        if (TextUtils.isEmpty(noteTitle) || TextUtils.isEmpty(noteText)) {
            Toast.makeText(NoteItemActivity.this, "note is empty!", Toast.LENGTH_SHORT).show();
        }
        else {
            update(noteTitle,noteDate,noteText);
        }

        return super.onOptionsItemSelected(item);
    }

    private void update(final String title, final String date, final String text) {
        HashMap<String,Object> map=new HashMap<>();
        map.put("title",title);
        map.put("description",text);
        map.put("date",date);

        databaseReference.child("Notes").child(noteId).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(NoteItemActivity.this,"NOTE Edited",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(NoteItemActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));

            }
        });
    }

    private void setEditVisibility() {
        editTitle.setVisibility(View.VISIBLE);
        editNote.setVisibility(View.VISIBLE);
        editTitle.setText(title.getText().toString());
        editNote.setText(text.getText().toString());
        title.setVisibility(View.INVISIBLE);
        text.setVisibility(View.INVISIBLE);
        date.setVisibility(View.INVISIBLE);
        clicked = true;
        onMenuButtonClick();

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
