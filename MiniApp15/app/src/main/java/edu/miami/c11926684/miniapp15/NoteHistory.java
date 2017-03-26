package edu.miami.c11926684.miniapp15;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class NoteHistory extends AppCompatActivity implements AdapterView.OnItemLongClickListener {

    DataBase db;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_history);

        db = ((App)getApplication()).db;
        listView = ((ListView) findViewById(R.id.list));
        listView.setOnItemLongClickListener(this);
        fillList();
    }

    SimpleCursorAdapter cursorAdapter;


    private void fillList() {
        //SimpleCursorAdapter cursorAdapter;
        String[] displayFields = {
                "thought",
                "date",
                "time"
        };
        int[] displayViews = {
                R.id.field1,
                R.id.field2,
                R.id.field3
        };

        Cursor cursor = db.fetchAllThoughts();

        cursorAdapter = new SimpleCursorAdapter(this,
                R.layout.list_item,cursor,displayFields,
                displayViews,0);
        listView.setAdapter(cursorAdapter);

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        db.deleteThought(id);
        cursorAdapter.changeCursor(db.fetchAllThoughts());
        return true;
    }
}
