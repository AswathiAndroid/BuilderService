package com.serviceapp.services;

import android.app.Activity;
import android.content.Intent;
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
import org.json.JSONException;
import org.json.JSONObject;

public class New_password extends AppCompatActivity {

    Button submit;
    String json,houseNo,getPassword, getConfirmPass;
    Json_Post sParser = new Json_Post();
    EditText password, confirm_password;
    private static String url = "http://smallwebsitedesign.in/service_management/webservices/login/reset_password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
        submit = (Button) findViewById(R.id.submit_pswd);
        password = (EditText) findViewById(R.id.pswd);
        confirm_password = (EditText) findViewById(R.id.confirm_pswd);
        houseNo=getIntent().getExtras().getString("HouseNo");

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                getPassword=password.getText().toString();
                getConfirmPass=confirm_password.getText().toString();

                if (getPassword.isEmpty() && getConfirmPass.isEmpty()) {

                    Toast toast = Toast.makeText(getApplicationContext(), "Please Enter a Password", Toast.LENGTH_LONG);
                    View view = toast.getView();
                    toast.setGravity(Gravity.TOP, 0, 0);
                    view.setBackgroundResource(R.drawable.toast_backgrnd);
                    TextView text = (TextView) view.findViewById(android.R.id.message);
					 /*Here you can do anything with above textview like text.setTextColor(Color.parseColor("#000000"));*/
                    text.setTextColor(Color.parseColor("#FFFFFF"));
                    text.setPadding(20, 10, 20, 10);
                    text.setTextSize(15);
                    toast.show();
                }else if(!getPassword.equals(getConfirmPass)) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Password doesn't match", Toast.LENGTH_LONG);
                    View view = toast.getView();
                    toast.setGravity(Gravity.TOP, 0, 0);
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
        String rsltCode,rmsg;

        @Override
        protected String doInBackground(String... urls) {


            try {

                // build jsonObject
                JSONObject jsonObject = new JSONObject();

                jsonObject.accumulate("Newpassword", getPassword);
                jsonObject.accumulate("House_no",houseNo);

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

            try {
                //coverting the result string to jsonobject
                JSONObject jsonObject1 = null;
                jsonObject1 = new JSONObject(result);
                rsltCode = jsonObject1.getString("result_code");
                rmsg = jsonObject1.getString("message");

                //getting the value 'resultmsg'

                if (rsltCode.equals("1")) {

                    Intent intent = new Intent(New_password.this, Login.class);
                    intent.putExtra("HouseNo",houseNo);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), rmsg, Toast.LENGTH_LONG);
                    View view = toast.getView();
                    toast.setGravity(Gravity.TOP, 0, 0);
                    view.setBackgroundResource(R.drawable.toast_backgrnd);
                    TextView text = (TextView) view.findViewById(android.R.id.message);
					/*Here you can do anything with above textview like text.setTextColor(Color.parseColor("#000000"));*/
                    text.setTextColor(Color.parseColor("#FFFFFF"));
                    text.setPadding(10, 0, 10, 0);
                    text.setTextSize(15);
                    toast.show();
                }

            }catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }
}

