package com.example.offersview.activities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.offersview.R;
import com.example.offersview.logic.JSONParser;

public class FavouritesListActivity extends Activity {
	private ListView lv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favourites_list);
		lv = (ListView) findViewById(R.id.lvFav);
		new task().execute();
	}
	
	
	class task extends AsyncTask<String, String, Void>
	{
		//Pernw to user ID
		int userID = LoginActivity.uid;
		String result;
		
		@Override
		protected Void doInBackground(String... params) {
			
			JSONParser jParserFav = new JSONParser();
			String uid = String.valueOf(userID);
			try {
				/*
				 * kalw tin fetchFavourites apo ton JSONParser
				 * me orisma to userID
				 */
				result = jParserFav.fetchFavrourites(uid);
			} catch (Exception e) {
			}
			return null;
		}
		@SuppressLint("NewApi")
		protected void onPostExecute(Void v) {
			try {
				List<String> offersList = new ArrayList();
				JSONArray Jarray = new JSONArray(result);
				for(int i=0;i<Jarray.length();i++)
				{
					JSONObject Jasonobject = null;
					Jasonobject = Jarray.getJSONObject(i);
					String name = Jasonobject.getString("ShopName");
					offersList.add(name);
				}
				/*
				 * Ta apotelesmata pou pira apo ton server
				 * ta apothikevw se enan JSONArray 
				 * kai meta ta topotheto se enan list adaptor
				 * gia na emfanistoun sto programma
				 */
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(FavouritesListActivity.this, R.layout.favourites_list, R.id.tvFav, offersList);
				adapter.notifyDataSetChanged();
				lv.setAdapter(adapter);
			} catch (Exception e) {
				Log.e("log_tag", "Error parsing data "+e.toString());
			}
		}
	}
}
