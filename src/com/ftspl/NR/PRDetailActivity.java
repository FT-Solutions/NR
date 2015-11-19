package com.ftspl.NR;

import java.util.ArrayList;
import java.util.HashMap;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


@SuppressLint("NewApi")
public class PRDetailActivity extends Activity implements OnClickListener {
	
	Button accept, reject;
	Context context;
	TextView pr_num;
	 ListView list;
	String action, codeStr, pr_no, item_no;
	ArrayList<HashMap<String, String>> detailList = new ArrayList<HashMap<String, String>>();
	private ProgressDialog pdia;
	PRDetailLazyAdapter adapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pr_detail);
        context = this;
        Intent intent = getIntent();
        int index = intent.getIntExtra("jsonIndex", 0);
        TextView pr_num = (TextView) findViewById(R.id.pr_num);
        try {
        	JSONObject list = Constants.prListData.getJSONObject(index);
        	codeStr = list.getString("FRGC");
        	pr_no = list.getString("BANFN");
        	item_no = list.getString("BNFPO");
        	pr_num.setText(pr_no);
        	
        } catch (JSONException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
        
        list=(ListView)findViewById(R.id.listView1);
        adapter = new PRDetailLazyAdapter(this, detailList);
        list.setAdapter(adapter);
        
        accept = (Button) findViewById(R.id.accept);
        reject = (Button) findViewById(R.id.reject);
        
        accept.setOnClickListener(this);
        reject.setOnClickListener(this);
        
        getActionBar().setTitle(Html.fromHtml("<font color='#000000'>Detail View</font>"));
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        
        
       try {
    	   for (int i = 0; i < Constants.releaseDetail.length(); i++) {
    		   JSONObject record = Constants.releaseDetail.getJSONObject(i);
    		   HashMap<String, String> map = new HashMap<String, String>();
    		   
    		   map.put("BANFN", record.getString("BANFN"));
    		   map.put("BNFPO", record.getString("BNFPO"));
    		   map.put("MATNR", record.getString("MATNR"));
    		   map.put("MAKTX", record.getString("MAKTX"));
    		   map.put("MENGE", record.getString("MENGE"));
    		   map.put("MEINS", record.getString("MEINS"));
    		   map.put("AFNAM", record.getString("AFNAM"));
    		   map.put("WERKS", record.getString("WERKS"));
    		   map.put("STOCK", record.getString("STOCK"));
    		   map.put("C0_STOCK", record.getString("C0_STOCK"));
    		   detailList.add(map);
    	   }
        
    	
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
    
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
		case R.id.accept:
			action = "APP";
			Log.d("TTTT", "APP");
			
			break;
		case R.id.reject:
			action = "REJ";
			Log.d("TTTT", "REJ");
			
			break;
		}
		if(Constants.connect.isConnectingToInternet(Constants.context)) {
			new prActionTask().execute("");
		} else {
			Toast.makeText(context, Constants.NO_CONNECTION, Toast.LENGTH_LONG).show();
		}
 	}
	
	class prActionTask extends AsyncTask<String, Void, String> {

      
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
				feed = Constants.connect.prAcceptReject(Constants.context, pr_no, item_no ,codeStr, action);
				Log.d("XML DATA", feed);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return feed;
        }

        protected void onPostExecute(String feed) {
        	pdia.dismiss();
        	new RetrievePRList().execute("");
        	Toast.makeText(getApplicationContext(), feed, Toast.LENGTH_LONG).show();
        }
    }
    
	class RetrievePRList extends AsyncTask<String, Void, String> {

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
				feed = Constants.connect.getPRList(Constants.context);
				Log.d("XML DATA", feed);
				Constants.responseData = feed;
				Log.d("RESPONSE", Constants.responseData);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return feed;
        }

        protected void onPostExecute(String feed) {
        	pdia.dismiss();
        	Intent intent = new Intent(Constants.context, PRListView.class);
        	intent.putExtra("prdata", Constants.responseData);
       	 	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
       	 	intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
       	 	startActivity(intent);
       	 	finish();
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
	
	public void refresh(View v) {
		
    }
}
