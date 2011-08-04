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

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class UserSubmittedRemedyDisplayActivity extends Activity{
    
	private ImageView homeImage;
    private ImageView remedyPrevious;
    private ImageView remedyNext;
    private TextView  smallFont; 
    private TextView  mediumFont;
    private TextView  largeFont;
    private TextView  remedyFont;
    private int totalRemedies=0;
    private int currIndexOfRemedy=0;
    private ArrayList<AilmentServerInfoBean> userSubmittedRemedy = new ArrayList<AilmentServerInfoBean>();
    
       //  @Override
        public void onCreate(Bundle savedInstanceState) {
        	
            super.onCreate(savedInstanceState);
            setContentView(R.layout.userremedydetails);
            String ailmentname = getIntent().getStringExtra(GlobalConstant.USER_AILMENT_NAME);            
            AilmentServerInfoBean ailmentServerObject = null;
                       
            JSONArray ailmentArray = QuasorServerHelper.getdataFromServer(GlobalConstant.USER_SUBMITTED_REMEDY_DETAIL,ailmentname);         
            
            try{
            	for(int k=0;k<ailmentArray.length();k++){
            		
                    JSONObject remedyInfoObj = ailmentArray.getJSONObject(k);
                    ailmentServerObject = new AilmentServerInfoBean();
                    ailmentServerObject.setAilmentNum(remedyInfoObj.getInt(GlobalConstant.USER_COLUMN_AILMENT_NUM));
                    ailmentServerObject.setAilmentName(remedyInfoObj.getString(GlobalConstant.USER_COLUMN_AILMENT_NAME));                    
                    ailmentServerObject.setAilDescription(remedyInfoObj.getString(GlobalConstant.USER_COLUMN_REMEDY_DESC));
                    ailmentServerObject.setAilModerator(remedyInfoObj.getString(GlobalConstant.USER_COLUMN_MODERATOR_APPROVAL));
                    ailmentServerObject.setAilSubmittedBy(remedyInfoObj.getString(GlobalConstant.USER_COLUMN_SUBMITTED_BY));
                    userSubmittedRemedy.add(ailmentServerObject);
            	}
            	
            }
            catch(JSONException e){
            	Log.e(getClass().getSimpleName(), GlobalConstant.PARSING_DATA_ERROR+e.toString());
           }

            this.homeImage = (ImageView)this.findViewById(R.id.imageHome);
            this.remedyPrevious = (ImageView)this.findViewById(R.id.imagePrevious);
            this.remedyNext = (ImageView)this.findViewById(R.id.imageNext);
            
            this.smallFont = (TextView)this.findViewById(R.id.smalltextView);
            this.mediumFont = (TextView)this.findViewById(R.id.mediumtextView);
            this.largeFont = (TextView)this.findViewById(R.id.largetextView);
            this.remedyFont = (TextView)this.findViewById(R.id.remedyDesctextView);
            
            homeImage.setOnClickListener(clickListener);
            smallFont.setOnClickListener(clickListener);
            mediumFont.setOnClickListener(clickListener);
            largeFont.setOnClickListener(clickListener);
            remedyPrevious.setOnClickListener(clickListener);
            remedyNext.setOnClickListener(clickListener);
            showUserSubmittedRemedyDetails(userSubmittedRemedy,0);
  
       }
        
        View.OnClickListener clickListener = new View.OnClickListener(){
            public void  onClick  (View  v){
            	switch(v.getId()){            	
    	    	case R.id.imageHome:
    	    		finish();
    	    		break;
    	    	case R.id.imageNext:
    	    		remedyNext.setFocusable(true);
    	    		if(currIndexOfRemedy!=totalRemedies-1){
    	    		   showUserSubmittedRemedyDetails(userSubmittedRemedy,currIndexOfRemedy+1);   	    			 
    	    		}
    	    		else{
    	    		   remedyNext.setFocusable(false);;
    	    		}
    	    		    		
    	    		break;
    	    	case R.id.imagePrevious:
    	    		remedyPrevious.setFocusable(true);
    	    		if(currIndexOfRemedy!=0){
    	    			showUserSubmittedRemedyDetails(userSubmittedRemedy,currIndexOfRemedy-1);    	                
    	    		}
    	    		else{
    	    			remedyPrevious.setFocusable(false);
    	    		}   	    		
    	    		break;
   	    		case R.id.smalltextView:    	    		
    	    		remedyFont.setTextAppearance(UserSubmittedRemedyDisplayActivity.this, android.R.style.TextAppearance_Small);
    	    		remedyFont.setTextColor(Color.rgb(15, 34, 35));
    	    		break;
    	    	case R.id.mediumtextView:    	    		
    	    		remedyFont.setTextAppearance(UserSubmittedRemedyDisplayActivity.this, android.R.style.TextAppearance_Medium);
    	    		remedyFont.setTextColor(Color.rgb(15, 34, 35));
    	    		break;
    	    	case R.id.largetextView:    	    		
    	    		remedyFont.setTextAppearance(UserSubmittedRemedyDisplayActivity.this, android.R.style.TextAppearance_Large);
    	    		remedyFont.setTextColor(Color.rgb(15, 34, 35));
    	    		break;
        	}	
            }
        };
              
        
        private void showUserSubmittedRemedyDetails(ArrayList<AilmentServerInfoBean> remedyDetailArr,int index){
        	
        	totalRemedies=remedyDetailArr.size();
        	currIndexOfRemedy=index;
        	
        	TextView ailName = (TextView) findViewById(R.id.ailmentNametextView);
            CharSequence userName = "Submitted By: "+remedyDetailArr.get(index).getAilSubmittedBy();
          	ailName.setText(userName);
            TextView textDesc = (TextView) findViewById(R.id.remedyDesctextView);
            CharSequence ailDesc = remedyDetailArr.get(index).getAilDescription();
            textDesc.setText(ailDesc);
        }
   
}