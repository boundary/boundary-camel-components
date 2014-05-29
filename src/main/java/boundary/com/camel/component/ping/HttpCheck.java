package boundary.com.camel.component.ping;

public class HttpCheck implements ServiceCheck {
	
	public HttpCheck() {
		
	}
	
	public HttpStatus performCheck() {
		HttpStatus status = new HttpStatus();
		return status;
	}
}
