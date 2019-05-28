package com.serviceapp.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.navdrawer.SimpleSideDrawer;
import com.tokenautocomplete.TokenCompleteTextView;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

@SuppressWarnings("rawtypes")
public class RequestForm extends AppCompatActivity implements TokenCompleteTextView.TokenListener {

	String fnl;
	ImageView dateImg, timeImgf,milestoneDateimg;
	EditText edDate,edTimef,edTimet,milestoneDate,title,servicePerson,des;
	Spinner serviceSpn,requestType;
	Button submit,add;
	TextView timeImgt;
	ScrollView scrollView;

	Calendar myCalendar;

	public static final String PREF_NAME = "MyPrefs" ;
	SharedPreferences sp;

	int houseId;
	Json_Post sParser=new Json_Post();
	String params_d;
	String params1;
	String json1,token;
	String json;

	String s=null;

	private static String url2="http://smallwebsitedesign.in/service_management/webservices/service/service_available";
	private static String url3="http://smallwebsitedesign.in/service_management/webservices/service/add_service_reqst";

	ArrayList<String> spinnerItems=new ArrayList<>();
	ArrayList<String> serviceId=new ArrayList<>();

	String[] rType={"RequestType","Low","Normal","High","Urgent","Immediate"};
	String rTitle,rperson,rrtype,rmDate,rdes,rservice,rserviceId,curDate,getEdDate,getEdTime;

	String edate,eTimef,eTimet;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.request_form_new);
		//Geting houseId from the shared preference
		sp = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
		houseId = sp.getInt("houseId", 0);
		token = sp.getString("token", "");
		System.out.println("houseId in Request form"+ houseId);

	//	ActionBar actionBar = getActionBar();
	//	actionBar.setTitle(" Request Form");


		//Defining the controls
		dateImg=(ImageView) findViewById(R.id.dateImg);
		timeImgf=(ImageView) findViewById(R.id.timeImg);
		timeImgt=(TextView) findViewById(R.id.timeImga);
		milestoneDateimg=(ImageView) findViewById(R.id.mDateImg);
		edDate=(EditText) findViewById(R.id.datee);
		edTimef=(EditText) findViewById(R.id.time);
		edTimet=(EditText) findViewById(R.id.timea);

		milestoneDate=(EditText) findViewById(R.id.mDate);
		title=(EditText) findViewById(R.id.title);
		servicePerson=(EditText) findViewById(R.id.servicePerson);
		des=(EditText) findViewById(R.id.description);

		    des.setMovementMethod(new ScrollingMovementMethod());
	       	des.setVerticalScrollBarEnabled(true);
		    des.isVerticalScrollBarEnabled();

		scrollView=(ScrollView)findViewById(R.id.scroll);

		des.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				scrollView.requestDisallowInterceptTouchEvent(true);
				return false;
			}
		});

		serviceSpn=(Spinner) findViewById(R.id.serviceSpn);
		requestType=(Spinner) findViewById(R.id.requestType);
		submit=(Button) findViewById(R.id.submit);

		edDate.setInputType(InputType.TYPE_NULL);
		    edTimef.setInputType(InputType.TYPE_NULL);
		    edTimet.setInputType(InputType.TYPE_NULL);
		    milestoneDate.setInputType(InputType.TYPE_NULL);


        requestType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
			{
				//Change the selected item's text color

				((TextView) view).setTextColor(Color.WHITE);
				((TextView) view).setTextSize(12);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{


			}
		});


		//Click event of Submit Button
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

//				 StringBuilder sb = new StringBuilder();
//                 for (Object token: completionView.getObjects()) {
//                     sb.append(token.toString());
//                     sb.append(" ");
//                 }
//
//                 fnl=sb.toString();
				System.out.println("============="+fnl);

				Calendar cal = Calendar.getInstance();
				// TODO Auto-generated method stub


				rTitle=title.getText().toString();
				rperson=servicePerson.getText().toString();
				rrtype=requestType.getSelectedItem().toString();
				rmDate=milestoneDate.getText().toString();
				rservice=serviceSpn.getSelectedItem().toString();
				rdes=des.getText().toString();

				edate=edDate.getText().toString();
				eTimef=edTimef.getText().toString();
				eTimet=edTimet.getText().toString();

				fnl=edate+"from"+eTimef+"to"+eTimet;

				int month =cal.get(Calendar.MONTH)+1;
				int date = cal.get(Calendar.DAY_OF_MONTH);
				int year = cal.get(Calendar.YEAR);
				curDate = year + "-" + month + "-" +date ;
				View selectedView = requestType.getSelectedView();
				View selectedView2 = serviceSpn.getSelectedView();

				if(TextUtils.isEmpty(rTitle)) {
					title.setError("Cannot be empty");
					return;
				}
