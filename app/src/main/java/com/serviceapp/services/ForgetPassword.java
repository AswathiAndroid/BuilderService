package com.serviceapp.services;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ForgetPassword extends AppCompatActivity {
    Button bt1;
    EditText edhouseNo;
    String houseNo, json;
    Json_Post sParser = new Json_Post();
    private static String url = "http://smallwebsitedesign.in/service_management/webservices/login/forgot_password_link";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
//        ActionBar actionBar = getActionBar();
//        actionBar.hide();
        edhouseNo = (EditText) findViewById(R.id.houseNo);
        bt1 = (Button) findViewById(R.id.submit_email);

        bt1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                houseNo=edhouseNo.getText().toString();

                if (houseNo.isEmpty()) {

                    Toast toast = Toast.makeText(getApplicationContext(), "Please Enter your House No.", Toast.LENGTH_LONG);
                    View view = toast.getView();
                    toast.setGravity(Gravity.TOP, 20, 20);
                    view.setBackgroundResource(R.drawable.toast_backgrnd);
                    TextView text = (TextView) view.findViewById(android.R.id.message);
					 /*Here you can do anything with above textview like text.setTextColor(Color.parseColor("#000000"));*/
                    text.setTextColor(Color.parseColor("#FFFFFF"));
                    text.setPadding(20, 10, 20, 10);
                    text.setTextSize(15);
                    toast.show();
                } else {

                    //Calling the function to check the username and password
                    //new jsonParsing2().execute();
                    new HttpAsyncTask().execute(url);

                }
            }


        });
    }
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            try {
                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("int_house_number", houseNo);

                //convert JSONObject to JSON to String
                json = jsonObject.toString();

                System.out.println("jsonnnnnnnnnnnnnnnnnnnnnnnn" + json);

            } catch (JSONException e1) {
                // TODO Auto-generated catch block
                Log.d("exceptionnnnnn", "");
                e1.printStackTrace();
            }

            //Calling the POST method of Json_Post class
            return sParser.POST(urls[0], json);
        }

        @Override
        protected void onPostExecute(String result) {

            try{

                //coverting the result string to jsonobject
                JSONObject jsonObject1 = new JSONObject(result);
                //getting the value 'resultmsg'
                String rsltCode=jsonObject1.getString("result_code");
                String rmsg=jsonObject1.getString("message");


                if(rsltCode.equals("1"))
                {

                    Intent intent = new Intent(ForgetPassword.this, ResetPasswordCode.class);
                    intent.putExtra("HouseNo",houseNo);
                    startActivity(intent);

                }

                else{

                    Toast toast = Toast.makeText(getApplicationContext(), rmsg, Toast.LENGTH_LONG);
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

            }
        }
    }
}