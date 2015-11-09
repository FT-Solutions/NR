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
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class QuickViewActivity extends Activity {

	public ProgressDialog pdia;
	public RelativeLayout prTab, poTab;
	public Context context;
	public ImageButton refresh;
	
	Boolean running = false;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quickview);
		
		context = this;
		prTab = (RelativeLayout) findViewById(R.id.pr_tab);
		poTab = (RelativeLayout) findViewById(R.id.po_tab);
		
		refresh = (ImageButton) findViewById(R.id.refresh);
		refresh.setVisibility(View.INVISIBLE);
		
		getActionBar().setTitle(Html.fromHtml("<font color='#000000'>Asisindia</font>"));
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        
		
	}

	@Override
	protected void onResume (){
		super.onResume();
		if(!running) {
			Constants.context = this.getApplicationContext();
			Constants.loginDetails = Constants.context.getSharedPreferences("com.ftspl.NR", Context.MODE_PRIVATE);
	        //SharedPreferences loginDetails = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
	        
	        Constants.usernameStr = Constants.loginDetails.getString("sysem_username", "");
	        Constants.passwordStr = Constants.loginDetails.getString("sysem_password", "");
	        Constants.ApplicationServer = Constants.loginDetails.getString("application_server", "");
			
			if(Constants.connect.isConnectingToInternet(Constants.context)) {
				new fetchCount().execute("");
			} else {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
				
				alertDialogBuilder
				.setMessage(Constants.NO_CONNECTION)
				.setCancelable(false)
				.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						finish();
					}
				  });

				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			}
		}
	}
	
	@Override
	protected void onPause (){
		super.onPause();
		running = false;
	}
	
	@Override
	protected void onRestart (){
		super.onRestart();
		if(!running) {
			if(Constants.connect.isConnectingToInternet(Constants.context)) {
				new fetchCount().execute("");
			} else {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
				
				alertDialogBuilder
				.setMessage(Constants.NO_CONNECTION)
				.setCancelable(false)
				.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						finish();
					}
				  });

				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
            return super.onOptionsItemSelected(item);
    }
	
	/* Async fetch Data */
	class fetchCount extends AsyncTask<String, Void, String> {
	      
    	@Override
    	protected void onPreExecute(){ 
    	   super.onPreExecute();
    	   		running = true;
    	        pdia = new ProgressDialog(context);
    	        pdia.setMessage("Please wait...");
    	        pdia.show();    
    	}
    	
        protected String doInBackground(String... param) {
        	String data = "";
        	pdia.setCancelable(false);
			try {
				data = Constants.connect.getCounts(Constants.context);
				Log.d("PO List", data);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return data;
        }

        @Override
        protected void onPostExecute(String data) {
        	super.onPostExecute(data);
        	running = false;
        	String temData = data;
        	JSONObject jsonObj;
        	JSONArray prArray = null;

    		poTab.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.grid_bg));
			try {
				jsonObj = new JSONObject(data);
				prArray = jsonObj.getJSONArray("MOBI");
				
				if(prArray instanceof JSONArray) {
	        		String prCount = "";
	        		String poCount = "";
	        		
	        		prCount = prArray.getJSONObject(0).getString("PR");
	        		poCount = prArray.getJSONObject(0).getString("PO");
	        		
	        		if(prCount.equals("")) {
	        			prTab.setBackgroundDrawable(getResources().getDrawable(R.drawable.grid_disable_bg));
	        			ImageView prImage = (ImageView) prTab.findViewById(R.id.imageView1);
	        			prImage.setImageDrawable(getResources().getDrawable(R.drawable.po_grey));
	        			prCount = "0";
	        		}
	        		
	        		ImageView prImage = (ImageView) poTab.findViewById(R.id.imageView1);
	        		
	        		if(poCount.equals("")) {
	        			poTab.setBackgroundDrawable(getResources().getDrawable(R.drawable.grid_disable_bg));
	        			prImage = (ImageView) poTab.findViewById(R.id.imageView1);
	        			prImage.setImageDrawable(getResources().getDrawable(R.drawable.po_grey));
	        			poCount = "0";
	        		} else {
	        			prImage.setImageDrawable(getResources().getDrawable(R.drawable.po_blue));
	        			poTab.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								poTab.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.grid_selected_bg));
								if(Constants.connect.isConnectingToInternet(context)){
									new RetrievePOApproval().execute("");
								} else {
									Toast.makeText(context, Constants.NO_CONNECTION, Toast.LENGTH_LONG).show();
									poTab.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.grid_bg));
								}
							}
						});
	        		}
	        		
	        		TextView pr = (TextView) prTab.findViewById(R.id.pr_count);
        			pr.setText(prCount);
        			TextView po = (TextView) poTab.findViewById(R.id.po_count);
        			po.setText(poCount);
        			
	        		
	        	}  else {
	        		Toast.makeText(Constants.context, "Something went Wrong", Toast.LENGTH_LONG).show();
	        	}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
			pdia.dismiss();
			
        	
        }
    }

	class RetrievePOApproval extends AsyncTask<String, Void, String> {

        private Exception exception;
        
        @Override
    	protected void onPreExecute(){ 
    	   super.onPreExecute();
    	        pdia = new ProgressDialog(context);
    	        pdia.setMessage("Please wait...");
    	        pdia.show();    
    	}
        
        protected String doInBackground(String... param) {
        	String feed = "";
			try {
				feed = Constants.connect.callRESTservice(Constants.context);
				Log.d("XML DATA", feed);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return feed;
        }

        protected void onPostExecute(String feed) {
          
        	pdia.dismiss();
        	String temData = feed;
        	JSONObject jsonObj;
        	JSONArray prArray = null;
			try {
				jsonObj = new JSONObject(feed);
				prArray = jsonObj.getJSONArray("ITAB");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        	if(prArray instanceof JSONArray) {
	        	Intent intent = new Intent(context, CustomizedListView.class);
	        	intent.putExtra("prdata", temData);
	        	startActivity(intent);
	        			
        	} else {
        		Toast.makeText(getApplicationContext(), "Something Wrong", Toast.LENGTH_LONG).show();
        	}
        }
    }

	public void logout(View v) {
		SharedPreferences.Editor ed = Constants.loginDetails.edit();
		ed.remove("system_username");
		ed.remove("system_password");
		
		ed.clear();
		ed.commit();
		
    	
    	Intent intent = new Intent(Constants.context, LoginActivity.class);
    	 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
    	 intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
    	startActivity(intent);
    	finish();
    }
	
	public void exit(View v) {
	   	
		finish();
   		
	    int pid = android.os.Process.myPid();
	    android.os.Process.killProcess(pid);
	    System.exit(0);
	}
}
