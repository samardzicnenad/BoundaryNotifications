package boundaryNotifications;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.core.RowMapper;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import java.util.Map;
import java.util.HashMap;

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
			"SELECT * FROM notifications WHERE user_id = ? ORDER BY timestamp DESC LIMIT ?",
			new Object[] {user_id, limitNo},
			new RowMapper<Notification>() {
				@Override
				public Notification mapRow(ResultSet rs, int rowNum) throws SQLException {
					return new Notification(rs.getLong("id"), rs.getInt("user_id"), rs.getInt("timestamp"), rs.getInt("is_read"), rs.getString("message"));
				}
			});
		boolean firstPass = true;
		String idList = "";
		for (Notification resultInstance : resultList) {
			if (firstPass) {
				idList += resultInstance.getId();
				firstPass = false;
			} else {
				idList = resultInstance.getId()+ ", " + idList;
			}
		}
		idList = "UPDATE notifications SET is_read = 1 WHERE id IN (" + idList + ")";
        jdbcTemplate.update(idList);
		Notification[] notifications = new Notification[resultList.size()];
		notifications = resultList.toArray(notifications);
        return notifications;
	}

    // /notifications
    @RequestMapping(value="/notifications", method=RequestMethod.POST, headers={"Content-type=application/json"})
    @ResponseBody
    public Notification saveNotification(@RequestBody Notification notification) {
		// set db access
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(org.h2.Driver.class);
        dataSource.setUrl("jdbc:h2:boundary");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

		// insert new Notification and retrieve new record's key
        SimpleJdbcInsert insertNotification = new SimpleJdbcInsert(dataSource);
        insertNotification.withTableName("notifications");
        insertNotification.usingGeneratedKeyColumns("id");

        Map parameters = new HashMap();
        parameters.put("user_id", notification.getUser_id());
        parameters.put("timestamp", notification.getTimestamp());
        parameters.put("is_read", 0);
        parameters.put("message", notification.getMessage());
        Number notificationId = insertNotification.executeAndReturnKey(parameters);

		// return Notification with associated key
		notification.setId(notificationId.longValue());
		return notification;
    }

    // /notifications/edit/<id>
    @RequestMapping(value="/notifications/edit/{id}", method=RequestMethod.PUT)
    @ResponseBody
    public void editNotification(@PathVariable long id, @RequestBody String newMessage) {
		// set db access
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(org.h2.Driver.class);
        dataSource.setUrl("jdbc:h2:boundary");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update("UPDATE notifications SET message = ? WHERE id = ?", new Object[] {newMessage, id});
    }
}
