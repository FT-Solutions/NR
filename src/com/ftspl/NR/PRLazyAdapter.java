package com.ftspl.NR;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
 
public class PRLazyAdapter extends BaseAdapter {
 
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
 
    public PRLazyAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
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
            vi = inflater.inflate(R.layout.pr_list_row, null);
 
        TextView text = (TextView)vi.findViewById(R.id.pr_text);
        TextView prNum = (TextView)vi.findViewById(R.id.pr_no);
        TextView item = (TextView) vi.findViewById(R.id.item);
        TextView prDate = (TextView) vi.findViewById(R.id.prDate);
        
        HashMap<String, String> poListItem = new HashMap<String, String>();
        poListItem = data.get(position);
 
        String date = poListItem.get("prDate");
        
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
        
        // Setting all values in listview
        text.setText(poListItem.get("text"));
        prNum.setText(poListItem.get("pr_no"));
        item.setText(poListItem.get("item_no"));
        prDate.setText(date);
        return vi;
    }
}