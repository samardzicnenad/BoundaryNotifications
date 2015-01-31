package boundaryNotifications;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@ComponentScan
@EnableAutoConfiguration
public class Application {

    public static void main(String args[]) {
		createTable();
		SpringApplication.run(Application.class, args);
    }

    private static void createTable() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(org.h2.Driver.class);
        dataSource.setUrl("jdbc:h2:boundary");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        //jdbcTemplate.execute("drop table notifications if exists");
        jdbcTemplate.execute("create table if not exists notifications(id serial, user_id integer, timestamp integer, message varchar(255))");
	}
}
