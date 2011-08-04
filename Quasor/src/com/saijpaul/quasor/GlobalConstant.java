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
	public static final String DISCLAIMER_TEXT = "DISCLAIMER: This Application is built for educational/informational purposes" +
												 " only and Author takes no responsibility for any of the content in the application " +
												 "and is no way responsible for any impact caused physically or otherwise to the end " +
												 "user. All medical ailments needs to be treated by trained medical professional only " +
												 "and this application is not a substitute for professional treatment/medication.The " +
												 "remedies contained herein are not FDA approved. This software is provided 'AS IS' and " +
												 "developer assumes no liability for any consequences arising out of usage of software. ";
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
	public static final String USER_SUBMITTED_AILMENT_LIST = "http://10.0.2.2/getAllAilment.php";
	public static final String USER_SUBMITTED_REMEDY_DETAIL = "http://10.0.2.2/getAllRemedies.php";
	public static final String PARSING_DATA_ERROR = "Error encountered in parsing of data";
	public static final String USER_AILMENT_NAME = "userAilmentName";
	public static final String USER_COLUMN_AILMENT_NAME = "ailmentName";
	public static final String USER_COLUMN_AILMENT_NUM = "num";
	public static final String USER_COLUMN_REMEDY_DESC = "remedyDescription";
	public static final String USER_COLUMN_MODERATOR_APPROVAL = "moderatorApproval";
	public static final String USER_COLUMN_SUBMITTED_BY = "submittedBy";    
	public static final String aN_TO_FETCH_REMEDIES = "aN";
	public static final String HTTP_CONNECTION_ERROR = "Error encountered in http connection";
	public static final String CONVERSION_ERROR = "Error encountered in conversion";
	public static final String SERVER_CLASS_NAME = "QuasorServerHelper";
	public static final String AILMENT_NAME_TO_STORE_REMEDY = "ailmentN";
	public static final String REMEDY_DESC_TO_STORE = "remedyD";
	public static final String SUBMITTED_BY_USER_NAME_TO_STORE = "submittedBy";
	public static final String DATA_STORAGE_TO_SERVER_ERROR = "Error in http connection or problem in inserting data in database ";
	public static final String STORE_USER_SUBMITTED_REMEDY_DETAIL = "http://10.0.2.2/storeUsersRemedy.php";
}
