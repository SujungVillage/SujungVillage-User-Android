package kr.co.sujungvillage.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class HomeInfoResultDTO(
    @SerializedName("residentInfo")
    val residentInfo: HomeResidentInfo,
    @SerializedName("rollcallDays") // 모든 점호 예정일
    val rollcallDays: List<HomeDay>,
    @SerializedName("appliedRollcallDays") // 점호 완료일 (점호 예정일과 비교 필요)
    val appliedDays: List<HomeDay>,
    @SerializedName("appliedExeatDays") // 외박 신청일
    val stayoutDays: List<HomeDay>,
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

data class HomeDay(
    @SerializedName("id")
    val id: Long,
    @SerializedName("day")
    val day: Int,
): Serializable {}