//				else if(TextUtils.isEmpty(rperson)) {
//					servicePerson.setError("Cannot be empty");
//					return;
//				}
				else if(TextUtils.isEmpty(rmDate)) {
					milestoneDate.setError("Cannot be empty");
					return;
				}else if(TextUtils.isEmpty(edate)) {
					edDate.setError("Cannot be empty");
					return;
				}else if(TextUtils.isEmpty(eTimef)) {
					edTimef.setError("Cannot be empty");
					return;
				}else if(TextUtils.isEmpty(eTimet)) {
					edTimet.setError("Cannot be empty");
					return;
				}
				else if(TextUtils.isEmpty(rdes)) {
					des.setError("Cannot be empty");
					return;
				}
				else if(rrtype.equalsIgnoreCase("RequestType")){
					TextView selectedTextView = (TextView) selectedView;
					selectedTextView.setError("Select Request");
					return;
				}

				else if(rservice.equalsIgnoreCase("Select Service")){
					TextView selectedTextView = (TextView) selectedView2;
					selectedTextView.setError("Select Service");
					return;
				}


				else{
					//Calling the
					System.out.print("final available date"+fnl);
					new insertDatas().execute();
				}

			}
		});





		edDate.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				v.onTouchEvent(event);
				InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
				if (imm != null) {
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
				return true;
			}
		});

		edTimef.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				v.onTouchEvent(event);
				InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
				if (imm != null) {
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
				return true;
			}
		});
		edTimet.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				v.onTouchEvent(event);
				InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
				if (imm != null) {
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
				return true;
			}
		});

		milestoneDate.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				v.onTouchEvent(event);
				InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
				if (imm != null) {
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
				return true;
			}
		});

		new insertSpinner().execute();

		ArrayAdapter<String> adapter=new ArrayAdapter<>(RequestForm.this, android.R.layout.simple_spinner_dropdown_item, rType);
		requestType.setAdapter(adapter);

		myCalendar = Calendar.getInstance();
		//DatePicker Dialog for Available user Date
		final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
								  int dayOfMonth) {
				// TODO Auto-generated method stub
				myCalendar.set(Calendar.YEAR, year);
				myCalendar.set(Calendar.MONTH, monthOfYear);
				myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				updateLabel();
			}
		};

		//DatePicker Dialog for Milestone Date
		final DatePickerDialog.OnDateSetListener date2=new  DatePickerDialog.OnDateSetListener(){

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
								  int dayOfMonth) {
				// TODO Auto-generated method stub
				myCalendar.set(Calendar.YEAR, year);
				myCalendar.set(Calendar.MONTH, monthOfYear);
				myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				label2();
			}
		};



		//Click Event on Date Picker image for Available User Date
		edDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new DatePickerDialog(RequestForm.this, R.style.date_picker_theme, date, myCalendar
						.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
						myCalendar.get(Calendar.DAY_OF_MONTH)).show();
			}
		});


		//Click Event on Date Picker image for Milestone Date
		milestoneDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new DatePickerDialog(RequestForm.this,  R.style.date_picker_theme, date2, myCalendar
						.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
						myCalendar.get(Calendar.DAY_OF_MONTH)).show();
			}
		});

		//Click Event on Time Picker
		edTimef.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Calendar mcurrentTime = Calendar.getInstance();
				int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
				int minute = mcurrentTime.get(Calendar.MINUTE);
				TimePickerDialog mTimePicker;
				mTimePicker = new TimePickerDialog(RequestForm.this,  R.style.date_picker_theme, new TimePickerDialog.OnTimeSetListener() {
					@Override
					public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//						edTimef.setText( selectedHour + ":" + selectedMinute);
						edTimef.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
					}
				}, hour, minute, true);//Yes 24 hour time
				mTimePicker.show();
			}
		});

		edTimet.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Calendar mcurrentTime = Calendar.getInstance();
				int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
				int minute = mcurrentTime.get(Calendar.MINUTE);
				TimePickerDialog mTimePicker;
				mTimePicker = new TimePickerDialog(RequestForm.this, R.style.date_picker_theme, new TimePickerDialog.OnTimeSetListener() {
					@Override
					public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//						edTimet.setText("%02d:%02d",selectedHour ,selectedMinute);
						edTimet.setText(String.format("%02d:%02d", selectedHour, selectedMinute));


					}
				}, hour, minute, true);//Yes 24 hour time
				mTimePicker.show();
			}
		});





	}


	//Posting the Values to the url using POST method
	private class insertDatas extends AsyncTask<String, String, String>
	{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			JSONObject jsonObject = new JSONObject();
			try{
				jsonObject.accumulate("requested_service_milestone",rmDate);
				jsonObject.accumulate("request_description",rdes);
				jsonObject.accumulate("request_type",rrtype);
				jsonObject.accumulate("date_request_made",curDate);
				jsonObject.accumulate("suggested_service_person",rperson);
				jsonObject.accumulate("user_available_date",fnl);
				jsonObject.accumulate("request_title",rTitle);
				jsonObject.accumulate("requset_service_id",rserviceId);
				jsonObject.accumulate("service_request_code","");
				jsonObject.accumulate("house_id",houseId);


				params1 = jsonObject.toString();
				System.out.println("JSONNNNNNNNNNNNNNNNN ISSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSs"+params1);
				json1 = sParser.POST(url3+"?token="+token,params1);


				Log.d("Album22 JSONmenuuu: ", "> " + json1);

			}catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {
				JSONObject jsonObject1 = new JSONObject(json1);
				s= jsonObject1.getString("resultmsg");
				Log.d("Msg", s);

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

				Toast toast = Toast.makeText(getApplicationContext(), "Request Successfully Submitted", Toast.LENGTH_LONG);
				View view = toast.getView();
				toast.setGravity(Gravity.TOP, 20, 20);
				view.setBackgroundResource(R.drawable.toast_backgrnd);
				TextView text = (TextView) view.findViewById(android.R.id.message);
				/*Here you can do anything with above textview like text.setTextColor(Color.parseColor("#000000"));*/
				text.setTextColor(Color.parseColor("#FFFFFF"));
				text.setTextSize(15);
				toast.show();

				Intent i=new Intent(RequestForm.this,ListByStatus.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i);
			}
			else{

				Toast toast = Toast.makeText(getApplicationContext(), "Error Submitting", Toast.LENGTH_LONG);
				View view = toast.getView();
				view.setBackgroundResource(R.drawable.toast_backgrnd);
				toast.setGravity(Gravity.TOP, 20, 20);
				TextView text = (TextView) view.findViewById(android.R.id.message);
					/*Here you can do anything with above textview like text.setTextColor(Color.parseColor("#000000"));*/
				text.setTextColor(Color.parseColor("#FFFFFF"));
				text.setTextSize(15);
				toast.show();

			}
		}
	}

	private class insertSpinner extends AsyncTask<String, String, String>
	{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			JSONObject jsonObject = new JSONObject();

			//Fetching the services available for the particular user from the url
			try {

				jsonObject.accumulate("house_id",houseId);
				params_d = jsonObject.toString();
				json = sParser.POST(url2+"?token="+token,params_d);

				Log.d("Spinner Items r ", "> " + json);

				JSONObject jsonObject1 = new JSONObject(json);
				JSONObject result = jsonObject1.getJSONObject("resultArray");
				JSONArray getArrayItems = result.getJSONArray("services");
				spinnerItems.add("Select Service");
				for(int i=0;i<getArrayItems.length();i++){

					JSONObject items=getArrayItems.getJSONObject(i);
					spinnerItems.add(items.getString("str_service_name"));
					serviceId.add(items.getString("int_service_id"));
					System.out.println("Spinneerrrrrrrrr"+spinnerItems +serviceId);

				}


			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub

			//Setting the fethced values to the url
			ArrayAdapter<String> adapter=new ArrayAdapter<>(RequestForm.this, android.R.layout.simple_spinner_dropdown_item, spinnerItems);
			serviceSpn.setAdapter(adapter);
			serviceSpn.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
										   int arg2, long arg3) {
					// TODO Auto-generated method stub

					((TextView) arg1).setTextColor(Color.WHITE);
					((TextView) arg1).setTextSize(12);

					int id=(int) serviceSpn.getSelectedItemId();
//					Toast.makeText(getApplicationContext(), ""+id, Toast.LENGTH_LONG).show();

					if(id!=0){
						rserviceId=serviceId.get(id-1);
						System.out.println("---------------"+rserviceId);
					}

				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub

				}
			});
		}

	}

	//Class to fetch the services from the url and set it to the Spinner
