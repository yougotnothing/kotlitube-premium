package com.example.youtubemusicpremium.data

import android.content.Context
import com.example.youtubemusicpremium.R

enum class Endpoints(val title: String) {
    GetUser("/users/get-user"),
    GetPlaylist("/playlists/get-playlist"),
    GetHome("/get-home"),
    GetSong("/songs/get-song");
}