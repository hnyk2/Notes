package ir.shariaty.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ir.shariaty.notes.Adapter.NoteAdapter;
import ir.shariaty.notes.Model.Note;

public class MainActivity extends AppCompatActivity implements NoteAdapter.OnNoteListener {

    List<Note> NoteList;
    RecyclerView recyclerView;
    NoteAdapter adapter;
    FloatingActionButton fab;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar =  findViewById(R.id.logout_toolbar);
        setSupportActionBar(toolbar);

        NoteList = fill_with_data();
        recyclerView = findViewById(R.id.recView);
        adapter = new NoteAdapter(getApplication(),NoteList,this);
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
        final String publisher = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        final List<Note> note = new ArrayList<>();
        Query query = FirebaseDatabase.getInstance().getReference().child("Notes").orderByChild("publisher").equalTo(publisher);
        query.addValueEventListener(new ValueEventListener() {
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


    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(this,NoteItemActivity.class);
        intent.putExtra("selected_note",NoteList.get(position));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        //log out from firebase

        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(MainActivity.this, StartActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();


        return super.onOptionsItemSelected(item);
    }
}
