package com.example.youtubemusicpremium.data

import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
object Search

@Serializable
data class Profile(val id: String)