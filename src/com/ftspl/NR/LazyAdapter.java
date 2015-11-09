package com.ftspl.NR;

import java.util.ArrayList;
import java.util.HashMap;

import com.ftspl.NR.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
 
public class LazyAdapter extends BaseAdapter {
 
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
 
    public LazyAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
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
            vi = inflater.inflate(R.layout.list_row, null);
 
        TextView title = (TextView)vi.findViewById(R.id.name);
        TextView artist = (TextView)vi.findViewById(R.id.po_no);
 
        HashMap<String, String> poListItem = new HashMap<String, String>();
        poListItem = data.get(position);
 
        // Setting all values in listview
        title.setText(poListItem.get("name"));
        artist.setText(poListItem.get("po_no"));
        return vi;
    }
}