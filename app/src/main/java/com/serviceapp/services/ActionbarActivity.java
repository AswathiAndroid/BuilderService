package com.serviceapp.services;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

public class ActionbarActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		//set the title of the action bar
		actionBar.setTitle(" Request Form");
		getActionBar().setDisplayShowTitleEnabled(true);

	    actionBar.setHomeButtonEnabled(false);
	    actionBar.setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
	    actionBar.setDisplayHomeAsUpEnabled(false);

	    //set background of the actionbar
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#CA0E27")));

	}
}
