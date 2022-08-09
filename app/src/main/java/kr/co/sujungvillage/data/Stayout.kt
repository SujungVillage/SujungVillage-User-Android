package kr.co.sujungvillage.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class StayoutCreateDTO(
    @SerializedName("destination")
    val destination: String,
    @SerializedName("reason")
    val reason: String,
    @SerializedName("emergencyPhoneNumber")
    val emergencyNumber: String,
    @SerializedName("dateToStart")
    val startDate: String,
    @SerializedName("dateToEnd")
    val endDate: String,
): Serializable {}