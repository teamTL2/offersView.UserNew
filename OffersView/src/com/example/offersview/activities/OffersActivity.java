package com.example.offersview.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import com.example.offersview.R;
import com.example.offersview.logic.JSONParser;

public class OffersActivity  extends ListActivity {
	 
	// Progress Dialog
    private ProgressDialog pDialog;
    
    String sid;
    
    ArrayList<HashMap<String, String>> offersList; 
	
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_OFFERS = "offers";
	private static final String TAG_SHOP_ID = "Shop_ID";
	private static final String TAG_OFFER = "Offer";

    
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);
        
        
        Intent i = getIntent();
        
        sid = i.getStringExtra(TAG_SHOP_ID);
        
        // Hashmap for ListView
        offersList = new ArrayList<HashMap<String, String>>();
 
        // Loading shop details in Background Thread
        new LoadAllOffers().execute();
        
        
        
        
	}
	
	
	/**
     * Background Async Task to Load all product by making HTTP Request
     * */
    class LoadAllOffers extends AsyncTask<String, String, String> {
    	
        // Creating JSON Parser object
        JSONParser jParser = new JSONParser();
        // url to get all shop details list
        private static final String url_all_offers = "http://offesview.bugs3.com/php/GetAllOffersShop.php";
     

        // products JSONArray
        JSONArray offers = null;
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(OffersActivity.this);
            pDialog.setMessage("Loading info. Please wait...");
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
            params.add(new BasicNameValuePair("Shop_ID", sid));
        	
            // getting shop details by making HTTP request
            // Note that product details url will use POST request
            JSONObject json = jParser.makeHttpRequest(url_all_offers, "POST", params);
            
            // Check your log cat for JSON reponse
            Log.d("All Offers: ", json.toString());
            
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                    // products found
                    // Getting Array of Details
                        offers = json.getJSONArray(TAG_OFFERS);
                       
 
                        // looping through All Details
                        for (int i = 0; i < offers.length(); i++) {
                        	JSONObject c = offers.getJSONObject(i);
 
                        	// Storing each json item in variable
                        	String offer  = c.getString(TAG_OFFER);
 
                        	// creating new HashMap
                        	HashMap<String, String> map = new HashMap<String, String>();
 
                        	// adding each child node to HashMap key => value
                        	map.put(TAG_OFFER, offer);
                        	
 
                        	// adding HashList to ArrayList
                        	offersList.add(map);
                        }
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
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            OffersActivity.this, offersList,
                            R.layout.offers_list, new String[] { TAG_OFFER },
                            new int[] {R.id.offer });
                    //updating listview
                    setListAdapter(adapter);
                }
            });
 
        }
 
    }	
	
}


