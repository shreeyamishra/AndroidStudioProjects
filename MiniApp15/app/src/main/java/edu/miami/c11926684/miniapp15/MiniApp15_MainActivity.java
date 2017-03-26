package edu.miami.c11926684.miniapp15;

import android.content.ContentValues;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

public class MiniApp15_MainActivity extends AppCompatActivity implements View.OnClickListener{

    DataBase db;
    EditText thought;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_app15__main);

        db = ((App)getApplication()).db;
        thought = ((EditText) findViewById(R.id.edit));
        ((Button)findViewById(R.id.save_button)).setOnClickListener(this);
        ((Button)findViewById(R.id.history_button)).setOnClickListener(this);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        //db.close();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.save_button:
                ContentValues item = new ContentValues();
                item.put("thought", thought.getText().toString());
                Calendar c = Calendar.getInstance();
                String date = Integer.toString(c.get(Calendar.MONTH))
                        + " / "
                        + Integer.toString(c.get(Calendar.DAY_OF_WEEK))
                        + " / "
                        + Integer.toString(c.get(Calendar.YEAR));
                item.put("date", date);
                String time = Integer.toString(c.get(Calendar.HOUR))
                        + " : "
                        + Integer.toString(c.get(Calendar.MINUTE))
                        + " : "
                        + Integer.toString(c.get(Calendar.SECOND));
                item.put("time", time);
                db.addThought(item);
                thought.setText("");
                break;
            case R.id.history_button:
                Intent nextActivity = new Intent(this,NoteHistory.class);
                startActivity(nextActivity);
                break;
            default:
                break;
        }
    }
}
