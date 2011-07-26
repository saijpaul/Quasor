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
import android.text.Editable;
import android.text.Html;

import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class QuasorMainAilmentListActivity extends ListActivity{
    
	private AilmentAdapter ailmentAdapter = null;
	private ImageView filterImage = null;
	private EditText textFilter = null;
	TextWatcher textFilterWatcher = null;
	private ImageView shareRemedy;
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.mainailmentlist);
	    ArrayList<AilmentInfoBean> ailmentList = (ArrayList<AilmentInfoBean>) getIntent().getSerializableExtra(GlobalConstant.AILMENT_ARRAY);
	    showAilmentList(ailmentList);
	    
	    this.filterImage = (ImageView)this.findViewById(R.id.imageOK);
	    this.shareRemedy = (ImageView)this.findViewById(R.id.imageShareApp);
	    
	    filterImage.setOnClickListener(clickListener);
	    shareRemedy.setOnClickListener(clickListener);
	    
	    textFilter = (EditText) findViewById(R.id.searchbox);
	    textFilter.addTextChangedListener(textFilterWatcher);
	}
        
    View.OnClickListener clickListener = new View.OnClickListener(){
        public void  onClick  (View  v){
        	switch(v.getId()){
        	
	    	case R.id.imageOK:
	    		
	    		InputMethodManager iMethodMgr = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
	    		iMethodMgr.hideSoftInputFromWindow(textFilter.getWindowToken(), 0);
	    		break;
	    	case R.id.imageShareApp:
	    		
	    		Intent shareAppIntent = new Intent(Intent.ACTION_SEND);
	    		shareAppIntent.setType("text/html");
	    		shareAppIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Hey, Try this app regarding simple home remedies");
	    		shareAppIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml("<p>Try this app: [Market link]</p>"));
	    		startActivity(Intent.createChooser(shareAppIntent,"Email to Family/Friend"));
	    		break;
	    	}	
        }
    };
    
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
            	msg.putExtra(GlobalConstant.REMEDY_COUNT, getListAdapter().getCount());
            	QuasorMainAilmentListActivity.this.startActivity(msg);
            }
          });
        
        textFilterWatcher = new TextWatcher() {
        	
           public void onTextChanged(CharSequence s, int start, int before,
                    int count) {
            	ailmentAdapter.getFilter().filter(s);
            	ailmentAdapter.notifyDataSetChanged();
         
            }

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
	
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
        };

	}
    	

	private class AilmentAdapter extends ArrayAdapter<AilmentInfoBean> implements Filterable{

		public ArrayList<AilmentInfoBean> ailmentArray;
        public ArrayList<AilmentInfoBean> ailArray;
        private AilmentsFilter ailmentFilter = null;
        public final Object lock = new Object();
        
        public AilmentAdapter(Context context, int textViewResourceId, ArrayList<AilmentInfoBean> objects) {
        	    super(context, textViewResourceId, objects);
                this.ailmentArray = objects;
                this.ailArray = objects;
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
        
        @Override
        public int getCount() {
            return ailmentArray.size();
        }
 
        @Override
        public AilmentInfoBean getItem(int position) {
            return ailmentArray.get(position);
        }
 
        @Override
        public int getPosition(AilmentInfoBean item) {
            return ailmentArray.indexOf(item);
        }
 
        @Override
        public long getItemId(int position) {
            return position;
        }
        
        public Filter getFilter() {
            if (ailmentFilter == null) {
            	ailmentFilter = new AilmentsFilter();
            }
            return ailmentFilter;
        }
        
        private class AilmentsFilter extends Filter {
            
        	protected FilterResults performFiltering(CharSequence cs) {
                
                FilterResults filteredAilmentRes = new FilterResults();
 
                if (ailmentArray == null) {                	
                    synchronized (lock){
                    	ailmentArray = new ArrayList<AilmentInfoBean>(ailArray);
                    }
                }
 
                if (cs == null || cs.length() == 0) {
                    synchronized (lock) {
                    	filteredAilmentRes.values = ailArray;
                    	filteredAilmentRes.count = ailArray.size();
                    }
                } 
                else{
                    
                    final ArrayList<AilmentInfoBean> ailments = ailArray;
                    final int ailmentCount = ailments.size();
                    final ArrayList<AilmentInfoBean> newList = new ArrayList<AilmentInfoBean>(ailmentCount);
                    String prefixOfAilment = cs.toString().toLowerCase();
 
                    for (int k = 0; k < ailmentCount; k++) {
                    	
                    	AilmentInfoBean element = ailments.get(k);
                        final String itemName = element.getAilmentName().toLowerCase();
 
                        if (itemName.startsWith(prefixOfAilment)) {
                        	newList.add(element);
                        } 
                        else{                        	
                        }
                    }
 
                    filteredAilmentRes.values = newList;
                    filteredAilmentRes.count = newList.size();
                }
 
                return filteredAilmentRes;
            }
 
            protected void publishResults(CharSequence cs, FilterResults filterRes) {
                
            	ailmentArray = (ArrayList<AilmentInfoBean>) filterRes.values;
           
                if (filterRes.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        }

   }
	
	@Override
    protected void onDestroy() {
        super.onDestroy();
        textFilter.removeTextChangedListener(textFilterWatcher);
	}
}