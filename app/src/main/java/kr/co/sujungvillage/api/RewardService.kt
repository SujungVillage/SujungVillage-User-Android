package kr.co.sujungvillage.api

import kr.co.sujungvillage.data.RewardGetResultDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface RewardService {
    // 상벌점 내역 리스트 조회
    @GET("/api/student/lmp/getLmpHistory")
    fun rewardGet(
        @Header("jwt_token") token: String,
    ): Call<List<RewardGetResultDTO>>
}