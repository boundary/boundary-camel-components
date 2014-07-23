/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.boundary.camel.component.ssh;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.boundary.camel.component.common.ServiceResult;

public class SshxResult extends ServiceResult {

	/**
	 * The value of this header is a {@link InputStream} with the standard error
	 * stream of the executable.
	 */
	public static final String STDERR = "CamelSshStderr";

	/**
	 * The value of this header is the exit value that is returned, after the
	 * execution. By convention a non-zero status exit value indicates abnormal
	 * termination. <br>
	 * <b>Note that the exit value is OS dependent.</b>
	 */
	public static final String EXIT_VALUE = "CamelSshExitValue";

	private final String command;

	private final int exitValue;

	private final InputStream stdout;

	private final InputStream stderr;

	public SshxResult(String command, int exitValue, InputStream out,
			InputStream err) {
		this.command = command;
		this.exitValue = exitValue;
		this.stdout = out;
		this.stderr = err;
	}

	public String getCommand() {
		return command;
	}

	public int getExitValue() {
		return exitValue;
	}

	public InputStream getStdout() {
		return stdout;
	}

	public InputStream getStderr() {
		return stderr;
	}

	public String getOutput() {
		InputStreamReader is = new InputStreamReader(stdout);
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(is);
		String read;
		try {
			read = br.readLine();
			while (read != null) {
				sb.append(read);
				read = br.readLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return sb.toString();
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("exitValue=" + exitValue);
		sb.append("command=" + command);
		sb.append("output=" + getOutput());
		return sb.toString();
	}
}
