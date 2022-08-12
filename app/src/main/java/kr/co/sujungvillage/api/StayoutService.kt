package kr.co.sujungvillage.api

import kr.co.sujungvillage.data.StayoutCheckResultDTO
import kr.co.sujungvillage.data.StayoutCreateDTO
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface StayoutService {
    // 외박 신청
    @POST("/api/student/exeat/applyExeat")
    fun stayoutCreate(
        @Header("jwt_token") token: String?,
        @Body stayoutInfo: StayoutCreateDTO,
    ): Call<List<StayoutCheckResultDTO>>

    // 외박 신청 조회
    @GET("/api/student/exeat/getAppliedExeat")
    fun stayoutCheck(
        @Header("jwt_token") token: String,
        @Query("exeatId") stayoutId: Long,
    ): Call<StayoutCheckResultDTO>

    // 외박 취소
    @DELETE("/api/student/exeat/cancelExeat")
    fun stayoutCancel(
        @Header("jwt_token") token: String,
        @Query("exeatId") stayoutId: Long,
    ): Call<Void>
}