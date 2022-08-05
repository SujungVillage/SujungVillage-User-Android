package kr.co.sujungvillage.api

import kr.co.sujungvillage.data.HomeInfoResultDTO
import retrofit2.Call
import retrofit2.http.*

interface HomeService {
    // 학생 홈 화면 정보 조회
    @GET("/api/student/home_info")
    fun homeInfo(
        @Header("user_id") userId: String,
        @Query("year") year: String,
        @Query("month") month: String,
    ): Call<HomeInfoResultDTO>
}