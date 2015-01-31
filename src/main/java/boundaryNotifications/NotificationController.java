package boundaryNotifications;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/test")
    public Notification test(@RequestParam(value="name", defaultValue="Hackeratus") String name) {
		int ts = (int) (System.currentTimeMillis() / 1000L);
        return new Notification(counter.incrementAndGet(), 1, ts, String.format(template, name));
    }
}
