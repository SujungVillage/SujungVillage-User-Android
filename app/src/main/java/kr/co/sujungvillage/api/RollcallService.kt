package kr.co.sujungvillage.api

import kr.co.sujungvillage.data.AppliedRollcallCheckResultDTO
import kr.co.sujungvillage.data.RollcallCheckResultDTO
import kr.co.sujungvillage.data.RollcallCreateDTO
import kr.co.sujungvillage.data.RollcallCreateResultDTO
import retrofit2.Call
import retrofit2.http.*

interface RollcallService {
    // 점호 신청
    @POST("/api/student/rollcall/applyRollcall")
    fun rollcallCreate(
        @Header("jwt_token") token: String,
        @Body rollcallInfo: RollcallCreateDTO,
    ): Call<RollcallCreateResultDTO>

    // 점호일 조회
    @GET("/api/common/rollcall/getRollcallDateInfo")
    fun rollcallCheck(
        @Header("jwt_token") token: String,
        @Query("rollcallDateId") rollcallId: Long,
    ): Call<RollcallCheckResultDTO>

    // 점호 신청 조회
    @GET("/api/common/rollcall/getAppliedRollcallInfo")
    fun appliedRollcallCheck(
        @Header("jwt_token") token: String,
        @Query("rollcallId") rollcallId: Long,
    ): Call<AppliedRollcallCheckResultDTO>
}