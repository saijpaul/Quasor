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

import java.io.Serializable;

public class AilmentInfoBean implements Serializable{

	private int ailmentNum = GlobalConstant.INVALID_AILMENT_NUM;
	private String ailmentName = null;
	
	public int getAilmentNum() {
		return ailmentNum;
	}
	public void setAilmentNum(int ailmentNum) {
		this.ailmentNum = ailmentNum;
	}
	public String getAilmentName() {
		return ailmentName;
	}
	public void setAilmentName(String ailmentName) {
		this.ailmentName = ailmentName;
	}
}
