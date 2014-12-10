package com.example.offersview.activities;

import com.example.offersview.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
// splash screen
public class SplashActivity extends Activity{
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
	
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
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
