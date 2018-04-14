package com.example.michael.notekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class NoteListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NoteListActivity.this, NoteActivity.class));
            }
        });

        initializeDisplayContent();
    }

    private void initializeDisplayContent() {
        final ListView listNotes = (ListView) findViewById(R.id.list_notes);

        //data manager to get all notes, load into adapter to populate our list view!
        List<NoteInfo> notes = DataManager.getInstance().getNotes();
        ArrayAdapter<NoteInfo> adapterNotes = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);

        listNotes.setAdapter(adapterNotes);

        //accepts a reference to an interface, which can be implemented on our notelistactivity
        //take advantage of java anonymous class here!
        listNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //we want to create an intent and show our note activity

                //need to qualify reference to be NoteListActivity.this
                Intent intent = new Intent(NoteListActivity.this, NoteActivity.class);

                //the selected note is now passed in with the intent!
                //we no longer need this when we use the note position, since the datasource is
                //a singleton and both the noteactivity and the notelistactivity have access to it.
//                NoteInfo note = (NoteInfo) listNotes.getItemAtPosition(position);

                intent.putExtra(NoteActivity.NOTE_POSITION, position);
                //start our activity from the intent!
                startActivity(intent);
            }
        });
    }

}
