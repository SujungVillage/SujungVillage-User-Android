package kr.co.sujungvillage.api

import kr.co.sujungvillage.data.StayoutCheckResultDTO
import kr.co.sujungvillage.data.StayoutCreateDTO
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface StayoutService {
    // 외박 신청
    @POST("/api/student/applyExeat")
    fun stayoutCreate(
        @Header("user_id") userId: String?,
        @Body stayoutInfo: StayoutCreateDTO,
    ): Call<List<StayoutCheckResultDTO>>

    // 외박 신청 조회
    @GET("/api/student/getAppliedExeatInfo")
    fun stayoutCheck(
        @Header("user_id") userId: String,
        @Query("date") date: String,
    ): Call<StayoutCheckResultDTO>

    // 외박 취소
    @DELETE("/api/student/cancleExeat")
    fun stayoutCancel(
        @Header("user_id") userId: String,
        @Query("date") date: String,
    ): Call<Void>
}