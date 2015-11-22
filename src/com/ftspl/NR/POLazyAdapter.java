package com.ftspl.NR;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
 
@SuppressLint("SimpleDateFormat")
public class POLazyAdapter extends BaseAdapter {
 
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
 
    public POLazyAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
 
    public int getCount() {
        return data.size();
    }
 
    public Object getItem(int position) {
        return position;
    }
 
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.po_list_row, null);
 
        TextView name = (TextView)vi.findViewById(R.id.name);
        TextView poDate = (TextView)vi.findViewById(R.id.poDate);
        TextView poNo = (TextView) vi.findViewById(R.id.po_no);
        TextView netValue = (TextView) vi.findViewById(R.id.netValue);
        
        HashMap<String, String> poListItem = new HashMap<String, String>();
        poListItem = data.get(position);
        String date = poListItem.get("po_date");
        
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        java.util.Date newDate = null;
		try {
			newDate = format.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        format = new SimpleDateFormat("MMMM dd, yyyy");
        date = format.format(newDate);
         
        name.setText(poListItem.get("name"));
        poDate.setText(date);
        poNo.setText(poListItem.get("po_no"));
        netValue.setText(poListItem.get("net_value"));
        return vi;
    }
}