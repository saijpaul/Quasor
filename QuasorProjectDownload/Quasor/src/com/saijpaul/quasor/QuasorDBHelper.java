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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class QuasorDBHelper extends SQLiteOpenHelper{
	
	public String DBPath;
	public static Context currentContext;
	
	public QuasorDBHelper(Context context) {
		super(context, GlobalConstant.DATABASE_NAME, null, GlobalConstant.VERSION);
		currentContext = context;
		DBPath = GlobalConstant.DATABASE_PATH;
		createDBFromAssetFolderDB();
	}

	private void createDBFromAssetFolderDB() {
		
		if (checkQuasorDBExistence()) {
			// database already present
		} else {
			
			this.getReadableDatabase();
		
			try {
    	    	InputStream quasorDBFromAssests = currentContext.getAssets().open(GlobalConstant.DATABASE_NAME);
    	 
    	    	OutputStream currentQuasorDB = new FileOutputStream(DBPath+GlobalConstant.DATABASE_NAME);
    	 
    	    	byte[] buffer = new byte[1024];
    	    	int length;
    	    	while ((length = quasorDBFromAssests.read(buffer))>0){
    	    		currentQuasorDB.write(buffer, 0, length);
    	    	}
    	 
    	    	currentQuasorDB.flush();
    	    	currentQuasorDB.close();
    	    	quasorDBFromAssests.close();
	 
			} catch (IOException e) {
	 
				Log.e(getClass().getSimpleName(), "Exception encountered in copying the database.");
	 
			}	
			
		}		
	}

	private boolean checkQuasorDBExistence() {
		
		SQLiteDatabase chkDBExists = null;

		try {
			String quasorDBPath = DBPath + GlobalConstant.DATABASE_NAME;
			chkDBExists = SQLiteDatabase.openDatabase(quasorDBPath, null,SQLiteDatabase.OPEN_READONLY);

		} catch (SQLiteException e) {
			Log.e(getClass().getSimpleName(), "Database doesnot exist.");
		}

		if (chkDBExists != null) {
			chkDBExists.close();
			return true;
		}
		return false;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
}
