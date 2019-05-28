package com.serviceapp.services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {


	Button signIn;
	TextView txt,txt1;
	static EditText user;
	static EditText pass;

	private static String url ="http://smallwebsitedesign.in/service_management/webservices/login";

	Json_Post sParser=new Json_Post();

	//String json;
	String json;

	//Shared preferrence
	public static final String PREF_NAME = "MyPrefs" ;
	static SharedPreferences sp;
	static String id;
	Boolean logged;

	String u,p;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);


		//action bar
//		ActionBar actionBar = getActionBar();
//		actionBar.hide();


		//Defining the controls
		txt=(TextView) findViewById(R.id.textview1);
		user=(EditText) findViewById(R.id.userName);
		pass=(EditText) findViewById(R.id.password);
		txt1=(TextView) findViewById(R.id.forgot);
		txt1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i=new Intent(Login.this, ForgetPassword.class);
				startActivity(i);
			}
		});


		signIn=(Button) findViewById(R.id.signIn);

		sp= getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		logged=sp.getBoolean("hasLogged",false);

		if(logged){
			Intent intent = new Intent(Login.this, ListByStatus.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
			Login.this.finish();
			startActivity(intent);

		}

		//Click event of the SignIn button
		signIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				u=user.getText().toString();
				p=pass.getText().toString();
				checkInternetConenction();

				if(user.getText().toString().isEmpty()|| pass.getText().toString().isEmpty()){

					Toast toast = Toast.makeText(getApplicationContext(), "Please Enter a Valid Username & Password", Toast.LENGTH_LONG);
					View view = toast.getView();
					toast.setGravity(Gravity.TOP, 0, 0);
					view.setBackgroundResource(R.drawable.toast_backgrnd);
					TextView text = (TextView) view.findViewById(android.R.id.message);
					 /*Here you can do anything with above textview like text.setTextColor(Color.parseColor("#000000"));*/
					text.setTextColor(Color.parseColor("#FFFFFF"));
					text.setPadding(20,10,20,10);
					text.setTextSize(15);
					toast.show();
				}
				else{

					//Calling the function to check the username and password
					//new jsonParsing2().execute();
					new HttpAsyncTask().execute(url);

				}
			}
		});
	}

	private boolean checkInternetConenction() {
		// get Connectivity Manager object to check connection
		ConnectivityManager connec
				=(ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

		// Check for network connections
		if ( connec.getNetworkInfo(0).getState() ==
				android.net.NetworkInfo.State.CONNECTED ||
				connec.getNetworkInfo(0).getState() ==
						android.net.NetworkInfo.State.CONNECTING ||
				connec.getNetworkInfo(1).getState() ==
						android.net.NetworkInfo.State.CONNECTING ||
				connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
			return true;
		}else if (
				connec.getNetworkInfo(0).getState() ==
						android.net.NetworkInfo.State.DISCONNECTED ||
						connec.getNetworkInfo(1).getState() ==
								android.net.NetworkInfo.State.DISCONNECTED  ) {

			Toast toast = Toast.makeText(getApplicationContext(), " Please Check Your Internet Connection ", Toast.LENGTH_LONG);
			View view = toast.getView();
			toast.setGravity(Gravity.TOP, 20, 20);
			view.setBackgroundResource(R.drawable.toast_backgrnd);
			TextView text = (TextView) view.findViewById(android.R.id.message);
					 /*Here you can do anything with above textview like text.setTextColor(Color.parseColor("#000000"));*/
			text.setTextColor(Color.parseColor("#FFFFFF"));
			text.setPadding(20, 10, 20, 10);
			text.setTextSize(15);
			toast.show();

			return false;
		}
		return false;
	}
	//Function checking username and password using POST method
	private class HttpAsyncTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {

			//Getting the user Typed username and password into variables
			String username = u.toString();
			String passwrd = p.toString();

			//Passing the username and the password
			try {

				// build jsonObject
				JSONObject jsonObject = new JSONObject();
				jsonObject.accumulate("password",passwrd);
				jsonObject.accumulate("userName",username);

				//convert JSONObject to JSON to String
				json = jsonObject.toString();

				System.out.println("jsonnnnnnnnnnnnnnnnnnnnnnnn"+json);

			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				Log.d("exceptionnnnnn", "");
				e1.printStackTrace();
			}

			//Calling the POST method of Json_Post class
			return sParser.POST(urls[0], json);
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {

			System.out.println("JSONNNNNNNNNNNNNNNNNNN========"+result);


			try{

				//coverting the result string to jsonobject
				JSONObject jsonObject1 = new JSONObject(result);

				//getting the value 'resultmsg'
				String rmsg=jsonObject1.getString("resultmsg");


				if(rmsg.equals("Success"))
				{

					Toast toast = Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_LONG);
					View view = toast.getView();
					toast.setGravity(Gravity.TOP, 20,20);
					view.setBackgroundResource(R.drawable.toast_backgrnd);
					TextView text = (TextView) view.findViewById(android.R.id.message);
		   				 /*Here you can do anything with above textview like text.setTextColor(Color.parseColor("#000000"));*/
					text.setTextColor(Color.parseColor("#FFFFFF"));
					text.setPadding(20,10,20,10);
					text.setTextSize(15);
					toast.show();


					Intent login = new Intent(Login.this, ListByStatus.class);
					login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
					Login.this.finish();
					startActivity(login);
					SharedPreferences.Editor editor = sp.edit();
					editor.putBoolean("hasLogged",true);
					editor.commit();

				}

				else{
					Toast toast = Toast.makeText(getApplicationContext(), "Wrong Username and Password", Toast.LENGTH_LONG);
					View view = toast.getView();
					toast.setGravity(Gravity.TOP, 20, 20);
					view.setBackgroundResource(R.drawable.toast_backgrnd);
					TextView text = (TextView) view.findViewById(android.R.id.message);
					/*Here you can do anything with above textview like text.setTextColor(Color.parseColor("#000000"));*/
					text.setTextColor(Color.parseColor("#FFFFFF"));
					text.setPadding(20,10,20,10);
					text.setTextSize(15);
					toast.show();
				}


			}catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try{
				JSONObject jsonObject1 = new JSONObject(result);
				JSONArray gamesarray = jsonObject1.getJSONArray("resultArray");
				JSONObject one = gamesarray.getJSONObject(0);
				int id=one.getInt("int_house_id");
				String token=one.getString("app_token");
				System.out.println("The house id is==="+id);
				//Storing the houseId from the url into the shared preference
				sp = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
				SharedPreferences.Editor editor = sp.edit();
				editor.putInt("houseId", id);
				editor.putString("token", token);
				editor.commit();

			}catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	//Function Checking the username and password using GET method
//	private class jsonParsing extends AsyncTask<String, String, String>{
//
//		@Override
//		protected String doInBackground(String... arg0) {
//			// TODO Auto-generated method stub
//
//			//Getting the user Typed username and password into variables
//			String username = user.getText().toString();
//            String passwrd = pass.getText().toString();
//
//            Log.e("USERNAME", username);
//            Log.e("PASSWORD", passwrd);
//
//            JSONObject jsonObject = new JSONObject();
//
//            //Passing the username and the password
//            try {
//            	jsonObject.put("password",passwrd);
//				jsonObject.put("userName",username);
//				params_d = jsonObject.toString();
//
////				json = jParser.makeHttpRequest(url, "GET", params_d);
//
//	            json = jParser.makeHttpRequest1(url, jsonObject);
//
//	            Log.e("Json response", json.toString());
//
//			} catch (JSONException e1) {
//				// TODO Auto-generated catch block
//				Log.d("exceptionnnnnn", "");
//				e1.printStackTrace();
//			}
//
//            //Getting the resultmsg of the url
//            try {
//
//                s= json.getString("resultmsg");
//                Log.d("Msg", s);
//
//            } catch (JSONException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//
//            //Getting the houseId from the url
//           try{
//            	JSONArray gamesarray = json.getJSONArray("resultArray");
//            	JSONObject one = gamesarray.getJSONObject(0);
//            	int id=one.getInt("int_house_id");
//
//            	//Storing the houseId from the url into the shared preference
//            	sp = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
//				SharedPreferences.Editor editor = sp.edit();
//				editor.putInt("houseId", id);
//				editor.commit();
//
//
//            }catch (JSONException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//            return null;
//
//		}
//
//		@Override
//		protected void onPostExecute(String result) {
//			// TODO Auto-generated method stub
//		}

//	}

}
