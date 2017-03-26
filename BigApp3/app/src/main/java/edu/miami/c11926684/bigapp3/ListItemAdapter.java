package edu.miami.c11926684.bigapp3;

import android.content.ContentValues;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by woodyjean-louis on 11/15/16.
 */

public class ListItemAdapter extends SimpleAdapter {
    /**
     * Constructor
     *
     * @param context  The context where the View associated with this SimpleAdapter is running
     * @param data     A List of Maps. Each entry in the List corresponds to one row in the list. The
     *                 Maps contain the data for each row, and should include all the entries specified in
     *                 "from"
     * @param resource Resource identifier of a view layout that defines the views for this list
     *                 item. The layout file should include at least those named views defined in "to"
     * @param from     A list of column names that will be added to the Map associated with each
     *                 item.
     * @param to       The views that should display column in the "from" parameter. These should all be
     *                 TextViews. The first N views in this list are given the values of the first N columns
     */
    public ListItemAdapter(final Context context, final List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);

        final DataBase dataBase = ((App) context.getApplicationContext()).db;


        registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {

                ContentValues info = dataBase.getThoughtById(itemPosition + 1);
                super.onChanged();
                Map map = (Map) getItem(itemPosition);
                map.remove("text");
                map.put("text", info.getAsString("thought"));

                //                if (Object obj = info.getAsByteArray("TTS") == null) {
                //
                //                }
            }
        });
    }

    int itemPosition;

    public void setItemPosition(int p) {
        itemPosition = p;
    }


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
