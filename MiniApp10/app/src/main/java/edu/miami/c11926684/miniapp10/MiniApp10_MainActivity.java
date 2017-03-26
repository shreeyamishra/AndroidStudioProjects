package edu.miami.c11926684.miniapp10;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MiniApp10_MainActivity extends Activity implements TextToSpeech.OnInitListener, TextToSpeech.OnUtteranceCompletedListener{

    private TextToSpeech mySpeaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_app10__main);

        setListenerForButtons();

        mySpeaker = new TextToSpeech(this,this);
        mySpeaker.setOnUtteranceCompletedListener(this);
        sideQueue.postDelayed(task, 10000);
    }

    private void setListenerForButtons() {
        ((Button) findViewById(R.id.b1)).setOnClickListener(ear);
        ((Button) findViewById(R.id.b2)).setOnClickListener(ear);
        ((Button) findViewById(R.id.b3)).setOnClickListener(ear);
        ((Button) findViewById(R.id.b4)).setOnClickListener(ear);
        ((Button) findViewById(R.id.b5)).setOnClickListener(ear);
        ((Button) findViewById(R.id.b6)).setOnClickListener(ear);
        ((Button) findViewById(R.id.b7)).setOnClickListener(ear);
        ((Button) findViewById(R.id.b8)).setOnClickListener(ear);

    }

    public View.OnClickListener ear = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            myClickHandler(v);
        }
    };

    public void myClickHandler(View view) {
        sideQueue.removeCallbacks(task);
        CharSequence text = ((Button) findViewById(view.getId())).getText();
        mySpeaker.speak(text.toString(),TextToSpeech.QUEUE_ADD,null);
        //mySpeaker.speak(text, TextToSpeech.QUEUE_ADD, null, "1");

        sideQueue.postDelayed(task, 5000);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            //Toast.makeText(this,R.string.talk_prompt,Toast.LENGTH_SHORT).show();
            //((Button)findViewById(R.id.speak)).setClickable(true);
        } else {
            Toast.makeText(this,"You need to install TextToSpeech",
                    Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public void onUtteranceCompleted(String utteranceId) {

    }

    private String wordToSay;

    private Handler sideQueue = new Handler();

    private Runnable task = new Runnable() {
        @Override
        public void run() {
            mySpeaker.speak("I have nothing to say", TextToSpeech.QUEUE_ADD, null);
            sideQueue.postDelayed(task, 5000);
        }
    };

    public void onDestroy() {

        super.onDestroy();
        sideQueue.removeCallbacks(task);
        mySpeaker.shutdown();
    }
}
