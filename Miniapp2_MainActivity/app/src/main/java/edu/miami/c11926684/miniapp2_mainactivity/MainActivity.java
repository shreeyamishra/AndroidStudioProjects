package edu.miami.c11926684.miniapp2_mainactivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //------------------------------------------------------------------------------------------------------------------------
    private static final int ACTIVITY_SELECT_PICTURE = 1;
    //-------------------------------------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, ACTIVITY_SELECT_PICTURE);

    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data) {

        ImageView pictureView;
        Uri selectedURI;
        Bitmap selectedPicture;

        super.onActivityResult(requestCode,resultCode,data);

        switch (requestCode) {
            case ACTIVITY_SELECT_PICTURE:
                if (resultCode == Activity.RESULT_OK) {
                    pictureView = (ImageView)findViewById(R.id.selected_picture);
                    selectedURI = data.getData();
                    try {
                        selectedPicture = MediaStore.Images.Media.getBitmap(
                                this.getContentResolver(),selectedURI);
                        pictureView.setImageBitmap(selectedPicture);
                    } catch (Exception e) {
//----Should do something here
                    }
                }else {
                    finish();
                }
                break;
        }
    }

    public void myClickHandler(View view) {

        switch (view.getId()) {
            case R.id.like_button:
                Toast.makeText(this,"YOU LIKED THE PHOTO!",Toast.LENGTH_LONG).show();
                finish();
                break;
            case R.id.dislike_button:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, ACTIVITY_SELECT_PICTURE);
                break;
            default:
                break;
        }
    }
}
