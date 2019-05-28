package com.serviceapp.services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.navdrawer.SimpleSideDrawer;
import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RequestDetails extends AppCompatActivity {
	EditText descrptn,dt,tmFrom,tmTo,feedback,serviceMan,serviceDate,servicePhone;
	TextView serviceName;
	Button submt;
	public Spinner status;
	SimpleRatingBar ratingBar;
	ScrollView scrollView;
	
	 public static final String PREF_NAME = "MyPrefs";
	 SharedPreferences sp;
	private Toolbar toolbar;
	 SimpleSideDrawer slide_me;
	 LinearLayout servc,rqst;
	 int requestId,houseId;
	 String serviceId,servTitle;
	 String one,two,availDate;
	 Json_Post sParser=new Json_Post();
	 String params1,token;
	 String json;
	 String[] datee,timee;
	 String s=null;
	 String rtg,sts,fb,spn,spdate,spPhn,prnstatus;
	String[] Status={"Status","Pending","Processing","Completed"};
	 
	 private static String url="http://smallwebsitedesign.in/service_management/webservices/service/service_reqst_details";
	 private static String url2="http://smallwebsitedesign.in/service_management/webservices/service/edit_service_reqst";
	  
	 @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.request_details_page);



		//Getting value of houseid from the sharedpreference 
		sp = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
		houseId = sp.getInt("houseId", 0);
		 token = sp.getString("token", "");
		
		//actionbar
		//ActionBar actionBar = getActionBar();
	//	actionBar.setTitle(" Feedback Request Form");

		 toolbar = (Toolbar) findViewById(R.id.main_bar);
		 setSupportActionBar(toolbar);

		 getSupportActionBar().setTitle("My Service Requests");


		 ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#D41031"));
		 getSupportActionBar().setBackgroundDrawable(colorDrawable);
		 toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
		
		//Defining the controls
		serviceName=(TextView) findViewById(R.id.serviceName);
		dt=(EditText) findViewById(R.id.dt);
		tmFrom=(EditText) findViewById(R.id.tmFrom);
		tmTo=(EditText) findViewById(R.id.tmTo);
		descrptn=(EditText) findViewById(R.id.msg);
		 serviceMan=(EditText)findViewById(R.id.serviceMan);
		 serviceDate=(EditText)findViewById(R.id.serviceDate);
		 servicePhone=(EditText)findViewById(R.id.servicePhone);
		ratingBar=(SimpleRatingBar) findViewById(R.id.ratingBar);
		 status=(Spinner) findViewById(R.id.status);
		feedback=(EditText) findViewById(R.id.feedBack);
		 feedback.setMovementMethod(new ScrollingMovementMethod());
		 feedback.setVerticalScrollBarEnabled(true);
		 feedback.isVerticalScrollBarEnabled();
		 scrollView=(ScrollView)findViewById(R.id.scrollDetail);
		 feedback.setOnTouchListener(new View.OnTouchListener() {
			 @Override
			 public boolean onTouch(View view, MotionEvent motionEvent) {
				 scrollView.requestDisallowInterceptTouchEvent(true);
				 return false;
			 }
		 });
		submt=(Button) findViewById(R.id.submt);

		 status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		 {

			 @Override
			 public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
			 {
				 //Change the selected item's text color
				 ((TextView) view).setTextColor(Color.WHITE);
				 ((TextView) view).setTextSize(10);
			 }

			 @Override
			 public void onNothingSelected(AdapterView<?> parent)
			 {

			 }
		 });

//		 LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
//		// stars.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
//		 stars.getDrawable(2).setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
//		 stars.getDrawable(0).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
		 //Defining the adapter for the rating dropdown
	//	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item) {
		
          //  @Override
          //  public int getCount() {
          //      return super.getCount()-1; // you dont display last item. It is used as hint.
          //  }

      //  };
	// initiate a rating bar
		 // ArrayAdapter<String> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //adapter.add("1");
        //adapter.add("2");
        //adapter.add("3");
       // adapter.add("4");
       // adapter.add("5");
       // adapter.add("Rating"); //This is the text that will be displayed as hint.

       // rating.setAdapter(adapter);
        //rating.setSelection(adapter.getCount()); //set the hint the default selection so it appears on launch.
