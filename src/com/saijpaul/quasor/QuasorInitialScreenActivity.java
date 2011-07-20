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
import android.util.Log;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;

public class QuasorInitialScreenActivity extends Activity {
			
		private SQLiteDatabase dbInstance = null;
	    
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.splashscreen);
	        new DownloadTask().execute("");
	    }
	    
	    private class DownloadTask extends AsyncTask<String, Void, ArrayList<AilmentInfoBean>> {
	    	
	    	@Override
			protected ArrayList<AilmentInfoBean> doInBackground(String... params) {
				
	    		ArrayList<AilmentInfoBean> ailmentArray = fetchAllAilmentName();
	    		return ailmentArray;
			}
	    	
	    	@Override
	         protected void onPostExecute(ArrayList<AilmentInfoBean> result) {
	        	         	 
	        	final Intent msg = new Intent(QuasorInitialScreenActivity.this,QuasorMainAilmentListActivity.class);
	        	msg.putExtra(GlobalConstant.AILMENT_ARRAY, result);
	        	QuasorInitialScreenActivity.this.startActivity(msg);
	        	QuasorInitialScreenActivity.this.finish();
		    }
		
	    }
	    
	    private ArrayList<AilmentInfoBean> fetchAllAilmentName() {

	    	ArrayList<AilmentInfoBean> ailmentNameArray = new ArrayList<AilmentInfoBean>();
	    	AilmentInfoBean ailmentInfo = null;
			try {
				
				QuasorDBHelper dbHelper = new QuasorDBHelper(this.getApplicationContext());
				dbInstance = dbHelper.getWritableDatabase();
				Cursor cursorAilmentList = dbInstance.rawQuery(GlobalConstant.INITIAL_SCREEN_AILMENT_LIST_SQL , null);
				
		    	if (cursorAilmentList != null ) {
		    		
		    		if  (cursorAilmentList.moveToFirst()) {
		    			do {
		    				int ailmentNum = cursorAilmentList.getInt(cursorAilmentList.getColumnIndex(GlobalConstant.AILMENT_NUM));
		    				String ailmentName = cursorAilmentList.getString(cursorAilmentList.getColumnIndex(GlobalConstant.AILMENT_NAME));
		    				ailmentInfo = new AilmentInfoBean();
		    				ailmentInfo.setAilmentNum(ailmentNum);
		    				ailmentInfo.setAilmentName(ailmentName);
		    				ailmentNameArray.add(ailmentInfo);
		    			}while (cursorAilmentList.moveToNext());
		    		} 
		    	}
		    	cursorAilmentList.close();
		    	dbInstance.close();
	        	return ailmentNameArray;
			} catch (SQLiteException se ) {
	        	Log.e(getClass().getSimpleName(), "Exception encountered in opening the database.");
	        	dbInstance.close();
	        	return null;
	        }
		}	
}
