package com.serviceapp.services;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class Launcher extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.launcher);

		//ActionBar actionBar = getActionBar();

		//actionBar.hide();


		handler.sendEmptyMessageDelayed(0, 3000);

	}

	Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			Intent i =new Intent(Launcher.this, Login.class);
			startActivity(i);
			Launcher.this.finish();
		}
	};

}
