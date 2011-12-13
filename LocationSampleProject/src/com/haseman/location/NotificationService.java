package com.haseman.location;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.RemoteViews;

public class NotificationService extends Service{

	public final static int GPS_ID = 1;
	private LocationManager mLocationManager;
	public int onStartCommand(Intent intent, int flags, int startId){
		
		mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        
        String provider = getBestProvider();
        
        mLocationManager.requestLocationUpdates(provider, 5000, 100, listener);
        Location last = mLocationManager.getLastKnownLocation(provider);
        if(last!=null)
        	notifyAdvanced(last.getLatitude(), last.getLongitude(), true);
		return Service.START_NOT_STICKY;
	}
	private LocationListener listener = new LocationListener(){

		@Override
		public void onLocationChanged(Location location) {
			notifyAdvanced(location.getLatitude(), location.getLongitude(), false);
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	public void onDestroy(){
		super.onDestroy();
		
		
	}
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	private String getBestProvider(){
    	Criteria criteria = new Criteria();
    	criteria.setAccuracy(Criteria.ACCURACY_COARSE);
    	criteria.setPowerRequirement(Criteria.POWER_LOW);
    	criteria.setCostAllowed(false);
    	return mLocationManager.getBestProvider(criteria, true);
    }
	
	public void notifySimple(double lat, double lon, boolean foreground){
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);

		Context context = getApplicationContext();
		Intent notificationIntent = new Intent(this, LocationSampleLaunch.class);
		notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

		CharSequence contentTitle = "Your Location!";
		CharSequence contentText = "Lat: "+lat +" Lon: "+lon;
		
		Notification notification = new Notification(R.drawable.icon, "Location Update!", System.currentTimeMillis());
		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
		
		if(foreground)
			startForeground(GPS_ID, notification);
		else
			mNotificationManager.notify(GPS_ID, notification);
		
		
	}
	public void notifyAdvanced(double lat, double lon, boolean foreground){
		
		NotificationManager mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		
		Notification notification = new Notification(R.drawable.icon, "Location Update!", System.currentTimeMillis());
		
		RemoteViews remoteViews = new RemoteViews(this.getPackageName(), R.layout.notification_view);
        remoteViews.setTextViewText(R.id.notification_text, "Lat: "+lat +" Lon: "+lon);
        //remoteViews.setTextColor(R.id.notification_text, android.R.color.black);
        float results2[] = new float[1];
        Location.distanceBetween(lat, lon, 40.713305, -73.972149, results2);
        remoteViews.setTextViewText(R.id.notification_text_second, "you're "+results2[0]+" meters from the bridge");
        //remoteViews.setTextColor(R.id.notification_text_second, android.R.color.black);
        
        notification.contentView = remoteViews;
        
        Intent tapIntent = new Intent(this, LocationSampleLaunch.class);
        tapIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pi = PendingIntent.getActivity(this, 0, tapIntent, 0);
        
        notification.contentIntent = pi;
        
        if(foreground)
        		startForeground(GPS_ID, notification);
        else        
        	mNotificationManager.notify(GPS_ID, notification);
        
	}
	
}
