package edu.miami.c11926684.miniapp5;

import android.app.Activity;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.icu.util.TimeZone;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Date;
import java.util.Locale;

public class MiniApp5_MainActivity extends Activity
        implements DatePicker.OnDateChangedListener, TimePicker.OnTimeChangedListener {
//-----------------------------------------------------------------------------

    TimePicker timePicker;
    DatePicker datePicker;
@Override
public void onCreate(Bundle savedInstanceState) {

        //DatePicker datePicker;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_app5__main);

        datePicker = (DatePicker)findViewById(R.id.date_picker);
        datePicker.init(datePicker.getYear(),datePicker.getMonth(),
        datePicker.getDayOfMonth(),this);
        timePicker = (TimePicker)findViewById(R.id.time_picker);

        ((TimePicker)findViewById(R.id.time_picker)).
        setOnTimeChangedListener(this);

        hourOfDay = timePicker.getHour();
        minute = timePicker.getMinute();
        runnable.run();
        }
//-----------------------------------------------------------------------------
public void onDateChanged(DatePicker view,int year,int monthOfYear,
        int dayOfMonth) {
    //System.out.println("Hey!!!");
        }
//-----------------------------------------------------------------------------
public void onTimeChanged(TimePicker view,int hourOfDay,int minute) {
    this.hourOfDay = hourOfDay;
    this.minute = minute;


    //System.out.println("Hey!!!");
        }
//--------------------------------------------------------------------------
    int hourOfDay;
    int minute;

    //------------------------------------------------------------------------------------
    private final Runnable runnable =  new Runnable() {
        Handler someHandler = new Handler();
        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT"));

        @Override
        public void run() {
            if (HourofDay == 0) {

            }
            timePicker.setHour(((minute + 1)%60 == 0)? (hourOfDay + 1)%12 : hourOfDay);
            timePicker.setMinute((minute + 1)%60);
            someHandler.postDelayed(runnable, 60000);
        }
    };

//-----------------------------------------------------------------------------
}
//=============================================================================