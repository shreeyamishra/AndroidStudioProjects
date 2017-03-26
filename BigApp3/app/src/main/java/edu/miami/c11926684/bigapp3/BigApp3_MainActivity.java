package edu.miami.c11926684.bigapp3;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class BigApp3_MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, CustomDialog.DialogHelper {

    private static final int MY_PERMISSIONS_READ_REQUEST_STORAGE = 1;
    private static final int MY_PERMISSIONS_WRITE_REQUEST_STORAGE = 2;
    private static final int MY_PERMISSIONS_REQUEST_MIC = 3;
    public static final int EDIT_DIALOG = 13;

    DataBase db;
    protected App myApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_app3__main);
        listView = ((ListView) findViewById(R.id.list));
        myApp = (App)getApplication();
        db = myApp.db;
        askForPermission();
    }

    private void askForPermission() {
        // Here, thisActivity is the current activity

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_WRITE_REQUEST_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }

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

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_READ_REQUEST_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            playSong();
            fillDB();
            fillList();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_WRITE_REQUEST_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Please give me access to write to your storage!!", Toast.LENGTH_LONG);

                }

            }
            case MY_PERMISSIONS_READ_REQUEST_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Please give me access to read your storage!!", Toast.LENGTH_LONG);

                }

            }
            case MY_PERMISSIONS_REQUEST_MIC: {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                        ||(grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                        || (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {


                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Missing some permissons", Toast.LENGTH_LONG);
                    askForPermission();
                    return;
                }

            }
            default:
                playSong();
                fillDB();
                fillList();


            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    MediaPlayer mediaPlayer;
    private void playSong() {
        try{

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

            audioCursor.moveToPosition(((int)(Math.random() * 100)) % audioCursor.getCount());

            mediaPlayer = new MediaPlayer();

            //audioCursor.moveToPosition(index);
            String audioFilename = audioCursor.getString(audioCursor.getColumnIndex(
                    MediaStore.Audio.Media.DATA));

            mediaPlayer.setDataSource(audioFilename);
            mediaPlayer.prepare();
            mediaPlayer.start();

        } catch (Exception c) {
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
                Toast.makeText(this, "Couldn't play music", Toast.LENGTH_LONG);
                e.printStackTrace();
            }
        }
    }

    ListView listView;
    private void fillList() {
        System.out.println("Made it!!!");
        String[] fromHashMapFieldNames = {"text", "image", "check"};
        int[] toListRowFieldIds = {R.id.detail_text, R.id.image, R.id.checkbox};

        HashMap<String,Object> oneItem;
        ArrayList<HashMap<String,Object>> listItems = new ArrayList<HashMap<String,Object>>();

        Cursor cursor = db.fetchAllThoughts();
        String[] fieldNames = cursor.getColumnNames();
        cursor.moveToFirst();
        Uri image;

        do {
                oneItem = new HashMap<String,Object>();
            for (int index = 0; index < fieldNames.length; index++) {
                if (fieldNames[index].equals("thought") && cursor.getCount() > 0) {
                    oneItem.put("text", cursor.getString(index));
                    System.out.print("thought text: " + cursor.getString(index));
                } else if (fieldNames[index].equals("image_uri") && cursor.getCount() > 0) {
                    System.out.println("\nimage uri: " + cursor.getString(index));
                    image = Uri.parse(cursor.getString(index));
                    oneItem.put("image", image);
                } else if (fieldNames[index].equals("recording") && cursor.getCount() > 0) {
                    //if (cursor.getBlob(index) != null)
                        //oneItem.put("check", true);
                }
            }
            if ((new File(oneItem.get("image").toString())).exists()) {
                listItems.add(oneItem);
            } else {
                db.deleteThought(cursor.getInt(0));
            }

        } while (cursor.moveToNext());
        cursor.close();

        ListItemAdapter adapter = new ListItemAdapter(this, listItems,
                R.layout.list_item, fromHashMapFieldNames, toListRowFieldIds);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(listener);
    }

    private void fillDB() {
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name, column_index_id;
        String absolutePathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        System.out.println("URI :" + uri.toString());

        String[] projection = {MediaStore.Images.Media._ID, MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
        System.out.println("projection :" + projection);
        cursor = this.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        column_index_id = cursor.getColumnIndex(MediaStore.Images.Media._ID);
        System.out.println("column_index_data: " + column_index_data + "\n");
        System.out.println("column_index_folder_name: " + column_index_folder_name + "\n");
        int mediaId;
        //cursor.moveToFirst();
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);
            mediaId = cursor.getInt(column_index_id);
            if (db.getThoughtByMediaId(mediaId) == null) {
                ContentValues data = new ContentValues();
                System.out.println("Log:   media_id: " + mediaId);
                data.put("media_id", mediaId);
                data.put("thought", "");
                data.put("image_uri", absolutePathOfImage);
                db.addThought(data);

                System.out.println("absolute: " + absolutePathOfImage + "\n");
            }
        }
        cursor.close();
    }

    AdapterView.OnItemLongClickListener listener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            openDialog(id, position, EDIT_DIALOG);
            return true;
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        System.out.println("position: " + position);
        System.out.println("ID: " + id);

        openDialog(id,position, 0);


    }

    void openDialog(long id, int position, int whichDialog) {

        Bundle bundleToFragment = new Bundle();
        bundleToFragment.putLong("id", id + 1);
        bundleToFragment.putInt("which_dialog", whichDialog);
        CustomDialog theDialogFragment = new CustomDialog();
        System.out.println("made it");
        theDialogFragment.setArguments(bundleToFragment);
        System.out.println("made it");
        mediaPlayer.pause();
        ((ListItemAdapter) listView.getAdapter()).setItemPosition(position);
        theDialogFragment.show(getFragmentManager(),"my_custom_fragment");
    }

    private void clearReferences(){
        Activity currActivity = myApp.getCurrentActivity();
        if (this.equals(currActivity))
            myApp.setCurrentActivity(null);
    }

    @Override
    protected void onResume() {
        myApp.setCurrentActivity(this);
        mediaPlayer.start();
        super.onResume();
    }

    @Override
    protected void onPause() {
        clearReferences();
        mediaPlayer.pause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        clearReferences();
        mediaPlayer.stop();
        super.onDestroy();
    }

    @Override
    public void resume() {
        mediaPlayer.start();
    }

    @Override
    public void presentToast(boolean works) {
        if (works)
            Toast.makeText(this,"Goovy!! your TTS works",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,"You need to install TextToSpeech",
                    Toast.LENGTH_LONG).show();
    }
}

