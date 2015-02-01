# BoundaryNotifications
Spring framework Java application - Notifications API
- Data persistance implemented
- Server runs on http://localhost:8080

# Requirements
- jdk
- maven (`apt-get install maven` for linux)

# Steps
- Clone this repo
- execute `mvn spring-boot:run`

# Endpoints
## 1. GET /notifications/by_user/user_id(?limit=limit_value)
Returns the most recent notifications ordered by timestamp descending. If 'limit' parameter is not provided, default value is 3.
### Examples
<pre>GET /notifications/by_user/1
GET /notifications/by_user/2?limit=5</pre>
## 2. POST /notifications
