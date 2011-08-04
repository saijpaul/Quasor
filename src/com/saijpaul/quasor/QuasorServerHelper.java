/*******************************************************************
* Copyright© 2011 Seema Saijpaul
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.

* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details. You should have
* received a copy of the GNU General Public License along with this program. 
* If not, see <http://www.gnu.org/licenses/>.

* Author: Seema Saijpaul
* Feedback: nssell.2009@gmail.com
*******************************************************************/

package com.saijpaul.quasor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;


import android.util.Log;


public class QuasorServerHelper {
	
	public static JSONArray getdataFromServer(String urlToFetchDataFrom ,String ailmentName){
		InputStream in = null;
		String finalResult = "";
		JSONArray finalResultArray = null;
		
		try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(urlToFetchDataFrom);
            ArrayList<NameValuePair> nameValue = new ArrayList<NameValuePair>();
	        nameValue.add(new BasicNameValuePair(GlobalConstant.aN_TO_FETCH_REMEDIES,ailmentName));
	        httppost.setEntity(new UrlEncodedFormEntity(nameValue));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity responseEntity = response.getEntity();
            in = responseEntity.getContent();
        }catch(Exception e){
        	Log.e(GlobalConstant.SERVER_CLASS_NAME, GlobalConstant.HTTP_CONNECTION_ERROR+e.toString());	            
	    }
	    
	    try{
    	    BufferedReader reader = new BufferedReader(new InputStreamReader(in,"iso-8859-1"),8);
            StringBuilder sbuilder = new StringBuilder();
            String lineReader = null;
            while ((lineReader = reader.readLine()) != null) {
            	sbuilder.append(lineReader + "\n");
            }
            in.close();
            finalResult=sbuilder.toString();            
	    }catch(Exception e){
	    	Log.e(GlobalConstant.SERVER_CLASS_NAME, GlobalConstant.CONVERSION_ERROR+e.toString());
	    }
	    
	    try{
	    	finalResultArray = new JSONArray(finalResult);            
	    }catch(JSONException e){
	    	Log.e(GlobalConstant.SERVER_CLASS_NAME, GlobalConstant.PARSING_DATA_ERROR+e.toString());
	    }
    
	    return finalResultArray;
	}
	
	public static boolean sendDatatoServer(String urlToSendDataTo,String submitedBy,String remedyDesc,String ailmentName){

	    try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(urlToSendDataTo);

        	ArrayList<NameValuePair> nameValue = new ArrayList<NameValuePair>();
            nameValue.add(new BasicNameValuePair(GlobalConstant.AILMENT_NAME_TO_STORE_REMEDY,ailmentName));
            nameValue.add(new BasicNameValuePair(GlobalConstant.REMEDY_DESC_TO_STORE,remedyDesc));
            nameValue.add(new BasicNameValuePair(GlobalConstant.SUBMITTED_BY_USER_NAME_TO_STORE,submitedBy));
            httppost.setEntity(new UrlEncodedFormEntity(nameValue));
            httpclient.execute(httppost);
	    }catch(Exception e){
	    	Log.e(GlobalConstant.SERVER_CLASS_NAME, GlobalConstant.DATA_STORAGE_TO_SERVER_ERROR+e.toString());
	    }	    
	   return true;
	}


}
