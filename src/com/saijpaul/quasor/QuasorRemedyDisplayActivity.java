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
import java.util.StringTokenizer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class QuasorRemedyDisplayActivity extends Activity{
    
	
	private QuasorDBDAO dbDAOObject;
	String ailmentName=null;
	String ailmentDescription=null;
	String ailmentImage=null;
	String ailmentAllInfo = null;
	int ailmentNum;
    private ImageView homeImage;
    private TextView  smallFont; 
    private TextView  mediumFont;
    private TextView  largeFont;
    private TextView  remedyFont;
    private ImageView remedyPrevious;
    private ImageView remedyNext;
    private ImageView shareRemedy;
    private ImageView addFavoriteImage;
    private int countRemedy = -1;
       
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remedydetails);
        int numAil = getIntent().getIntExtra(GlobalConstant.NUM_OF_AILMENT,0);
        countRemedy = getIntent().getIntExtra(GlobalConstant.REMEDY_COUNT,0);
        String allOrFav = getIntent().getStringExtra(GlobalConstant.ALL_OR_FAVORITE);
        
        dbDAOObject = new QuasorDBDAO(this.getApplicationContext());
        ailmentAllInfo = dbDAOObject.fetchRemedyOfSelectedAilment(numAil);
        
        StringTokenizer tokenizer = new StringTokenizer(ailmentAllInfo,"~");
        while(tokenizer.hasMoreTokens()){
            
        	ailmentNum = Integer.parseInt(tokenizer.nextToken());
        	ailmentName = tokenizer.nextToken();
        	ailmentDescription = tokenizer.nextToken();
        	ailmentImage = tokenizer.nextToken();
        }    
        
        showRemedyInfo(ailmentName,ailmentDescription,ailmentImage);
        
        this.homeImage = (ImageView)this.findViewById(R.id.imageHome);
        this.remedyPrevious = (ImageView)this.findViewById(R.id.imagePrevious);
        this.remedyNext = (ImageView)this.findViewById(R.id.imageNext);
        this.shareRemedy = (ImageView)this.findViewById(R.id.imageShareRemedy);
        this.addFavoriteImage = (ImageView) this.findViewById(R.id.imageFavoriteAdd);
       
        this.smallFont = (TextView)this.findViewById(R.id.smalltextView);
        this.mediumFont = (TextView)this.findViewById(R.id.mediumtextView);
        this.largeFont = (TextView)this.findViewById(R.id.largetextView);
        this.remedyFont = (TextView)this.findViewById(R.id.remedyDesctextView);
        
        
        if(allOrFav.equals(GlobalConstant.ALL)){
        	addFavoriteImage.setVisibility(View.VISIBLE);
        	remedyNext.setVisibility(View.VISIBLE);
        	remedyPrevious.setVisibility(View.VISIBLE);
        }
        else{
        	addFavoriteImage.setVisibility(View.INVISIBLE);
        	remedyNext.setVisibility(View.INVISIBLE);
        	remedyPrevious.setVisibility(View.INVISIBLE);
        }
        
        smallFont.setOnClickListener(clickListener);
        mediumFont.setOnClickListener(clickListener);
        largeFont.setOnClickListener(clickListener);
        homeImage.setOnClickListener(clickListener);
        remedyPrevious.setOnClickListener(clickListener);
        remedyNext.setOnClickListener(clickListener);
        shareRemedy.setOnClickListener(clickListener);
        addFavoriteImage.setOnClickListener(clickListener);
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
	    	case R.id.imageNext:
	    		remedyNext.setFocusable(true);
	    		if(countRemedy!=ailmentNum){
	    			
	    			ailmentAllInfo = dbDAOObject.fetchRemedyOfSelectedAilment(ailmentNum+1);
	    	        
	    	        StringTokenizer tokenizer = new StringTokenizer(ailmentAllInfo,"~");
	    	        while(tokenizer.hasMoreTokens()){	    	            
	    	        	ailmentNum = Integer.parseInt(tokenizer.nextToken());
	    	        	ailmentName = tokenizer.nextToken();
	    	        	ailmentDescription = tokenizer.nextToken();
	    	        	ailmentImage = tokenizer.nextToken();
	    	        }    	    	        
	    	        showRemedyInfo(ailmentName,ailmentDescription,ailmentImage);	    			
	    		}
	    		else{
	    			remedyNext.setFocusable(false);;
	    		}
	    		    		
	    		break;
	    	case R.id.imagePrevious:
	    		remedyPrevious.setFocusable(true);
	    		if(ailmentNum!=0){
	    			ailmentAllInfo = dbDAOObject.fetchRemedyOfSelectedAilment(ailmentNum-1);
	    	        
	    	        StringTokenizer tokenizer = new StringTokenizer(ailmentAllInfo,"~");
	    	        while(tokenizer.hasMoreTokens()){	    	            
	    	        	ailmentNum = Integer.parseInt(tokenizer.nextToken());
	    	        	ailmentName = tokenizer.nextToken();
	    	        	ailmentDescription = tokenizer.nextToken();
	    	        	ailmentImage = tokenizer.nextToken();
	    	        }    	    	        
	    	        showRemedyInfo(ailmentName,ailmentDescription,ailmentImage);	    		}
	    		else{
	    			remedyPrevious.setFocusable(false);
	    		}   	    		
	    		break;
	    	case R.id.imageShareRemedy:
	    		
	    		Intent shareRemedyIntent = new Intent(android.content.Intent.ACTION_SEND);
	    		shareRemedyIntent.setType("text/html");
	    		shareRemedyIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Remedy For: "+ailmentName);
	    		shareRemedyIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml("<p>"+ailmentDescription+"</p>"));
	    		startActivity(Intent.createChooser(shareRemedyIntent, "Email to Family/Friend"));
	    		break;
	    	case R.id.imageFavoriteAdd:	  
	    		boolean bResult =(boolean) dbDAOObject.addRemedyAsFavorite(ailmentNum);
	    		if(bResult==true){
	    			Toast.makeText(getApplicationContext(), "This remedy has been successfully stored in your favorite remedy list.", 
	    					Toast.LENGTH_LONG).show();
	    		}	    		
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
}