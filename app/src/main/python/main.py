import os
from ytmusicapi import YTMusic, OAuthCredentials
from os.path import join, dirname
import json
import requests
import functools

s = requests.Session()
s.request = functools.partial(s.request, timeout=99999)


yt = YTMusic(join(dirname(__file__), 'oauth.json'), oauth_credentials=OAuthCredentials(
    client_id=os.getenv('CLIENT_ID'),
    client_secret=os.getenv('CLIENT_SECRET')),
    requests_session=s
)


def get_home():
    return json.dumps(yt.get_home())


def get_self():
    return yt.get_account_info()
