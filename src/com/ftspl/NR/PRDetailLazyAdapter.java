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
 
public class PRDetailLazyAdapter extends BaseAdapter {
 
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
 
    public PRDetailLazyAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
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
            vi = inflater.inflate(R.layout.pr_detail_list_row, null);
        
        //TextView BANFN = (TextView) vi.findViewById(R.id.BANFN);
        TextView BNFPO = (TextView) vi.findViewById(R.id.BNFPO);
        TextView MATNR = (TextView) vi.findViewById(R.id.MATNR);
        TextView MAKTX = (TextView) vi.findViewById(R.id.MAKTX);
        TextView MENGE = (TextView) vi.findViewById(R.id.MENGE);
        TextView MEINS = (TextView) vi.findViewById(R.id.MEINS);
        TextView AFNAM = (TextView) vi.findViewById(R.id.AFNAM);
        TextView WERKS = (TextView) vi.findViewById(R.id.WERKS);
        TextView STOCK = (TextView) vi.findViewById(R.id.STOCK);
        TextView C0_STOCK = (TextView) vi.findViewById(R.id.CO_STOCK);
        
        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);
 
        // Setting all values in listview
        //BANFN.setText(song.get("BANFN"));
        BNFPO.setText(song.get("BNFPO"));
        MATNR.setText(song.get("MATNR"));
        MAKTX.setText(song.get("MAKTX"));
        MENGE.setText(song.get("MENGE"));
        MEINS.setText(song.get("MEINS"));
        AFNAM.setText(song.get("AFNAM"));
        WERKS.setText(song.get("WERKS"));
        STOCK.setText(song.get("STOCK"));
        C0_STOCK.setText(song.get("C0_STOCK"));
        
        return vi;
    }
}