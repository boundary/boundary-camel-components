package boundary.com.camel.component.ping;

public class SocketCheck implements ServiceCheck {
	
	public SocketCheck() {
		
	}

	public SocketStatus performCheck() {
		SocketStatus status = new SocketStatus();
		return status;
	}
}
