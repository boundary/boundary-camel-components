package boundary.com.camel.component.ping;

public abstract class ServiceStatus {
	
	Status status = Status.SUCCESS;
	
	void setStatus(Status status) {
		this.status = Status.SUCCESS;
	}
}
