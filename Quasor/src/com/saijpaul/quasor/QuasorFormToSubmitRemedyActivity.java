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

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class QuasorFormToSubmitRemedyActivity extends Activity{
   
	private Spinner   ailmentSpinner;
	private EditText  remedyText;
	private EditText  nameOfSubmitter;
	private Button  submitButton;
	private ImageView  homeImage;
	
    //  @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.submitremedyform);
       
        this.remedyText = (EditText) findViewById(R.id.edittextRemedyBody);
        this.nameOfSubmitter = (EditText) findViewById(R.id.edittextName);
        this.submitButton = (Button) findViewById(R.id.submitbt);
        this.homeImage = (ImageView) findViewById(R.id.imageHome);
     
        this.ailmentSpinner = (Spinner) findViewById(R.id.ailmentListSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, 
        			 R.array.ailment_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ailmentSpinner.setAdapter(adapter);
        homeImage.setOnClickListener(clickListener);
        submitButton.setOnClickListener(clickListener);           
    }
    
    View.OnClickListener clickListener = new View.OnClickListener(){
        public void  onClick  (View  v){
        	switch(v.getId()){
        	case R.id.imageHome:
	    		finish();
	    		break;
	    	case R.id.submitbt:
	    		String name = nameOfSubmitter.getText().toString();
	    		String remedy = remedyText.getText().toString();
	    		String spinnertext = ailmentSpinner.getSelectedItem().toString();
	    		QuasorServerHelper.sendDatatoServer(GlobalConstant.STORE_USER_SUBMITTED_REMEDY_DETAIL,name,remedy,spinnertext);
	    		
	    		Toast.makeText(getApplicationContext(), "Data Stored successfully,please wait for moderator approval for it to " +
	    				"come online", Toast.LENGTH_SHORT).show();
	    		
	    		finish();    	    		
	    		break;
        	}	
        }
    };   
}