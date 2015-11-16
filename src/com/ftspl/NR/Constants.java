package com.ftspl.NR;

import org.json.JSONArray;

import android.content.Context;
import android.content.SharedPreferences;

public class Constants {
	public static String API_URL = "";
	public static String USERNAME = "ABAP";
	public static String PASSWORD = "sapabapgvd";
	public static String SAP_CLIENT = "100";
	public static Integer PORT = 8001;
	public static String NO_CONNECTION = "Please check internet connection";
	public static String NO_DATA = "No data";
	
	public static JSONArray poListData = null;
	public static JSONArray prListData = null;
	public static String responseData = "";
	public static JSONArray releaseDetail = null;
	
	public static SharedPreferences loginDetails;
	
	public static String ApplicationServer = null;
	public static String usernameStr = "";
	public static String passwordStr = "";
	public static String userId = "";
	
	
	public static Connect connect = new Connect(); 
	public static Context context = null;
	
}