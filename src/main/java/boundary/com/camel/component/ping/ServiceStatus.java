package boundary.com.camel.component.ping;

public interface ServiceStatus {
	
	Status status = Status.UP;
	
	public enum Status {
		UP,
		DOWN
	}
}
