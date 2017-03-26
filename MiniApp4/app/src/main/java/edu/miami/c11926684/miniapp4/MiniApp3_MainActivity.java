package edu.miami.c11926684.miniapp4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;

public class MiniApp3_MainActivity extends AppCompatActivity {

    private static final int STATUS_BAR_ACTIVITY = 5;
    private RatingBar theRatingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_app3__main);

        theRatingBar = (RatingBar)findViewById(R.id.rating_bar);
        theRatingBar.setRating(0);
    }

    public void myClickHandler(View view){

        int id = view.getId();
        Intent newIntent;

        switch (id) {
            case R.id.fancy_button :
                newIntent = new Intent(this, MiniApp4_MainActivity_StatusBar.class);
                startActivityForResult(newIntent,STATUS_BAR_ACTIVITY);
                break;
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        System.out.println("made it!!! " + requestCode);
        switch (requestCode) {
            case STATUS_BAR_ACTIVITY:
                if (resultCode != RESULT_CANCELED) {
                    theRatingBar.setRating(theRatingBar.getRating() + 1);
                    if (theRatingBar.getRating() == 5) {
                        finish();
                    }
                } else {
                    Toast.makeText(this, "Loser, stop hitting the back button", Toast.LENGTH_LONG);
                }
                break;
            default:

                break;
        }
    }
}
