package edu.miami.c11926684.bigapp1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class BigApp1_MainActivity_Game extends AppCompatActivity implements OnItemClickListener {

    GridView gridView;
    SimpleAdapter adapter;
    Player user1;
    Player user2;
    //player1 will be true and player2 will be false
    boolean whoseTurn;
    boolean didSomeoneWin = false;
    boolean anyPossibleMoves = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_app1__main__game);
        gridView = (GridView)(findViewById(R.id.grid));


        user1 = (Player)getIntent().getSerializableExtra("user1");
        user2 = (Player)getIntent().getSerializableExtra("user2");
        timer = (int)getIntent().getSerializableExtra("timer");
        setupPlayerView();
        setUpGrid();
        setUpProgressBar();
        System.out.println(timer);
        sideQueue.run();

    }

    private void setupPlayerView() {
        ((TextView)(findViewById(R.id.user1_name))).setText(user1.getName());
        ((TextView)(findViewById(R.id.user2_name))).setText(user2.getName());
        ImageView pView1 = (ImageView) findViewById(R.id.user1_photo);
        ImageView pView2 = (ImageView) findViewById(R.id.user2_photo);
        try {
            Bitmap selectedPicture = MediaStore.Images.Media.getBitmap(
                    this.getContentResolver(),user1.getPhoto());
            pView1.setImageBitmap(selectedPicture);
        } catch (Exception e) {

        }
        try {
            Bitmap selectedPicture = MediaStore.Images.Media.getBitmap(
                    this.getContentResolver(),user2.getPhoto());
            pView2.setImageBitmap(selectedPicture);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
            Toast.makeText(this, "Something weird happened? please call woody for help",Toast.LENGTH_LONG).show();
        }

        whoseTurn = true;
    }

    private void setUpGrid() {
        String[] fromHashMapFieldNames = {"block"};
        int[] toListRowFieldIds = {R.id.block};

        HashMap<String,Object> oneItem;
        ArrayList<HashMap<String,Object>> listItems = new ArrayList<HashMap<String,Object>>();
        for (int i = 0;i < 9; i++) {
            oneItem = new HashMap<String,Object>();
            oneItem.put("block",R.id.block);
            listItems.add(oneItem);
        }
        adapter = new SimpleAdapter(this, listItems,
                R.layout.cell_layout, fromHashMapFieldNames, toListRowFieldIds);

        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
    }



    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        System.out.println(position);
        System.out.println("array: " + ticTacToe[position]);
        if (ticTacToe[position] == 0) {
            //Updates the grid view
            setBlock(view);
            //Updates the data
            updateData(position);
            if (didSomeoneWin || !anyPossibleMoves) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("didSomeoneWin", didSomeoneWin);
                //this line will specify who won.
                returnIntent.putExtra("whoseTurn", whoseTurn);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
            changeTurn();
            progress.setProgress(timer);
        }
    }

    private void changeTurn() {
        if (whoseTurn) {
            findViewById(R.id.user1).setVisibility(View.INVISIBLE);
            findViewById(R.id.user2).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.user2).setVisibility(View.INVISIBLE);
            findViewById(R.id.user1).setVisibility(View.VISIBLE);
        }
        whoseTurn = !whoseTurn;
    }

    private void setBlock(View view) {

        view.findViewById(R.id.tic_block).setVisibility(View.INVISIBLE);
        ImageView blockView = (ImageView)view.findViewById(R.id.tic_pic);
        try {
            Bitmap selectedPicture = MediaStore.Images.Media.getBitmap(
                    this.getContentResolver(),whoseTurn? user1.getPhoto() : user2.getPhoto());
            blockView.setImageBitmap(selectedPicture);
        } catch (Exception e) {
            Toast.makeText(this, "WHY ANDROID!!!!!",Toast.LENGTH_LONG).show();
        }

        blockView.setVisibility(View.VISIBLE);
    }

    private int[] ticTacToe = new int[9];
    private int ticTacToeSize = 0;

    private void updateData(int position) {
        if (whoseTurn){
            ticTacToe[position] = 1;
            ticTacToeSize++;
        } else {
            ticTacToe[position] = -1;
            ticTacToeSize++;
        }
        if (function1() || function2() || function3() || function4()) {
            didSomeoneWin = true;
        }
        System.out.println(function1() + " " +  function2() + " " +  function3() + " " + function4());
        anyPossibleMoves = ticTacToeSize == 9? false : true;

    }
//these functions will take in a starting point and return the sum of a row, column and diagonal.
    private boolean function1() {
        int startingPoint = 0;
        for (int counter = 0; counter < 3; counter++) {
            int sum = ticTacToe[startingPoint] + ticTacToe[startingPoint + 1] + ticTacToe[startingPoint + 2];
            if (sum == 3 || sum == -3) {
                return true;
            }
            startingPoint = startingPoint + 3;
        }
        return false;
    }

    private boolean function2() {
        int startingPoint = 0;
        for (int counter = 0; counter < 3; counter++) {
            int sum = ticTacToe[startingPoint] + ticTacToe[startingPoint + 3] + ticTacToe[startingPoint + 6];
            if (sum == 3 || sum == -3) {
                return true;
            }
            startingPoint = startingPoint + 1;
        }
        return false;
    }

    private boolean function3() {
        int startingPoint = 0;
        int sum = ticTacToe[startingPoint] + ticTacToe[startingPoint + 4] + ticTacToe[startingPoint + 8];
        if (sum == 3 || sum == -3) {
            return true;
        }
        return false;
    }

    private boolean function4() {
        int startingPoint = 2;
        int sum = ticTacToe[startingPoint] + ticTacToe[startingPoint + 2] + ticTacToe[startingPoint + 4];
        if (sum == 3 || sum == -3) {
            return true;
        }
        return false;
    }

    private void setUpProgressBar() {
        decrement = 10;
        progress = (ProgressBar)findViewById(R.id.progress_bar);
        progress.setProgress(timer);
    }

    private ProgressBar progress;
    private int decrement;
    private int timer;


    private Runnable sideQueue = new Runnable() {

        private Handler myHandler = new Handler();
        @Override
        public void run() {

            if (progress.getProgress() > 0) {
                progress.setProgress(progress.getProgress() - decrement);
            } else {
                changeTurn();
                progress.setProgress(timer);
            }
            if (!myHandler.postDelayed(sideQueue, timer)) {
                Log.e("ERROR","Cannot postDelayed");
            }

        }
    };

    @Override
    public void onBackPressed() {
        //Handle back button
        super.onBackPressed();
    }
}
