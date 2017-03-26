package edu.miami.c11926684.miniapp18;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class MiniApp18_MainActivity extends AppCompatActivity implements LocationListener {

    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 0;
    private static final int MY_PERMISSIONS_REQUEST_COARSE_LOCATION = 1;
    private static final int MY_PERMISSIONS_REQUEST_MIC = 2;
    private LocationManager locationManager;
    private Location currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_app18__main);
        //detectLocators();
        askForPermission();
    }

    private void askForPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_FINE_LOCATION);

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_COARSE_LOCATION);

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    MY_PERMISSIONS_REQUEST_MIC);

//            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.ACCESS_FINE_LOCATION)) {
//
//                // Show an explanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//
//            } else if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
//
//
//            } else {
//
//                // No explanation needed, we can request the permission.
//                ActivityCompat.requestPermissions(this,
//                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                            MY_PERMISSIONS_REQUEST_FINE_LOCATION);
//
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
//                        MY_PERMISSIONS_REQUEST_COARSE_LOCATION);
//
//
//
//                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
//                // app-defined int constant. The callback method gets the
//                // result of the request.
//
//            }
        } else {
            locationManager = (LocationManager)(getSystemService(LOCATION_SERVICE));
            detectLocators();
            granted = true;

            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,2000,0,this);
            onResume();
        }
    }

    Boolean granted = false;

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[grantResults.length - 1] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    return;

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }

            }

            case MY_PERMISSIONS_REQUEST_MIC:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[grantResults.length - 1] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    return;

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
            case MY_PERMISSIONS_REQUEST_COARSE_LOCATION:

                if (grantResults.length > 0
                        && grantResults[grantResults.length - 1] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    locationManager = (LocationManager)(getSystemService(LOCATION_SERVICE));
                    detectLocators();
                    granted = true;
                    onResume();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }

                return;

            // other 'case' lines to check for other
            // permissions this app might request
        }
        locationManager = (LocationManager)(getSystemService(LOCATION_SERVICE));
        detectLocators();
        granted = true;
        onResume();
    }

    TextToSpeech mySpeaker;


    @Override
    public void onResume() {

        if(granted) {

            String[] providers = {LocationManager.GPS_PROVIDER,
                    LocationManager.NETWORK_PROVIDER};
            int providerIndex;
            TextView locationText;
            Location lastLocation;
            String errorMessage = "";

            super.onResume();
            currentLocation = null;
            providerIndex = 0;
            locationText = (TextView) findViewById(R.id.current_location);
            locationText.setMovementMethod(new ScrollingMovementMethod());

            while (currentLocation == null && providerIndex < providers.length) {
                try {
                    if ((lastLocation =
                            locationManager.getLastKnownLocation(providers[providerIndex])) != null &&
                            (System.currentTimeMillis() - lastLocation.getTime()) <
                                    10000) {
                        locationText.setText("Previous location ...\n");
                        currentLocation = lastLocation;
                        onLocationChanged(currentLocation);

                        return;
                    }
                    providerIndex++;
                } catch (SecurityException e) {
                    errorMessage = e.getMessage();
                    e.printStackTrace();
                }
            }
            Toast.makeText(this, "No previous location available" + errorMessage,
                    Toast.LENGTH_LONG).show();
        } else {
            super.onResume();
        }
    }

    private void detectLocators() {

        List<String> locators;

        locators = locationManager.getProviders(true);
        for (String aProvider : locators) {
            if (aProvider.equals(LocationManager.GPS_PROVIDER)) {
                Toast.makeText(this,"GPS available",Toast.LENGTH_SHORT).show();
            }}
//            if (aProvider.equals(LocationManager.NETWORK_PROVIDER)) {
//                ((Button)findViewById(R.id.select_network)).setClickable(true);
//                Toast.makeText(this,"Network available",Toast.LENGTH_SHORT).show();
//            }
//        }
    }


    @Override
    public void onPause() {

        super.onPause();
        if (granted) {
            try {
                locationManager.removeUpdates(this);
            } catch (SecurityException e) {
                Toast.makeText(this, "Cannot removeUpdates" + e.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onLocationChanged(Location newLocation) {
        TextView locationText;
        String currentText;
        Time timeOfChange;

        locationText = (TextView)findViewById(R.id.current_location);
        currentText = locationText.getText().toString();

        currentLocation = newLocation;

        timeOfChange = new Time();
        timeOfChange.set(currentLocation.getTime());
        currentText += timeOfChange.format("%A %D %T") + "   ";
        currentText += "\nProvider " + currentLocation.getProvider() + " found location\n";

        currentText += String.format("%.2f %s",newLocation.getLatitude(),
                newLocation.getLatitude() >= 0.0?"N":"S") + "   ";
        currentText += String.format("%.2f %s",newLocation.getLongitude(),
                newLocation.getLongitude() >= 0.0?"E":"W")  + "   ";
        CharSequence text = locationText.getText();
        mySpeaker.speak(text.toString(), TextToSpeech.QUEUE_ADD,null);
        mySpeaker.speak("         ",TextToSpeech.QUEUE_ADD,null);
        if (newLocation.hasAccuracy()) {
            currentText += String.format("%.2fm",newLocation.getAccuracy());
        }
        currentText += "\n\n";
        locationText.setText(currentText);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
