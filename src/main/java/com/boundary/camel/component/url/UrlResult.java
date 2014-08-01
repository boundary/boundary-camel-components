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
import static com.boundary.camel.component.url.UrlStatus.*;

public class UrlResult extends ServiceResult {
	
	private UrlStatus urlStatus;
	private URL url;
	private long responseTime;
	private int responseCode;
	private String responseBody;
	private String requestMethod;


	UrlResult() {
		urlStatus = OK;
	}

	public void setURLStatus(UrlStatus urlStatus) {
		this.urlStatus = urlStatus;
	}
	
	public UrlStatus getURLStatus() {
		return this.urlStatus;
	}
	
	public void setURL(URL url) {
		this.url = url;
	}
	
	public URL getURL() {
		return url;
	}

	public long getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(long responseTime) {
		this.responseTime = responseTime;
	}

	public String getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	
	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	@Override
	public String toString() {
		return "UrlResult [urlStatus=" + urlStatus + ", url=" + url
				+ ", responseTime=" + responseTime + ", responseCode="
				+ responseCode + ", responseBody=" + responseBody
				+ ", requestMethod=" + requestMethod + "]";
	}
}
