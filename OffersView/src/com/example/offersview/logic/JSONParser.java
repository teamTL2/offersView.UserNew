package com.example.offersview.logic;
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
 
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;
 
import android.util.Log;
 
public class JSONParser {
 
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
 
    // constructor
    public JSONParser() {
 
    }
 
    // function get json from url
    // by making HTTP POST or GET method
    public JSONObject makeHttpRequest(String url, String method,
            List<NameValuePair> params) {
 
        // Making HTTP request
        try {
 
            // check for request method
            if(method == "POST"){
                // request method is POST
                // defaultHttpClient
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(params));
 
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
 
            }else if(method == "GET"){
                // request method is GET
                DefaultHttpClient httpClient = new DefaultHttpClient();
                String paramString = URLEncodedUtils.format(params, HTTP.UTF_8);
                url += "?" + paramString;
                HttpGet httpGet = new HttpGet(url);
 
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            }           
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, HTTP.UTF_8), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
            	if(!line.startsWith("<", 0)){
            		if(!line.startsWith("(", 0)){
            			sb.append(line + "\n");
            		}
            	}
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
 
        // try parse the string to a JSON object
        //String JsonString="{\"token\":\"Abcxyy\"}";
        try {
            jObj = new JSONObject(json);
            Log.e("jObj", jObj.toString());
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
 
        // return JSON String
        return jObj;
 
    }
    
//**************************************************************************
    /*
     * H fetchFavourites pernei ws orisma to user id kai
     * epistrefei ola ta favourites tou antistixou xristi
     * kai kaleite sto FavouritesListActivity
     */
    
    public String fetchFavrourites(String uid){
    	String result="";
		InputStream is = null ;
		String url_select = "http://offesview.bugs3.com/php/getFav.php";
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url_select);
		ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("User_ID", uid));
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(param));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			is =  httpEntity.getContent();					
		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection "+e.toString());
		}
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			String line = "";
			while((line=br.readLine())!=null)
			{
				sb.append(line+"\n");
			}
			is.close();
			return result=sb.toString();
		} catch (Exception e) {
			Log.e("log_tag", "Error converting result "+e.toString());
		}
		return null;
    }
    
//***************************************************************************
    
    
    /*
     * Sunartisi pou prosthetei sta favorites 
     * to magazi pou epileksame
     * kai kaleite sto ShopDetailsActivity
     */

	public void addFavorite(final String storeID, final String userID) {
		// TODO Auto-generated method stub
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				List<NameValuePair> pairs = new ArrayList<NameValuePair>();
				pairs.add(new BasicNameValuePair("Shop_ID", storeID));
				pairs.add(new BasicNameValuePair("User_ID", userID));

				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost("http://offesview.bugs3.com/php/CreateUserFavorite.php");

				try {
					httppost.setEntity(new UrlEncodedFormEntity(pairs));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					httpclient.execute(httppost);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		t.start();
	}//end addFavourite()
	
//***********************************************************************
	
	
	/* 
	 * Sunartisi pou afairei to magazi apo ta favourites 
	 * kai kaleite sto ShopDetailsActivity
	 */

	public void removeFavouirte(final String storeID, final String userID){
		// TODO Auto-generated method stub
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				List<NameValuePair> pairs = new ArrayList<NameValuePair>();
				pairs.add(new BasicNameValuePair("Shop_ID", storeID));
				pairs.add(new BasicNameValuePair("User_ID", userID));

				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost("http://offesview.bugs3.com/php/RemoveUserFavourite.php");
				Log.v("ertag1", pairs.toString());
				try {
					httppost.setEntity(new UrlEncodedFormEntity(pairs));
					Log.v("ertag2", httppost.toString());
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					httpclient.execute(httppost);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		t.start();
	}//end removeFavouirte()
}