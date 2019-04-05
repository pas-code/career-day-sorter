import os
import json # this import from tom
from flask import Flask, redirect, url_for
from flask_dance.contrib.google import make_google_blueprint, google
from flask_sqlalchemy import SQLAlchemy
from flask_login import UserMixin, current_user, LoginManager, login_required, login_user, logout_user
from flask_dance.consumer.backend.sqla import OAuthConsumerMixin, SQLAlchemyBackend
from flask_dance.consumer import oauth_authorized
from sqlalchemy.orm.exc import NoResultFound

app = Flask(__name__)

app.config['SECRET_KEY'] = 'thisissupposedtobeasecret'
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:////Users/kentjo/Documents/git/CareerDay/login.db'

google_blueprint = make_google_blueprint(
    client_id='1090068349238-kocnn1c7fvlkq8l1tt6bc501celipbpa.apps.googleusercontent.com', 
    client_secret='SbJYSWWIF1QqZ65jbB557_8t',
    scope=['profile', 'email'],
    offline=True   #added in
)

app.register_blueprint(google_blueprint, url_prefix='/google_login')

db = SQLAlchemy(app)
login_manager = LoginManager(app)

class User(UserMixin, db.Model):
    id = db.Column(db.Integer, primary_key=True)
    email = db.Column(db.String(250), unique=True)
    first_name = db.Column(db.String(250), unique=False)
    last_name = db.Column(db.String(250), unique=False)
    time_stamp = db.Column(db.String(25), unique=False)
    first_choice = db.Column(db.String(250), unique=False)
    second_choice = db.Column(db.String(250), unique=False)
    third_choice = db.Column(db.String(250), unique=False)
    fourth_choice = db.Column(db.String(250), unique=False)
    fifth_choice = db.Column(db.String(250), unique=False)
    submitted = db.Column(db.Boolean, unique=False, default=True)

class OAuth(OAuthConsumerMixin, db.Model):
    user_id = db.Column(db.Integer, db.ForeignKey(User.id))
    user = db.relationship(User)


@app.route("/google")
def google_login():

    if not google.authorized:
        return redirect(url_for("google.login"))

    account_info=google.get("/user")
    account_info_json = account_info.json()

    json_str = json.dumps(account_info_json)

    email = json.loads(json.dumps(json.loads(json_str)['emails']))[0]['value']

    return '<h1>Your email is {}'.format(email) #add stuf here  (email or emails)

    if account_info.ok:
	    return "succeeded"

    return "request failed"    

 
if __name__ == '__main__':  
	app.run(port=5000, debug=True)