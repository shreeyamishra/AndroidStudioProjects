package edu.miami.c11926684.miniapp15;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MiniApp15_MainActivity extends AppCompatActivity {

    private static final int ACTIVITY_SELECT_PICTURE = 0;
    private static final int ACTIVITY_SELECT_CONTACT = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_app15__main);

        checkPermission();
    }

    private void checkPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, ACTIVITY_SELECT_PICTURE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, ACTIVITY_SELECT_PICTURE);

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    Uri selectedURI;
    Uri contactData;
    Cursor contactsCursor;
    String contactName;
    int contactId;
    String email;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //ImageView pictureView;
        Bitmap selectedPicture;

        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case ACTIVITY_SELECT_PICTURE:
                if (resultCode == Activity.RESULT_OK) {
                    selectedURI = data.getData();
                    Intent nextIntent;

                    nextIntent = new Intent(Intent.ACTION_PICK,
                            ContactsContract.Contacts.CONTENT_URI);
                    startActivityForResult(nextIntent, ACTIVITY_SELECT_CONTACT);
//                    pictureView = (ImageView)findViewById(R.id.selected_picture);
//                    selectedURI = data.getData();
//                    try {
//                        selectedPicture = MediaStore.Images.Media.getBitmap(
//                                this.getContentResolver(),selectedURI);
//                        pictureView.setImageBitmap(selectedPicture);
//                    } catch (Exception e) {
////----Should do something here
//                    }
//                }
//                break;
                }
                break;
            case ACTIVITY_SELECT_CONTACT:
                if (resultCode == Activity.RESULT_OK){
                    contactData = data.getData();
                    contactsCursor = getContentResolver().query(contactData,
                            null,null,null,null);
                    if (contactsCursor.moveToFirst()){
                        contactId = contactsCursor.getInt(
                                contactsCursor.getColumnIndex(ContactsContract.Contacts._ID));
                        email = searchForEmailAddressById(contactId);
                        emailSomeOne();
                    }
                }
                break;

        }
    }

    private void emailSomeOne() {
        String[] emailToSendTo = new String[1];
        Intent nextIntent;
        emailToSendTo[0] = email;
        nextIntent = new Intent(Intent.ACTION_SEND);
        nextIntent.setType("plain/text");
        try {
            nextIntent.putExtra(Intent.EXTRA_EMAIL, emailToSendTo);
            nextIntent.putExtra(Intent.EXTRA_STREAM, selectedURI);
            startActivity(Intent.createChooser(nextIntent,"Choose ..."));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String searchForEmailAddressById(int contactId) {

        String[] projection = new String[] {
                ContactsContract.CommonDataKinds.Email.CONTACT_ID,
                ContactsContract.CommonDataKinds.Email.DATA
        };
        Cursor emailCursor;
        String emailAddress;

        emailCursor = getContentResolver().query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI,projection,"CONTACT_ID = ?",
                new String[]{Integer.toString(contactId)},null);
        if (emailCursor.moveToFirst()) {
            emailAddress = emailCursor.getString(emailCursor.getColumnIndex(
                    ContactsContract.CommonDataKinds.Email.DATA));
        } else {
            emailAddress = null;
        }
        return(emailAddress);
    }
}