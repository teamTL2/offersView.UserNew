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
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

import com.example.offersview.MainActivity;
import com.example.offersview.R;
import com.example.offersview.DAO.JSONParser;


//Login View
public class LoginActivity extends Activity{
	
	private EditText email,password;
	private Button button, buttonR;
	private ProgressDialog pDialog;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		email = (EditText) findViewById(R.id.login_email);
		password = (EditText) findViewById(R.id.login_edit_password);
		pDialog = new ProgressDialog(LoginActivity.this);
		password.setOnEditorActionListener(new OnEditorActionListener() {
		    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		        if (actionId == EditorInfo.IME_ACTION_GO) {
		        	button.performClick();
		            return true;
		        }
		        return false;
		    }
		});
		
		button = (Button) findViewById(R.id.btnLogin);
		buttonR = (Button) findViewById(R.id.btnNewUser);
		
		buttonR.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intetn = new Intent(getApplicationContext(),RegisterActivity.class);
        		
        		startActivity(intetn);
        		finish();
			}
		});
		
		
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (!email.getText().toString().equals("") && !password.getText().toString().equals("")) {
					
					new LoginOperation(email.getText().toString(),password.getText().toString()).execute();	
				}
				else {
					
	        		Toast.makeText(getApplicationContext(), "Παρακαλώ συμπληρώστε όλα τα πεδία", Toast.LENGTH_SHORT).show();
				}

			}
		});
	
	}
	
	
	private class LoginOperation extends AsyncTask<String, Void, Boolean> {
		
		// JSON parser class
	    JSONParser jsonParser = new JSONParser();
	    
	    // login url
	    private static final String url_login = "http://offesview.bugs3.com/php/LoginUser.php";

	    // JSON Node names
	    private static final String TAG_SUCCESS = "success";
	    
		String email,password;
		
		public LoginOperation(String email,String password)
		{
			this.email = email;
			this.password = password;
		}
		
		@Override
        protected void onPreExecute() {
        	pDialog.setMessage("Γίνετε ο έλεγχος των στοιχείων...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
        }
		
        
        protected Boolean doInBackground(String... args){
        	
        	
        	int success;
        	boolean result = true;
        	
        	try {
        		// Building Parameters
        		List<NameValuePair> params = new ArrayList<NameValuePair>();
        		params.add(new BasicNameValuePair("Username", email));
        	    params.add(new BasicNameValuePair("Password", password));

        		// getting user details by making HTTP request
        		// Note that user details url will use GET request
        		JSONObject json = jsonParser.makeHttpRequest(
                    url_login, "POST", params);

        		

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
        		Toast.makeText(getApplicationContext(), "Καλώς ήρθατε", Toast.LENGTH_SHORT).show();
        		
        		Intent intetn = new Intent(getApplicationContext(),MainActivity.class);
        		
        		startActivity(intetn);
        		finish();
        	}
        	else
        		Toast.makeText(getApplicationContext(), "Τα στοιχεία είναι λάθος", Toast.LENGTH_SHORT).show();
        }

    }

}