//      rating.setOnItemSelectedListener(this);
		
		
//		ArrayAdapter<String> adapter1=new ArrayAdapter<>(RequestDetails.this, android.R.layout.simple_spinner_dropdown_item, rtng);
//		rating.setAdapter(adapter1);
		
//		ArrayAdapter<String> adapter2=new ArrayAdapter<>(RequestDetails.this, android.R.layout.simple_spinner_dropdown_item, stats);
//		status.setAdapter(adapter2);

        //Defing the adapter for the status dropdown


        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item) {

			@Override
           public int getCount() {
               return super.getCount()-1; // you dont display last item. It is used as hint.

           }
        };

         adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         adapter2.add("Completed");
         adapter2.add("Processing");
         adapter2.add("Pending");
		 adapter2.add("Status");
		//This is the text that will be displayed as hint.
//		status.setPrompt("Status");
        status.setAdapter(adapter2);
        status.setSelection(adapter2.getCount());     //set the hint the default selection so it appears on launch.

        //Defining the action for the submit button
		submt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
                 View selectedView2 = status.getSelectedView();
				  if(ratingBar.getRating()==0.0)
				  {
					  Toast toast = Toast.makeText(getApplicationContext(), "Rate Us", Toast.LENGTH_LONG);
					  View view = toast.getView();
					  toast.setGravity(Gravity.TOP, 20, 20);
					  view.setBackgroundResource(R.drawable.toast_backgrnd);
					  TextView text = (TextView) view.findViewById(android.R.id.message);
				/*Here you can do anything with above textview like text.setTextColor(Color.parseColor("#000000"));*/
					  text.setTextColor(Color.parseColor("#FFFFFF"));
					  text.setTextSize(15);
					  toast.show();

					  Intent i=new Intent(RequestDetails.this,ListByStatus.class);
					  i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
					  startActivity(i);
                 }
				 else if((status.getSelectedItem().toString()).equalsIgnoreCase("Status")){
                	 TextView selectedTextView = (TextView) selectedView2;
                	 selectedTextView.setError("Select Service");
                	 return;
                 }
				 
				 else{					 
					 //call update
					 new updateData().execute();
				 }
				
			}
		});		
		
		//Getting the value of requestid from sharedpreference
		sp = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
		requestId = sp.getInt("requestId", 0);
		Log.e("idddd-----", ""+requestId);
		
		//calling the function to display the details
		new getDetails().execute();		
		
		//Code for Sidedrawer
				slide_me = new SimpleSideDrawer(this);
				slide_me.setRightBehindContentView(R.layout.right_menu);
				servc=(LinearLayout) slide_me.findViewById(R.id.servc);
				rqst=(LinearLayout) findViewById(R.id.request);
				servc.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i=new Intent(RequestDetails.this, ListByStatus.class);
						startActivity(i);
						slide_me.close();
					}
				});
				
				rqst.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i=new Intent(RequestDetails.this, RequestForm.class);
						startActivity(i);
						slide_me.close();
					}
				});
	}
	 
	 
	 //getdetails Function calling the POST method
	 private class getDetails extends AsyncTask<String, String, String>{
		 @Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			 
			 try {
				 System.out.println("reQUESTTTTTTTTTTTTTTTTTTTTTTTTTTTIDDDDDDDDDDDDDd"+requestId);

				 // build jsonObject
	            	JSONObject jsonObject = new JSONObject();
		            jsonObject.accumulate("request_id",requestId);
		            //convert JSONObject to JSON to String
		            json = jsonObject.toString();
		            
		            //Calling the POST method of Json_Post class
		            String result=sParser.POST(url+"?token="+token, json);
		            
		            JSONObject jsonObject1 = new JSONObject(result);
		            JSONObject gamesarray = jsonObject1.getJSONObject("resultArray");
	            	 JSONArray getArray = gamesarray.getJSONArray("request_details");
	 	            System.out.println("-------"+getArray);
	 	            
	 	            for(int i=0;i<getArray.length();i++){
	 	            	JSONObject items=getArray.getJSONObject(i);
	 	            	serviceId=items.getString("int_service_id");
	 	            	System.out.println("------------hi"+serviceId);
	 	            	servTitle=items.getString("str_service_title");
	 	            	one=items.getString("str_service_name");	            	
	 	            	availDate=items.getString("str_date_available");
	 	            	two=items.getString("txt_request_description");	 
	 	            	sts=items.getString("int_request_status");
	 	            	rtg=items.getString("int_request_rating");
	 	            	fb=items.getString("str_feedback");
	 	            	datee=availDate.split("from");
	 	            	timee=datee[1].split("to"); 
			 }
				 JSONArray assign=gamesarray.getJSONArray("assignee_details");
				 System.out.println("-------"+assign);
				 if(assign.length()!=0) {
					 for (int i = 0; i < assign.length(); i++) {
						 JSONObject itemss = assign.getJSONObject(i);
						 {
							 spn = itemss.getString("str_personal_title");
							 spPhn = itemss.getString("str_personal_phone");
							 spdate = itemss.getString("date_contact");

						 }
					 }
				 }
				 else
				 {
					 spn="Service Person:  Not Assigned";
					 spPhn="phone number : not available";
					 spdate="Date : Not Assigned";}

			 }catch (JSONException e1) {
					// TODO Auto-generated catch block
					Log.d("exceptionnnnnn", "");
					e1.printStackTrace();		
				}
			return null;
		}
		 
		 @Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			serviceName.setText(one.toString());
			dt.setText(datee[0]);
			tmFrom.setText(timee[0]);
			tmTo.setText(timee[1]);
        	descrptn.setText(two.toString());

			 serviceMan.setText(spn.toString());
			 servicePhone.setText(spPhn.toString());
			 serviceDate.setText(spdate.toString());

			 if(fb.equals("null")) {
				 feedback.getText().clear();
			 }
			 else
			 {
				 feedback.setText(fb.toString());
			 }
        	if(sts.equals("0")){
        		status.setSelection(2);
        	}
        	else if(sts.equals("1")){
        		status.setSelection(1);
        	}
        	else if(sts.equals("2")){
        		status.setSelection(0);
        	}



        	if(rtg.equals("1")){
        		ratingBar.setRating(1);
        	}
        	else if(rtg.equals("2")){
        		ratingBar.setRating(2);
        	}
        	else if(rtg.equals("3")){
        		ratingBar.setRating(3);
        	}
        	else if(rtg.equals("4")){
        		ratingBar.setRating(4);
        	}
        	else if(rtg.equals("5")){
        		ratingBar.setRating(5);
        	}
		}
	 }


	 //Update funtion calling POST method
		private class updateData extends AsyncTask<String, String, String> {
			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				
				JSONObject jsonObject = new JSONObject();
				
				try{
					String st=status.getSelectedItem().toString();
					jsonObject.accumulate("request_id",requestId);
					jsonObject.accumulate("request_title", servTitle);
					jsonObject.accumulate("user_available_date",
							dt.getText().toString()+
							"from"+tmFrom.getText().toString()+
							"to"+tmTo.getText().toString());
					jsonObject.accumulate("request_description",
							descrptn.getText().toString());

					if(st.equalsIgnoreCase("completed")){
					jsonObject.put("request_status", 2);
				}
				else if(st.equalsIgnoreCase("processing")){
					jsonObject.put("request_status", 1);
				}
				else if(st.equalsIgnoreCase("pending")){
					jsonObject.put("request_status", 0);
				}


					jsonObject.accumulate("str_personal_title",
							serviceMan.getText().toString());

					jsonObject.accumulate("date_contact",
							serviceDate.getText().toString());

					jsonObject.accumulate("str_personal_phone",
							servicePhone.getText().toString());

					jsonObject.accumulate("service_feedback",
						feedback.getText().toString());

				jsonObject.accumulate("service_rating",
						ratingBar.getRating());
				
				params1 = jsonObject.toString();
	            json = sParser.POST(url2+"?token="+token,params1);
	            
	            Log.d("Album22 JSONmenuuu: ", "> " + json);
					
				}catch (JSONException e1) {
					// TODO Auto-generated catch block
					Log.d("exceptionnnnnn", "");
					e1.printStackTrace();		
				}
				
				 try {
					 JSONObject jsonObject1 = new JSONObject(json);
		                s= jsonObject1.getString("resultmsg");
		                Log.d("Msggggggggggggggg", s);               
	
		            } catch (JSONException e) {
		                // TODO Auto-generated catch block
		                e.printStackTrace();
		            }
				
				return null;
			}
			
			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				
				 if(s.equals("Success")){
		            	
//						Toast.makeText(getApplicationContext(), "Request Successfully Submitted", Toast.LENGTH_LONG).show();
						Toast toast = Toast.makeText(getApplicationContext(), "Request Successfully Updated", Toast.LENGTH_LONG);
						View view = toast.getView();
					    toast.setGravity(Gravity.TOP, 20, 20);
						view.setBackgroundResource(R.drawable.toast_backgrnd);
						TextView text = (TextView) view.findViewById(android.R.id.message);
						/*Here you can do anything with above textview like text.setTextColor(Color.parseColor("#000000"));*/
						text.setTextColor(Color.parseColor("#FFFFFF"));
					    text.setPadding(20,10,20,10);
						text.setTextSize(15);
						toast.show();

					 Intent i=new Intent(RequestDetails.this,ListByStatus.class);
					 i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
					 startActivity(i);

			            }
			            else{
			            	
//			            	Toast.makeText(getApplicationContext(), "Error Submitting", Toast.LENGTH_LONG).show();
			            	Toast toast = Toast.makeText(getApplicationContext(), "Error Updating", Toast.LENGTH_LONG);
							View view = toast.getView();
					       toast.setGravity(Gravity.TOP, 20, 20);
							view.setBackgroundResource(R.drawable.toast_backgrnd);
							TextView text = (TextView) view.findViewById(android.R.id.message);
							/*Here you can do anything with above textview like text.setTextColor(Color.parseColor("#000000"));*/
							text.setTextColor(Color.parseColor("#FFFFFF"));
							text.setTextSize(15);
							toast.show();
			            	
			            }
			}
		}
	
		
		//Getdetails function calling the GET method
