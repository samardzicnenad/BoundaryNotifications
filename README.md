# BoundaryNotifications
Spring framework Java application - Notifications API
- Data persistance implemented
- Server runs on http://localhost:8080

# Requirements:
- jdk
- maven (`apt-get install maven` for linux)

# Steps:
- Clone this repo
- execute `mvn spring-boot:run`

# Endpoints:
## 1. GET /notifications/by_user/user_id(?limit=limit_value)
Returns the most recent notifications ordered by timestamp descending. If 'limit' parameter is not provided, default value is 3.
### Examples
<pre>GET /notifications/by_user/1
GET /notifications/by_user/2?limit=5</pre>
## 2. POST /notifications

# Notes:
- My test application was able to handle ~ 2^10 parallel threads. After that I would get connection denial. Because of that, I created a limitation of the number of concurrent threads.
- Testing showed that 20000 of mixed POST and GET requests required 1123.72 seconds, which is 17.8 of requests per second.
- Testing showed that 20000 of POST requests required 1126.57 seconds, which is 17.75 of requests per second.
- Testing showed that 20000 of GET requests required 1032.64 seconds, which is 19.38 of requests per second.
