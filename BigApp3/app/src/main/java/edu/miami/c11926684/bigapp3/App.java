package edu.miami.c11926684.bigapp3;

import android.app.Activity;
import android.app.Application;

import android.content.res.Configuration;
import android.widget.Toast;

import edu.miami.c11926684.bigapp3.DataBase;

/**
 * Created by woodyjean-louis on 11/10/16.
 */

public class App extends Application {

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
    DataBase db;
    @Override
    public void onCreate() {
        super.onCreate();

        db = new DataBase(this);
    }

    private Activity mCurrentActivity = null;
    public Activity getCurrentActivity(){
        return mCurrentActivity;
    }
    public void setCurrentActivity(Activity mCurrentActivity){
        this.mCurrentActivity = mCurrentActivity;
    }

    @Override
    public void onLowMemory() {
        Toast.makeText(getCurrentActivity(), "Memory is very low. This application may crash.", Toast.LENGTH_LONG);
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        db.close();
        super.onTerminate();
    }
}
