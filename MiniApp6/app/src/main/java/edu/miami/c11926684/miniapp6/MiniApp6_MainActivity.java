package edu.miami.c11926684.miniapp6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;


public class MiniApp6_MainActivity extends AppCompatActivity implements OnItemClickListener {

    //-----------------------------------------------------------------------------
    private final String[] COUNTRIES = {
            "Korea","Mexico","Russia","Turkey","Uruguay","Greece","Philippines",
            "Puerto Rico","Egypt","China","Vietnam","Germany","Cuba","Panama","Indonesia",
            "Dominican Republic","Guatemala","Oman","Laos","Chile","Cambodia","Angola",
            "Iran","El Salvador","Nicaragua","Lebanon","Grenada","Honduras","Bolivia",
            "Virgin Islands","Panama","Saudi Arabia","Kuwait","Somalia","Yugoslavia",
            "Bosnia","Haiti","Zaire","Albania","Sudan","Yugoslavia","Macedonia",
            "Afghanistan","Philippines","Colombia","Iraq","Liberia","Haiti","Pakistan",
            "Somalia","Syria","Yemen","Libya"};

    private final int NUM_OF_COLUMNS = 3;
//-----------------------------------------------------------------------------

    GridView gridView;
    MySimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_app6__main);
        //setContentView(R.layout.app_layout_grid);


        gridView = (GridView)(findViewById(R.id.grid));
        //gridView.setNumColumns(NUM_OF_COLUMNS);
        //ImageView mImageView = (ImageView)findViewById(R.id.picture);
        //mImageView.setImageResource(R.drawable.pandapanda);

        String[] fromHashMapFieldNames = {"name"};
        int[] toListRowFieldIds = {R.id.textView};

        HashMap<String,Object> oneItem;
        ArrayList<HashMap<String,Object>> listItems = new ArrayList<HashMap<String,Object>>();
        for (int i = 0;i < COUNTRIES.length; i++) {
            oneItem = new HashMap<String,Object>();
            oneItem.put("name",COUNTRIES[i]);
            //oneItem.put("picture",R.id.picture);
            listItems.add(oneItem);
        }
        adapter = new MySimpleAdapter(this, listItems,
                R.layout.grid_item_layout, fromHashMapFieldNames, toListRowFieldIds);

        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
    }

    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        view.findViewById(R.id.textView).setVisibility(View.INVISIBLE);
        view.findViewById(R.id.picture).setVisibility(View.VISIBLE);
        adapter.setDisplayImage(position);
    }



    private int getIndex(String name) {

        for (int index = 0;index < COUNTRIES.length;index++) {
            if (name.equals(COUNTRIES[index])) {
                return index;
            }

        }
        return -1;
    }
}
