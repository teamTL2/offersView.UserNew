package com.example.offersview;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.offersview.activities.NearestShopsActivity;
import com.example.offersview.activities.ShopListActivity;
 
public class MainActivity extends Activity{
 
    Button btnViewShops;
    Button btnViewNearestShops;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        // Buttons
        btnViewShops = (Button) findViewById(R.id.btnViewShops);
        btnViewNearestShops = (Button) findViewById(R.id.btnViewNearestShops);
        
        
        // view all shops click event
        btnViewShops.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View view) {
                // Launching All shops Activity
                Intent i = new Intent(getApplicationContext(), ShopListActivity.class);
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
