package com.example.metaler_android.data

data class Home(
    val materials: List<HomePost>,
    val manufactures: List<HomePost>
)

data class HomePost(
    val post_id: Int,
    val title: String,
    val nickname: String,
    val date: String,
    val tags: List<String>
)

