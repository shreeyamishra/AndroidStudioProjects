package edu.miami.c11926684.bigapp2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;


/**
 * Created by woodyjean-louis on 10/15/16.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewItem> {

    private List<GalleryImage> galleryImages;

    /**
     *
     *
     * @param pictures a list of Pictures
     */
    public GalleryAdapter(List<GalleryImage> pictures){
        galleryImages = pictures;
    }


    @Override
    public GalleryViewItem onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // the Gird item is an image
        View GridItem = inflater.inflate(R.layout.grid_item, parent, false);
        return new GalleryViewItem(GridItem);
    }

    @Override
    public void onBindViewHolder(GalleryViewItem holder, int position) {
        GalleryImage currentImage = galleryImages.get(position);
        try {
            Glide
                    .with(holder.itemView.getContext())
                    .load(currentImage.getPhoto())
                    .into(holder.image);
        } catch (Exception e) {
            Glide.with(holder.itemView.getContext())
                    .load(new File(currentImage.getPhoto()))
                    .centerCrop()
                    .into(holder.image);
        }
//            Picasso
//                    .with(holder.itemView.getContext())
//                    .load(currentImage.getURL())
//                    .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return galleryImages.size();
    }

    /**
     * ViewHolder containing views to display individual
     */
    public static final class GalleryViewItem extends RecyclerView.ViewHolder {

        private final ImageView image;

        public GalleryViewItem(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.gallery_image);
        }
    }
}
