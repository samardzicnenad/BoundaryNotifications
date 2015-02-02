from threading import Thread
import threading
import json
import string
import random
import requests
import time

def async(f):
	def wrapper(*args, **kwargs):
		newThread = Thread(target = f, args = args, kwargs = kwargs)
		newThread.start()
	return wrapper

@async
def addGet(uid):
	url = 'http://localhost:8080/notifications/by_user/' + str(uid)
	requestGet = requests.get(url)

	global noRequests
	noRequests += 1
	global start
	print 'GET request: ' + str(noRequests) + '; time: ' + str(time.time() - start) + ' seconds'

@async
def addPost(uid, ts):
	data = {
		"user_id": uid,
		"timestamp": ts,
		"message": 'message-' + str(uid)
	}
	url = 'http://localhost:8080/notifications'
	requestPost = requests.post(url, data=json.dumps(data), headers={'content-type': 'application/json'})

	global noRequests
	noRequests += 1
	global start
	print 'POST request: ' + str(noRequests) + '; time: ' + str(time.time() - start) + ' seconds'

@async
def addPut(nid):
	url = 'http://localhost:8080/notifications/edit/' + str(nid)
	requestPost = requests.put(url, "New Message")

counter = 0
noRequests = 0
start = time.time()
while counter < 20000:
	method = random.randint(0,1)
	ts = int(time.time())
	atc = threading.active_count()
	if atc < 1022:
		if method == 0:
			addGet(counter)
			#addPost(counter, ts)
			counter += 1
		else:
			addPost(counter, ts)
			#addGet(counter)
			counter += 1
addPut(1)
