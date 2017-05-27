from flask import Flask, request, render_template
import json
import urllib.parse
import redis

app = Flask(__name__)
redis = redis.StrictRedis(host='redis', port=6379, db=0)

@app.route('/', methods=['GET', 'POST', 'DELETE'])
def mainpage():

	if request.method == 'POST':
		text = urllib.parse.unquote(request.args.get('text')).strip()
		if text != '':
			redis.set(text, None)
	if request.method == 'DELETE':
		redis.delete(urllib.parse.unquote(request.args.get('text')))

	return render_template('index.html')

@app.route('/getAllTasks', methods=['GET'])
def getAllTasks():
	tmp = []
	for key in redis.keys('*'):
		tmp.append('' + key.decode("utf-8"))
	return json.dumps(tmp)

if __name__ == '__main__':
    app.run(host='0.0.0.0')
