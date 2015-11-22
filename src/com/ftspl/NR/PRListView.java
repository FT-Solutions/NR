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
import android.widget.Toast;
 
@SuppressLint("NewApi")
public class PRListView extends Activity {
  
    ListView list;
    PRLazyAdapter adapter;
    Button logout;
    String jsonStr, prNoStr, itemNo;
    int jsonIndex;
    Context context;
    
    ArrayList<HashMap<String, String>> prList = new ArrayList<HashMap<String, String>>();
    private ProgressDialog pdia;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pr_list);
 
        Intent intent = getIntent();
        jsonStr = intent.getStringExtra("prdata");
        context = this;
        populateList();
            
       getActionBar().setTitle(Html.fromHtml("<font color='#000000'>PR Inbox</font>"));
       getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
 
        list=(ListView)findViewById(R.id.list);
 
        // Getting adapter by passing xml data ArrayList
        adapter=new PRLazyAdapter(this, prList);
        list.setAdapter(adapter);
 
        // Click event for single list row
        list.setOnItemClickListener(new OnItemClickListener() {
 
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
            	//TextView pr_no = (TextView) view.findViewById(R.id.pr_no);
            	//TextView item_no = (TextView) view.findViewById(R.id.item_no);
            	//prNoStr = pr_no.getText().toString();
            	//itemNo = item_no.getText().toString();
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
            	prList.clear();
                JSONObject jsonObj = new JSONObject(jsonStr);
                 
                // Getting JSON Array node
                Constants.prListData = jsonObj.getJSONArray("ITAB_PR");
                
                // looping through All Contacts
                for (int i = 0; i < Constants.prListData.length(); i++) {
                    JSONObject c = Constants.prListData.getJSONObject(i);
                     
                    String prNo = c.getString("BANFN");
                    String text = c.getString("MAKTX");
                    String item_no = c.getString("BNFPO");
                    String rel_code = c.getString("FRGC");
                    String prDate = c.getString("BADAT");

                    Log.d("ITEM", prNo+"=="+item_no);
                    // tmp hashmap for single contact
                    HashMap<String, String> map = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    map.put("pr_no", prNo);
                    map.put("text", text);
                    map.put("item_no", item_no);
                    map.put("rel_code", rel_code);
                    map.put("prDate", prDate);

                    // adding contact to contact list
                    prList.add(map);
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
				JSONObject c = Constants.prListData.getJSONObject(jsonIndex);
                String prNo = c.getString("BANFN");
                String item_no = c.getString("BNFPO");
				feed = Constants.connect.getPRDetail(Constants.context, prNo, item_no);
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
				prArray = jsonObj.getJSONArray("ITAB_PR");
				Constants.releaseDetail = prArray;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        	if(prArray instanceof JSONArray) {
	        	//Toast.makeText(getApplicationContext(), "Login SuccessFul", Toast.LENGTH_LONG).show();
            	
	        	Intent intent = new Intent(Constants.context, PRDetailActivity.class);
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
				feed = Constants.connect.getPRList(Constants.context);
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
				prArray = jsonObj.getJSONArray("ITAB_PR");
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