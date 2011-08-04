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

public class AilmentServerInfoBean{

	private int ailNum = -1;
	private String ailName = null;
	private String ailDescription = null;
	private String ailModerator = null;
	private String ailSubmittedBy = null;
	
	public String getAilDescription() {
		return ailDescription;
	}
	public void setAilDescription(String ailDescription) {
		this.ailDescription = ailDescription;
	}
	public String getAilModerator() {
		return ailModerator;
	}
	public void setAilModerator(String ailModerator) {
		this.ailModerator = ailModerator;
	}
	public String getAilSubmittedBy() {
		return ailSubmittedBy;
	}
	public void setAilSubmittedBy(String ailSubmittedBy) {
		this.ailSubmittedBy = ailSubmittedBy;
	}
		
	public int getAilmentNum() {
		return ailNum;
	}
	public void setAilmentNum(int ailmentNum) {
		this.ailNum = ailmentNum;
	}
	public String getAilmentName() {
		return ailName;
	}
	public void setAilmentName(String ailmentName) {
		this.ailName = ailmentName;
	}	
}
