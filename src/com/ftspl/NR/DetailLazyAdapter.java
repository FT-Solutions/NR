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
 
public class DetailLazyAdapter extends BaseAdapter {
 
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
 
    public DetailLazyAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
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
            vi = inflater.inflate(R.layout.po_detail_list_row, null);
 
        TextView EBELN = (TextView)vi.findViewById(R.id.EBELN);
        TextView EBELP = (TextView)vi.findViewById(R.id.EBELP);
        TextView TXZ01 = (TextView)vi.findViewById(R.id.TXZ01);
        TextView MATNR = (TextView)vi.findViewById(R.id.MATNR);
        TextView WERKS = (TextView)vi.findViewById(R.id.WERKS);
        TextView MATKL = (TextView)vi.findViewById(R.id.MATKL);
        TextView MENGE = (TextView)vi.findViewById(R.id.MENGE);
        TextView MEINS = (TextView)vi.findViewById(R.id.MEINS);
        TextView NETWR = (TextView)vi.findViewById(R.id.NETWR);
        TextView BRTWR = (TextView)vi.findViewById(R.id.BRTWR);
 
        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);
 
        // Setting all values in listview
        EBELN.setText(song.get("EBELN"));
        EBELP.setText(song.get("EBELP"));
        TXZ01.setText(song.get("TXZ01"));
        MATNR.setText(song.get("MATNR"));
        WERKS.setText(song.get("WERKS"));
        MATKL.setText(song.get("MATKL"));
        MENGE.setText(song.get("MENGE"));
        MEINS.setText(song.get("MEINS"));
        NETWR.setText(song.get("NETWR"));
        BRTWR.setText(song.get("BRTWR"));
        
        return vi;
    }
}