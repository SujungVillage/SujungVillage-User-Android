package kr.co.sujungvillage.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class StayoutCreateDTO(
    @SerializedName("destination")
    val destination: String,
    @SerializedName("reason")
    val reason: String,
    @SerializedName("emergencyPhoneNumber")
    val emergencyNumber: String,
    @SerializedName("dateToUse")
    val date: String,
): Serializable {}

data class StayoutCodeDTO(
    @SerializedName("code")
    val code: Long,
    @SerializedName("data")
    val data: String,
): Serializable {}
