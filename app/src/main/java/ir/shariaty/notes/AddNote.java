package ir.shariaty.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

public class AddNote extends AppCompatActivity {

    Toolbar toolbar;
    TextView date;
    EditText title;
    EditText note;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        date = findViewById(R.id.date);
        title = findViewById(R.id.title);
        note = findViewById(R.id.note);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        PersianDate pDate = new PersianDate();
        PersianDateFormat pdFormater = new PersianDateFormat("Y/m/d  H:i");
        date.setText(pdFormater.format(pDate));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        //save data on firebase

        String noteTitle = title.getText().toString();
        String noteDate = date.getText().toString();
        String noteText = note.getText().toString();

        if (TextUtils.isEmpty(noteTitle) || TextUtils.isEmpty(noteText)) {
            Toast.makeText(AddNote.this, "note is empty!", Toast.LENGTH_SHORT).show();
        }
        else {
            add(noteTitle , noteDate , noteText);
        }

        return super.onOptionsItemSelected(item);
    }

    private void add(final String title, final String date, final String text) {

        String noteId = databaseReference.push().getKey();
        HashMap<String,Object> map=new HashMap<>();
        map.put("noteID",noteId);
        map.put("title",title);
        map.put("date",date);
        map.put("description",text);
        map.put("publisher", FirebaseAuth.getInstance().getCurrentUser().getUid());

        databaseReference.child("Notes").child(noteId).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(AddNote.this,"NOTE Saved",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddNote.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }else
                    Toast.makeText(AddNote.this,"NOTE Not Saved",Toast.LENGTH_SHORT).show();
            }
        });


    }


}
