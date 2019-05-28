package com.serviceapp.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
 

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences;
import android.util.Log;


public class JSONParser {
	
	 static InputStream is = null;
	 static JSONObject jObj = null;
	 static String json = "";
	 static JSONArray jarray = null;
	    
	 
	    // constructor
	    public JSONParser() {
	 
	    } 
	    
	    
	    // by making HTTP POST or GET mehtod
	    public JSONObject makeHttpRequest(String url, String method,
	           String params) {
	  
	        // Making HTTP request
	        try {
	  
	            // check for request method
	            if(method == "POST"){
//	            	List<NameValuePair> params = new List<NameValuePair>() 
	                // request method is POST
	                // defaultHttpClient
	                DefaultHttpClient httpClient = new DefaultHttpClient();
	                
//	                url +=URLEncoder.encode(params,"UTF-8");
//	                url="10.0.2.2/test/post.php";
	                Log.e("posting URLLLLLLLl====", url);
	                HttpPost httpPost = new HttpPost(url);
//	                httpPost.setEntity(new URLEncodedUtils(params));
	                	                
	                HttpResponse httpResponse = httpClient.execute(httpPost);
	                HttpEntity httpEntity = httpResponse.getEntity();
	                is = httpEntity.getContent();
	            		                
	  
	            }else if(method == "GET"){
	                // request method is GET
	                DefaultHttpClient httpClient = new DefaultHttpClient();
	                
//	                String paramString = URLEncodedUtils.format(params, "utf-8");
	                url += "?q=" + URLEncoder.encode(params,"UTF-8");            
	                Log.e("URL", url);
	                
//	                String finalUrl=URLEncoder.encode(url,"UTF-8");                
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
	                    is, "iso-8859-1"), 8);
	            StringBuilder sb = new StringBuilder();
	            String line = null;
	            
	            Log.e("CHEKINGGGGG", ""+sb);
	            while ((line = reader.readLine()) != null) {
	                sb.append(line + "\n");
	                
	            }
	            is.close();
	            json = sb.toString();
	            
	            
	        } catch (Exception e) {
	            Log.e("Buffer Error", "Error converting result " + e.toString());
	        }
	        
	        // try parse the string to a JSON object
	        try {
	        	
	            jObj = new JSONObject(json);
	            
	        } catch (JSONException e) {
	            Log.e("JSON Parser", "Error parsing data " + e.toString());
	        }
	  
	        // return JSON String
	        return jObj;
	  
	    }
	    
	       
	    
	    
	    public JSONArray getJSONFromUrl(String url) {
	 
	    	 StringBuilder builder = new StringBuilder();
	            HttpClient client = new DefaultHttpClient();
	            HttpGet httpGet = new HttpGet(url);
	            try {
	              HttpResponse response = client.execute(httpGet);
	              StatusLine statusLine = response.getStatusLine();
	              int statusCode = statusLine.getStatusCode();
	              if (statusCode == 200) {
	                HttpEntity entity = response.getEntity();
	                InputStream content = entity.getContent();
	                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
	                String line;
	                while ((line = reader.readLine()) != null) {
	                  builder.append(line);
	                }
	              } else {
	                Log.e("==>", "Failed to download file");
	              }
	            } catch (ClientProtocolException e) {
	              e.printStackTrace();
	            } catch (IOException e) {
	              e.printStackTrace();
	            }
	           
	        // Parse String to JSON object
	        try {
	            jarray = new JSONArray( builder.toString());
	        } catch (JSONException e) {
	            Log.e("JSON Parser", "Error parsing data " + e.toString());
	        }
	 
	        // return JSON Object
	        return jarray;
	 
	    }
	    
	    public JSONObject makeHttpRequest1(String url,
		           JSONObject params) {     	
	    	
	    	System.out.println("heyyyyy");
	    	try{

//	    		url="http://10.0.2.2/test/post.php";
      	  Map<String, String> params1 = new HashMap<String, String>();
            params1.put("John", "Taxi Driver");
            params1.put("Mark", "Professional Killer");
                  	
          DefaultHttpClient httpclient = new DefaultHttpClient();
          
//          url +=URLEncoder.encode(params.toString(),"UTF-8"); 
          
          //url with the post data
          HttpPost httpost = new HttpPost(url);
          System.out.println(url);
          //convert parameters into JSON object
//          JSONObject holder = getJsonObjectFromMap(params1);
          
          System.out.println("The valuesssssss"+params.toString());
          //passes the results to a string builder/entity
          StringEntity se = new StringEntity(params.toString());
//          
//          //sets the post request as the resulting string
          httpost.setEntity(se);
          
          //sets a request header so the page receving the request
          //will know what to do with it
          httpost.setHeader("Accept", "application/json");
          httpost.setHeader("Content-type", "application/json");
          

          //Handles what is returned from the page 
//          ResponseHandler responseHandler = new BasicResponseHandler();
          
          HttpResponse httpResponse = httpclient.execute(httpost);
          
          HttpEntity httpEntity = httpResponse.getEntity();
          is = httpEntity.getContent();          
//          System.out.println("End "+httpclient.execute(httpost, responseHandler));
          
          
	    } catch (UnsupportedEncodingException e) {
	    	System.out.println("First Chathc");
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            
        } 
  
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            
            StringBuilder sb = new StringBuilder();
            String line = null;
            
//            Log.e("CHEKINGGGGG", ""+sb);
            while ((line = reader.readLine()) != null) {
            	System.out.println("The Looppppp");
                sb.append(line + "\n");   
                System.out.println("Repeatttt "+sb.toString());
            }            
            
            is.close();
            json = sb.toString();
            System.out.println("Succeesssfullll"+json);
            
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
        
        // try parse the string to a JSON object
        try {
        	System.out.println(json.toString());

            jObj = new JSONObject(json);
            
        } catch (JSONException e) {
            Log.e("JSON Parserhyeyyy", "Error parsing data " + e.toString());
        }
  
        // return JSON String
        return jObj;
	    }

}
