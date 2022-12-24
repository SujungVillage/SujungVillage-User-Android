package kr.co.sujungvillage.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RollcallCreateDTO(
    @SerializedName("image")
    val imgUrl: ByteArray,
    @SerializedName("location")
    val location: String
) : Serializable

data class RollcallCreateResultDTO(
    @SerializedName("id")
    val id: Long,
    @SerializedName("userId")
    val userId: String,
    @SerializedName("image")
    val imgUrl: ByteArray,
    @SerializedName("location")
    val location: String,
    @SerializedName("rollcallDateTime")
    val time: String,
    @SerializedName("state")
    val state: String
) : Serializable

data class RollcallCheckResultDTO(
    @SerializedName("id")
    val id: Long,
    @SerializedName("start")
    val start: String,
    @SerializedName("end")
    val end: String,
    @SerializedName("dormitoryName")
    val dormitory: String
) : Serializable

data class AppliedRollcallCheckResultDTO(
    @SerializedName("id")
    val id: Long,
    @SerializedName("userId")
    val userId: String,
    @SerializedName("image")
    val imgUrl: ByteArray,
    @SerializedName("location")
    val location: String,
    @SerializedName("rollcallDateTime")
    val date: String,
    @SerializedName("state")
    val state: String
) : Serializable
