package kr.co.sujungvillage.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NoticeRequestResultDTO(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("dormitoryName")
    val dormitory: String,
    @SerializedName("regDate")
    val date: String
) : Serializable

data class NoticeDetailResultDTO(
    @SerializedName("id")
    val id: String,
    @SerializedName("writerId")
    val userId: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("regDate")
    val regDate: String,
    @SerializedName("modDate")
    val modDate: String,
    @SerializedName("dormitoryName")
    val dormitory: String
) : Serializable
