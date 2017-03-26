package edu.miami.c11926684.bigapp1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.net.sip.SipAudioCall;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.Serializable;

public class BigApp1_MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PHOTO_SELECTION_FOR_P1 = 1;
    private static final int PHOTO_SELECTION_FOR_P2 = 2;
    private static final int BEGIN_GAME = 3;
//----------------------------------------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_app1__main);
        ((ImageButton)findViewById(R.id.first_user_layout_image)).setOnClickListener(this);
        ((ImageButton)findViewById(R.id.second_user_layout_image)).setOnClickListener(this);
        ((Button)findViewById(R.id.enter_game)).setOnClickListener(this);

        user1ImageUri = Uri.parse("android.resource://edu.miami.c11926684.bigapp1/" + R.drawable.kanye1);
        user2ImageUri = Uri.parse("android.resource://edu.miami.c11926684.bigapp1/" + R.drawable.kanye2);

        try {
            ImageButton imageButton = (ImageButton)findViewById(R.id.first_user_layout_image);
            Bitmap selectedImage = MediaStore.Images.Media.getBitmap(
                    this.getContentResolver(),user1ImageUri);
            imageButton.setImageBitmap(selectedImage);
            imageButton = (ImageButton)findViewById(R.id.second_user_layout_image);
            selectedImage = MediaStore.Images.Media.getBitmap(
                    this.getContentResolver(),user2ImageUri);
            imageButton.setImageBitmap(selectedImage);
        } catch (Exception e) {
            Toast.makeText(this, "Error setting photos", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        myClickHandler(v);
    }
    //------------------------------------------------------------------------------------------------------------------------
    private Uri user1ImageUri;
    private Uri user2ImageUri;
    //------------------------------------------------------------------------------------------------------------------------

    public void myClickHandler(View view) {
        int id = view.getId();
        Intent nextActivity;

        switch (id) {
            case R.id.first_user_layout_image:
                nextActivity = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(nextActivity,PHOTO_SELECTION_FOR_P1);
                break;
            case R.id.second_user_layout_image:
                nextActivity = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(nextActivity,PHOTO_SELECTION_FOR_P2);
                break;
            case R.id.enter_game:
                nextActivity = prepareForStart();
                startActivityForResult(nextActivity, BEGIN_GAME);
                break;
            default:
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data ) {
        ImageButton imageButton;
        Uri imageURI;

        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case PHOTO_SELECTION_FOR_P1:
                    imageButton = (ImageButton)findViewById(R.id.first_user_layout_image);
                    imageURI = data.getData(); user1ImageUri = imageURI;
                    try {
                        Bitmap selectedImage = MediaStore.Images.Media.getBitmap(
                                this.getContentResolver(),imageURI);
                        imageButton.setImageBitmap(selectedImage);
                    } catch (Exception e) {
                        Toast.makeText(this, "Error setting photo", Toast.LENGTH_LONG).show();
                    }
                    break;
                case PHOTO_SELECTION_FOR_P2:
                    imageButton = (ImageButton)findViewById(R.id.second_user_layout_image);
                    imageURI = data.getData(); user2ImageUri = imageURI;
                    try {
                        Bitmap selectedImage = MediaStore.Images.Media.getBitmap(
                                this.getContentResolver(),imageURI);
                        imageButton.setImageBitmap(selectedImage);
                    } catch (Exception e) {
                        Toast.makeText(this, "Error setting photo", Toast.LENGTH_LONG).show();
                    }
                    break;
                case BEGIN_GAME:
                    Player.decrementPlayers();
                    Player.decrementPlayers();
                    break;
                default:
                    break;
            }
        }
    }

    private Intent prepareForStart() {

        Intent nextActivity;
        String user1Name = ((EditText)(findViewById(R.id.first_user_layout_name))).getText().toString();
        String user2Name = ((EditText)(findViewById(R.id.second_user_layout_name))).getText().toString();

        Player user1 = new Player(user1Name, user1ImageUri);
        Player user2 = new Player(user2Name, user2ImageUri);

        nextActivity = new Intent(this, BigApp1_MainActivity_AfterStart.class);
        nextActivity.putExtra("user1", user1);
        nextActivity.putExtra("user2", user2);
        return nextActivity;
    }
}
