package boundaryNotifications;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RestController
public class NotificationController {

    // /notifications/by_user/<user_id>(?limit=<limit_value>)
	@RequestMapping(value="/notifications/by_user/{user_id}", method=RequestMethod.GET)
	public Notification[] getRecent(@PathVariable int user_id, @RequestParam(value="limit", defaultValue="3") int limitNo) {
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		dataSource.setDriverClass(org.h2.Driver.class);
		dataSource.setUrl("jdbc:h2:boundary");
		dataSource.setUsername("sa");
		dataSource.setPassword("");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        List<Notification> resultList = jdbcTemplate.query(
                "select * from notifications where user_id = ? order by timestamp desc limit ?",
                new Object[] {user_id, limitNo},
                new RowMapper<Notification>() {
                    @Override
                    public Notification mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return new Notification(rs.getLong("id"), rs.getInt("user_id"), rs.getInt("timestamp"), rs.getString("message"));
                    }
                });
		Notification[] notifications = new Notification[resultList.size()];
		notifications = resultList.toArray(notifications);
        return notifications;
	}
}
