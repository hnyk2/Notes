package ir.shariaty.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ir.shariaty.notes.Adapter.NoteAdapter;
import ir.shariaty.notes.Model.Note;

public class MainActivity extends AppCompatActivity {

    List<Note> NoteList;
    RecyclerView recyclerView;
    NoteAdapter adapter;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NoteList = fill_with_data();
        recyclerView = (RecyclerView) findViewById(R.id.recView);
        adapter = new NoteAdapter(getApplication(),NoteList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        fab = findViewById(R.id.floating_action_button);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddNote.class));

            }
        });


    }

    private List<Note> fill_with_data() {
        final List<Note> note = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("Notes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                NoteList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Note note1 = dataSnapshot.getValue(Note.class);
                            note.add(note1);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return note;
    }


}
