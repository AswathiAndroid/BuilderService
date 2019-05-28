package com.serviceapp.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Json_Post {
	
	 static InputStream is = null;
	 static JSONObject jObj = null;
	 static String json = "";
	 static JSONArray jarray = null;
	    
	 
	    // constructor
	    public Json_Post() {
	 
	    }
	    
		 public String POST(String url, String json){
		        InputStream inputStream = null;
		        String result = "";
		        try {
		 
		            // 1. create HttpClient
		            HttpClient httpclient = new DefaultHttpClient();

		            System.out.println("urllll"+url);
		 
		            // 2. make POST request to the given URL
		            HttpPost httpPost = new HttpPost(url);

		            // ** Alternative way to convert Person object to JSON string usin Jackson Lib 
		            // ObjectMapper mapper = new ObjectMapper();
		            // json = mapper.writeValueAsString(person); 
		 
		            // 5. set json to StringEntity
//		            StringEntity se = new StringEntity(json);
//
//		            // 6. set httpPost Entity
//		            httpPost.setEntity(se);
					if(!json.equals("")){
						httpPost.setEntity(new UrlEncodedFormEntity(jsonStringToArray(json), "UTF-8"));
					}
		 
		            // 7. Set some headers to inform server about the type of the content   
		            httpPost.setHeader("Accept", "application/json");
		            httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
		 
		            // 8. Execute POST request to the given URL
		            HttpResponse httpResponse = httpclient.execute(httpPost);
		 
		            // 9. receive response as inputStream
		            inputStream = httpResponse.getEntity().getContent();           
		 
		            result = convertInputStreamToString(inputStream);
		                        
		 
		        } catch (Exception e) {
		            Log.d("InputStream", e.getLocalizedMessage());
		        }
		        // 11. return result	        
		        Log.i("Result", result); 
		    	
		        return result;
		    }

	List<NameValuePair> jsonStringToArray(String jsonString) throws JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);

		JSONObject jsonArray = new JSONObject(jsonString);
		Iterator<String> iter = jsonArray.keys();
		while (iter.hasNext()) {
			String key = iter.next();
			try {
				String value = jsonArray.getString(key);
				params.add(new BasicNameValuePair(key, value));
			} catch (JSONException e) {
				// Something went wrong! } }
			}
		}
		for (int i = 0; i < jsonArray.length(); i++) {

			params.add(new BasicNameValuePair("param-2", "Hello!"));
		}
		return params;
	}
		 
		 
		 private static String convertInputStreamToString(InputStream inputStream) throws IOException{
		        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
		        String line = "";
		        String result = "";
		        while((line = bufferedReader.readLine()) != null)
		            result += line;
		        inputStream.close();
		        
		        Log.i("RESULT22222", result);        
		        return result;	 
		    }  
		 
	    
	  
}
