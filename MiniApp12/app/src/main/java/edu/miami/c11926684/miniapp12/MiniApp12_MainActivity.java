package edu.miami.c11926684.miniapp12;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceGroup;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.Camera.Size;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;

public class MiniApp12_MainActivity extends AppCompatActivity implements SurfaceHolder.Callback, Camera.PictureCallback, MediaPlayer.OnCompletionListener, View.OnClickListener {

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;
    private static final int MY_PERMISSIONS_REQUEST_STORAGE = 2;
    private  boolean save = false;
    private SurfaceView cameraPreview;
    private SurfaceHolder surfaceHolder;
    private Camera camera;
    private ImageView takenPhoto;
    private String cameraFileName;
    ProgressBar sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_app12__main);

        ((Button) findViewById(R.id.take_photo_button)).setOnClickListener(this);
        ((Button) findViewById(R.id.exit_app)).setOnClickListener(this);

        cameraPreview = (SurfaceView)findViewById(R.id.camera_view);
        surfaceHolder = cameraPreview.getHolder();
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceHolder.addCallback(this);
        takenPhoto = (ImageView)findViewById(R.id.image_view);

        cameraFileName = Environment.getExternalStorageDirectory().toString() +
                "/Download/" + "MiniApp12Pic"; //getString(R.string.camera_file_name);
        askForPermission();
        sp = ((ProgressBar) findViewById(R.id.progressBar));
        sp.setVisibility(View.VISIBLE);
        myHandler.postDelayed(sideQueue,1000);
    }

    private void askForPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

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
    }

    int[] grantResults;

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        this.grantResults = grantResults;
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    openCamera();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        try {
            camera.setPreviewDisplay(holder);
//----This will make the surface be changed
            camera.startPreview();
        } catch (Exception e) {
            //----Do something
            System.out.println("Exception");
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Camera.Parameters cameraParameters;
        boolean sizeFound;

        sizeFound = false;
        if (camera != null){
            cameraParameters = camera.getParameters();
            for (Size size : cameraParameters.getSupportedPreviewSizes()) {
                if (size.width == width || size.height == height) {
                    width = size.width;
                    height = size.height;
                    sizeFound = true;
                    break;
                }
            }
            if (sizeFound) {
                cameraParameters.setPreviewSize(width, height);
                camera.setParameters(cameraParameters);
            } else {
                Toast.makeText(getApplicationContext(),
                        "Camera cannot do " + width + "x" + height, Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        System.out.println("Photo taken method called");

        //takenPhoto.setVisibility(View.VISIBLE);
        photoBitmap = BitmapFactory.decodeByteArray(data,0,data.length);
        takenPhoto.setImageBitmap(photoBitmap);
        camera.startPreview();
    }

    private void openCamera() {

        if ((camera = Camera.open(0)) == null) {
            Toast.makeText(getApplicationContext(),"Camera not available!",
                    Toast.LENGTH_LONG).show();
        } else {
//----This will make the surface be created
            cameraPreview.setVisibility(View.VISIBLE);
        }
    }

    private void closeCamera() {

        cameraPreview.setVisibility(View.INVISIBLE);
        camera.stopPreview();
        camera.release();
    }

    @Override
    public void onResume() {
        super.onResume();
        askForPermission();
        if (camera != null) {
            openCamera();
        }
    }
    //-----------------------------------------------------------------------------
    @Override
    public void onPause() {
        super.onPause();
//----Alternatively, just be greedy and lock the camera when opened
        if (camera != null) {
            //closeCamera();
        }

    }

    @Override
    protected void onDestroy() {
        if (camera != null) {
            closeCamera();
        }
        super.onDestroy();
    }

    Bitmap photoBitmap;

    @Override
    public void onClick(View v) {
        int id = v.getId();
        FileOutputStream photoStream;

        switch (id) {
            case R.id.take_photo_button:
                save = true;
                camera.takePicture(null,null,null,this);
                break;
            case R.id.exit_app:
                if (save) {
                    try {
                        photoStream = new FileOutputStream(cameraFileName);
                        photoBitmap.compress(Bitmap.CompressFormat.JPEG,100,photoStream);
                        photoStream.close();
                        Toast.makeText(this,"photo saved to" + cameraFileName,
                                Toast.LENGTH_LONG).show();
                        finish();
                    } catch (IOException e) {
                        Toast.makeText(this,"ERROR: Cannot save photo to file",
                                Toast.LENGTH_LONG).show();
                    }
                }
                break;
            default:
                break;
        }
    }
    Handler myHandler = new Handler();
    Runnable sideQueue = new Runnable() {

        @Override
        public void run() {

                sp.setVisibility(View.GONE);
                openCamera();

        }
    };
}
