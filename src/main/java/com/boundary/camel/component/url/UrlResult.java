// Copyright 2014 Boundary, Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.boundary.camel.component.url;

import java.net.URL;

import com.boundary.camel.component.common.ServiceResult;

public class UrlResult extends ServiceResult {
	
	private URLStatus urlStatus;
	private URL url;
	private long elapsedTime;
	private int responseCode;

	UrlResult() {
	}

	public void setURLStatus(URLStatus urlStatus) {
		this.urlStatus = urlStatus;
	}
	
	public URLStatus getURLStatus() {
		return this.urlStatus;
	}
	
	public void setURL(URL url) {
		this.url = url;
	}
	
	public URL getURL() {
		return url;
	}

	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append(",url=" + getURL());
		s.append(",timeout=" + getTimeout());
		s.append(",urlStatus=" + (getURLStatus() == null ? "" : getURLStatus().toString()));
		
		return s.toString();
	}

	public long getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
}
