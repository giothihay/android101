package com.es;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.es.chatapp.R;
import com.sachinvarma.easylocation.EasyLocationInit;
import com.sachinvarma.easylocation.event.Event;
import com.sachinvarma.easylocation.event.LocationEvent;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class Mss extends AppCompatActivity {

    private int timeInterval = 3000;
    private int fastestTimeInterval = 3000;
    private boolean runAsBackgroundService = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mss);

        findViewById(R.id.btGetLocation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // new EasyLocationInit(context, timeInterval , fastestTimeInterval, runAsBackgroundService);

                //timeInterval -> setInterval(long)(inMilliSeconds) means - set the interval in which you want to get locations.
                //fastestTimeInterval -> setFastestInterval(long)(inMilliSeconds) means - if a location is available sooner you can get it.
                //(i.e. another app is using the location services).
                //runAsBackgroundService = True (Service will run in Background and updates Frequently(according to the timeInterval and fastestTimeInterval))
                //runAsBackgroundService = False (Service will getDestroyed after a successful location update )
                new EasyLocationInit(Mss.this, timeInterval, fastestTimeInterval, runAsBackgroundService);
                Log.d("AAAA", "onClick() called with: v = [" + "ok1" + "]");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @SuppressLint("SetTextI18n")
    @Subscribe
    public void getEvent(final Event event) {

        if (event instanceof LocationEvent) {
            if (((LocationEvent) event).location != null) {
                ((TextView) findViewById(R.id.tvLocation)).setText("The Latitude is "
                        + ((LocationEvent) event).location.getLatitude()
                        + " and the Longitude is "
                        + ((LocationEvent) event).location.getLongitude());
                Log.d("AAAA", "onClick() called with: v = [" + "ok2" + "]");
            }
            Log.d("AAAA", "onClick() called with: v = [" + "ok3" + "]");
        }
    }
     double distanceBetween2Points(double la1, double lo1,
                                                double la2, double lo2) {
        double dLat = (la2 - la1) * (Math.PI / 180);
        double dLon = (lo2 - lo1) * (Math.PI / 180);
        double la1ToRad = la1 * (Math.PI / 180);
        double la2ToRad = la2 * (Math.PI / 180);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(la1ToRad)
                * Math.cos(la2ToRad) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = 6371 * c;
        return d;
    }

}
