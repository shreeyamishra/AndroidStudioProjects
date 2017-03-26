package edu.miami.c11926684.miniapp17;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MiniApp17_MainActivity extends AppCompatActivity {

    private static String PREFERENCES_NAME = "DataPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_app17__main);

        SharedPreferences persistentData;
        SharedPreferences.Editor editor;

        persistentData = getSharedPreferences(PREFERENCES_NAME,MODE_PRIVATE);
        long index = persistentData.getLong("last_num", 0);
        index++;
        editor = persistentData.edit();
        editor.putLong("last_num",index);
        editor.commit();
        ((TextView) findViewById(R.id.tv)).setText(Long.toString(index));
    }
}
