package edu.miami.c11926684.bigapp1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Rating;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

public class BigApp1_MainActivity_AfterStart extends AppCompatActivity {

    private final int BEGIN_GAME = 1;
    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------

    Player user1;
    Player user2;
    int timer = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_app1__main_activity__after_start);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        user1 = (Player)getIntent().getSerializableExtra("user1");
        user2 = (Player)getIntent().getSerializableExtra("user2");

        setDisplay();


    }

    private void setDisplay() {
        ((TextView)(findViewById(R.id.first_user_layout_name))).setText(user1.getName());
        ((TextView)(findViewById(R.id.second_user_layout_name))).setText(user2.getName());
        ImageView picView1 = (ImageView)findViewById(R.id.first_user_layout_image);
        ImageView picView2 = (ImageView)findViewById(R.id.second_user_layout_image);
        try {
            Bitmap selectedPicture = MediaStore.Images.Media.getBitmap(
                    this.getContentResolver(),user1.getPhoto());
            picView1.setImageBitmap(selectedPicture);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
            Toast.makeText(this, "Something weird happened? please call woody for help",Toast.LENGTH_LONG).show();
        }
        try {
            Bitmap selectedPicture = MediaStore.Images.Media.getBitmap(
                    this.getContentResolver(),user2.getPhoto());
            picView2.setImageBitmap(selectedPicture);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
            Toast.makeText(this, "Something weird happened? please call woody for help",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bigapp1_in_game_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.item1:
                ((RatingBar) findViewById(R.id.first_rating_bar)).setRating(0);
                ((RatingBar) findViewById(R.id.second_rating_bar)).setRating(0);
                break;
            case R.id.item2:
                timer = 100;
                break;
            case R.id.item3:
                timer = 200;
                break;
            case R.id.item4:
                timer = 500;
                break;
            case R.id.item5:
                timer = 1000;
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    //for the tic tac toe grid make a three dimensional array and have a pointer in every index point a specific button.

    public void myClickHandler(View view){
        int id = view.getId();
        Intent nextActivity;

        switch (id) {
            case R.id.enter_game:
                nextActivity = prepareForStart();
                startActivityForResult(nextActivity, BEGIN_GAME);
                break;
            default:
                break;
        }
    }

    private Intent prepareForStart() {

        Intent nextActivity;

        nextActivity = new Intent(this, BigApp1_MainActivity_Game.class);
        nextActivity.putExtra("user1", user1);
        nextActivity.putExtra("user2", user2);
        nextActivity.putExtra("timer", timer);

        return nextActivity;
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("user1", user1);
        returnIntent.putExtra("user2", user2);
        setResult(RESULT_CANCELED,returnIntent);
        finish();
        //super.onBackPressed();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case BEGIN_GAME:
                    if (data.getBooleanExtra("didSomeoneWin", false)) {
                        if (data.getBooleanExtra("whoseTurn", false)) {
                            RatingBar theRatingBar = (RatingBar)findViewById(R.id.first_rating_bar);
                            theRatingBar.setRating(theRatingBar.getRating() + 1);
                            if (theRatingBar.getRating() == 5) {
                                finish();
                            }
                        } else {
                            RatingBar theRatingBar = (RatingBar)findViewById(R.id.second_rating_bar);
                            theRatingBar.setRating(theRatingBar.getRating() + 1);
                            if (theRatingBar.getRating() == 5) {
                                finish();
                            }
                        }

                    }
                    break;
                default:
                    break;
            }
        }
    }

}
