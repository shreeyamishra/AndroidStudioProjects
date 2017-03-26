package edu.miami.c11926684.bigapp2;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by woodyjean-louis on 10/15/16.
 */

public class GalleryImage extends Photo implements Serializable {

    public static int numberOfPhotos;

    private String caption;
    private String[] hashTags;
    private String[] suggestedHashTags;
    //private Location location;

    public GalleryImage(String title, String photo, String imageSize) {
        super(title, photo, imageSize);

        numberOfPhotos ++;
    }

    public void addTag(String[] tags) {
        if (hashTags == null)
            hashTags = tags;
        else {
            //implement
        }
    }
    @Override
    public String[] getHashTag() {
        return new String[0];
    }

    @Override
    public String[] getSuggestedHashTags() {
        return new String[0];
    }
}
