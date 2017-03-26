package edu.miami.c11926684.miniapp11;

import android.content.res.AssetFileDescriptor;
import android.media.MediaMetadata;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

public class MiniApp11_MainActivity extends AppCompatActivity implements View.OnClickListener, MediaPlayer.OnCompletionListener {

    private final String VIDEO_PATH = "https://dl.dropbox.com/s/du8j57fngsu0zxo/What%20Happens%20if%20the%20Planets%20Align.mp4";
    //private final String VIDEO_PATH = "https://www.dropbox.com/playlist/What%20Happens%20if%20the%20Planets%20Align.mp4";
    //private final String VIDEO_PATH = "http://www.law.duke.edu/cspd/contest/finalists/entries/documentariesandyou.mp4";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_app11__main);

        setButtons();
        playSong();
    }

    MediaPlayer mediaPlayer;

    public void playSong() {
        mediaPlayer = new MediaPlayer();
        try {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = new MediaPlayer();
            }

            AssetFileDescriptor song = getAssets().openFd("Coldplay - Fix You.mp3");
            mediaPlayer.setDataSource(song.getFileDescriptor(), song.getStartOffset(), song.getLength());
            song.close();

            mediaPlayer.prepare();
            //m.setVolume(1f, 1f);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setButtons() {
        ((Button) findViewById(R.id.play_button)).setOnClickListener(this);
        ((Button) findViewById(R.id.pause_button)).setOnClickListener(this);
        ((Button) findViewById(R.id.stop_button)).setOnClickListener(this);
    }

    VideoView videoScreen;
    boolean videoPlaying = false;


    @Override
    public void onClick(View v) {
        int id = v.getId();

        Uri videoURI;

        switch (id) {
            case R.id.play_button:
                if (!videoPlaying) {
                    mediaPlayer.pause();
                    videoScreen = (VideoView) findViewById(R.id.video_view);
                    videoURI = Uri.parse(VIDEO_PATH);
                    videoScreen.setVideoURI(videoURI);
                    videoScreen.setOnCompletionListener(this);
                    videoScreen.setVisibility(View.VISIBLE);
                    videoScreen.start();
                    videoPlaying = true;
                }
                break;
            case R.id.pause_button:
                if (videoPlaying) {
                    mediaPlayer.start();
                    videoScreen.pause();
                    videoPlaying = false;
                }
                break;
            case R.id.resume_button:
                if (!videoPlaying) {
                    mediaPlayer.pause();
                    videoScreen.start();
                    videoPlaying = true;
                }
            case R.id.stop_button:
                if (videoPlaying) {
                    mediaPlayer.start();
                    videoScreen.stopPlayback();
                    videoScreen.setVisibility(View.INVISIBLE);
                    videoPlaying = false;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        videoScreen.setVisibility(View.INVISIBLE);
    }
}
