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

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;


public class QuasorMainAilmentListActivity extends ListActivity{
    
	private AilmentAdapter ailmentAdapter = null;
			
    @Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.mainailmentlist);
	    ArrayList<AilmentInfoBean> ailmentList = (ArrayList<AilmentInfoBean>) getIntent().getSerializableExtra(GlobalConstant.AILMENT_ARRAY);
	    showAilmentList(ailmentList);	    
	}
        
        
	private void showAilmentList(ArrayList<AilmentInfoBean> ailmentList) {
				
		this.ailmentAdapter = new AilmentAdapter(this, R.layout.ailmentlistview, ailmentList);
		setListAdapter(this.ailmentAdapter);
        getListView().setTextFilterEnabled(true);
       
        getListView().setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            	
            	AilmentInfoBean infoBean = (AilmentInfoBean)getListAdapter().getItem(position);
            	int num = infoBean.getAilmentNum();
            	Intent msg = new Intent(QuasorMainAilmentListActivity.this,QuasorRemedyDisplayActivity.class);
            	msg.putExtra(GlobalConstant.NUM_OF_AILMENT, num);
            	QuasorMainAilmentListActivity.this.startActivity(msg);
            }
          });
        }
    	

	private class AilmentAdapter extends ArrayAdapter<AilmentInfoBean>{

		public ArrayList<AilmentInfoBean> ailmentArray;
        public ArrayList<AilmentInfoBean> itemArray;
        
        public AilmentAdapter(Context context, int textViewResourceId, ArrayList<AilmentInfoBean> objects) {
        	    super(context, textViewResourceId, objects);
                this.ailmentArray = objects;
                this.itemArray = objects;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
        	
        	View view = convertView;
                if (view == null) {
                    LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view = vi.inflate(R.layout.ailmentlistview, null);
                }
              
                AilmentInfoBean ailmentInfoBeanObject = ailmentArray.get(position);

            if (ailmentInfoBeanObject != null) {
                    TextView textview = (TextView) view.findViewById(R.id.text);
                    
                    if (textview != null) {
                    	textview.setText(ailmentInfoBeanObject.getAilmentName());                           
                    }
            }
            
			
            return view;
        } 
   }
 
}