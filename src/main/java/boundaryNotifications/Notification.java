package boundaryNotifications;

public class Notification {

    private long id;
    private int user_id, timestamp;
    private String message;

    public Notification(long id, int user_id, int timestamp, String message) {
        this.id = id;
        this.user_id = user_id;
        this.timestamp = timestamp;
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
