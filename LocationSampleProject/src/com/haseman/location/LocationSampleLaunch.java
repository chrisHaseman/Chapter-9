package com.haseman.location;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViews.RemoteView;
import android.widget.TextView;

public class LocationSampleLaunch extends Activity implements LocationListener{
    private LocationManager mLocationManager;
    private TextView mDisplay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
         
        mDisplay = (TextView)findViewById(R.id.main_text);
        
        mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        
        String provider = getBestProvider(mLocationManager);
        
        mLocationManager.requestLocationUpdates(provider, 60000, 100, this);
        UpdateLocation(mLocationManager.getLastKnownLocation(provider));
        
        //startService(new Intent(this, NotificationService.class));
//        Builder b = new Builder(getApplicationContext());
//       
//        b.setContentInfo("Gps In Action!");
//        b.setTicker("You're now using the gps!");
//        nm.notify("haseman.gps", GPS_ID, b.getNotification());

    }
    public void onDestroy(){
    	super.onDestroy();
    	mLocationManager.removeUpdates(this);
    }
    private void UpdateLocation(Location loc){
    		if(loc==null){
    			mDisplay.setText(R.string.no_location);
    			return;
    		}
    		float results[] = new float[1];
    		Location.distanceBetween(loc.getLatitude(), loc.getLongitude(), 37.386799, -122.082825, results);
    		float results2[] = new float[1];//40.713305
    		Location.distanceBetween(loc.getLatitude(), loc.getLongitude(), 40.713305, -73.972149, results2);
    		mDisplay.setText("Lat:"+loc.getLatitude()+" Lon: "+loc.getLongitude() + "\n"+"Your are "+results[0]+" meters from Mountan View" + "\n and "+results2[0]+" meters from the middle of the willamsburg bridge");
    }
    
    private String getBestProvider(LocationManager locationManager){
    		Criteria criteria = new Criteria();
    		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
    		criteria.setPowerRequirement(Criteria.POWER_LOW);
    		criteria.setCostAllowed(false);
    	return locationManager.getBestProvider(criteria, true);
    }
    //======================Location Listener===============
	@Override
	public void onLocationChanged(final Location location) {
		// TODO Auto-generated method stub
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				UpdateLocation(location);
			}
		});
		
	}
	@Override
	public void onProviderDisabled(String provider) {
		
	}
	@Override
	public void onProviderEnabled(String provider) {
		
		
	}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
}