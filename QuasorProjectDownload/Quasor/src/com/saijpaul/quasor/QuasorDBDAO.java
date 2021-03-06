/*******************************************************************
* Copyrightę 2011 Seema Saijpaul 
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
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;


public class QuasorDBDAO {
	
	private Context currContext;
	private static QuasorDBHelper dbHelper=null;
	private SQLiteDatabase dbInstance = null;
	private String ailmentAllInfo = null;
	private String ailmentName=null;
	private String ailmentDescription=null;
	private String ailmentImage=null;
	private int ailmentNum;
	
	public QuasorDBDAO(Context context) {
		
		this.currContext = context;
		
	}
	
	public QuasorDBHelper getDBHelperInstance(){
	
		if(dbHelper == null){
			dbHelper = new QuasorDBHelper(currContext);
		}
		
		return dbHelper;
	}
	
	public ArrayList<AilmentInfoBean> fetchAllAilmentName() {

    	ArrayList<AilmentInfoBean> ailmentNameArray = new ArrayList<AilmentInfoBean>();
    	AilmentInfoBean ailmentInfo = null;
		try {
			dbInstance = getDBHelperInstance().getWritableDatabase();
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
        	Log.e(getClass().getSimpleName(), GlobalConstant.SQL_EXCEPTION_MESSAGE);
        	dbInstance.close();
        	return null;
        }
	}
	
	public String fetchRemedyOfSelectedAilment(int ailNum) {
		try {
			dbInstance = getDBHelperInstance().getWritableDatabase();
			Cursor cursorRemedyInfo = dbInstance.rawQuery(GlobalConstant.REMEDY_INFO_SCREEN_SQL+ailNum , null);
			if (cursorRemedyInfo != null ) {
	    		if(cursorRemedyInfo.moveToFirst()) {
	    			do {
	    				ailmentNum = cursorRemedyInfo.getInt(cursorRemedyInfo.getColumnIndex(GlobalConstant.COLUMN_NUM));
	    				ailmentName = cursorRemedyInfo.getString(cursorRemedyInfo.getColumnIndex(GlobalConstant.COLUMN_NAME));
	    				ailmentDescription = cursorRemedyInfo.getString(cursorRemedyInfo.getColumnIndex(GlobalConstant.COLUMN_DESCRIPTION));
	    				ailmentImage = cursorRemedyInfo.getString(cursorRemedyInfo.getColumnIndex(GlobalConstant.COLUMN_IMAGE));
	    				
	    				ailmentAllInfo = ailmentNum+"~"+ailmentName+"~"+ailmentDescription+"~"+ailmentImage;
	    			}while (cursorRemedyInfo.moveToNext());
	    		} 
	    	}
			cursorRemedyInfo.close();
			dbInstance.close();
			return ailmentAllInfo;
		} catch (SQLiteException se ) {
        	Log.e(getClass().getSimpleName(), GlobalConstant.SQL_EXCEPTION_MESSAGE);
        	dbInstance.close();
        	return null;
        }
	}
    public boolean addRemedyAsFavorite(int ailmentnum) {
		try {
			dbInstance = getDBHelperInstance().getWritableDatabase();
			int favoriteVal = GlobalConstant.FAVORITE_REMEDY_YES;
			String UPDATE_SQL = GlobalConstant.UPDATE_FAVORITE_VALUE_FIRST_PART+favoriteVal+GlobalConstant.UPDATE_FAVORITE_VALUE_SECOND_PART+ailmentNum;
			dbInstance.execSQL(UPDATE_SQL);
			dbInstance.close();
			return true;
		} catch (SQLiteException se ) {
			Log.e(getClass().getSimpleName(), GlobalConstant.SQL_EXCEPTION_MESSAGE);
			return false;
        } 
	}
    
    public ArrayList<AilmentInfoBean> fetchAllFavoriteAilmentName() {
    	
    	ArrayList<AilmentInfoBean> ailmentNameArray = new ArrayList<AilmentInfoBean>();
    	AilmentInfoBean ailmentInfo = null;
		try {
			
			dbInstance = getDBHelperInstance().getWritableDatabase();
			Cursor cursorFavoriteAilment = dbInstance.rawQuery(GlobalConstant.FAVORITE_AILMENT_LIST_SQL , null);
			
	    	if (cursorFavoriteAilment != null ) {
	    		
	    		if  (cursorFavoriteAilment.moveToFirst()) {
	    			do {  				
	    				int ailmentNum = cursorFavoriteAilment.getInt(cursorFavoriteAilment.getColumnIndex(GlobalConstant.AILMENT_NUM));
	    				String ailmentName = cursorFavoriteAilment.getString(cursorFavoriteAilment.getColumnIndex(GlobalConstant.AILMENT_NAME));
	    				
	    				ailmentInfo = new AilmentInfoBean();
	    				ailmentInfo.setAilmentNum(ailmentNum);
	    				ailmentInfo.setAilmentName(ailmentName);
	    				ailmentNameArray.add(ailmentInfo);
	    			}while (cursorFavoriteAilment.moveToNext());
	    		} 
	    	}
	    	cursorFavoriteAilment.close();
	    	dbInstance.close();
	    	return ailmentNameArray;
		} catch (SQLiteException se ) {
        	Log.e(getClass().getSimpleName(), GlobalConstant.SQL_EXCEPTION_MESSAGE);
        	dbInstance.close();
        	return null;
        }

	}
    public boolean removeRemedyAsFavorite(ArrayList<Integer> removeFavorite) {
		try {
			dbInstance = getDBHelperInstance().getWritableDatabase();
			int notFavoriteVal = GlobalConstant.FAVORITE_REMEDY_NO;
			String UPDATE_SQL = null;
			
			for(int k =0; k< removeFavorite.size();k++){
				Integer num = removeFavorite.get(k);
				UPDATE_SQL = GlobalConstant.UPDATE_FAVORITE_VALUE_FIRST_PART+notFavoriteVal+GlobalConstant.UPDATE_FAVORITE_VALUE_SECOND_PART+num;
				dbInstance.execSQL(UPDATE_SQL);
			}			
			dbInstance.close();
			return true;
		} catch (SQLiteException se ) {
			Log.e(getClass().getSimpleName(), GlobalConstant.SQL_EXCEPTION_MESSAGE);
			dbInstance.close();
			return false;
        } 
	}
}