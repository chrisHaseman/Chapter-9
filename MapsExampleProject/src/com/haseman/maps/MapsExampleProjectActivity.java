package com.haseman.maps;

import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

public class MapsExampleProjectActivity extends MapActivity {
    
	MapView mv;
	MapController controller;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mv = (MapView)findViewById(R.id.map_view);
        controller = mv.getController();//;
        GeoPoint point = new GeoPoint((int)(40.734641 * 1e6), (int)(-73.996181 * 1e6));
        controller.animateTo(point);
        controller.setZoom(12);
    }

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}