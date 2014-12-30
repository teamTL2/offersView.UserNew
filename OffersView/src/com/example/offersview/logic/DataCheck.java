package com.example.offersview.logic;

import android.content.Context;
import android.widget.Toast;

public class DataCheck {
    
	String email;
	String password;
	String rePassword;
	Context c;
	
	
	
    public DataCheck(String email, String password, String rePassword, Context context) {
    	
    	this.email = email; 
        this.password = password; 
        this.rePassword = rePassword; 
        this.c= context;
	}
    
    public DataCheck(String email, String password, Context context) {
    	
    	this.email = email; 
        this.password = password; 
        this.c= context;
	}

   public boolean isNotEmptyLogin(){
    	
    	if (email.equals("") || password.equals("")){
    		Toast.makeText(c, "Please make sure to fill all the fields", Toast.LENGTH_SHORT).show();
    		return false;
    	}
    	else
    		return true;	
    }
    
    
    
    public boolean isNotEmpty(){
    	
    	if (email.equals("") || password.equals("") || rePassword.equals(""))
    		return false;
    	else
    		return true;	
    }
    
    
    
    public boolean isPassMatch(){
    	
    	if (password.equals(rePassword))
    		return true;
    	else
    		return false;
    }
    
    
    
    public boolean isSymbolsNotUsed() {
    	
        String[] RestrictedSymbols = {"!","@","#","$","%","^","&","*","(",")","-","_","+","*","/"};

        
        for(int i=0; i<RestrictedSymbols.length; i++){
            if(email.contains(RestrictedSymbols[i]))
                return false;
        }
        if(email.contains(" "))
            return false;
        
        return true;
    
    }
    			
    
    public boolean isCheckPassed(){
    	
    	boolean empty = isNotEmpty(); 
        boolean match = isPassMatch();
        boolean symbols = isSymbolsNotUsed();
        
    	if (!empty){
    		Toast.makeText(c, "Please make sure to fill all the fields", Toast.LENGTH_SHORT).show();
    		return false;
    	}
        else if(!match){
    		Toast.makeText(c, "Please make sure that passwords match", Toast.LENGTH_SHORT).show();
    		return false;
        }
        else if(!symbols){
    		Toast.makeText(c, "Please make sure that you are not using any symbols in your username", Toast.LENGTH_SHORT).show();
    		return false;
        }
    	
    	return true;
    	
    }
    
 
}