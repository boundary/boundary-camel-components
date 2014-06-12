package com.boundary.camel.component.url;

import java.net.URI;

import com.boundary.camel.component.common.ServiceInfo;

public class UrlInfo extends ServiceInfo {
	
	private URLStatus urlStatus;
	private URI uri;
	private String url;

	UrlInfo() {
	}

	public void setURLStatus(URLStatus urlStatus) {
		this.urlStatus = urlStatus;
	}
	
	public URLStatus getURLStatus() {
		return this.urlStatus;
	}
	
	public void setURI(URI uri) {
		this.uri = uri;
	}
	
	public URI getURI() {
		return uri;
	}
	
	public void setURL(String url) {
		this.url = url;
	}
	
	public String getURL() {
		return url;
	}

	
	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append(",url=" + getURL());
		s.append(",timeout=" + getTimeout());
		s.append(",urlStatus=" + (getURLStatus() == null ? "" : getURLStatus().toString()));
		
		return s.toString();
	}
}
