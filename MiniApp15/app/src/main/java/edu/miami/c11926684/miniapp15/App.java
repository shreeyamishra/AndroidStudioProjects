package edu.miami.c11926684.miniapp15;

import android.app.Application;
import android.content.res.Configuration;

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

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        db.close();
        super.onTerminate();
    }
}
