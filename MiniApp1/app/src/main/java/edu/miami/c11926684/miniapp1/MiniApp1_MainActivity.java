package edu.miami.c11926684.miniapp1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MiniApp1_MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_app1__main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.i("IN onCreate","Created OK");

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    public void myClickHandler(View view) {

        Intent nextActivity;
        int id = view.getId();
        Vibrator buzzer;
        final long[] menuBuzz =
                {0,250,50,250,500,250,50,250,500,250,50,250,500,250,50,250};

        switch (id) {
            case R.id.the_button:
                Log.i("IN myClickHandler","The button has been clicked");
                nextActivity = new Intent();
                nextActivity.setClassName("edu.miami.c11926684.miniapp1",
                        "edu.miami.c11926684.miniapp1.MiniApp1_MainActivityInButton");
                startActivity(nextActivity);
                Log.i("IN myClickHandler","Started MiniApp1_MainActivityInButton");
                break;
            case R.id.the_buzz:
                Log.i("IN myClickHandler","The button has been clicked");
                buzzer = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                buzzer.vibrate(menuBuzz,-1);
                Log.i("IN myClickHandler","Started the Vibrator");
                break;
            case R.id.the_toast:
                Log.i("IN myClickHandler","The button has been clicked");
                Toast.makeText(this,"Toast is Hotter than a pepper ready!",Toast.LENGTH_LONG).show();
                Log.i("IN myClickHandler","Started the toast tag");
                break;
            default:
                break;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mini_app1__main, menu);
        Log.i("IN onCreateOptionsMenu","The menu has been created");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent nextActivity;

        Vibrator buzzer;
        final long[] menuBuzz =
                {0,250,50,250,500,250,50,250,500,250,50,250,500,250,50,250};
        Log.i("IN onOptionsItemSelect","Menu item has been selected");
        switch (id){
            case R.id.buzz_menu_item:
                Log.i("IN onOptionsItemSelect","Buzzer item has been selected");
                buzzer = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                buzzer.vibrate(menuBuzz,-1);
                return(true);
            case R.id.popup_menu_item:
                Log.i("IN onOptionsItemSelect","Popup item has been selected");
                Toast.makeText(this,"Toast is Hotter than a pepper ready!",Toast.LENGTH_LONG).show();
                return(true);
            case R.id.action_InButton:
                Log.i("IN onOptionsItemSelect", "Popup item has been selected");
                nextActivity = new Intent();
                nextActivity.setClassName("edu.miami.c11926684.miniapp1",
                        "edu.miami.c11926684.miniapp1.MiniApp1_MainActivityInButton");
                startActivity(nextActivity);
            default:
                return super.onOptionsItemSelected(item);
        }
        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
    }
}
