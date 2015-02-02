package boundaryNotifications;

public class Notification {

    private long id;
    private int user_id, timestamp, is_read;
    private String message;

	public Notification() {}

	public Notification(long id, int user_id, int timestamp, int is_read, String message) {
		this.id = id;
		this.user_id = user_id;
		this.timestamp = timestamp;
		this.is_read = is_read;
		this.message = message;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getUser_id() {
		return user_id;
	}

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

	public int getTimestamp() {
		return timestamp;
	}

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

	public int getIs_read() {
		return is_read;
	}

    public void setIs_read(int is_read) {
        this.is_read = is_read;
    }

	public String getMessage() {
		return message;
	}

    public void setMessage(String message) {
        this.message = message;
    }
}
