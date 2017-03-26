package edu.miami.c11926684.bigapp2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class GalleryActivity extends Activity implements OnItemClickListener {

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1 ;
    /**
     * The images.
     */
    private ArrayList<String> images;
    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        mRecyclerView = (RecyclerView) findViewById(R.id.galleryGridView);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(manager);

        mRecyclerView.setAdapter(new GalleryAdapter(getAllShownImagesPath()));

        //askForPermission();

        //GridView gallery = (GridView) findViewById(R.id.galleryGridView);



        //gallery.setAdapter(new GalleryAdapter(this, getAllShownImagesPath())));

//        gallery.setOnItemClickListener(new OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1,
//                                    int position, long arg3) {
//                if (null != images && !images.isEmpty())
//                    Toast.makeText(
//                            getApplicationContext(),
//                            "position " + position + " " + images.get(position),
//                            Toast.LENGTH_SHORT).show();
//                ;
//
//            }
//        });

    }

    /**
     * Getting All Images Path.
     *
     *
     * @return List of GalleryImages Path
     */
    private ArrayList<GalleryImage> getAllShownImagesPath() {
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        ArrayList<GalleryImage> listOfAllImages = new ArrayList<GalleryImage>();
        String absolutePathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        System.out.println("URI :" + uri.toString());

        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
        System.out.println("projection :" + projection);
        cursor = this.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

        System.out.println("column_index_data: " + column_index_data + "\n");
        System.out.println("column_index_folder_name: " + column_index_folder_name + "\n");
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);


            System.out.println("absolute: " + absolutePathOfImage + "\n");

            listOfAllImages.add(new GalleryImage(null, absolutePathOfImage, null));
        }
        return listOfAllImages;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    /**
     * The Class ImageAdapter.
     */
    private class ImageAdapter extends BaseAdapter {

        /**
         * The context.
         */
        private Activity context;

        /**
         * Instantiates a new image adapter.
         *
         * @param localContext the local context
         */
        public ImageAdapter(Activity localContext) {
            context = localContext;
            images = getAllShownImagesPath(context);
        }

        public int getCount() {
            return images.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            ImageView picturesView;
            if (convertView == null) {
                picturesView = new ImageView(context);
                picturesView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                picturesView
                        .setLayoutParams(new GridView.LayoutParams(270, 270));

            } else {
                picturesView = (ImageView) convertView;
            }

            Glide.with(context).load(images.get(position))
                    .placeholder(R.drawable.place_holder).centerCrop()
                    .into(picturesView);

            return picturesView;
        }

        /**
         * Getting All Images Path.
         *
         * @param activity the activity
         * @return ArrayList with images Path
         */
        private ArrayList<String> getAllShownImagesPath(Activity activity) {
            Uri uri;
            Cursor cursor;
            int column_index_data, column_index_folder_name;
            ArrayList<String> listOfAllImages = new ArrayList<String>();
            String absolutePathOfImage = null;
            uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            System.out.println("URI :" + uri.toString());

            String[] projection = {MediaColumns.DATA,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
            System.out.println("projection :" + projection);
            cursor = activity.getContentResolver().query(uri, projection, null,
                    null, null);

            column_index_data = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
            column_index_folder_name = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

            System.out.println("column_index_data: " + column_index_data + "\n");
            System.out.println("column_index_folder_name: " + column_index_folder_name + "\n");
            while (cursor.moveToNext()) {
                absolutePathOfImage = cursor.getString(column_index_data);

                System.out.println("absolute: " + absolutePathOfImage + "\n");

                listOfAllImages.add(absolutePathOfImage);
            }
            return listOfAllImages;
        }
    }
}
