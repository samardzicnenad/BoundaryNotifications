# BoundaryNotifications
Spring framework Java application - Notifications API
- Server runs on http://localhost:8080
- Data persistance implemented
- Contains info whether the Notification has been read

# Requirements:
- jdk
- maven (`apt-get install maven` for linux)

# Steps:
- Clone this repo and cd into the repo folder `cd BoundaryNotifications`
- execute `mvn spring-boot:run`
- execute test application with `python BoundaryNotificationsTest.py` (find appropriate Notification id to be updated)

# Endpoints:
## 1. GET /notifications/by_user/user_id(?limit=limit_value)
Returns the most recent notifications ordered by timestamp descending. If 'limit' parameter is not provided, default value is 3.
### Examples
<pre>
http://localhost:8080/notifications/by_user/1
http://localhost:8080/notifications/by_user/2?limit=5
</pre>
## 2. POST /notifications
### Example
<pre>
http://localhost:8080/notifications
</pre>
## 3. PUT /notifications/edit/{id}
Accepts String argument - new message and updates Notification with id - {id}. Type void.
### Example
<pre>
http://localhost:8080/notifications/edit/4
</pre>

# Notes:
- My test application was able to handle ~ 2^10 parallel threads. After that I would get connection denial. Because of that, I created a limitation of the number of concurrent threads.
- Testing showed that 20000 of mixed POST and GET requests required 1123.72 seconds, which is 17.8 of requests per second.
- Testing showed that 20000 of POST requests required 1126.57 seconds, which is 17.75 of requests per second.
- Testing showed that 20000 of GET requests required 1032.64 seconds, which is 19.38 of requests per second.
