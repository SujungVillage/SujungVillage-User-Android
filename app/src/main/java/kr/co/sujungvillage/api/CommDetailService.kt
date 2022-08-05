package kr.co.sujungvillage.api

import kr.co.sujungvillage.data.CommDetailResultDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CommDetailService {
    //커뮤니티 상세 내용 가져오기
    @GET("/api/common/community/getPostDetail")
    fun commDetail(
        @Header("user_id")userId:String,
        @Query("post_id")postId:String,
    ): Call<CommDetailResultDTO>
}