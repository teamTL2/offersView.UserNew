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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import com.example.offersview.R;
import com.example.offersview.logic.JSONParser;

public class ShopDetailsActivity  extends ListActivity {
	 
	// Progress Dialog
    private ProgressDialog pDialog;
    private Button buttonFav, buttonOffers;
    
    String sid;
    
    ArrayList<HashMap<String, String>> detailsList; 
	
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_SHOPS = "shops";
	private static final String TAG_SHOP_ID = "Shop_ID";
    private static final String TAG_NAME = "ShopName";
    private static final String TAG_STREET = "Street";
    private static final String TAG_EMAIL = "Email";
	private static final String TAG_PHONE = "Phone";
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);
        
        buttonFav = (Button) findViewById(R.id.btnAddFavorites);
		buttonOffers = (Button) findViewById(R.id.btnShowShopOffers);
        
        Intent i = getIntent();
        
        sid = i.getStringExtra(TAG_SHOP_ID);
        
        // Hashmap for ListView
        detailsList = new ArrayList<HashMap<String, String>>();
 
        // Loading shop details in Background Thread
        new LoadAllDetails().execute();
        
        buttonFav.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//
				//
			}
		});
        
        buttonOffers.setOnClickListener(new OnClickListener() {
        
			@Override
			public void onClick(View view) {
                
                // Starting new intent
                Intent in = new Intent(getApplicationContext(),
                		OffersActivity.class);
                // sending pid to next activity
                in.putExtra(TAG_SHOP_ID, sid);
 
                // starting new activity and expecting some response back
                startActivityForResult(in, 100);
				
                
                
			}
		});
        
        
	}
	
	
	/**
     * Background Async Task to Load all product by making HTTP Request
     * */
    class LoadAllDetails extends AsyncTask<String, String, String> {
    	
        // Creating JSON Parser object
        JSONParser jParser = new JSONParser();
        // url to get all shop details list
        private static final String url_all_details = "http://offesview.bugs3.com/php/GetDetailsShop.php";
     

        // products JSONArray
        JSONArray details = null;
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ShopDetailsActivity.this);
            pDialog.setMessage("Loading information. Please wait...");
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
            JSONObject json = jParser.makeHttpRequest(url_all_details, "POST", params);
            
            // Check your log cat for JSON reponse
            Log.d("All Info: ", json.toString());
            
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                    // products found
                    // Getting Array of Details
                        details = json.getJSONArray(TAG_SHOPS);
 
                        // looping through All Details
                        for (int i = 0; i < details.length(); i++) {
                        	JSONObject c = details.getJSONObject(i);
 
                        	// Storing each json item in variable
                        	String id = c.getString(TAG_SHOP_ID);
                        	String name = c.getString(TAG_NAME);
                        	String street = c.getString(TAG_STREET);
                        	String phone = c.getString(TAG_PHONE);
                        	String email = c.getString(TAG_EMAIL);
 
                        	// creating new HashMap
                        	HashMap<String, String> map = new HashMap<String, String>();
 
                        	// adding each child node to HashMap key => value
                        	map.put(TAG_SHOP_ID, id);
                        	map.put(TAG_NAME, name);
                        	map.put(TAG_STREET, street);
                        	map.put(TAG_PHONE, phone);
                        	map.put(TAG_EMAIL, email);
 
                        	// adding HashList to ArrayList
                        	detailsList.add(map);
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
                            ShopDetailsActivity.this, detailsList,
                            R.layout.details_list, new String[] { TAG_SHOP_ID,
                                    TAG_NAME, TAG_STREET, TAG_PHONE, TAG_EMAIL},
                            new int[] { R.id.id, R.id.name , R.id.street, R.id.phone, R.id.email });
                    //updating listview
                    setListAdapter(adapter);
                }
            });
 
        }
 
    }	
	
}


