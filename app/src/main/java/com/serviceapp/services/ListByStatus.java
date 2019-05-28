package com.serviceapp.services;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import android.support.design.widget.FloatingActionButton;


public class ListByStatus extends AppCompatActivity {
	
	LinearLayout add;
	ImageView img;
	TextView bt;
	FloatingActionButton fab;
    private Toolbar toolbar;
	ListView listview;
	ListViewAdapter adapter;
	RelativeLayout layout;
	
	 public static final String PREF_NAME = "MyPrefs";
	 SharedPreferences sp;

	private List<GetListDetails> getdetails = null;
	 private static String url1 ="http://smallwebsitedesign.in/service_management/webservices/service/house_service_requests";
	 
	 Json_Post sParser=new Json_Post();
	 String json,token;
	 int houseId;


	 ArrayList<Integer> items=new ArrayList<>();
	 ArrayList<Integer> items2=new ArrayList<>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.service_request_list);
	
		//Getting the houseId from the shared preference
		sp = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
		houseId = sp.getInt("houseId", 0);
		token = sp.getString("token", "");
		System.out.println("---------hey"+houseId);
		
		//Actionbar

        toolbar = (Toolbar) findViewById(R.id.main_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("My Service Requests");


        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#D41031"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
		add=(LinearLayout) findViewById(R.id.addButton);
		bt=(TextView) findViewById(R.id.button_text);
		img=(ImageView)findViewById(R.id.imgclick);
		listview = (ListView) findViewById(R.id.listview);
        //layout=(RelativeLayout)findViewById(R.id.layout);
        fab=(FloatingActionButton)findViewById(R.id.fab);

		     //   fab.setVisibility(View.INVISIBLE);


//		DisplayMetrics displayMetrics = new DisplayMetrics();
//		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//		int height = displayMetrics.heightPixels;
//		int half=height/2;
//		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)add.getLayoutParams();
//		params.setMargins(20, half, 20, 0);
//		add.setLayoutParams(params);





//		sp= getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
//		logged=sp.getBoolean("hasLogged",false);
//
//		if(logged){
//		      add.setVisibility(View.VISIBLE);
//		           }

		;

		//Calling the function to get the request list from the database
//		new getRequestList().execute();
		new HttpAsyncTask().execute(url1);
		
		//Click event of the Add button
		img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(ListByStatus.this, RequestForm.class);
				startActivity(i);
			}
		});
		fab.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(ListByStatus.this, RequestForm.class);
				startActivity(i);
			}
		});

	}

    //Function to display the request list using the POST method
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        String rsltcd;
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
              dialog = new ProgressDialog(ListByStatus.this,R.style.MyAlertDialogStyle);
              dialog.setMessage("Processing, please wait");
              dialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {


            //Getting the user Typed username and password into variables
            getdetails = new ArrayList<GetListDetails>();

            try {


                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("requested_view",0);
                jsonObject.accumulate("house_id",houseId);
                //convert JSONObject to JSON to String
                json = jsonObject.toString();

                //Calling the POST method of Json_Post class
                String result=sParser.POST(urls[0]+"?token="+token, json);

                JSONObject jsonObject1 = new JSONObject(result);
                JSONArray gamesarray2 = jsonObject1.getJSONArray("resultArray");

                rsltcd=jsonObject1.getString("resultcode");

                for(int i=0;i<gamesarray2.length();i++){
                    GetListDetails map = new GetListDetails();
                    JSONObject one = gamesarray2.getJSONObject(i);
//                	int serviceName=one.getInt("int_request_id");
                    items.add(one.getInt("int_request_id"));
                    System.out.println("--------------"+one);
                    System.out.println("Answer---"+items);
                    map.setService(one.getString("str_service_name"));
//					map.setLogo(R.drawable.landscaping);
                    if(one.getString("str_service_name").equalsIgnoreCase("Kitchen Repair")){
                        map.setLogo(R.drawable.plumbing);
                    }
                    else if (one.getString("str_service_name").equalsIgnoreCase("astrologer")) {
                        map.setLogo(R.drawable.ast);
                    }
                    else if(one.getString("str_service_name").equalsIgnoreCase("Electrition")){
                        System.out.println("iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
                        map.setLogo(R.drawable.electr);
                    }
                    else if (one.getString("str_service_name").equalsIgnoreCase("Gardening")) {
                        map.setLogo(R.drawable.detail1);
                    }
                    else if (one.getString("str_service_name").equalsIgnoreCase("Painting")) {
                        map.setLogo(R.drawable.painting);
                    }
                    else if (one.getString("str_service_name").equalsIgnoreCase("Polishing")) {
                        map.setLogo(R.drawable.polishing);
                    }
                    else if (one.getString("str_service_name").equalsIgnoreCase("Washing")) {
                        map.setLogo(R.drawable.wasing);
                    }

                    map.setStatus(R.drawable.orangeexclaim);
                    map.setDes(one.getString("txt_request_description"));
                    map.setRs(one.getInt("int_request_id"));
                    getdetails.add(map);
                }
            }catch (JSONException e1) {
                // TODO Auto-generated catch block
                Log.d("exceptionnnnnn", "");
                e1.printStackTrace();
            }
            //Passing the username and the password
            try {

                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("requested_view",1);
                jsonObject.accumulate("house_id",houseId);
                //convert JSONObject to JSON to String
                json = jsonObject.toString();

                //Calling the POST method of Json_Post class
                String result=sParser.POST(urls[0]+"?token="+token, json);

                JSONObject jsonObject1 = new JSONObject(result);
                JSONArray gamesarray = jsonObject1.getJSONArray("resultArray");
                //	rsltcd=jsonObject1.getBoolean("resultcode");
                for(int i=0;i<gamesarray.length();i++){
                    System.out.println("Startttt");
                    GetListDetails map = new GetListDetails();
                    JSONObject one = gamesarray.getJSONObject(i);
//                	int serviceName=one.getInt("int_request_id");
                    items2.add(one.getInt("int_request_id"));
                    map.setService((String) one.getString("str_service_name"));
//					map.setLogo(R.drawable.painting);
                    if(one.getString("str_service_name").equalsIgnoreCase("Kitchen Repair")){
                        map.setLogo(R.drawable.plumbing);
                    }
                    else if (one.getString("str_service_name").equalsIgnoreCase("astrologer")) {
                        map.setLogo(R.drawable.ast);
                    }
                    else if(one.getString("str_service_name").equalsIgnoreCase("Electrition")){
                        System.out.println("iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
                        map.setLogo(R.drawable.electr);
                    }
                    else if (one.getString("str_service_name").equalsIgnoreCase("Gardening")) {
                        map.setLogo(R.drawable.detail1);
                    }
                    else if (one.getString("str_service_name").equalsIgnoreCase("Painting")) {
                        map.setLogo(R.drawable.painting);
                    }
                    else if (one.getString("str_service_name").equalsIgnoreCase("Polishing")) {
                        map.setLogo(R.drawable.polishing);
                    }
                    else if (one.getString("str_service_name").equalsIgnoreCase("Washing")) {
                        map.setLogo(R.drawable.wasing);
                    }
                    map.setStatus(R.drawable.pending_red);
                    map.setDes(one.getString("txt_request_description"));
                    map.setRs(one.getInt("int_request_id"));
                    getdetails.add(map);

                }

            }catch (JSONException e1) {
                // TODO Auto-generated catch block
                Log.d("exceptionnnnnn", "");
                e1.printStackTrace();
            }

            try {

                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("requested_view",2);
                jsonObject.accumulate("house_id",houseId);
                //convert JSONObject to JSON to String
                json = jsonObject.toString();

                //Calling the POST method of Json_Post class
                String result=sParser.POST(urls[0]+"?token="+token, json);

                JSONObject jsonObject1 = new JSONObject(result);
                JSONArray gamesarray = jsonObject1.getJSONArray("resultArray");
                //	rsltcd=jsonObject1.getBoolean("resultcode");

                for(int i=0;i<gamesarray.length();i++){
                    System.out.println("Startttt");
                    GetListDetails map = new GetListDetails();
                    JSONObject one = gamesarray.getJSONObject(i);
//             	int serviceName=one.getInt("int_request_id");
                    items2.add(one.getInt("int_request_id"));
                    map.setService((String) one.getString("str_service_name"));
// 				map.setLogo(R.drawable.painting);
                    if(one.getString("str_service_name").equalsIgnoreCase("Kitchen Repair")){
                        map.setLogo(R.drawable.plumbing);
                    }
                    else if (one.getString("str_service_name").equalsIgnoreCase("astrologer")) {
                        map.setLogo(R.drawable.ast);
                    }
                    else if(one.getString("str_service_name").equalsIgnoreCase("Electrition")){
                        System.out.println("iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
                        map.setLogo(R.drawable.electr);
                    }
                    else if (one.getString("str_service_name").equalsIgnoreCase("Gardening")) {
                        map.setLogo(R.drawable.detail1);
                    }
                    else if (one.getString("str_service_name").equalsIgnoreCase("Painting")) {
                        map.setLogo(R.drawable.painting);
                    }
                    else if (one.getString("str_service_name").equalsIgnoreCase("Polishing")) {
                        map.setLogo(R.drawable.detail1);
                    }
                    else if (one.getString("str_service_name").equalsIgnoreCase("Washing")) {
                        map.setLogo(R.drawable.detail1);
                    }

                    map.setStatus(R.drawable.greentick);
                    map.setDes(one.getString("txt_request_description"));
                    map.setRs(one.getInt("int_request_id"));
                    getdetails.add(map);
                }


            } catch (JSONException e1) {
                // TODO Auto-generated catch block
                Log.d("exceptionnnnnn", "");
                e1.printStackTrace();
            }


            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            if (rsltcd.equals("true"))
            {
                add.setVisibility(View.GONE);
                listview.setVisibility(View.VISIBLE);
            }else {
                add.setVisibility(View.VISIBLE);
                listview.setVisibility(View.GONE);
            }



            // Pass the results into ListViewAdapter.java
            adapter = new ListViewAdapter(ListByStatus.this,getdetails);

            // Binds the Adapter to the ListView
            listview.setAdapter(adapter);
//            listview.setSelection(listview.getAdapter().getCount()-1);
            listview.setOnItemClickListener(new OnItemClickListener() {


                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int arg2, long arg3) {


                    // TODO Auto-generated method stub
                    int requestId=getdetails.get(arg2).getRs();
                    sp = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putInt("requestId", requestId);
                    editor.commit();
//					Toast.makeText(getApplicationContext(), ""+requestId, Toast.LENGTH_LONG).show();

                    Intent i = new Intent(getApplicationContext(), RequestDetails.class);
                    startActivity(i);
                }
            });
        }
    }
	
	//Class to get the request list from the database using the GET method
//	private class getRequestList extends AsyncTask<String, String, String>{
//
//		@Override
//		protected String doInBackground(String... arg0) {
//			// TODO Auto-generated method stub
//			getdetails = new ArrayList<GetListDetails>();
//			JSONObject jsonObject = new JSONObject();
//			
//            //Retrieving the Completed Requests 
//			 try {	            	
//	            	jsonObject.put("requested_view",2);
//	            	jsonObject.put("house_id",houseId);
//									
//					params = jsonObject.toString();
//		            json = jParser.makeHttpRequest(url1, "GET", params);
//		            JSONArray gamesarray = json.getJSONArray("resultArray");
//	            	for(int i=0;i<gamesarray.length();i++){
//	            		System.out.println("Startttt");
//	            		GetListDetails map = new GetListDetails();
//	            		JSONObject one = gamesarray.getJSONObject(i);
////	                	int serviceName=one.getInt("int_request_id");
//	            		items2.add(one.getInt("int_request_id"));                   	
//	                	map.setService((String) one.getString("str_service_name"));
////						map.setLogo(R.drawable.painting);
//	                	if(one.getString("str_service_name").equalsIgnoreCase("Interior Design")){
//	                		map.setLogo(R.drawable.painting);
//	                	}
//	                	else if (one.getString("str_service_name").equalsIgnoreCase("Polishing")) {
//	                		map.setLogo(R.drawable.flooring);
//						}
//	                	else if(one.getString("str_service_name").equalsIgnoreCase("Kitchen Repair")){
//	                		map.setLogo(R.drawable.plumbing);
//						}
//	                	else if (one.getString("str_service_name").equalsIgnoreCase("astrologer")) {
//	                		map.setLogo(R.drawable.landscaping);
//						}
//						map.setStatus(R.drawable.greentick);
//						map.setDes(one.getString("txt_request_description"));
//						map.setRs(one.getInt("int_request_id"));
//						getdetails.add(map);
//						
//	            	}
//	            	
//					
//				} catch (JSONException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();	
//					
//				}
//			 
//			 //Retrieving the Requests under processing
//			 try {	            	
//	            	jsonObject.put("requested_view",1);
//	            	jsonObject.put("house_id",houseId);
//									
//					params = jsonObject.toString();
//		            json = jParser.makeHttpRequest(url1, "GET", params);
//		            JSONArray gamesarray = json.getJSONArray("resultArray");
//	            	for(int i=0;i<gamesarray.length();i++){
//	            		System.out.println("Startttt");
//	            		GetListDetails map = new GetListDetails();
//	            		JSONObject one = gamesarray.getJSONObject(i);
////	                	int serviceName=one.getInt("int_request_id");
//	            		items2.add(one.getInt("int_request_id"));                   	
//	                	map.setService((String) one.getString("str_service_name"));
////						map.setLogo(R.drawable.painting);
//	                	if(one.getString("str_service_name").equalsIgnoreCase("Interior Design")){
//	                		map.setLogo(R.drawable.painting);
//	                	}
//	                	else if (one.getString("str_service_name").equalsIgnoreCase("Polishing")) {
//	                		map.setLogo(R.drawable.flooring);
//						}
//	                	else if(one.getString("str_service_name").equalsIgnoreCase("Kitchen Repair")){
//	                		map.setLogo(R.drawable.plumbing);
//						}
//	                	else if (one.getString("str_service_name").equalsIgnoreCase("astrologer")) {
//	                		map.setLogo(R.drawable.landscaping);
//						}
//						map.setStatus(R.drawable.processing);
//						map.setDes(one.getString("txt_request_description"));
//						map.setRs(one.getInt("int_request_id"));
//						getdetails.add(map);
//						
//	            	}
//	            	
//					
//				} catch (JSONException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();		
//				}
//			 
//			 
//            
//			//Retrieving the Pending Requests
//            try {            	
//            	jsonObject.put("requested_view",0);
//            	jsonObject.put("house_id",houseId);
//								
//				params = jsonObject.toString();
//	            json = jParser.makeHttpRequest(url1, "GET", params);	            
//	            JSONArray gamesarray2 = json.getJSONArray("resultArray");
//            	
//            	for(int i=0;i<gamesarray2.length();i++){
//            		GetListDetails map = new GetListDetails();
//            		JSONObject one = gamesarray2.getJSONObject(i);
////                	int serviceName=one.getInt("int_request_id");
//            		items.add(one.getInt("int_request_id"));               	
//                	System.out.println("--------------"+one);
//                	System.out.println("Answer---"+items);
//                	map.setService(one.getString("str_service_name"));
////					map.setLogo(R.drawable.landscaping);
//					if(one.getString("str_service_name").equalsIgnoreCase("Interior Design")){
//                		map.setLogo(R.drawable.painting);
//                	}
//                	else if (one.getString("str_service_name").equalsIgnoreCase("Polishing")) {
//                		map.setLogo(R.drawable.flooring);
//					}
//                	else if(one.getString("str_service_name").equalsIgnoreCase("Kitchen Repair")){
//                		map.setLogo(R.drawable.plumbing);
//					}
//                	else if (one.getString("str_service_name").equalsIgnoreCase("astrologer")) {
//                		map.setLogo(R.drawable.landscaping);
//					}
//					map.setStatus(R.drawable.orangeexclaim);
//					map.setDes(one.getString("txt_request_description"));
//					map.setRs(one.getInt("int_request_id"));
//					getdetails.add(map);
//            	}            	
//				
//			}catch (JSONException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();			
//			}           
//            
//			return null;
//		}
//		
//		@Override
//		protected void onPostExecute(String result) {
//			// TODO Auto-generated method stub
//			
//			// Pass the results into ListViewAdapter.java
//			adapter = new ListViewAdapter(ListByStatus.this,
//					getdetails);
//			
//			// Binds the Adapter to the ListView
//			listview.setAdapter(adapter);	
//			
//			listview.setOnItemClickListener(new OnItemClickListener() {
//
//				@Override
//				public void onItemClick(AdapterView<?> arg0, View arg1,
//						int arg2, long arg3) {
//					// TODO Auto-generated method stub
//					int requestId=getdetails.get(arg2).getRs();
//					sp = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
//					SharedPreferences.Editor editor = sp.edit();
//					editor.putInt("requestId", requestId);
//					editor.commit();
//					Toast.makeText(getApplicationContext(), ""+requestId, Toast.LENGTH_LONG).show();
//					
//					Intent i = new Intent(getApplicationContext(), RequestDetails.class);
//		            startActivity(i);
//		            
//				}
//			});	
//			
//		}
//		
//	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
//		  Take appropriate action actionfor each action item click
        switch (item.getItemId()) {

        case R.id.home_main:
            // Navigate
			Intent i=new Intent(ListByStatus.this,ListByStatus.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(i);
			return true;

        default:
            return super.onOptionsItemSelected(item);
            }
	}

}
