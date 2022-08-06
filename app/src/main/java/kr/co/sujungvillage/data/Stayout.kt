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
    @SerializedName("dateToUse")
    val date: String,
): Serializable {}

data class StayoutCreateResultDTO(
    @SerializedName("id")
    val id: Long,
    @SerializedName("user")
    val user: StayoutUser,
    @SerializedName("destination")
    val destination: String,
    @SerializedName("reason")
    val reason: String,
    @SerializedName("emergencyPhoneNumber")
    val emergencyNumber: String,
    @SerializedName("dateToUse")
    val date: Date,
): Serializable {}

data class StayoutUser(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("domitory")
    val dormitory: StayoutDormitory,
    @SerializedName("detailedAddress")
    val address: String,
    @SerializedName("authority")
    val authority: String,
): Serializable {}

data class StayoutDormitory(
    @SerializedName("id")
    val id: Long,
    @SerializedName("dormitoryName")
    val name: String,
    @SerializedName("address")
    val address: String,
):Serializable {}