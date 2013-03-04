
public class Message {
	private String username;
	private String message;
	
	Message (String usr, String msg) {
	
		this.username = usr;
		this.message = msg;
	}
	
	public void setMessage (String usr, String msg) {
		this.username = usr;
		this.message = msg;
	}
	
	public String getMessage () {
		return this.message;
	}
	
	public String getUsername () {
		return this.username;
	}
	
	public String getAll() {
		return username + ": " + message;
	}
}
