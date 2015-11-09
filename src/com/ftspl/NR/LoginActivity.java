package com.ftspl.NR;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


@SuppressLint("NewApi")
public class LoginActivity extends Activity {
	
	Button login;
	EditText username, password, appliServer, userId;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        login = (Button) findViewById(R.id.button1);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        appliServer = (EditText) findViewById(R.id.application_server);
        userId = (EditText) findViewById(R.id.userId);
        
        getActionBar().setTitle(Html.fromHtml("<font color='#000000'>NR Agrawal</font>"));
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        //loginDetails = context.getSharedPreferences("com.ftspl.asis.data", Context.MODE_PRIVATE);
        
        Constants.usernameStr = Constants.loginDetails.getString("sysem_username", "");
        Constants.passwordStr = Constants.loginDetails.getString("sysem_password", "");
		
        login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Boolean error = false;
				Intent intent;
				
				if(appliServer.getText().toString().equals("")) {
					error = true;
					appliServer.setError("Provide Server IP");
				} else if(username.getText().toString().equals("")) {
					error = true;
					username.setError("Provide username");
				} else if(password.getText().toString().equals("")) {
					error = true;
					password.setError("Provide password");
				}
				else if(userId.getText().toString().equals("")) {
					error = true;
					userId.setError("Provide User id");
				}
					
				if(!error) {
					SharedPreferences.Editor ed = Constants.loginDetails.edit();
					if(Constants.usernameStr.equals("") || Constants.passwordStr.equals("")) {
						Constants.usernameStr = username.getText().toString();
						Constants.passwordStr = password.getText().toString();
						Constants.ApplicationServer = appliServer.getText().toString();
						Constants.userId = userId.getText().toString();
			        	ed.putString("sysem_username", Constants.usernameStr);
			        	ed.putString("sysem_password", Constants.passwordStr);
			        	ed.putString("application_server", Constants.ApplicationServer);
			        	ed.putString("user_id", Constants.userId);
			        	ed.commit();
					}
		        	
					intent = new Intent(Constants.context, QuickViewActivity.class);
					startActivity(intent);
					finish();
				}
			}
		});
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
