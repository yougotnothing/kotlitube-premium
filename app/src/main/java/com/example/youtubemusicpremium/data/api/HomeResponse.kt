package com.example.youtubemusicpremium.data.api

data class HomeResponse(
    val title: String,
    val contents: List<HomeResponseContents>
)

data class HomeResponseContents(
    val title: String,
    val videoId: String,
    val playlistId: String,
    val thumbnails: List<HomeResponseContentsThumbnails>,
    val artists: List<HomeResponseContentsArtists>
)

data class HomeResponseContentsThumbnails(
    val url: String,
    val width: Int,
    val height: Int
)

data class HomeResponseContentsArtists(
    val name: String,
    val id: String?
)