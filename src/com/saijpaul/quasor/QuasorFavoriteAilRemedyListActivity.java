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
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class QuasorFavoriteAilRemedyListActivity extends ListActivity{
    
	private AilmentAdapter ailmentAdapter = null;
	private ImageView homeImage = null;
	private ImageView removeFavoriteImage = null;
	private ImageView filterImage = null;
	private EditText textFilter = null;
	TextWatcher textFilterWatcher = null;
	private CheckBox checkToRemove;
	ArrayList<Integer> removeResults = new ArrayList<Integer>();
    private QuasorDBDAO dbDAOObject;
    
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favoriteailmentlist);
        dbDAOObject = new QuasorDBDAO(this.getApplicationContext());
        ArrayList<AilmentInfoBean> favList = dbDAOObject.fetchAllFavoriteAilmentName();
        showFavoriteAilmentList(favList);
        
        this.homeImage = (ImageView)this.findViewById(R.id.imageHome);
        this.removeFavoriteImage = (ImageView)this.findViewById(R.id.imageFavoriteRemove);
        this.filterImage = (ImageView)this.findViewById(R.id.imageOK);                
        this.textFilter = (EditText) findViewById(R.id.searchbox);
        
        textFilter.addTextChangedListener(textFilterWatcher);
        removeFavoriteImage.setOnClickListener(clickListener);
        homeImage.setOnClickListener(clickListener);
        filterImage.setOnClickListener(clickListener);
    }
    
    View.OnClickListener clickListener = new View.OnClickListener(){
        public void  onClick  (View  v){
        	switch(v.getId()){     	
	    	
	    	case R.id.imageOK:
	    		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
	    		imm.hideSoftInputFromWindow(textFilter.getWindowToken(), 0);	    		
	    		break;
	    	case R.id.imageHome:
	    		finish();		
	    		break;
	    	case R.id.imageFavoriteRemove:
	    		boolean bRes = dbDAOObject.removeRemedyAsFavorite(removeResults);
	    		if(bRes==true){
	    			Toast.makeText(getApplicationContext(), "The selected remedy/remedies have been successfully removed from your favorite remedy list.", 
	    					Toast.LENGTH_LONG).show();
	    		}
	    		finish();
	    		break;	    		
        	}	
        }
    };
    
    
	private void showFavoriteAilmentList(ArrayList<AilmentInfoBean> favAilmentList) {
		this.ailmentAdapter = new AilmentAdapter(this, R.layout.favoriteaillistview, favAilmentList);
	    setListAdapter(this.ailmentAdapter);
        getListView().setTextFilterEnabled(true);
    
        getListView().setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            	AilmentInfoBean aInfo = (AilmentInfoBean)getListAdapter().getItem(position);
            	Intent msg = new Intent(QuasorFavoriteAilRemedyListActivity.this,QuasorRemedyDisplayActivity.class);
            	msg.putExtra(GlobalConstant.NUM_OF_AILMENT, aInfo.getAilmentNum());
            	msg.putExtra(GlobalConstant.ALL_OR_FAVORITE, GlobalConstant.FAVORITE);
            	QuasorFavoriteAilRemedyListActivity.this.startActivity(msg);
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
                    view = vi.inflate(R.layout.favoriteaillistview, null);
                }
              
                AilmentInfoBean ailmentInfoBeanObject = ailmentArray.get(position);
                if (ailmentInfoBeanObject != null) {
                    TextView textview = (TextView) view.findViewById(R.id.text);
                    
                    if (textview != null) {
                    	textview.setText(ailmentInfoBeanObject.getAilmentName());                           
                    }
                }
                
                checkToRemove = (CheckBox) view.findViewById(R.id.checkBoxFav);
                final AilmentInfoBean removeFavAilment = ailmentArray.get(position);
                checkToRemove.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                      
                        if (((CheckBox) v).isChecked()) {
                             removeResults.add(removeFavAilment.getAilmentNum());
                        }
                        else{
                        	for(int k =0; k< removeResults.size();k++){
                				Integer num = removeResults.get(k);
                				if(num==removeFavAilment.getAilmentNum()){
                					removeResults.remove(num);
                				}                				
                			}
                        }                        
                    }
                });
                
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