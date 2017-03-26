package edu.miami.c11926684.miniapp6;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by woodyjean-louis on 9/26/16.
 */
//----Have to do this because grid views are recycled

public class MySimpleAdapter extends SimpleAdapter {
    //-----------------------------------------------------------------------------
    boolean[] displayImage;
    //-----------------------------------------------------------------------------
    public MySimpleAdapter(Context context, List<? extends Map<String,?>> data,
                           int resource, String[] keyNames, int[] fieldIds) {

        super(context,data,resource,keyNames,fieldIds);

        displayImage = new boolean[getCount()];
        Arrays.fill(displayImage,false);
    }
    //-----------------------------------------------------------------------------
    public void setDisplayImage(int position) {

        displayImage[position] = true;
    }
    //-----------------------------------------------------------------------------
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;
        TextView name;
        ImageView smiley;

//----Let the superclass decide whether or not to recycle
        view = super.getView(position, convertView, parent);
        name = (TextView) view.findViewById(R.id.textView);
        smiley = (ImageView) view.findViewById(R.id.picture);
        if (displayImage[position]) {
            name.setVisibility(View.INVISIBLE);
            smiley.setVisibility(View.VISIBLE);
        } else {
            name.setVisibility(View.VISIBLE);
            smiley.setVisibility(View.INVISIBLE);
        }
        return (view);
    }
//-----------------------------------------------------------------------------
}
