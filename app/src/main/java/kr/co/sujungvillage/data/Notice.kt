package kr.co.sujungvillage.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NoticeRequestResultDTO(
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("dormitoryName")
    val dormitory: String,
    @SerializedName("regDate")
    val date: String,
): Serializable {}