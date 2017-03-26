package edu.miami.c11926684.miniapp4;

import android.content.Intent;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

public class MiniApp4_MainActivity_StatusBar extends AppCompatActivity {
    private ProgressBar progress;
    private int decrement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_app4__main_activity__status_bar);

        progress = (ProgressBar)findViewById(R.id.progress_bar);
        progress.setProgress(progress.getMax());
        decrement = getResources().getInteger(R.integer.bar_decrement_time);
        sideQueue.run();

    }

    private Runnable sideQueue = new Runnable() {

        private Handler myHandler = new Handler();
        @Override
        public void run() {


            //System.out.println("hey there" + getResources().getInteger(R.integer.bar_decrement_time));
            //System.out.println("hey there " + progress.getProgress());
            if (progress.getProgress() > 0) {
                progress.setProgress(progress.getProgress() - decrement);
            } else {
                Intent returnIntent = new Intent();
                setResult(RESULT_OK,returnIntent);
                finish();
            }
            if (!myHandler.postDelayed(sideQueue,decrement)) {
                Log.e("ERROR","Cannot postDelayed");
            }

        }
    };



}
