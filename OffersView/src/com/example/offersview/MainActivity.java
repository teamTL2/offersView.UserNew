package com.example.offersview;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.offersview.activities.MapViewActivity;
import com.example.offersview.activities.NearestShopsActivity;
import com.example.offersview.activities.ShopListActivity;
 
public class MainActivity extends Activity{
 
    Button btnViewShops;
    Button btnViewNearestShops;
    Button btnMapViewNearestShops;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        // Buttons
        btnViewShops = (Button) findViewById(R.id.btnViewShops);
        btnViewNearestShops = (Button) findViewById(R.id.btnViewNearestShops);
        btnMapViewNearestShops = (Button) findViewById(R.id.btnMapViewNearestShops);
        
        // view all shops click event
        btnViewShops.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View view) {
                // Launching All shops Activity
                Intent i = new Intent(getApplicationContext(), ShopListActivity.class);
                startActivity(i);
 
            }
        });
        
        btnMapViewNearestShops.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 Intent i = new Intent(getApplicationContext(), MapViewActivity.class);
	                startActivity(i);
			}
		});
        
        
        btnViewNearestShops.setOnClickListener(new View.OnClickListener() {
        	 
            @Override
            public void onClick(View view) {
                // Launching Nearest Shops Activity
                Intent i = new Intent(getApplicationContext(), NearestShopsActivity.class);
                startActivity(i);
 
            }
        });
 
       
    }
}
