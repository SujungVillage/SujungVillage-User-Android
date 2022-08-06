package kr.co.sujungvillage.api

import kr.co.sujungvillage.data.RollcallCreateDTO
import kr.co.sujungvillage.data.RollcallCreateResultDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface RollcallService {
    // 점호 신청
    @POST("/api/student/applyRollcall")
    fun rollcallCreate(
        @Header("user_id") userId: String,
        @Body rollcallInfo: RollcallCreateDTO,
    ): Call<RollcallCreateResultDTO>
}