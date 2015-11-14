package com.ftspl.NR;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class Connect {

	public String checkUser(Context context) {  
	    String result = "";  
	    HttpHost targetHost = new HttpHost(Constants.ApplicationServer, Constants.PORT, "http");  
	    DefaultHttpClient httpclient = new DefaultHttpClient();  
	    httpclient.getCredentialsProvider().setCredentials(new AuthScope(targetHost.getHostName(), targetHost.getPort()), new UsernamePasswordCredentials(Constants.usernameStr, Constants.passwordStr));  
	    // Create AuthCache instance  
	    AuthCache authCache = new BasicAuthCache();  
	    // Generate BASIC scheme object and add it to the local auth cache  
	    BasicScheme basicAuth = new BasicScheme();  
	    authCache.put(targetHost, basicAuth);  
	    // Add AuthCache to the execution context  
	    BasicHttpContext localcontext = new BasicHttpContext();  
	    localcontext.setAttribute(ClientContext.AUTH_SCHEME_PREF, authCache);  
	    
	    HttpGet request = new HttpGet("/sap/ft_mobi/ft_chk_usr/"+Constants.userId+"?sap-client="+Constants.SAP_CLIENT);
	    ResponseHandler<String> handler = new BasicResponseHandler();
	    
	    try {
	    	result = httpclient.execute(targetHost, request, handler);
	    } catch (ClientProtocolException e) {
	    	e.printStackTrace();
	    	result = "0";  
	    } catch (IOException e) {
	    	e.printStackTrace();
	    	result = "99";  
	    }  
	    
	    httpclient.getConnectionManager().shutdown();  
	    return result;
	} 
	
	public String getPoList(Context context) {  
	    String result = "";  
	    HttpHost targetHost = new HttpHost(Constants.ApplicationServer, Constants.PORT, "http");  
	    DefaultHttpClient httpclient = new DefaultHttpClient();  
	    httpclient.getCredentialsProvider().setCredentials(new AuthScope(targetHost.getHostName(), targetHost.getPort()), new UsernamePasswordCredentials(Constants.usernameStr, Constants.passwordStr));  
	    // Create AuthCache instance  
	    AuthCache authCache = new BasicAuthCache();  
	    // Generate BASIC scheme object and add it to the local auth cache  
	    BasicScheme basicAuth = new BasicScheme();  
	    authCache.put(targetHost, basicAuth);  
	    // Add AuthCache to the execution context  
	    BasicHttpContext localcontext = new BasicHttpContext();  
	    localcontext.setAttribute(ClientContext.AUTH_SCHEME_PREF, authCache);  
	    
	    HttpGet request = new HttpGet("/sap/ft_mobi/FT_PO_LIST/"+Constants.userId+"?sap-client="+Constants.SAP_CLIENT);
	    ResponseHandler<String> handler = new BasicResponseHandler();
	    
	    try {
	    	result = httpclient.execute(targetHost, request, handler);
	    } catch (ClientProtocolException e) {
	    	e.printStackTrace();
	    	result = "0";  
	    } catch (IOException e) {
	    	e.printStackTrace();
	    	result = "99";  
	    }  
	    
	    httpclient.getConnectionManager().shutdown();  
	    return result;
	}  
	
	public String poAcceptReject(Context context,  String po_no, String code, String action) {  
	    String result = "";  

	    HttpHost targetHost = new HttpHost(Constants.ApplicationServer, Constants.PORT, "http");  
	    DefaultHttpClient httpclient = new DefaultHttpClient();  
	    httpclient.getCredentialsProvider().setCredentials(new AuthScope(targetHost.getHostName(), targetHost.getPort()), new UsernamePasswordCredentials(Constants.usernameStr, Constants.passwordStr));  
	    // Create AuthCache instance  
	    AuthCache authCache = new BasicAuthCache();  
	    // Generate BASIC scheme object and add it to the local auth cache  
	    BasicScheme basicAuth = new BasicScheme();  
	    authCache.put(targetHost, basicAuth);  
	    // Add AuthCache to the execution context  
	    BasicHttpContext localcontext = new BasicHttpContext();  
	    localcontext.setAttribute(ClientContext.AUTH_SCHEME_PREF, authCache); 
	    HttpGet request = new HttpGet("/sap/ft_mobi/FT_PO_APP/"+Constants.userId+"/"+po_no+"/"+code+"/"+action+"?sap-client="+Constants.SAP_CLIENT);  
	    ResponseHandler<String> handler = new BasicResponseHandler();
	    
	    try {
	    	result = httpclient.execute(targetHost, request, handler);
	    } catch (ClientProtocolException e) {
	    	e.printStackTrace();
	    	result = "0";  
	    } catch (IOException e) {
	    	e.printStackTrace();
	    	result = "99";  
	    }  
	    
	    httpclient.getConnectionManager().shutdown();  
	    return result;
	}  
	
	public String getPODetail(Context context, String po_no) {  
	    String result = "";  

	    HttpHost targetHost = new HttpHost(Constants.ApplicationServer, Constants.PORT, "http");  
	    DefaultHttpClient httpclient = new DefaultHttpClient();  
	    httpclient.getCredentialsProvider().setCredentials(new AuthScope(targetHost.getHostName(), targetHost.getPort()), new UsernamePasswordCredentials(Constants.usernameStr, Constants.passwordStr));  
	    // Create AuthCache instance  
	    AuthCache authCache = new BasicAuthCache();  
	    // Generate BASIC scheme object and add it to the local auth cache  
	    BasicScheme basicAuth = new BasicScheme();  
	    authCache.put(targetHost, basicAuth);  
	    // Add AuthCache to the execution context  
	    BasicHttpContext localcontext = new BasicHttpContext();  
	    localcontext.setAttribute(ClientContext.AUTH_SCHEME_PREF, authCache);  
	    
	    HttpGet request = new HttpGet("/sap/ft_mobi/FT_PO_detail/"+Constants.userId+"/"+po_no+"?sap-client="+Constants.SAP_CLIENT);
	    ResponseHandler<String> handler = new BasicResponseHandler();
	    
	    try {
	    	result = httpclient.execute(targetHost, request, handler);
	    } catch (ClientProtocolException e) {
	    	e.printStackTrace();
	    	result = "0";  
	    } catch (IOException e) {
	    	e.printStackTrace();
	    	result = "99";  
	    }  
	    
	    httpclient.getConnectionManager().shutdown();  
	    return result;
	}  
	
	public String getCounts(Context context) {  
	    String result = "";  

	    HttpHost targetHost = new HttpHost(Constants.ApplicationServer, Constants.PORT, "http");  
	    DefaultHttpClient httpclient = new DefaultHttpClient();  
	    httpclient.getCredentialsProvider().setCredentials(new AuthScope(targetHost.getHostName(), targetHost.getPort()), new UsernamePasswordCredentials(Constants.usernameStr, Constants.passwordStr));  
	    // Create AuthCache instance  
	    AuthCache authCache = new BasicAuthCache();  
	    // Generate BASIC scheme object and add it to the local auth cache  
	    BasicScheme basicAuth = new BasicScheme();  
	    authCache.put(targetHost, basicAuth);  
	    // Add AuthCache to the execution context  
	    BasicHttpContext localcontext = new BasicHttpContext();  
	    localcontext.setAttribute(ClientContext.AUTH_SCHEME_PREF, authCache);  
	    
	    HttpGet request = new HttpGet("/sap/zget_mobi?sap-client="+Constants.SAP_CLIENT);
	    ResponseHandler<String> handler = new BasicResponseHandler();
	    
	    try {
	    	result = httpclient.execute(targetHost, request, handler);
	    } catch (ClientProtocolException e) {
	    	e.printStackTrace();
	    	result = "0";  
	    } catch (IOException e) {
	    	e.printStackTrace();
	    	result = "99";  
	    }  
	    
	    httpclient.getConnectionManager().shutdown();  
	    return result;
	} 
	public boolean isConnectingToInternet(Context context){
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
          if (connectivity != null) 
          {
              NetworkInfo[] info = connectivity.getAllNetworkInfo();
              if (info != null) 
                  for (int i = 0; i < info.length; i++) 
                      if (info[i].getState() == NetworkInfo.State.CONNECTED)
                      {
                          return true;
                      }
 
          }
          return false;
    }
	
}

