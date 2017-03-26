package edu.miami.c11926684.miniapp14;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MiniApp14_MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_STORAGE = 0;
    ImageView imageView;
    ArrayList<String> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_app14__main);

        imageView = ((ImageView) findViewById(R.id.image));
        checkForPermission();
    }

    private void checkForPermission() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            images = getAllShownImagesPath();
            setImage();
            playSong();
            sideQueue.run();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    images = getAllShownImagesPath();
                    setImage();
                    playSong();
                    sideQueue.run();
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Please give me access to your storage!!", Toast.LENGTH_LONG);
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private ArrayList<String> getAllShownImagesPath() {
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        String absolutePathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        System.out.println("URI :" + uri.toString());

        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
        System.out.println("projection :" + projection);
        cursor = this.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

        System.out.println("column_index_data: " + column_index_data + "\n");
        System.out.println("column_index_folder_name: " + column_index_folder_name + "\n");
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);


            System.out.println("absolute: " + absolutePathOfImage + "\n");

            listOfAllImages.add(absolutePathOfImage);
        }
        return listOfAllImages;
    }

    MediaPlayer mediaPlayer;
    ArrayList<String> songs;

    public void playSong() {
        if(true) {

            String[] queryFields = {
                    MediaStore.Audio.Media._ID,
                    MediaStore.Audio.Media.TITLE,
                    MediaStore.Audio.Media.DATA     //----Path to file on disk
            };
            String[] displayFields = {
                    MediaStore.Audio.Media.TITLE
            };
            int[] displayViews = {android.R.id.text1};

            Cursor audioCursor = getContentResolver().query(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,queryFields,null,null,
                    MediaStore.Audio.Media.TITLE + " ASC");
            audioCursor.moveToFirst();

            mediaPlayer = new MediaPlayer();

            //audioCursor.moveToPosition(index);
            String audioFilename = audioCursor.getString(audioCursor.getColumnIndex(
                    MediaStore.Audio.Media.DATA));
            try {
                mediaPlayer.setDataSource(audioFilename);
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                //----Should do something here
            }
        } else {
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
    }

    private void setImage() {
        Uri imageURI = Uri.parse(images.get(index));

        imageView.setImageURI(Uri.fromFile(new File(images.get(index))));
        //imageView.setImageURI(imageURI);

//        try {
//            Bitmap selectedImage = MediaStore.Images.Media.getBitmap(
//                    this.getContentResolver(),imageURI);
//            imageView.setImageBitmap(selectedImage);
//        } catch (Exception e) {
//            Toast.makeText(this, "Error setting photo", Toast.LENGTH_LONG).show();
//            e.printStackTrace();
//        }

    }

    int index = 0;

    Runnable sideQueue = new Runnable() {
        Handler handler = new Handler();
        @Override
        public void run() {
            index = (index + 1)%images.size();
            setImage();
            handler.postDelayed(sideQueue, 10000);
        }
    };
}