//	private class getDetails extends AsyncTask<String, String, String>{
//
//		@Override
//		protected String doInBackground(String... arg0) {
//			// TODO Auto-generated method stub
//			
//			JSONObject jsonObject = new JSONObject();
//			
//			try{
//				
//				jsonObject.put("request_id",requestId);
//				
//				params = jsonObject.toString();
//	            json = jParser.makeHttpRequest(url, "GET", params);
//	            
//	            JSONObject result=json.getJSONObject("resultArray");
//	            JSONArray getArray = result.getJSONArray("request_details");
//	            System.out.println("-------"+getArray);	            
//	            for(int i=0;i<getArray.length();i++){
//	            	JSONObject items=getArray.getJSONObject(i);
//	            	serviceId=items.getString("int_service_id");
//	            	System.out.println("------------hi"+serviceId);
//	            	servTitle=items.getString("str_service_title");
//	            	one=items.getString("str_service_name");	            	
//	            	availDate=items.getString("str_date_available");
//	            	two=items.getString("txt_request_description");	 
//	            	sts=items.getString("int_request_status");
//	            	rtg=items.getString("int_request_rating");
//	            	fb=items.getString("str_feedback");
//	            	datee=availDate.split("from");
//	            	timee=datee[1].split("to");            	
//	            }
//				
//			}catch(JSONException e){
//				e.printStackTrace();
//			}
//			
//			
//			return null;
//		}
//		
//		@Override
//		protected void onPostExecute(String result) {
//			// TODO Auto-generated method stub
//			super.onPostExecute(result);
//			serviceName.setText(one.toString()); 
//			dt.setText(datee[0]);
//			tmFrom.setText(timee[0]);
//			tmTo.setText(timee[1]);
//        	descrptn.setText(two.toString());
//        	feedback.setText(fb);
//        	
//        	if(sts.equals("0")){
//        		status.setSelection(2);
//        	}
//        	else if(sts.equals("1")){
//        		status.setSelection(1);
//        	}
//        	else if(sts.equals("2")){
//        		status.setSelection(0);
//        	}
//        	
//        	if(rtg.equals("1")){
//        		rating.setSelection(0);
//        	}
//        	else if(rtg.equals("2")){
//        		rating.setSelection(1);
//        	}
//        	else if(rtg.equals("3")){
//        		rating.setSelection(2);
//        	}
//        	else if(rtg.equals("4")){
//        		rating.setSelection(3);
//        	}
//        	else if(rtg.equals("5")){
//        		rating.setSelection(4);
//        	}
//		}		
//	}
	
	
		//update function calling the GET method
