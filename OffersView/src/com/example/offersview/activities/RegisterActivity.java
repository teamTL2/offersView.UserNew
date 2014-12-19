package com.example.offersview.activities;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

import com.example.offersview.R;
import com.example.offersview.DAO.JSONParser;


//Register View
public class RegisterActivity extends Activity{
	
	private EditText email,password,rePassword;
	private Button button,buttonReturn;
	private ProgressDialog pDialog;
	

    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		email = (EditText) findViewById(R.id.Register_email);
		password = (EditText) findViewById(R.id.Register_password);
		rePassword = (EditText) findViewById(R.id.Re_Register_password);
		pDialog = new ProgressDialog(RegisterActivity.this);
		password.setOnEditorActionListener(new OnEditorActionListener() {
		    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		        if (actionId == EditorInfo.IME_ACTION_GO) {
		        	button.performClick();
		            return true;
		        }
		        return false;
		    }
		});
		
		button = (Button) findViewById(R.id.btnRegister);
		buttonReturn = (Button) findViewById(R.id.btnReturn);
		
		buttonReturn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intetn = new Intent(getApplicationContext(),LoginActivity.class);
        		
        		startActivity(intetn);
        		finish();
				}
			});
			
	
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
								
				if (!email.getText().toString().equals("") 
						&& !password.getText().toString().equals("")
						&& !rePassword.getText().toString().equals("") 
						&&	password.getText().toString().equals(rePassword.getText().toString())){
					new RegisterOperation(email.getText().toString(),password.getText().toString()).execute();
				} 
				else if(email.getText().toString().equals("") || password.getText().toString().equals("")
								|| rePassword.getText().toString().equals("")){
	        		Toast.makeText(getApplicationContext(), "Παρακαλώ συμπληρώστε όλα τα πεδία", Toast.LENGTH_SHORT).show();
				} 
				else if(!password.getText().toString().equals(rePassword.getText().toString())){
	        		Toast.makeText(getApplicationContext(), "Παρακαλώ βεβαιωθείτε πως οι κωδικοί ταιριάζουν", Toast.LENGTH_SHORT).show();
				}
			}
		});
	
	}
	
	
	
	private class RegisterOperation extends AsyncTask<String, Void, Boolean> {
		
		// JSON parser class
	    JSONParser jsonParser = new JSONParser();
	    
	    // register url
	    private static final String url_register = "http://offesview.bugs3.com/php/CreateUser.php";

	    // JSON Node names
	    private static final String TAG_SUCCESS = "success";
	    
		String email,password;

		public RegisterOperation(String email,String password)
		{
			this.email = email;
			this.password = password;
		}
		
		@Override
        protected void onPreExecute() {
        	pDialog.setMessage("Γίνεται ο έλεγχος των στοιχείων...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
        }
		
        
        protected Boolean doInBackground(String... args){
        	
        	
        	int success;
        	boolean result;
        	result = true;
        	try {
        		// Building Parameters
        		List<NameValuePair> params = new ArrayList<NameValuePair>();
        		params.add(new BasicNameValuePair("Username", email));
        	    params.add(new BasicNameValuePair("Password", password));

        		//Posting user data to script
        		// Note that register url will use POST request
        		JSONObject json = jsonParser.makeHttpRequest(
                    url_register, "POST", params);

        		Log.d("Create Response", json.toString());

        		// json success tag
          	  	success = json.getInt(TAG_SUCCESS);
          	  	
          	  	if (success == 1) {
          	  		result = true;
          	  	}else{
          	  		result = false;
          	  	}
        	}catch (JSONException e) {
        		e.printStackTrace();
        	}
        	return result;
        	
        }

        @Override
        protected void onPostExecute(Boolean result) {
        	pDialog.dismiss();
        	
        	//Result Message
        	if(result)
        	{
        		Toast.makeText(getApplicationContext(), "Η εγγραφή σας ολοκληρώθηκε", Toast.LENGTH_SHORT).show();
        		
        		Intent intetn = new Intent(getApplicationContext(),LoginActivity.class);
        		
        		startActivity(intetn);
        		finish();
        	}
        	else
        		Toast.makeText(getApplicationContext(), "Η εγγραφή απέτυχε", Toast.LENGTH_SHORT).show();
        }

    }

}