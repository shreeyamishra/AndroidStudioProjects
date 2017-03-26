package edu.miami.c11926684.bigapp1;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by woodyjean-louis on 9/20/16.
 */
public class Player implements Serializable {

    private static int counter = 0;

    private final String NAME;
    private final String PHOTO;

    public Player(String name, Uri photo) {
        if ( counter > 1) {
            counter = 0;
        }
        counter ++;
        if (name.equals("") || name == null) {
            this.NAME = "Player " + counter;
        } else {
            this.NAME = name;
        }

        this.PHOTO = (photo == null ? null : photo.toString());

    }

    public String getName() {
        return NAME;
    }

    public Uri getPhoto() { return (PHOTO == null) ? null : Uri.parse(PHOTO); }

    public static void decrementPlayers() {
        counter--;
    }

    public String ToString() {
        return PHOTO;
    }



}