//	private class updateData extends AsyncTask<String, String, String>{
//
//		@Override
//		protected String doInBackground(String... arg0) {
//			// TODO Auto-generated method stub
//			JSONObject jsonObject = new JSONObject();
//			
//			try{
//				String st=status.getSelectedItem().toString();
//				jsonObject.put("request_id",requestId);
//				jsonObject.put("request_title", servTitle);
//				jsonObject.put("user_available_date", dt.getText().toString()+"from"+tmFrom.getText().toString()+"to"+tmTo.getText().toString());
//				jsonObject.put("request_description", descrptn.getText().toString());
//				
//				if(st.equalsIgnoreCase("completed")){
//					jsonObject.put("request_status", 2);
//				}
//				else if(st.equalsIgnoreCase("processing")){
//					jsonObject.put("request_status", 1);
//				}
//				else if(st.equalsIgnoreCase("pending")){
//					jsonObject.put("request_status", 0);
//				}
//								
//				jsonObject.put("service_feedback", feedback.getText().toString());
//				jsonObject.put("service_rating", rating.getSelectedItem().toString());
////								
//				params = jsonObject.toString();
//	            json = jParser.makeHttpRequest(url2, "GET", params);
//	            
//	            Log.d("Album22 JSONmenuuu: ", "> " + json);
//	            
//	         
//				
//			}catch(JSONException e){
//				e.printStackTrace();
//			}
//			
//			 try {
//	           	 
//	                s= json.getString("resultmsg");
//	                Log.d("Msg", s);               
//
//	            } catch (JSONException e) {
//	                // TODO Auto-generated catch block
//	                e.printStackTrace();
//	            }
//			
//			
//			return null;
//		}
//		
//		@Override
//		protected void onPostExecute(String result) {
//			// TODO Auto-generated method stub
//			 if(s.equals("Success")){
//	            	
////					Toast.makeText(getApplicationContext(), "Request Successfully Submitted", Toast.LENGTH_LONG).show();
//					Toast toast = Toast.makeText(getApplicationContext(), "Request Successfully Updated", Toast.LENGTH_LONG);
//					View view = toast.getView();
//					view.setBackgroundResource(R.drawable.toast_backgrnd);
//					TextView text = (TextView) view.findViewById(android.R.id.message);
//					/*Here you can do anything with above textview like text.setTextColor(Color.parseColor("#000000"));*/
//					text.setTextColor(Color.parseColor("#FFFFFF"));
//					text.setTextSize(15);
//					toast.show();
//					 
//		            }else{
//		            	
////		            	Toast.makeText(getApplicationContext(), "Error Submitting", Toast.LENGTH_LONG).show();
//		            	Toast toast = Toast.makeText(getApplicationContext(), "Error Updating", Toast.LENGTH_LONG);
//						View view = toast.getView();
//						view.setBackgroundResource(R.drawable.toast_backgrnd);
//						TextView text = (TextView) view.findViewById(android.R.id.message);
//						/*Here you can do anything with above textview like text.setTextColor(Color.parseColor("#000000"));*/
//						text.setTextColor(Color.parseColor("#FFFFFF"));
//						text.setTextSize(15);
//						toast.show();
//		            	
//		            }		}
//		
//		
//	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu); 
        return super.onCreateOptionsMenu(menu);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		// Take appropriate action for each action item click
		switch (item.getItemId()) {

			case R.id.home_main:
				// Navigate
				Intent i=new Intent(RequestDetails.this,ListByStatus.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i);
				return true;

			default:
				return super.onOptionsItemSelected(item);

		}
	}

}
