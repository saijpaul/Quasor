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

public class GlobalConstant {

	public static final String AILMENT_ARRAY = "ailmentArray";
	public static final String INITIAL_SCREEN_AILMENT_LIST_SQL = "SELECT ailmentNum,ailmentName FROM quasor_ailment_remedy";
	public static final String AILMENT_NUM = "ailmentNum";
	public static final String AILMENT_NAME = "ailmentName";
	public static final int INVALID_AILMENT_NUM = -1;
	public static final String DATABASE_NAME = "quasorDB.db";
	public static final int VERSION = 1;
	public static final String TABLE_NAME = "quasor_ailment_remedy";
	public static final String DATABASE_PATH = "/data/data/com.saijpaul.quasor/databases/";
	public static final String ACCEPT_BUTTON_TEXT = "I Accept";
	public static final String DECLINE_BUTTON_TEXT = "I Decline";
	public static final String DISCLAIMER_TEXT = "DISCLAIMER: message to be updated soon.";
	public static final String NUM_OF_AILMENT = "ailNum";
	public static final String REMEDY_INFO_SCREEN_SQL = "SELECT ailmentNum,ailmentName,ailmentDescription,ailmentImage FROM quasor_ailment_remedy Where ailmentNum=";
	public static final String COLUMN_NUM = "ailmentNum";
	public static final String COLUMN_NAME = "ailmentName";
	public static final String COLUMN_IMAGE = "ailmentImage";
	public static final String COLUMN_DESCRIPTION = "ailmentDescription";
	public static final String REMEDY_COUNT = "countR";
	public static final int FAVORITE_REMEDY_YES = 11;
	public static final int FAVORITE_REMEDY_NO = 00;
	public static final String UPDATE_FAVORITE_VALUE_FIRST_PART = "UPDATE quasor_ailment_remedy SET favoriteRemedy =";
	public static final String UPDATE_FAVORITE_VALUE_SECOND_PART = " WHERE ailmentNum =";
	public static final String FAVORITE_AILMENT_LIST_SQL = "SELECT ailmentNum,ailmentName FROM quasor_ailment_remedy where favoriteRemedy=11";
	public static final String SQL_EXCEPTION_MESSAGE = "Exception encountered in opening the database or executing the query";
	public static final String ALL_OR_FAVORITE = "AllOrFavorite";
	public static final String FAVORITE = "Favorite";
	public static final String ALL = "All";
}
