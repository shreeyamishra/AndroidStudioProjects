package edu.miami.c11926684.bigapp3;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CustomDialog extends DialogFragment implements TextToSpeech.OnInitListener, View.OnClickListener, TextToSpeech.OnUtteranceCompletedListener {

    private static final int RECORDER_SAMPLERATE = 8000;
    private static final int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_MONO;
    private static final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;

    //-----------------------------------------------------------------------------------------------------------------

    View dialogView;
    DataBase db;
    EditText editText;
    Long id;
    DialogHelper helper;
    byte[] audio;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);

        db = ((App) getActivity().getApplication()).db;
        getDialog().setTitle("Here's your pic");

        id = this.getArguments().getLong("id");

        ContentValues obj = db.getThoughtById(id);
        String text = obj.getAsString("thought");
        String imageURI = obj.getAsString("image_uri");
        audio = obj.getAsByteArray("recording");

        System.out.println("byte[] : " + audio);

        dialogView = inflater.inflate(R.layout.activity_custom_dialog, container);
        ImageView imageView = (ImageView) dialogView.findViewById(R.id.d_image);

        imageView.setImageURI(Uri.parse(imageURI));
        editText = ((EditText) dialogView.findViewById(R.id.text_edit));
        editText.setText(text);
        ((Button) dialogView.findViewById(R.id.dismiss)).
                setOnClickListener(this);
        ((Button) dialogView.findViewById(R.id.play)).
                setOnClickListener(this);
        if (getArguments().getInt("which_dialog") == BigApp3_MainActivity.EDIT_DIALOG) {
            Button button;
            button = ((Button) dialogView.findViewById(R.id.record));
            button.setVisibility(View.VISIBLE);
            button.setOnClickListener(this);
        }
        mySpeaker = new TextToSpeech(getActivity(),this);
        mySpeaker.setOnUtteranceCompletedListener(this);

        helper = (BigApp3_MainActivity) getActivity();
        return (dialogView);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dismiss:
                ContentValues thought = new ContentValues();
                thought.put("thought", editText.getText().toString());
                db.updateThought(id,thought);
                ListView listView = ((ListView)(getActivity().findViewById(R.id.list)));
                ((ListItemAdapter)listView.getAdapter()).notifyDataSetChanged();
                mySpeaker.stop();
                helper.resume();
                dismiss();
                break;
            case R.id.record:
                if (recording) {
                    ((Button) dialogView.findViewById(R.id.record)).setText("record");
                    if (recorder != null) {
                        try {
                            recorder.stop();
                            recorder.release();
                            recorder = null;
                            recordingThread = null;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        recording = false;
                    }
                } else {
                    try {
                        startRecording();
                    } catch (Exception e) {
                        Log.i("AUDIO ERROR","PREPARING RECORDER");
                        e.printStackTrace();
                    }

                }
                break;
            case R.id.play:
                player = new MediaPlayer();

                CharSequence text = editText.getText();
                mySpeaker.speak(text.toString(),TextToSpeech.QUEUE_ADD,null);
                mySpeaker.speak("         ",TextToSpeech.QUEUE_ADD,null);
                break;
            default:
                break;
        }
    }

    TextToSpeech mySpeaker;
    MediaPlayer player;

    int BufferElements2Rec = 1024; // want to play 2048 (2K) since 2 bytes we use only 1024
    int BytesPerElement = 2; // 2 bytes in 16bit format
    boolean recording = false;
    AudioRecord recorder;
    private Thread recordingThread;

    private void startRecording() throws Exception {

        recorder = new AudioRecord(MediaRecorder.AudioSource.MIC,
                RECORDER_SAMPLERATE, RECORDER_CHANNELS,
                RECORDER_AUDIO_ENCODING, BufferElements2Rec * BytesPerElement);
        if (recorder != null) {
            recording = true;
            ((Button) dialogView.findViewById(R.id.record)).setText("stop");

            recordingThread = new Thread(new Runnable() {
                public void run() {
                    writeAudioDataToDB();
                }
            }, "AudioRecorder Thread");
            recordingThread.start();
        }


    }

    public void writeAudioDataToDB() {

        short sData[] = new short[BufferElements2Rec];

        while (recording) {
            // gets the voice output from microphone to byte format

            recorder.read(sData, 0, BufferElements2Rec);
            System.out.println("Short writing to file" + sData.toString());

            // // writes the data to data base from buffer
            // // stores the voice buffer
            byte bData[] = short2byte(sData);
            ContentValues obj = new ContentValues();
            obj.put("recording", bData);
            db.updateThought(id, obj);
            //os.write(bData, 0, BufferElements2Rec * BytesPerElement);

        }
    }

    //convert short to byte
    private byte[] short2byte(short[] sData) {
        int shortArrsize = sData.length;
        byte[] bytes = new byte[shortArrsize * 2];
        for (int i = 0; i < shortArrsize; i++) {
            bytes[i * 2] = (byte) (sData[i] & 0x00FF);
            bytes[(i * 2) + 1] = (byte) (sData[i] >> 8);
            sData[i] = 0;
        }
        return bytes;

    }

    private void playMp3(byte[] mp3SoundByteArray) throws Exception {

        // create temp file that will hold byte array
        File tempMp3 = File.createTempFile("bigApp3", "mp3", getActivity().getCacheDir());
        tempMp3.deleteOnExit();
        FileOutputStream fos = new FileOutputStream(tempMp3);
        fos.write(mp3SoundByteArray);
        fos.close();

        // resetting mediaplayer instance to evade problems
        player.reset();

        // In case you run into issues with threading consider new instance like:
        // MediaPlayer mediaPlayer = new MediaPlayer();

        // Tried passing path directly, but kept getting
        // "Prepare failed.: status=0x1"
        // so using file descriptor instead
        FileInputStream fis = new FileInputStream(tempMp3);
        player.setDataSource(fis.getFD());

        player.prepare();
        player.start();

    }

    boolean canSpeak = false;

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            //Toast.makeText(getActivity(),"Groovy!! your TTS works",Toast.LENGTH_SHORT).show();
            helper.presentToast(true);
            ((Button) dialogView.findViewById(R.id.play)).setClickable(true);
            ((Button) dialogView.findViewById(R.id.record)).setClickable(true);
            canSpeak = true;
            this.onClick(dialogView.findViewById(R.id.play));
        } else {
            helper.presentToast(false);
            //Toast.makeText(getActivity(),"You need to install TextToSpeech",
                    //Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUtteranceCompleted(String utteranceId) {
        if (audio != null) {
            try {
                playMp3(audio);
            } catch (Exception e) {
                Log.i("AUDIO ERROR","PREPARING TO PLAY");
                e.printStackTrace();
                //----Should do something here
            }
        }
    }

    public interface DialogHelper {
        void resume();

        void presentToast(boolean works);
    }
}
