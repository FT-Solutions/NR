package com.ftspl.NR;

import java.util.ArrayList;
import java.util.HashMap;

import com.ftspl.NR.R;

import android.app.Activity;
import android.content.Context;
import android.nfc.TagLostException;
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
        TextView UNIT_1 = (TextView) vi.findViewById(R.id.UNIT_1);
        TextView UNIT_2 = (TextView) vi.findViewById(R.id.UNIT_2);
        TextView UNIT_3 = (TextView) vi.findViewById(R.id.UNIT_3);
        TextView UNIT_4 = (TextView) vi.findViewById(R.id.UNIT_4);
        TextView UNIT_5 = (TextView) vi.findViewById(R.id.UNIT_5);
        TextView UNIT_6 = (TextView) vi.findViewById(R.id.UNIT_6);
        TextView UNIT_7 = (TextView) vi.findViewById(R.id.UNIT_7);
        TextView LTEXT = (TextView) vi.findViewById(R.id.LTEXT);

        HashMap<String, String> fields = new HashMap<String, String>();
        fields = data.get(position);
 
        // Setting all values in listview
        //BANFN.setText(song.get("BANFN"));
        BNFPO.setText(fields.get("BNFPO"));
        MATNR.setText(fields.get("MATNR"));
        MAKTX.setText(fields.get("MAKTX"));
        MENGE.setText(fields.get("MENGE"));
        MEINS.setText(fields.get("MEINS"));
        AFNAM.setText(fields.get("AFNAM"));
        WERKS.setText(fields.get("WERKS"));
        STOCK.setText(fields.get("STOCK"));
        C0_STOCK.setText(fields.get("C0_STOCK"));
        UNIT_1.setText(fields.get("UNIT_1"));
        UNIT_2.setText(fields.get("UNIT_2"));
        UNIT_3.setText(fields.get("UNIT_3"));
        UNIT_4.setText(fields.get("UNIT_4"));
        UNIT_5.setText(fields.get("UNIT_5"));
        UNIT_6.setText(fields.get("UNIT_6"));
        UNIT_7.setText(fields.get("UNIT_7"));
        LTEXT.setText(fields.get("LTEXT"));
        
        return vi;
    }
}