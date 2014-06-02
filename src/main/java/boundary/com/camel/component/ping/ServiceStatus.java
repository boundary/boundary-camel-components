package boundary.com.camel.component.ping;

public abstract class ServiceStatus {
	
	private Status status;
	private String message;
	
	public ServiceStatus() {
		status = Status.SUCCESS;
		message = "OK";
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public Status getStatus() {
		return this.status;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
}
