package edu.miami.c11926684.miniapp1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MiniApp1_MainActivityInButton extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_app1__main_activity_in_button);
        Log.i("IN MainActivityInButton","Created OK");
    }
}
