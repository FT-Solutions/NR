package com.ftspl.NR;

import java.util.ArrayList;
import java.util.HashMap;

import com.ftspl.NR.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.TextView;
 
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
        TextView poNum = (TextView)vi.findViewById(R.id.po_no);
        TextView netValue = (TextView) vi.findViewById(R.id.netValue);
        
        HashMap<String, String> poListItem = new HashMap<String, String>();
        poListItem = data.get(position);
 
        // Setting all values in listview
        name.setText(poListItem.get("name"));
        poNum.setText(poListItem.get("po_no"));
        netValue.setText(poListItem.get("net_value"));
        return vi;
    }
}