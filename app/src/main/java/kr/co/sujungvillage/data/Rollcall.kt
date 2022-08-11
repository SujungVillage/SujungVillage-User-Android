package kr.co.sujungvillage.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RollcallCreateDTO(
    @SerializedName("imageURL")
    val imgUrl: String,
    @SerializedName("location")
    val location: String,
): Serializable {}

data class RollcallCreateResultDTO(
    @SerializedName("id")
    val id: Long,
    @SerializedName("userId")
    val userId: String,
    @SerializedName("imageURL")
    val imgUrl: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("rollcallTime")
    val time: String,
    @SerializedName("state")
    val state: String,
): Serializable {}

data class RollcallCheckResultDTO(
    @SerializedName("id")
    val id: Long,
    @SerializedName("start")
    val start: String,
    @SerializedName("end")
    val end: String,
    @SerializedName("domitoryName")
    val dormitory: String,
): Serializable {}

data class AppliedRollcallCheckResultDTO(
    @SerializedName("id")
    val id: Long,
    @SerializedName("userId")
    val userId: String,
    @SerializedName("imageURL")
    val imgUrl: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("rollcallDateTime")
    val date: String,
    @SerializedName("state")
    val state: String,
): Serializable {}