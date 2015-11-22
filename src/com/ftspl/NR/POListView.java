package com.ftspl.NR;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
 
@SuppressLint("NewApi")
public class POListView extends Activity {
  
    ListView list;
    POLazyAdapter adapter;
    Button logout;
    String jsonStr, poNoStr;
    int jsonIndex;
    Context context;
    
    ArrayList<HashMap<String, String>> poList = new ArrayList<HashMap<String, String>>();
    private ProgressDialog pdia;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pr_list);
 
        Intent intent = getIntent();
        jsonStr = intent.getStringExtra("prdata");
        context = this;
        populateList();
            
       getActionBar().setTitle(Html.fromHtml("<font color='#000000'>PO Inbox</font>"));
       getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
 
        list=(ListView)findViewById(R.id.list);
 
        // Getting adapter by passing xml data ArrayList
        adapter=new POLazyAdapter(this, poList);
        list.setAdapter(adapter);
 
        // Click event for single list row
        list.setOnItemClickListener(new OnItemClickListener() {
 
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
            	TextView poNo = (TextView) view.findViewById(R.id.po_no);
            	poNoStr = poNo.getText().toString();
            	jsonIndex = position;
            	new getDetails().execute("");
            }
        });
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
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
    
    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        
        if(!Constants.responseData.equals("")) {
        	Log.d("RESPONSE", Constants.responseData);
        	jsonStr = Constants.responseData;
        	Constants.responseData = "";
        	populateList();
        	adapter.notifyDataSetChanged();
        }
    } 
    
    public void populateList() {
    	if (jsonStr != null) {
            try {
            	poList.clear();
                JSONObject jsonObj = new JSONObject(jsonStr);
                 
                // Getting JSON Array node
                Constants.poListData = jsonObj.getJSONArray("ITAB");
                
                // looping through All Contacts
                for (int i = 0; i < Constants.poListData.length(); i++) {
                    JSONObject c = Constants.poListData.getJSONObject(i);
                     
                    String poNo = c.getString("EBELN");
                    String vendorName = c.getString("LIFNR");
                    String name = c.getString("NAME1");
                    String code = c.getString("FRGC");
                    String netValue = c.getString("NETWR");
                    String date = c.getString("AEDAT");


                    Log.d("ITEM", poNo+"=="+vendorName);
                    // tmp hashmap for single contact
                    HashMap<String, String> map = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    map.put("po_no", poNo);
                    map.put("vendorName", vendorName);
                    map.put("name", name);
                    map.put("po_code", code);
                    map.put("po_date", date);
                    map.put("net_value", netValue);

                    // adding contact to contact list
                    poList.add(map);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
        }
    }
    
class getDetails extends AsyncTask<String, Void, String> {
    	
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
        	pdia.setCancelable(false);
			try {
				feed = Constants.connect.getPODetail(Constants.context, poNoStr);
				Log.d("detail DATA", feed);
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
				Constants.releaseDetail = prArray;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        	if(prArray instanceof JSONArray) {
	        	//Toast.makeText(getApplicationContext(), "Login SuccessFul", Toast.LENGTH_LONG).show();
            	
	        	Intent intent = new Intent(Constants.context, DetailActivity.class);
	        	intent.putExtra("jsonIndex", jsonIndex);
	        	startActivity(intent);
	        			
        	} else if(temData.equals("99")) {
        		Toast.makeText(getApplicationContext(), "Something Wrong", Toast.LENGTH_LONG).show();
        	} else {
        		Toast.makeText(getApplicationContext(), "Unauthorized", Toast.LENGTH_LONG).show();
        	}
        }
    }
	
	public void refresh(View v) {
		new RetrievePOApproval().execute("");
	}
	
	public void exit(View v) {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		startActivity(intent);
	    int pid = android.os.Process.myPid();
	    android.os.Process.killProcess(pid);
	    System.exit(0);
	}
	
     
	class RetrievePOApproval extends AsyncTask<String, Void, String> {

        @Override
    	protected void onPreExecute(){ 
    	   super.onPreExecute();
    	        pdia = new ProgressDialog(context);
    	        pdia.setMessage("Please wait...");
    	        pdia.show();    
    	}
        
        protected String doInBackground(String... param) {
        	String feed = "";
        	pdia.setCancelable(false);
			try {
				feed = Constants.connect.getPoList(Constants.context);
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
	        	jsonStr = temData;
	        	populateList();
	        	adapter.notifyDataSetChanged();
	        	Toast.makeText(getApplicationContext(), "Data Updated", Toast.LENGTH_LONG).show();
	        	
        	} else {
        		Toast.makeText(getApplicationContext(), "Something Wrong", Toast.LENGTH_LONG).show();
        	}
        }
    }

}