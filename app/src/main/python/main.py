import os
from ytmusicapi import YTMusic, OAuthCredentials
from os.path import join, dirname
import json


yt = YTMusic(join(dirname(__file__), 'oauth.json'), oauth_credentials=OAuthCredentials(
    client_id=os.getenv('CLIENT_ID'),
    client_secret=os.getenv('CLIENT_SECRET'))
)


def get_home():
    return json.dumps(yt.get_home())
