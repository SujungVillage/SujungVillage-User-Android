package kr.co.sujungvillage.api

import kr.co.sujungvillage.data.HomeInfoResultDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface HomeService {
    // 학생 홈 화면 정보 조회
    @GET("/api/student/home/getInfo")
    fun homeInfo(
        @Header("jwt_token") token: String,
        @Query("year") year: String,
        @Query("month") month: String
    ): Call<HomeInfoResultDTO>
}
