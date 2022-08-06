package kr.co.sujungvillage.api

import kr.co.sujungvillage.data.CommDetailResultDTO
import kr.co.sujungvillage.data.CommWriteDTO
import kr.co.sujungvillage.data.CommWriteResultDTO
import retrofit2.Call
import retrofit2.http.*

interface CommunityService {
    // 커뮤니티 상세 내용 가져오기
    @GET("/api/common/community/getPostDetail")
    fun commDetail(
        @Header("user_id")userId:String,
        @Query("post_id")postId:String,
    ): Call<CommDetailResultDTO>

    // 커뮤니티 글 쓰기
    @POST("/api/common/community/writePost")
    fun commWrite(
        @Header("user_id") userId: String?,
        @Body commWriteInfo: CommWriteDTO,
    ): Call<CommWriteResultDTO>
}