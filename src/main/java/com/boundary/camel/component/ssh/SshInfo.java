package com.boundary.camel.component.ssh;

import com.boundary.camel.component.common.ServiceInfo;

/**
 * 
 * @author davidg
 *
 */
public class SshxInfo extends ServiceInfo {
	
	private String command;
	private String expectedOutput;

	public SshxInfo() {
		// TODO Auto-generated constructor stub
	}
	
	public String getCommand() {
		return command;
	}

	public String getExpectedOutput() {
		return expectedOutput;
	}

	public void setCommand(String command) {
		this.command = command;
	}
	
	public void setExpectedOutput(String expectedOutput) {
		this.expectedOutput = expectedOutput;
	}

	@Override
	public String toString() {
		return "SsshxInfo [expectedOutput=" + expectedOutput + "]";
	}



}
