package com.example.offersview.activities;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.location.Location;
import android.location.LocationListener;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.offersview.R;
import com.example.offersview.logic.JSONParser;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapViewActivity extends ActionBarActivity implements
		LocationListener {
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

		mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		map = mapFragment.getMap();
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

		locationManager = (LocationManager) getSystemService(getApplicationContext().LOCATION_SERVICE);

		// GPS is ON?
		boolean enabledGPS = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		// // NETWORK PROVIDER is ON?
		boolean enabledNetwork = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);

		if (!enabledGPS && !enabledNetwork) {
			Toast.makeText(getApplicationContext(), "Open a provider",
					Toast.LENGTH_LONG).show();
		}

		if (firstLaunch == false) {
			map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
					39.465401, 22.804357), 6));
			firstLaunch = true;
		}

	}

	@Override
	public void onResume() {

		boolean enabledGPS = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		// NETWORK PROVIDER is ON?
		boolean enabledNetwork = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);

		if (enabledGPS) {
			locationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 60000, 500, this);
		}

		if (enabledNetwork) {
			locationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, 60000, 500, this);
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
			new LoadShops(location.getLatitude(), location.getLongitude())
					.execute();
		}
		myLocation = map.addMarker(new MarkerOptions().position(
				new LatLng(location.getLatitude(), location.getLongitude()))
				.title("You are here"));

		CircleOptions optionRadius = new CircleOptions();
		//here declare radius 
		optionRadius.radius(200);
		optionRadius.strokeWidth(2);
		optionRadius.fillColor(0x3396AA39);
		optionRadius.strokeColor(0xDD96AA39);
		optionRadius.center(new LatLng(location.getLatitude(), location
				.getLongitude()));

		radius = map.addCircle(optionRadius);

		if (onlyOneTime) {
			map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
					location.getLatitude(), location.getLongitude()), 15));
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

	private class LoadShops extends
			AsyncTask<String, Void, ArrayList<ShopPojo>> {
		ProgressDialog progressDialog;

		// JSON parser class
		JSONParser jsonParser = new JSONParser();
		// login url
		private static final String url_login = "http://offesview.bugs3.com/php/GetGpsMapShops.php";

		// JSON Node names
		private static final String TAG_SUCCESS = "success";

		private double lat, lon;

		public LoadShops(double lat, double lon) {
			this.lat = lat;
			this.lon = lon;
		}

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(MapViewActivity.this);
			progressDialog.setMessage("Downloading near Shops...");
			progressDialog.setIndeterminate(false);
			progressDialog.setCancelable(false);
			progressDialog.show();
		}

		@Override
		protected ArrayList<ShopPojo> doInBackground(String... args) {
			ArrayList<ShopPojo> shops = null;

			// Longitude_User
			// Latitude_User
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("Longitude_User", String.valueOf(lon)));
			params.add(new BasicNameValuePair("Latitude_User", String.valueOf(lat)));

			final JSONObject json = jsonParser.makeHttpRequest(url_login,
					"POST", params);

			Log.e("", "" + json.toString());
			int succes;
			try {
				succes = json.getInt(TAG_SUCCESS);

				switch (succes) {
				case 0:
					runOnUiThread(new Runnable() {
						public void run() {
							try {
								Toast.makeText(getApplicationContext(),
										json.getString("message"),
										Toast.LENGTH_SHORT).show();
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});

					break;
				case 1:
					shops = new ArrayList<ShopPojo>();

					JSONArray array = json.getJSONArray("shops");

					for (int i = 0; i < array.length(); i++) {
						// {"success":1,"shops":[{"ShopName":"mpla","Latitude":41.054500579834,"Longitude":23.5107421875,"Shop_ID":20}]}
						JSONObject temp = array.getJSONObject(i);
						ShopPojo shop = new ShopPojo();
						shop.setId(temp.getInt("Shop_ID"));
						shop.setName(temp.getString("ShopName"));
						shop.setLatitube(temp.getDouble("Latitude"));
						shop.setLongitube(temp.getDouble("Longitude"));
						shops.add(shop);
					}

					break;

				default:
					break;
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}

			return shops;
		}

		@Override
		protected void onPostExecute(ArrayList<ShopPojo> result) {
			progressDialog.dismiss();

			if (result != null) {
				if (result.size() == 0) {
					Toast.makeText(getApplicationContext(),
							"There are not shops near you.", Toast.LENGTH_SHORT)
							.show();
				} else {
					Toast.makeText(
							getApplicationContext(),
							"There " + ((result.size() == 1) ? "is " : "are ")
									+ "" + result.size() + " shop(s) near you.",
							Toast.LENGTH_SHORT).show();

					for (int i = 0; i < result.size(); i++) {
						map.addMarker(new MarkerOptions()
								.position(
										new LatLng(result.get(i).getLatitube(),
												result.get(i).getLongitube()))
								.icon(BitmapDescriptorFactory
										.fromResource(R.drawable.ic_launcher))
								.title(result.get(i).getName()));
					}
				}
			}

		}
	}

}
