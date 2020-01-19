package com.srjhnd.opennews.api

import com.google.gson.annotations.SerializedName
import com.srjhnd.opennews.data.Headline


//"status": "ok",
//"totalResults": 70,
//-"articles": []

data class HeadlineResponse(
    @SerializedName("status")
    val status: String = "",
    @SerializedName("totalResults")
    val results: Int = 0,
    @SerializedName("articles")
    val articles: List<Headline> = emptyList()
)