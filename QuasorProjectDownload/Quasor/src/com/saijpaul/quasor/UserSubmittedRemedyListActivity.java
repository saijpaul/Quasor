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

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class UserSubmittedRemedyListActivity extends ListActivity{    
	
	private AilmentAdapter ailmentAdapter = null;
	private ImageView homeImage = null;
	private ImageView addRemedyImage = null;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usersubmittedremedylist);
        this.homeImage = (ImageView)this.findViewById(R.id.imageHome);
        this.addRemedyImage = (ImageView)this.findViewById(R.id.imageAddRemedy);
        
        homeImage.setOnClickListener(clickListener);
        addRemedyImage.setOnClickListener(clickListener);

        AilmentServerInfoBean ailmentServerObject = null;        
        
        final ArrayList<AilmentServerInfoBean> userSubmittedAilmentList = new ArrayList<AilmentServerInfoBean>();
        JSONArray ailmentArray = QuasorServerHelper.getdataFromServer(GlobalConstant.USER_SUBMITTED_AILMENT_LIST,null);
        
        if(ailmentArray!=null){
	        try{	        	
	        	for(int k=0;k<ailmentArray.length();k++){
	                JSONObject json_data = ailmentArray.getJSONObject(k);
	                ailmentServerObject = new AilmentServerInfoBean();
	                ailmentServerObject.setAilmentName(json_data.getString(GlobalConstant.COLUMN_NAME));
	                userSubmittedAilmentList.add(ailmentServerObject);
	        	}	        	
	        }
	        catch(JSONException e) {
	        	Log.e(getClass().getSimpleName(), GlobalConstant.PARSING_DATA_ERROR+e.toString());	        	
	       }
        }
        
        showUserSubmittedList(userSubmittedAilmentList);
    }
    
    View.OnClickListener clickListener = new View.OnClickListener(){
        public void  onClick  (View  v){
        	switch(v.getId()){
        	
	    	case R.id.imageHome:	    		
	    	    finish();
	    		break;
	    	case R.id.imageAddRemedy:
	    		Intent msg = new Intent(UserSubmittedRemedyListActivity.this,QuasorFormToSubmitRemedyActivity.class);
	    		UserSubmittedRemedyListActivity.this.startActivity(msg);
	    		break;

    	}	
        }
    };
    
    private void showUserSubmittedList(ArrayList<AilmentServerInfoBean> ailmentList) {
    	
    	this.ailmentAdapter = new AilmentAdapter(this, R.layout.ailmentlistview, ailmentList);
		setListAdapter(this.ailmentAdapter);        
        getListView().setTextFilterEnabled(true);
               
        getListView().setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              AilmentServerInfoBean aInfo = (AilmentServerInfoBean)getListAdapter().getItem(position);
        	  Intent msg = new Intent(UserSubmittedRemedyListActivity.this,UserSubmittedRemedyDisplayActivity.class);
        	  msg.putExtra(GlobalConstant.USER_AILMENT_NAME, aInfo.getAilmentName());
        	  UserSubmittedRemedyListActivity.this.startActivity(msg);
            }
          });        

	}
    
    private class AilmentAdapter extends ArrayAdapter<AilmentServerInfoBean> {

		public ArrayList<AilmentServerInfoBean> ailmentArray;
                
        public AilmentAdapter(Context context, int textViewResourceId, ArrayList<AilmentServerInfoBean> objects) {
        	   super(context, textViewResourceId, objects);
               this.ailmentArray = objects;                
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
        	
        	View view = convertView;
                if (view == null) {
                    LayoutInflater inflator = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view = inflator.inflate(R.layout.ailmentlistview, null);
                }
              
                AilmentServerInfoBean ailmentServerInfoBeanObject = ailmentArray.get(position);
               
                if (ailmentServerInfoBeanObject != null) {
                        TextView textview = (TextView) view.findViewById(R.id.text);                        
                        if (textview != null) {
                              textview.setText(ailmentServerInfoBeanObject.getAilmentName());                           
                        }
                }
                return view;
        }
    }

}