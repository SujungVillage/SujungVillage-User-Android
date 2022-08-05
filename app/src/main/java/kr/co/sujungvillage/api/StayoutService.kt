package kr.co.sujungvillage.api

import kr.co.sujungvillage.data.StayoutCodeDTO
import kr.co.sujungvillage.data.StayoutCreateDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface StayoutService {
    // 외박 신청
    @POST("/api/student/applyExeat")
    fun applyStayout(
        @Header("user_id") userId: String?,
        @Body stayoutInfo: StayoutCreateDTO,
    ): Call<StayoutCodeDTO>
}
