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

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class QuasorRemedyDisplayActivity extends Activity{
    
	
	private SQLiteDatabase dbInstance = null;
	String ailmentName=null;
	String ailmentDescription=null;
	String ailmentImage=null;
	int    ailmentNum;
    private ImageView homeImage;
    private TextView  smallFont; 
    private TextView  mediumFont;
    private TextView  largeFont;
    private TextView  remedyFont;
       
    /** Called when the activity is first created. */
      //  @Override
        public void onCreate(Bundle savedInstanceState) {
        	
            super.onCreate(savedInstanceState);
            setContentView(R.layout.remedydetails);
            int numAil = getIntent().getIntExtra(GlobalConstant.NUM_OF_AILMENT,0);
            
            fetchRemedyOfSelectedAilment(numAil);            
            showRemedyInfo(ailmentName,ailmentDescription,ailmentImage);
	    	
            this.homeImage = (ImageView)this.findViewById(R.id.imageHome);
           
            this.smallFont = (TextView)this.findViewById(R.id.smalltextView);
            this.mediumFont = (TextView)this.findViewById(R.id.mediumtextView);
            this.largeFont = (TextView)this.findViewById(R.id.largetextView);
            this.remedyFont = (TextView)this.findViewById(R.id.remedyDesctextView);
            
            smallFont.setOnClickListener(clickListener);
            mediumFont.setOnClickListener(clickListener);
            largeFont.setOnClickListener(clickListener);
            homeImage.setOnClickListener(clickListener);
        }
        
        View.OnClickListener clickListener = new View.OnClickListener(){
            public void  onClick  (View  v){
            	switch(v.getId()){
            	
    	    	case R.id.smalltextView:
    	    		remedyFont.setTextAppearance(QuasorRemedyDisplayActivity.this, android.R.style.TextAppearance_Small);
    	    		remedyFont.setTextColor(Color.rgb(15, 34, 35));
    	    		break;
    	    	case R.id.mediumtextView:
    	    		remedyFont.setTextAppearance(QuasorRemedyDisplayActivity.this, android.R.style.TextAppearance_Medium);
    	    		remedyFont.setTextColor(Color.rgb(15, 34, 35));
    	    		break;
    	    	case R.id.largetextView:
    	    		remedyFont.setTextAppearance(QuasorRemedyDisplayActivity.this, android.R.style.TextAppearance_Large);
    	    		remedyFont.setTextColor(Color.rgb(15, 34, 35));
    	    		break;
    	    	case R.id.imageHome:
    	    		finish();
    	    		break;
            	}	
            }
        };
            
        private void showRemedyInfo(String aName,String aDesc,String aImage){
        	
        	TextView textName = (TextView) findViewById(R.id.ailmentNametextView);
        	textName.setText(aName);
            TextView textDesc = (TextView) findViewById(R.id.remedyDesctextView);
            textDesc.setText(aDesc);
            String imgFile = "res/drawable/"+aImage;
            ImageView remImage = (ImageView) findViewById(R.id.remedyimageView);
            FileInputStream in;
            BufferedInputStream buf;
            try {
           	    in = new FileInputStream(imgFile);
                buf = new BufferedInputStream(in);
                Bitmap bMap = BitmapFactory.decodeStream(buf);
                remImage.setImageBitmap(bMap);
                if (in != null) {
                     	in.close();
                }
                if (buf != null) {
             	buf.close();
                }
            } catch (Exception e) {
                Log.e("Error reading file", e.toString());
            }
    	}
        
        private void fetchRemedyOfSelectedAilment(int ailNum) {
    		try {
    			
    			QuasorDBHelper dbHelper = new QuasorDBHelper(this.getApplicationContext());
    			dbInstance = dbHelper.getWritableDatabase();
    			Cursor cursorRemedyInfo = dbInstance.rawQuery(GlobalConstant.REMEDY_INFO_SCREEN_SQL+ailNum , null);
    			if (cursorRemedyInfo != null ) {
    	    		if(cursorRemedyInfo.moveToFirst()) {
    	    			do {
    	    				ailmentNum = cursorRemedyInfo.getInt(cursorRemedyInfo.getColumnIndex(GlobalConstant.COLUMN_NUM));
    	    				ailmentName = cursorRemedyInfo.getString(cursorRemedyInfo.getColumnIndex(GlobalConstant.COLUMN_NAME));
    	    				ailmentDescription = cursorRemedyInfo.getString(cursorRemedyInfo.getColumnIndex(GlobalConstant.COLUMN_DESCRIPTION));
    	    				ailmentImage = cursorRemedyInfo.getString(cursorRemedyInfo.getColumnIndex(GlobalConstant.COLUMN_IMAGE));
    	    			}while (cursorRemedyInfo.moveToNext());
    	    		} 
    	    	}
    			cursorRemedyInfo.close();
    		} catch (SQLiteException se ) {
            	Log.e(getClass().getSimpleName(), "Exception encountered in opening the database.");
            } finally {
            	dbInstance.close();
            }
    	}    
}