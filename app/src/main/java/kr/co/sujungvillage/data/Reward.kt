package kr.co.sujungvillage.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RewardGetResultDTO(
    @SerializedName("id")
    val id: Long,
    @SerializedName("userId")
    val userId: String,
    @SerializedName("score")
    val score: Int,
    @SerializedName("reason")
    val reason: String,
    @SerializedName("regDate")
    val date: String,
): Serializable {}