//	private class insertSpinner extends AsyncTask<String, String, String>{
//
//		@Override
//		protected String doInBackground(String... arg0) {
//			// TODO Auto-generated method stub
//
//			 JSONObject jsonObject = new JSONObject();
//
//	            //Fetching the services available for the particular user from the url
//	            try {
//
//	            	jsonObject.put("house_id",houseId);
//					params_d = jsonObject.toString();
//		            json = jParser.makeHttpRequest(url2, "GET", params_d);
//
//		            Log.d("Spinner Items are-----------========= ", "> " + json);
//
//		            JSONObject result = json.getJSONObject("resultArray");
//		            JSONArray getArrayItems = result.getJSONArray("services");
//		            spinnerItems.add("Select Service");
//		            for(int i=0;i<getArrayItems.length();i++){
//
//		            JSONObject items=getArrayItems.getJSONObject(i);
//		            spinnerItems.add(items.getString("str_service_name"));
//		            serviceId.add(items.getString("int_service_id"));
//		            System.out.println("Spinneerrrrrrrrr"+spinnerItems +serviceId);
//
//
//		            }
//
//
//				} catch (JSONException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//
//			return null;
//		}
//
//		@Override
//		protected void onPostExecute(String result) {
//			// TODO Auto-generated method stub
//			//Setting the fethced values to the url
//			ArrayAdapter<String> adapter=new ArrayAdapter<>(RequestForm.this, android.R.layout.simple_spinner_dropdown_item, spinnerItems);
//    		serviceSpn.setAdapter(adapter);
//    		serviceSpn.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//				@Override
//				public void onItemSelected(AdapterView<?> arg0, View arg1,
//						int arg2, long arg3) {
//					// TODO Auto-generated method stub
//					int id=(int) serviceSpn.getSelectedItemId();
////					Toast.makeText(getApplicationContext(), ""+id, Toast.LENGTH_LONG).show();
//
//					if(id!=0){
//						rserviceId=serviceId.get(id-1);
//						System.out.println("---------------"+rserviceId);
//					}
//
//				}
//
//				@Override
//				public void onNothingSelected(AdapterView<?> arg0) {
//					// TODO Auto-generated method stub
//
//				}
//			});
//		}
//
//	}


	//Posting the Values to the url using GET method
