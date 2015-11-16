package com.ftspl.NR;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


@SuppressLint("NewApi")
public class LoginActivity extends Activity {
	
	public ProgressDialog pdia;
	public Context context;
	Button login;
	EditText username, password, appliServer, userId, sapClient;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        login = (Button) findViewById(R.id.button1);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        appliServer = (EditText) findViewById(R.id.application_server);
        userId = (EditText) findViewById(R.id.userId);
        sapClient = (EditText) findViewById(R.id.sap_client);
        
        getActionBar().setTitle(Html.fromHtml("<font color='#000000'>NR Agrawal</font>"));
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        //loginDetails = context.getSharedPreferences("com.ftspl.asis.data", Context.MODE_PRIVATE);
        
        Constants.usernameStr = Constants.loginDetails.getString("sysem_username", "");
        Constants.passwordStr = Constants.loginDetails.getString("sysem_password", "");
        Constants.userId = Constants.loginDetails.getString("user_id", "");
        Constants.SAP_CLIENT = Constants.loginDetails.getString("sap_client", "");
		
        login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Boolean error = false;
				
				if(appliServer.getText().toString().equals("")) {
					error = true;
					appliServer.setError("Provide Server IP");
				} else if(sapClient.getText().toString().equals("")) {
					error = true;
					sapClient.setError("Provide SAP Client");
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
					new checkUser().execute("");
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
    
    class checkUser extends AsyncTask<String, Void, String> {

        @Override
    	protected void onPreExecute(){ 
    	   super.onPreExecute();
    	        pdia = new ProgressDialog(context);
    	        pdia.setMessage("Please wait...");
    	        pdia.show();
				
    	}
        
        protected String doInBackground(String... param) {
        	String feed = "";
        	Constants.usernameStr = username.getText().toString();
        	Constants.SAP_CLIENT = sapClient.getText().toString();
			Constants.passwordStr = password.getText().toString();
			Constants.ApplicationServer = appliServer.getText().toString();
			Constants.userId = userId.getText().toString();
			
			try {
				feed = Constants.connect.checkUser(Constants.context);
				Log.d("User Check", feed);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return feed;
        }

        protected void onPostExecute(String feed) {
        	pdia.dismiss();
        	
        	if(feed.equals("Valid User")) { 
	        	SharedPreferences.Editor ed = Constants.loginDetails.edit();
				//if(Constants.usernameStr.equals("") || Constants.passwordStr.equals("") || Constants.userId.equals("")) {
//					Constants.usernameStr = username.getText().toString();
//					Constants.passwordStr = password.getText().toString();
//					Constants.ApplicationServer = appliServer.getText().toString();
//					Constants.userId = userId.getText().toString();
					
		        	ed.putString("sysem_username", Constants.usernameStr);
		        	ed.putString("sysem_password", Constants.passwordStr);
		        	ed.putString("application_server", Constants.ApplicationServer);
		        	ed.putString("user_id", Constants.userId);
		        	ed.putString("sap_client", Constants.SAP_CLIENT);
		        	ed.commit();
				//}
	        	
				Intent intent = new Intent(Constants.context, QuickViewActivity.class);
				startActivity(intent);
				finish();
        	} else {
        		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
				
				alertDialogBuilder
				.setMessage(feed)
				.setCancelable(false)
				.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						dialog.dismiss();
					}
				  });

				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
        	}
			
        }
    }
}
