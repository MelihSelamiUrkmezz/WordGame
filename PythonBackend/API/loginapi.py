import flask
import mysql.connector
import redis
from flask import request, jsonify,abort,Response
from apscheduler.schedulers.background import BackgroundScheduler


app = flask.Flask(__name__)
r = redis.Redis(
    host='127.0.0.1',
    port=6379, 
)
global conn 
conn = mysql.connector.connect(host='127.0.0.1',
                        user='root',
                        password='rootpassw0rd!',
                        database='wordgame'
                        )
global conn_cursor
conn_cursor = conn.cursor(buffered=True)


scheduler = BackgroundScheduler()
@scheduler.scheduled_job('cron', hour='*')

def refresh_database():
    conn_cursor.close()
    conn.close()
    conn = mysql.connector.connect(host='127.0.0.1',
                        user='root',
                        password='rootpassw0rd!',
                        database='wordgame'
                        )
    conn_cursor = conn.cursor(buffered=True)
r.flushdb()
r.flushall()
@app.route('/', methods=['GET'])
def home():
    return '''Login api is running now.'''

def login_control(username,password):
    
    # r.set('mykey', 'Hello from Python!')
    # value = r.get('mykey')
    # value = value.decode("utf-8")
    # print(type(value))
    
    query="Select Email from users where Username='{}' AND Password='{}'".format(username,password)

    if(r.exists(query)):
        print("Login Cache")
        if r.get(query).decode('utf-8')=="True":
            return True
        else:
            return False
    
    else:
       
        conn_cursor.execute(query)
        result=conn_cursor.fetchall()
        print(result)
        if len(result)==0:
            r.set(query,"False")
            return False
        r.set(query,"True")
        return True
      
def register_function(email,username,password):

    query="Select Password from users where Username='{}' OR Email='{}'".format(username,email)

    if(r.exists(query)):
        print("Register Cache")
        if r.get(query).decode('utf-8')=="True":
            return True
        else:
            return False
    
    else:
  
        conn_cursor.execute("Select Password from users where Username='{}' OR Email='{}'".format(username,email))
        result=conn_cursor.fetchall()
        
        if(len(result)>0):

            r.set(query,"False")
            return False
    
        else:
           
            conn_cursor.execute("INSERT INTO `wordgame`.`users` (`Username`, `Email`,`Password`) VALUES ('{}', '{}','{}');".format(username,email,password))
            conn.commit()

            r.set(query,"True")
            return True
              
def change_password(username,old_password,new_password):
    
    query="UPDATE `wordgame`.`users` SET `Password`='{}' WHERE  `Username`='{}';".format(new_password,username)
    
    if login_control(username=username,password=old_password):
        r.flushdb()
        r.flushall()
        
        
        if(r.exists(query)):
            
            print("Change password Cache")
            if r.get(query).decode('utf-8')=="True":
                return True
            else:
                return False
    
        
        else:

            conn_cursor.execute(query)
            conn.commit()
            r.set(query,"True")
            return True
    else:
        r.set(query,"False")
        return False
       
    
@app.route('/api/register', methods=['POST'])
def register():
        data = request.get_json()
        username=data['username']
        email=data['email']
        password=data['password']
        
        if register_function(email=email,username=username,password=password):
            return "OK"
        
        return "NOTOK DUPUSR"    
    
    
# A route to return all of the available entries in our catalog.
@app.route('/api/login', methods=['POST'])
def login():
        data = request.get_json()
        username=data['username']
        password=data['password']
        if login_control(username=username,password=password):
            return "OK"
        
        return "NOTOK FLSUSR"

@app.route('/api/chgpassword', methods=['POST'])
def chgpassword():
        data = request.get_json()
        username=data['username']
        old_password=data['oldpassword']
        new_password=data['newpassword']
    
        if change_password(username=username,old_password=old_password,new_password=new_password):
            return "OK"
        
        return "NOTOK FLSUSR"    

@app.route('/health', methods=['GET'])
def health():
        query = "Select Email from users"
        conn_cursor.execute(query)
        result = conn_cursor.fetchall()
        
        if len(result)>0:
            return Response(
            "Container is healthy.",
                status=200,
            ) 
        return Response(
            "Container is unhealthy.",
                status=500,
            )  
    

if __name__ == '__main__':
    scheduler.start()
    app.run("0.0.0.0",debug=True,port=5000)
