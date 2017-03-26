package edu.miami.c11926684.bigapp2;

import android.net.Uri;

/**
 * Created by woodyjean-louis on 10/13/16.
 */

public abstract class Photo extends Object {
    //---------------------------------------------------------------------------------------------------------------------
    private final String PHOTO_PATH;
    private String title;
    private String caption;
    private String imageSize;
    private String[] HashTags;
    private String[] suggestedHashTags;
    //private Location location;

    /**
     *
     * @param title
     * @param photo
     * @param imageSize
     */

    public Photo(String title, String photo, String imageSize) {

        if ( title == null || title.equals("")) {
            this.title = "Untitled";
        } else {
            this.title = title;
        }

        this.PHOTO_PATH = photo;

        this.imageSize = imageSize;

    }

    public String getTitle() { return title; }

    public String getPhoto() { return PHOTO_PATH; }

    public String getImageSize() {return imageSize;}



    public abstract String[] getHashTag();

    public abstract String[] getSuggestedHashTags();

    public String ToString() {
        return (title + " : " + PHOTO_PATH + " : " + imageSize);
    }
}
