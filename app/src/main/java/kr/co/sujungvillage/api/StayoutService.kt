package kr.co.sujungvillage.api

import kr.co.sujungvillage.data.StayoutCheckResultDTO
import kr.co.sujungvillage.data.StayoutCreateDTO
import retrofit2.Call
import retrofit2.http.* // ktlint-disable no-wildcard-imports

interface StayoutService {
    // 외박 신청
    @POST("/api/student/exeat/applyExeat")
    fun stayoutCreate(
        @Header("jwt_token") token: String?,
        @Body stayoutInfo: StayoutCreateDTO
    ): Call<List<StayoutCheckResultDTO>>

    // 외박 신청 조회
    @GET("/api/student/exeat/getAppliedExeat")
    fun stayoutCheck(
        @Header("jwt_token") token: String,
        @Query("exeatId") stayoutId: Long
    ): Call<StayoutCheckResultDTO>

    // 외박 취소
    @DELETE("/api/student/exeat/cancelExeat")
    fun stayoutCancel(
        @Header("jwt_token") token: String,
        @Query("exeatId") stayoutId: Long
    ): Call<Void>

    // 이번 달 외박 횟수 조회
    @GET("/api/student/exeat/numOfExeats")
    fun stayoutCount(
        @Header("jwt_token") token: String
    ): Call<Int>
}
