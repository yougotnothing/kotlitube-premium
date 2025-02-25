package com.example.youtubemusicpremium.data.api

data class HomeResponse(
    val title: String,
    val contents: List<HomeResponseContents>
)

data class HomeResponseContents(
    val title: String,
    val videoId: String?,
    val browseId: String?,
    val playlistId: String?,
    val description: String?,
    val count: String?,
    val thumbnails: List<HomeResponseContentsThumbnails>,
    val artists: List<HomeResponseContentsArtists>?,
    val author: List<HomeResponseContentsArtists>?,
    val album: HomeResponseContentsArtists?
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
