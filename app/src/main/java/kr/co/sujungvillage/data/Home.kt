package kr.co.sujungvillage.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class HomeInfoResultDTO(
    @SerializedName("residentInfo")
    val residentInfo: HomeResidentInfo,
    @SerializedName("todayRollCallInfo")
    val todayRollcall: Long,
    @SerializedName("rollcallDayList")
    val rollcallDays: List<String>,
    @SerializedName("appliedRollcallList")
    val appliedRollcalls: List<HomeRollcall>,
    @SerializedName("appliedExeatInfoList")
    val appliedStayout: List<HomeStayout>,
): Serializable {}

data class HomeResidentInfo(
    @SerializedName("name")
    val name: String,
    @SerializedName("dormitoryName")
    val dormitory: String,
    @SerializedName("detailedAddress")
    val address: String,
    @SerializedName("plusLMP")
    val plusLMP: Long,
    @SerializedName("minusLMP")
    val minusLMP: Long,
): Serializable {}

data class HomeRollcall(
    @SerializedName("rollcallId")
    val id: Long,
    @SerializedName("imageURL")
    val img: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("rollcallTime")
    val time: String,
    @SerializedName("state")
    val status: String,
): Serializable {}

data class HomeStayout(
    @SerializedName("exeatId")
    val id: Long,
    @SerializedName("destination")
    val destination: String,
    @SerializedName("reason")
    val reason: String,
    @SerializedName("emergencyPhoneNumber")
    val emergencyNumber: String,
    @SerializedName("dateToUse")
    val date: String,
): Serializable {}