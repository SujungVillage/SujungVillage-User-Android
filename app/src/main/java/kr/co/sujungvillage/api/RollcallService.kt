package kr.co.sujungvillage.api

import kr.co.sujungvillage.data.RollcallCheckResultDTO
import kr.co.sujungvillage.data.RollcallCreateDTO
import kr.co.sujungvillage.data.RollcallCreateResultDTO
import retrofit2.Call
import retrofit2.http.*

interface RollcallService {
    // 점호 신청
    @POST("/api/student/applyRollcall")
    fun rollcallCreate(
        @Header("user_id") userId: String,
        @Body rollcallInfo: RollcallCreateDTO,
    ): Call<RollcallCreateResultDTO>

    // 점호일 조회
    @GET("/api/admin/getRollcallDateInfo")
    fun rollcallCheck(
        @Header("user_id") userId: String,
        @Query("date") date:String,
    ): Call<RollcallCheckResultDTO>
}