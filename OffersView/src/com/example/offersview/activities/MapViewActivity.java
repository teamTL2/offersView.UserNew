package com.example.offersview.activities;

import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.example.offersview.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.Marker;




public class MapViewActivity extends ActionBarActivity
{
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
		
		
		
	}
	
}

//public class MapViewActivity extends implements LocationListener {
//
//	private GoogleMap map;
//	private LocationManager locationManager;
//	private SupportMapFragment mapFragment;
//	private Marker myLocation;
//	private static View view;
//	private boolean firstLaunch = false;
//	private Circle radius;
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		if (view != null) {
//			ViewGroup parent = (ViewGroup) view.getParent();
//			if (parent != null)
//				parent.removeView(view);
//		}
//		try {
//			view = inflater.inflate(R.layout.fragment_gps, container, false);
//
//		} catch (InflateException e) {
//			/* map is already there, just return view as it is */
//		}
//
//		return view;// inflater.inflate(R.layout.fragment_gps, container,
//					// false);
//
//	}
//
//	@Override
//	public void onActivityCreated(Bundle savedInstanceState) {
//		super.onActivityCreated(savedInstanceState);
//
//		mapFragment = (SupportMapFragment) PARENT_ACTIVITY.getSupportFragmentManager().findFragmentById(R.id.map);
//		map = mapFragment.getMap();
//		// Φόρτωση χάρτη που δείχνει δρόμους
//		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//
//		locationManager = (LocationManager) PARENT_ACTIVITY.getSystemService(getApplicationContext().LOCATION_SERVICE);
//
//		// Τελευταία τοποθεσία απο το GPS αν υπάρχει
//		// Location location =
//		// locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//		// Τελευταία τοποθεσία απο το NETWORK αν υπάρχει
//		// Location location2 =
//		// locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//
//		// GPS is ON?
//		boolean enabledGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//		// NETWORK PROVIDER is ON?
//		boolean enabledNetwork = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//
//		if (!enabledGPS && !enabledNetwork) {
//			Toast.makeText(getApplicationContext(), "Ανοίξτε τις ρυθμίσεις και ενεργοποιήστε κάποιον provider", Toast.LENGTH_LONG).show();
//		}
//
//		if (firstLaunch == false) {
//			map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(39.465401, 22.804357), 6));
//			firstLaunch = true;
//		}
//
//	}
//
//	@Override
//	public void onResume() {
//
//		boolean enabledGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//		// NETWORK PROVIDER is ON?
//		boolean enabledNetwork = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//
//		if (enabledGPS) {
//
//			if (PARENT_ACTIVITY.currentLocation == null)
//				locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 500, this);
//		}
//
//		if (enabledNetwork) {
//			if (PARENT_ACTIVITY.currentLocation == null)
//				locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60000, 500, this);
//		}
//		super.onResume();
//	}
//
//	private boolean onlyOneTime = true;
//
//	@Override
//	public void onLocationChanged(Location location) {
//		PARENT_ACTIVITY.currentLocation = location;
//		
//		if (radius != null)
//		{
//			radius.remove();
//		}
//		if (myLocation != null) {
//			myLocation.remove();
//		} else {
//			new LoadShops().execute();
//		}
//		
//		
//		
//		
//		myLocation = map.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("Βρίσκεστε εδώ"));
//		
//		CircleOptions optionRadius = new CircleOptions();
//		optionRadius.radius(200);
//		optionRadius.strokeWidth(2);
//		optionRadius.fillColor(0x3396AA39);
//		optionRadius.strokeColor(0xDD96AA39);
//		optionRadius.center(new LatLng(location.getLatitude(), location.getLongitude()));
//		
//		radius = map.addCircle(optionRadius);
//		
//		
//		if (onlyOneTime) {
//			map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));
//			onlyOneTime = false;
//		}
//		
//		
//		
//		
//	}
//
//	private class LoadShops extends AsyncTask<String, Void, ArrayList<ShopPojo>> {
//
//		@Override
//		protected void onPreExecute() {
//
//			showProgressDialog("Downloading near Shops...");
//		}
//
//		@Override
//		protected ArrayList<ShopPojo> doInBackground(String... params) {
//			ArrayList<ShopPojo> items = new ArrayList<ShopPojo>();
//
//			for (int i = 0; i < 45; i++) {
//				ShopPojo item = new ShopPojo();
//				item.setName("Shop " + i);
//				item.setStreet("King Street " + i);
//				item.setZipCode("57001");
//				item.setPhone("6999999999");
//				item.setRegion("Serres");
//				item.setLatitude(40.576454 +i);
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
//
//			return items;
//		}
//
//		@Override
//		protected void onPostExecute(ArrayList<ShopPojo> result) {
//			dismissProgressDialog();
//			PARENT_ACTIVITY.shops = result;
//			for (int i = 0; i < result.size(); i++) {
//				map.addMarker(new MarkerOptions().position(new LatLng(result.get(i).getLatitude(), result.get(i).getLongitude())).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)).title(result.get(i).getName()));
//
//			}
//		}
//
//	}
//
//	@Override
//	public void onDestroyView() {
//
//		locationManager.removeUpdates(this);
//		super.onDestroyView();
//	}
//
//	@Override
//	public void onProviderDisabled(String provider) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void onProviderEnabled(String provider) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void onStatusChanged(String provider, int status, Bundle extras) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void display() {
//		// TODO Auto-generated method stub
//		Log.e("","GPSfragment");
//		super.display();
//	}
//	
//}
