package edu.miami.c11926684.miniapp7;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class MainActivity_MiniApp7 extends AppCompatActivity implements AdapterView.OnItemClickListener{

    String[] data = { "Pikachu", "Squirtle", "Charmander", "Ditto"};
    int[] pics = { R.drawable.pikachu, R.drawable.squirtle, R.drawable.charmander, R.drawable.ditto};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__mini_app7);
        registerForContextMenu(findViewById(R.id.image));

        ImageView imageView = (ImageView) findViewById(R.id.image);
        imageView.setImageResource(R.drawable.pikachu);

        ListView theList = (ListView) findViewById(R.id.theList);
        theList.setOnItemClickListener(this);

        theList.setAdapter(new ArrayAdapter<String>(this,R.layout.list_item, data));

        help();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (view.isEnabled()) {
            ImageView imageView = (ImageView) findViewById(R.id.image);
            imageView.setImageResource(pics[position]);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater;

        inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        ImageView theImage;
        ListView listView;
        boolean isChecked;

        switch (id) {
            case R.id.item1:
                theImage = (ImageView) findViewById(R.id.image);
                theImage.setVisibility(View.VISIBLE);
                return(true);
            case R.id.item2:
                theImage = (ImageView) findViewById(R.id.image);
                theImage.setVisibility(View.INVISIBLE);
                return(true);
            case R.id.pikachu:
                //MenuItem mItem = (MenuItem) findViewById(R.id.pikachu);
                isChecked = item.isChecked();
                if (isChecked) {
                    item.setChecked(!isChecked);
                    listView = (ListView) findViewById(R.id.theList);
                    (listView.getAdapter().getView(0, null, listView)).setEnabled(false);
                    listView.getChildAt(0).setEnabled(false);
                } else {
                    item.setChecked(!isChecked);
                    listView = (ListView) findViewById(R.id.theList);
                    (listView.getAdapter().getView(0, null, listView)).setEnabled(true);
                    listView.getChildAt(0).setEnabled(true);
                }
                return(true);
            case R.id.squirtle:
                isChecked = item.isChecked();
                if (isChecked) {
                    item.setChecked(!isChecked);
                    listView = (ListView) findViewById(R.id.theList);
                    (listView.getAdapter().getView(1, null, listView)).setEnabled(false);
                    listView.getChildAt(1).setEnabled(false);
                } else {
                    item.setChecked(!isChecked);
                    listView = (ListView) findViewById(R.id.theList);
                    (listView.getAdapter().getView(1, null, listView)).setEnabled(true);
                    listView.getChildAt(1).setEnabled(true);
                }
                return(true);
            case R.id.charmander:
                isChecked = item.isChecked();
                if (isChecked) {
                    item.setChecked(!isChecked);
                    listView = (ListView) findViewById(R.id.theList);
                    (listView.getAdapter().getView(2, null, listView)).setEnabled(false);
                    listView.getChildAt(2).setEnabled(false);
                } else {
                    item.setChecked(!isChecked);
                    listView = (ListView) findViewById(R.id.theList);
                    (listView.getAdapter().getView(2, null, listView)).setEnabled(true);
                    listView.getChildAt(2).setEnabled(true);
                }
                return(true);
            case R.id.ditto:
                isChecked = item.isChecked();
                if (isChecked) {
                    item.setChecked(!isChecked);
                    listView = (ListView) findViewById(R.id.theList);
                    (listView.getAdapter().getView(3, null, listView)).setEnabled(false);
                    listView.getChildAt(3).setEnabled(false);
                } else {
                    item.setChecked(!isChecked);
                    listView = (ListView) findViewById(R.id.theList);
                    (listView.getAdapter().getView(3, null, listView)).setEnabled(true);
                    listView.getChildAt(3).setEnabled(true);
                }
                return(true);
            default:
                return(super.onOptionsItemSelected(item));
        }
    }

    public void onCreateContextMenu(ContextMenu menu, View view,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu,view,menuInfo);
        getMenuInflater().inflate(R.menu.context_menu,menu);
    }

    public boolean onContextItemSelected(MenuItem item) {

        LinearLayout layout;

        layout = (LinearLayout) findViewById(R.id.linear_layout);

        switch (item.getItemId()) {
            case R.id.black:
                layout.setBackgroundColor(getResources().getColor(R.color.black));
                return(true);
            case R.id.red:
                layout.setBackgroundColor(getResources().getColor(R.color.red));
                return(true);
            case R.id.green:
                layout.setBackgroundColor(getResources().getColor(R.color.green));
                return(true);
            default:
                return(super.onOptionsItemSelected(item));
        }
    }

    private void help() {
        ((CheckBox) findViewById(R.id.checkbox)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { myClickHandler(view);}
        });
    }

    public void myClickHandler (View view) {
        ListView listView;
        boolean isChecked = !(((CheckBox) view).isChecked());
        System.out.println(isChecked);
        if (isChecked) {
            ((CheckBox) view).setChecked(!isChecked);
            listView = (ListView) findViewById(R.id.theList);
            (listView.getAdapter().getView(2, null, listView)).setEnabled(false);
            listView.getChildAt(2).setEnabled(false);
        } else {
            ((CheckBox) view).setChecked(!isChecked);
            listView = (ListView) findViewById(R.id.theList);
            (listView.getAdapter().getView(2, null, listView)).setEnabled(true);
            listView.getChildAt(2).setEnabled(true);
        }
    }
}
