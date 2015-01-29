package com.boundary.camel.component.ssh;

import java.io.IOException;
import java.util.Arrays;
import java.util.EnumSet;

import org.apache.camel.component.ssh.FileKeyPairProvider;
import org.apache.sshd.SshServer;
import org.apache.sshd.server.PasswordAuthenticator;
import org.apache.sshd.server.command.ScpCommandFactory;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.session.ServerSession;
import org.apache.sshd.server.shell.InvertedShellWrapper;
import org.apache.sshd.server.shell.ProcessShellFactory;
import org.apache.sshd.server.shell.ProcessShellFactory.TtyOptions;

/**
 * TODO: Mock SSH Daemon for testing
 * 
 * @author davidg
 *
 */
public class MockSSHServer {
	
	private static final int DEFAULT_SSH_PORT=22;
	private static final int DEFAULT_SSH_TIMEOUT=5000;

	private SshServer sshd;
	private int port;
	private int timeOut;
	
	/**
	 * TODO Add javadoc
	 *
	 * @author <a href="mailto:dev@mina.apache.org">Apache MINA SSHD Project</a>
	 */
	public class EmptyPasswordAuthenticator implements PasswordAuthenticator {

	    public boolean authenticate(String username, String password, ServerSession session) {
	        return true;
	    }
	}

	public MockSSHServer() {
		this(DEFAULT_SSH_PORT,DEFAULT_SSH_TIMEOUT);
	}
	
	public MockSSHServer(int port) {
		this(port,DEFAULT_SSH_TIMEOUT);
	}
	
	public MockSSHServer(int port,int timeOut) {
		this.port = port;
		this.timeOut = timeOut;
		sshd = SshServer.setUpDefaultServer();
		configure();
	}
	
	private void configure() {
		// Set the port to bind the socket to
		sshd.setPort(port);
        //sshd.getProperties().put(SshServer.IDLE_TIMEOUT,Integer.toString(timeOut));
        sshd.setKeyPairProvider(new FileKeyPairProvider(new String[]{"src/test/resources/hostkey.pem"}));
        //sshd.setKeyPairProvider(Utils.createTestHostKeyProvider());
        //sshd.setSubsystemFactories(Arrays.<NamedFactory<Command>>asList(new SftpSubsystem.Factory()));
		EnumSet<ProcessShellFactory.TtyOptions> options = EnumSet.of(TtyOptions.ONlCr);
		sshd.setShellFactory(new ProcessShellFactory(new String[] { "/bin/bash", "-i","-l"},options));
		// sshd.setShellFactory(new InvertedShellWrapper(new String[] { "/bin/bash", "-i","-l"}));

        //sshd.setShellFactory(new EchoShellFactory());
        sshd.setPasswordAuthenticator(new EmptyPasswordAuthenticator());
		sshd.setCommandFactory(new ScpCommandFactory());
	}
	
	public void start() {
		try {
			sshd.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void stop() {
		try {
			sshd.stop(true);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		MockSSHServer server = new MockSSHServer(12345);
		server.start();
	}
}