//	private class insertDatas extends AsyncTask<String, String, String>{
//
//		@Override
//		protected String doInBackground(String... arg0) {
//
//
//
//			System.out.println("------"+ rTitle + rserviceId + rdate + rtime + rperson + rrtype + rmDate + rdes+  curDate);
//            JSONObject jsonObject = new JSONObject();
//            fnl=edDate.getText().toString()+"from"+edTimef.getText().toString()+"to"+edTimet.getText().toString();
//            System.out.print("final available date"+fnl);
//            try{
//            	jsonObject.put("requested_service_milestone",rmDate);
//            	jsonObject.put("request_description",rdes);
//            	jsonObject.put("request_type",rrtype);
//            	jsonObject.put("date_request_made",curDate);
//            	jsonObject.put("suggested_service_person",rperson);
//            	jsonObject.put("user_available_date",fnl);
//            	jsonObject.put("request_title",rTitle);
//            	jsonObject.put("requset_service_id",rserviceId);
//            	jsonObject.put("service_request_code","");
//            	jsonObject.put("house_id",houseId);
//
//
//				params = jsonObject.toString();
//	            json = jParser.makeHttpRequest(url3, "GET", params);
//
//	            Log.d("Album22 JSONmenuuu: ", "> " + json);
//
//            }catch (JSONException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//
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
//            return null;
//		}
//
//		@Override
//		protected void onPostExecute(String result) {
//			// TODO Auto-generated method stub
//			 if(s.equals("Success")){
//
////				Toast.makeText(getApplicationContext(), "Request Successfully Submitted", Toast.LENGTH_LONG).show();
//				Toast toast = Toast.makeText(getApplicationContext(), "Request Successfully Submitted", Toast.LENGTH_LONG);
//				View view = toast.getView();
//				view.setBackgroundResource(R.drawable.toast_backgrnd);
//				TextView text = (TextView) view.findViewById(android.R.id.message);
//				/*Here you can do anything with above textview like text.setTextColor(Color.parseColor("#000000"));*/
//				text.setTextColor(Color.parseColor("#FFFFFF"));
//				text.setTextSize(15);
//				toast.show();
//
//	            }else{
//
////	            	Toast.makeText(getApplicationContext(), "Error Submitting", Toast.LENGTH_LONG).show();
//	            	Toast toast = Toast.makeText(getApplicationContext(), "Error Submitting", Toast.LENGTH_LONG);
//					View view = toast.getView();
//					view.setBackgroundResource(R.drawable.toast_backgrnd);
//					TextView text = (TextView) view.findViewById(android.R.id.message);
//					/*Here you can do anything with above textview like text.setTextColor(Color.parseColor("#000000"));*/
//					text.setTextColor(Color.parseColor("#FFFFFF"));
//					text.setTextSize(15);
//					toast.show();
//
//	            }
//		}
//
//	}


	private void updateLabel() {

		String myFormat = "dd-MM-yyyy"; //In which you need put here
		SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

		edDate.setText(sdf.format(myCalendar.getTime()));
	}

	private void label2(){

		String myFormat = "dd-MM-yyyy"; //In which you need put here
		SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

		milestoneDate.setText(sdf.format(myCalendar.getTime()));
	}


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
				Intent i=new Intent(RequestForm.this,ListByStatus.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i);
				// Navigate
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onTokenAdded(Object arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTokenRemoved(Object arg0) {
		// TODO Auto-generated method stub

	}
}
