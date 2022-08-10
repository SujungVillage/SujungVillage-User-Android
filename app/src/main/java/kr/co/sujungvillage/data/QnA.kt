package kr.co.sujungvillage.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FaqGetResultDTO(
    @SerializedName("id")
    val id: Long,
    @SerializedName("question")
    val question: String,
    @SerializedName("domitoryName")
    val dormitory: String,
): Serializable {}

data class MyqGetResultDTO(
    @SerializedName("questionId")
    val id: Long,
    @SerializedName("queristId")
    val userId: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("answered")
    val isAnswered: Boolean,
): Serializable {}