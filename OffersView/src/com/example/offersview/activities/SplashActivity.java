package com.example.offersview.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.offersview.R;
import com.example.offersview.logic.ConnectionDetector;
import com.example.offersview.logic.GPSDetector;
// splash screen
public class SplashActivity extends Activity{
	
	// flags for Internet & GPS connection status
	Boolean isInternetPresent = false;
	Boolean isGPSPresent = false;
	
	ConnectionDetector cd;
	GPSDetector gpsd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
	
		cd = new ConnectionDetector(getApplicationContext());
		gpsd = new GPSDetector(getApplicationContext()); 
		
		isInternetPresent = cd.isConnectingToInternet();
		isGPSPresent = gpsd.isConnectingToGPS();
		
		if (!isInternetPresent && !isGPSPresent)
		{
    		Toast.makeText(getApplicationContext(), "There is no internet and GPS connection", Toast.LENGTH_LONG).show();

		}else if(isInternetPresent && !isGPSPresent){
    		Toast.makeText(getApplicationContext(), "There is no GPS connection", Toast.LENGTH_LONG).show();

		}else if(!isInternetPresent && isGPSPresent){
    		Toast.makeText(getApplicationContext(), "There is no Internet connection", Toast.LENGTH_LONG).show();
		}
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					
					Thread.sleep(3000);
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
				startActivity(intent);
				finish();
				
			}
		}).start();
	
	}


}
