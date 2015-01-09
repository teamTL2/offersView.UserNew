package com.example.offersview.activities;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.offersview.R;
import com.example.offersview.pojos.ShopPojo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapViewActivity extends ActionBarActivity implements LocationListener {
	private GoogleMap map;
	private LocationManager locationManager;
	private SupportMapFragment mapFragment;
	private Marker myLocation;
	private static View view;
	private boolean firstLaunch = false;
	private Circle radius;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activitiy_mapview);

		ActionBar actionbar = getSupportActionBar();
		actionbar.setHomeButtonEnabled(true);
		actionbar.setDisplayHomeAsUpEnabled(true);
		actionbar.setTitle("Maps");
		// actionbar.

		mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		map = mapFragment.getMap();
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

		locationManager = (LocationManager) getSystemService(getApplicationContext().LOCATION_SERVICE);

		// GPS is ON?
		boolean enabledGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		// // NETWORK PROVIDER is ON?
		boolean enabledNetwork = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

		if (!enabledGPS && !enabledNetwork) {
			Toast.makeText(getApplicationContext(), "Ανοίξτε τις ρυθμίσεις και ενεργοποιήστε κάποιον provider", Toast.LENGTH_LONG).show();
		}

		if (firstLaunch == false) {
			map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(39.465401, 22.804357), 6));
			firstLaunch = true;
		}

	}

	@Override
	public void onResume() {

		boolean enabledGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		// NETWORK PROVIDER is ON?
		boolean enabledNetwork = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

		if (enabledGPS) {
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 500, this);
		}

		if (enabledNetwork) {
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60000, 500, this);
		}
		super.onResume();
	}

	private boolean onlyOneTime = true;

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// I do not want this...
			// Home as up button is to navigate to Home-Activity not previous
			// acitivity
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onLocationChanged(Location location) {
		if (radius != null) {
			radius.remove();
		}
		if (myLocation != null) {
			myLocation.remove();
		} else {
			// new LoadShops().execute();
		}

		myLocation = map.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("Βρίσκεστε εδώ"));

		CircleOptions optionRadius = new CircleOptions();
		optionRadius.radius(200);
		optionRadius.strokeWidth(2);
		optionRadius.fillColor(0x3396AA39);
		optionRadius.strokeColor(0xDD96AA39);
		optionRadius.center(new LatLng(location.getLatitude(), location.getLongitude()));

		radius = map.addCircle(optionRadius);

		if (onlyOneTime) {
			map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));
			onlyOneTime = false;
		}
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub

		locationManager.removeUpdates(this);
		super.onDestroy();
	}

	private class LoadShops extends AsyncTask<String, Void, ArrayList<ShopPojo>> {
		ProgressDialog progressDialog;
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(MapViewActivity.this);
			progressDialog.setMessage("Downloading near Shops...");
			progressDialog.setIndeterminate(false);
			progressDialog.setCancelable(false);
			progressDialog.show();
		}

		@Override
		protected ArrayList<ShopPojo> doInBackground(String... params) {
//			ArrayList<ShopPojo> items = new ArrayList<ShopPojo>();
//
//			for (int i = 0; i < 45; i++) {
//				ShopPojo item = new ShopPojo();
//				item.setName("Shop " + i);
//				item.setStreet("King Street " + i);
//				item.setZipCode("57001");
//				item.setPhone("6999999999");
//				item.setRegion("Serres");
//				item.setLatitude(40.576454 + i);
//				item.setLongitude(22.994972 + i);
//
//				for (int k = 0; k < i; k++) {
//					item.addOffer(new OfferPojo("offer " + k, "-0." + k));
//				}
//
//				items.add(item);
//			}
//
//			try {
//				Thread.sleep(2000);
//			} catch (InterruptedException e) {
//				Thread.interrupted();
//			}

			return null;
		}

		@Override
		protected void onPostExecute(ArrayList<ShopPojo> result) {
			progressDialog.dismiss();
		}

	}

}
