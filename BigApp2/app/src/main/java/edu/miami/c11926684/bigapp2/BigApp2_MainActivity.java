package edu.miami.c11926684.bigapp2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import edu.miami.c11926684.bigapp2.Services.ServiceGenerator;

import java.io.IOException;

import edu.miami.c11926684.bigapp2.SupportingFiles.TokenResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BigApp2_MainActivity extends AppCompatActivity implements AuthenticationListener {

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1 ;

    AuthenticationDialog dialog;
    EditText mText;
    String mAuthToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_app2__main);
        askForPermission();

    }

    @Override
    public void onCodeReceived(String code) {
        mText = (EditText)findViewById(R.id.editText2);

        if(code!= null){
            dialog.dismiss();

        }
        System.out.println("code: " + code);

        final Call<TokenResponse> accessToken =  ServiceGenerator.createTokenService().getAccessToken(Constants.CLIENT_ID,Constants.CLIENT_SECRET,Constants.REDIRECT_URI,Constants.AUTORIZATION_CODE,code);
        accessToken.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {

                if(response.isSuccessful()){

                    mAuthToken = response.body().getAccess_token();
                    System.out.println("mAuthToken: " + mAuthToken);
                    Intent intent = new Intent(BigApp2_MainActivity.this, SearchActivity.class);
                    intent.putExtra("AUTH_TOKEN", mAuthToken);
                    startActivity(intent);

                }else{
                    try {
                        if (mText == null) {
                            System.out.println("null");
                        }
                        mText.setText(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }


            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                mText.setText("failure");
            }
        });


    }

    public void myClickHandler(View view) {

        int id = view.getId();

        Intent nextActivity;

        switch (id) {
            case R.id.button1:
                //dialog = new AuthenticationDialog(BigApp2_MainActivity.this, this);
                dialog = new AuthenticationDialog(new ContextThemeWrapper(this, R.style.DialogSlideAnim), this);
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogSlideAnim;
                dialog.show();
                break;
            case R.id.button2:

                nextActivity = new Intent(this, GalleryActivity.class);
                     startActivity(nextActivity);
                break;
            default:
                break;
        }

    }
    private void askForPermission() {
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Explain to the user why we need to read the contacts
            }

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

            // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
            // app-defined int constant

            return;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // image-related task you need to do.
                    Intent nextActivity;
                    nextActivity = new Intent(this, GalleryActivity.class);
                    startActivity(nextActivity);

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
}