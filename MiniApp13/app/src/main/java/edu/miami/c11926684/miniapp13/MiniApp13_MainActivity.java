package edu.miami.c11926684.miniapp13;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class MiniApp13_MainActivity extends AppCompatActivity implements View.OnClickListener, TextToSpeech.OnInitListener, TextToSpeech.OnUtteranceCompletedListener{

    private static final int ACTIVITY_SELECT_PICTURE = 1;
    private static final int MY_PERMISSIONS_REQUEST_STORAGE = 2;
    private static final int MY_PERMISSIONS_REQUEST_MIC = 3;
    private boolean imageWasSet = false;
    EditText editText;
    private TextToSpeech mySpeaker;
    private String recordFileName;
    boolean recording = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_app13__main);

        ((Button) findViewById(R.id.record)).setOnClickListener(this);
        ((Button) findViewById(R.id.record)).setOnLongClickListener(longClickListener);
        ((ImageView) findViewById(R.id.image)).setOnClickListener(this);
        editText = ((EditText) findViewById(R.id.edit));

        mySpeaker = new TextToSpeech(this,this);
        mySpeaker.setOnUtteranceCompletedListener(this);

        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, ACTIVITY_SELECT_PICTURE);

        recordFileName = getApplicationContext().getExternalFilesDir(null).
                toString() + "/" + "recordings";
        Log.i("FILE NAME",recordFileName);
        askForPermission();
    }

    private void askForPermission() {
        // Here, thisActivity is the current activity

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECORD_AUDIO)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        MY_PERMISSIONS_REQUEST_MIC);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    MediaRecorder recorder;

    View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            Button button = (Button) findViewById(R.id.record);
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            recorder.setOutputFile(recordFileName);
            try {
                recorder.prepare();
                button.setText("stop");
                recording = true;
            } catch (IOException e) {
                Log.i("AUDIO ERROR","PREPARING RECORDER");
//----Should do something here
            }
            recorder.start();
            return true;
        }
    };

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data) {

        ImageView pictureView;
        Uri selectedURI;
        Bitmap selectedPicture;

        super.onActivityResult(requestCode,resultCode,data);

        switch (requestCode) {
            case ACTIVITY_SELECT_PICTURE:
                if (resultCode == Activity.RESULT_OK) {
                    pictureView = (ImageView)findViewById(R.id.image);
                    selectedURI = data.getData();
                    try {
                        selectedPicture = MediaStore.Images.Media.getBitmap(
                                this.getContentResolver(),selectedURI);
                        pictureView.setImageBitmap(selectedPicture);
                        imageWasSet = true;
                        editText.setText("");
                    } catch (Exception e) {
//----Should do something here
                    }
                } else if (imageWasSet){
                    //Do nothing
                } else {
                    finish();
                }
                break;
        }
    }
    MediaPlayer player;
    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch(id) {
            case R.id.record:
                Button button = (Button) findViewById(R.id.record);
                if (recording) {
                    button.setText("play");
                    recorder.stop();
                    recorder.release();
                    recording = false;
                } else {
                    CharSequence text = editText.getText();
                    mySpeaker.speak(text.toString(),TextToSpeech.QUEUE_ADD,null);
                    mySpeaker.speak("         ",TextToSpeech.QUEUE_ADD,null);
                    player = new MediaPlayer();
                    player.setOnCompletionListener(onAudioCompletion);
                    try {
                        player.setDataSource(recordFileName);
                        player.prepare();
                    } catch (IOException e) {
                        Log.i("AUDIO ERROR","PREPARING TO PLAY");
//----Should do something here
                    }
                }
                break;
            case R.id.image:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, ACTIVITY_SELECT_PICTURE);
                break;
            default:
                break;
        }
    }

    private MediaPlayer.OnCompletionListener onAudioCompletion =
            new MediaPlayer.OnCompletionListener() {

                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.release();
                }
            };

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
}
