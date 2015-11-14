package com.ftspl.NR;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class fetchData {

	public ProgressDialog pdia;
	
	
	class fetchPoList extends AsyncTask<String, Void, String> {
	      
    	@Override
    	protected void onPreExecute(){ 
    	   super.onPreExecute();
    	        pdia = new ProgressDialog(Constants.context);
    	        pdia.setMessage("Please wait...");
    	        pdia.show();    
    	}
    	
        protected String doInBackground(String... param) {
        	String data = "";
        	pdia.setCancelable(false);
			try {
				data = Constants.connect.getPoList(Constants.context);
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
        	pdia.dismiss(); 
        	
        	String temData = data;
        	JSONObject jsonObj;
        	JSONArray prArray = null;
			try {
				jsonObj = new JSONObject(data);
				prArray = jsonObj.getJSONArray("ITAB");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        	if(prArray instanceof JSONArray) {
	        	//Toast.makeText(getApplicationContext(), "Login SuccessFul", Toast.LENGTH_LONG).show();
	        	//Intent intent = new Intent(Constants.context, CustomizedListView.class);
	        	//intent.putExtra("prdata", temData);
	        	//startActivity(intent);
	        	//finish();
	        			
        	} else if(temData.equals("99")) {
        		Toast.makeText(Constants.context, "Something Wrong", Toast.LENGTH_LONG).show();
        	} else {
        		Toast.makeText(Constants.context, "Something went Wrong", Toast.LENGTH_LONG).show();
        	}
        }
    }
}
