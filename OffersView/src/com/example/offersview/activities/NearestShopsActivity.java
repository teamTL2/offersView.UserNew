package com.example.offersview.activities;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.offersview.R;
import com.example.offersview.logic.GPSTracker;
import com.example.offersview.logic.JSONParser;
 
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
 
public class NearestShopsActivity extends ListActivity {
 
    // Progress Dialog
    private ProgressDialog pDialog;
 
 // GPSTracker class
    GPSTracker gps;
    
    ArrayList<HashMap<String, String>> shopsList;   
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
	private static final String TAG_SHOPS = "shops";
    private static final String TAG_SHOP_ID = "Shop_ID";
    private static final String TAG_NAME = "ShopName";
    
	String latitude;
    String longitude;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearest_shops);
        
        // create class object
        gps = new GPSTracker(NearestShopsActivity.this);
        
        // check if GPS enabled     
        if(gps.canGetLocation()){
             
        	 latitude = String.valueOf(gps.getLatitude());
        	 longitude = String.valueOf(gps.getLongitude());
        }
        
        
        // Hashmap for ListView
        shopsList = new ArrayList<HashMap<String, String>>();
 
        // Loading shops in Background Thread
        new LoadNearestShops().execute();
 
        // Get listview
        ListView lv = getListView();
 

        // on selecting single shop
        // launching shop details screen
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                // getting values from selected ListItem
                String sid = ((TextView) view.findViewById(R.id.id)).getText()
                        .toString();
                if(sid == "not"){
                	Toast.makeText(getApplicationContext(), "There are no shops near you" , Toast.LENGTH_SHORT).show();
                }else{
                // Starting new intent
                Intent in = new Intent(getApplicationContext(),
                		ShopDetailsActivity.class);
                // sending pid to next activity
                in.putExtra(TAG_SHOP_ID, sid);
 
                // starting new activity and expecting some response back
                startActivityForResult(in, 100);
                }
            }
        });
 
    }
    
    
    /**
     * Background Async Task to Load all product by making HTTP Request
     * */
    class LoadNearestShops extends AsyncTask<String, String, String> {
    	
        // Creating JSON Parser object
        JSONParser jParser = new JSONParser();
        // url to get all shops list
        private static final String url_nearest_shops = "http://offesview.bugs3.com/php/GetGpsShops.php";
     

        // shops JSONArray
        JSONArray shops = null;
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(NearestShopsActivity.this);
            pDialog.setMessage("Loading shops. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
 
        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
        	
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Latitude_User", latitude));
    	    params.add(new BasicNameValuePair("Longitude_User", longitude));
            
            
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_nearest_shops, "POST", params);
 
            // Check your log cat for JSON reponse
            Log.d("All Shops: ", json.toString());
 
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                    // shops found
                    // Getting Array of Products
                    shops = json.getJSONArray(TAG_SHOPS);
 
                    // looping through All Products
                    for (int i = 0; i < shops.length(); i++) {
                        JSONObject c = shops.getJSONObject(i);
 
                        // Storing each json item in variable
                        String id = c.getString(TAG_SHOP_ID);
                        String name = c.getString(TAG_NAME);
 
                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();
 
                        // adding each child node to HashMap key => value
                        map.put(TAG_SHOP_ID, id);
                        map.put(TAG_NAME, name);
 
                        // adding HashList to ArrayList
                        shopsList.add(map);
                    }
                }else{
                	// creating new HashMap
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(TAG_SHOP_ID, "not");
                    map.put(TAG_NAME, "There are no shops near you");
                    shopsList.add(map);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
 
            return null;
        }
 
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all shops
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            NearestShopsActivity.this, shopsList,
                            R.layout.shops_list, new String[] { TAG_SHOP_ID,
                                    TAG_NAME},
                            new int[] { R.id.id, R.id.name });
                    // updating listview
                    setListAdapter(adapter);
                }
            });
 
        }
 
    }
    
    
    
    
}
    