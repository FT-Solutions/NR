package com.ftspl.NR;

import com.ftspl.NR.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;

public class SplashActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		Constants.context = this.getApplicationContext();
		
		Constants.loginDetails = Constants.context.getSharedPreferences("com.ftspl.NR", Context.MODE_PRIVATE);
        //SharedPreferences loginDetails = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        
        Constants.usernameStr = Constants.loginDetails.getString("sysem_username", "");
        Constants.passwordStr = Constants.loginDetails.getString("sysem_password", "");
        Constants.ApplicationServer = Constants.loginDetails.getString("application_server", "");
		Constants.userId = Constants.loginDetails.getString("user_id", "");
		Constants.SAP_CLIENT = Constants.loginDetails.getString("sap_client", "");

		
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
		  @Override
		  public void run() {
			  
			  Log.d("Login Detail:", "UserName:" + Constants.usernameStr + " -- Password:" + Constants.passwordStr);
		  if(Constants.usernameStr.equals("") || Constants.passwordStr.equals("")) {
				Intent login =  new Intent(Constants.context, LoginActivity.class);
				startActivity(login);
				finish();
			} else if(!Constants.usernameStr.equals("") && !Constants.passwordStr.equals("") && !Constants.ApplicationServer.equals("")) {
				//new RetrieveFeedTask().execute("");
				Intent intent =  new Intent(Constants.context, QuickViewActivity.class);
				startActivity(intent);
				finish();
			}
		  }
		}, 2000);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}

}
