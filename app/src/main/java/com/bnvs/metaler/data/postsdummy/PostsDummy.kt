package com.bnvs.metaler.data.postsdummy

import android.util.Log


class PostsDummy{

    var tags = listOf("tag1","tag2")

    var postsDummy = PostDummyData (
        1,
        2,
        2,
        "title",
        "content",
        2000,
        "cash",
        "2020.02.02",
        "2020.02.04",
        "헬로우",
         tags,
        10,
        5,
        false
    )

    fun getDummy (): PostDummyData {
        Log.d("dummy", postsDummy.toString())
        return postsDummy
    }
}