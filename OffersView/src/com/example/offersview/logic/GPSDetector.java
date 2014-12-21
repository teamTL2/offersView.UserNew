package com.example.offersview.logic;

import android.content.Context;
import android.location.LocationManager;

public class GPSDetector  {
	
	private Context _context;
	  
    public GPSDetector(Context context){
        this._context = context;		
    }
    
    
    
    public boolean isConnectingToGPS(){
    	
    	LocationManager connectivity = (LocationManager) _context.getSystemService(Context.LOCATION_SERVICE);
    	
        if ( connectivity.isProviderEnabled( LocationManager.GPS_PROVIDER ) ){
        	return true;
        }else{
        	return false;
        }
 
    }
        	
        	
}

	
	

