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

os.environ["OAUTHLIB_INSECURE_TRANSPORT"] = '1'  #not sure if needed
os.environ["OAUTHLIB_RELAX_TOKEN_SCOPE"] = '1'  #not sure if needed

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

@login_manager.user_loader
def load_user(user_id):
    return User.query.get(int(user_id))
 
google_blueprint.backend = SQLAlchemyBackend(OAuth, db.session, user=current_user, user_required=False) #need to do real solution and get rid of user_required=False eventually

@app.route('/google')
def google_login():
    if not google.authorized:
        return redirect(url_for('google.login'))
    
    account_info = google.get('/plus/v1/people/me') #change as needed (looked at bookmarked stack overflow about not getting OAuth token)
    account_info_json = account_info.json()

    json_str = json.dumps(account_info_json)

    username = json.loads(json.dumps(json.loads(json_str)['emails']))[0]['value']

    return '<h1>Your email is {}'.format(username) #add stuf here  (email or emails)


@oauth_authorized.connect_via(google_blueprint)
def google_logged_in(blueprint, token):

    account_info = blueprint.session.get('/plus/v1/people/me')

    if account_info.ok:
        account_info_json = account_info.json()

        json_str = json.dumps(account_info_json)

        # username = account_info_json['emails'][0]  #change email to username eventually
        username = json.loads(json.dumps(json.loads(json_str)['emails']))[0]['value']

        query = User.query.filter_by(username=username)

        try:
            user = query.one()
        except NoResultFound:
            user = User(username=username)
            db.session.add(user)
            db.session.commit()

        login_user(user)

@app.route('/')
@login_required
def index():
    return '<h1>You are logged in as {}'.format(current_user.username)

@app.route('/logout')
@login_required
def logout():
    logout_user()
    # User.logout()  #someting wrong here
    return redirect(url_for('index'))

if __name__ == '__main__':
    app.run(debug=True)

# @app.errorhandler(InvalidClientIdError)
# def handle_error(e):
#     session.clear()
#     return render_template('redirect.html', url=url_for('index'))


# Notes
# 1. Had to install "blinker" because I was getting the error: AttributeError: '_FakeSignal' object has no attribute 'connect_via'

# To Do
# 1. Rewrite code to manually create the user account and associate it with the OAuth token according to https://stackoverflow.com/questions/47643448/flask-dance-cannot-get-oauth-token-without-an-associated-user/48718671#48718671
# 2. Stop multiple entried


# What does CSV Need
# 1. Time stamp
# 2. email
# 3. First Name
# 4. Last Name
# 5. First Choice
# 6. Second Choice
# 7. Third Choice
# 8. Fourth Choice
# 9. Fifth Choice
# 10. Submitted