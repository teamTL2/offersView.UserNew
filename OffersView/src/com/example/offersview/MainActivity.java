package com.example.offersview;


import com.example.offersview.R;
import com.example.offersview.activities.ShopListActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
 
public class MainActivity extends Activity{
 
    Button btnViewShops;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        // Buttons
        btnViewShops = (Button) findViewById(R.id.btnViewShops);
 
        // view products click event
        btnViewShops.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View view) {
                // Launching All products Activity
                Intent i = new Intent(getApplicationContext(), ShopListActivity.class);
                startActivity(i);
 
            }
        });
 
       
    }
}
