package kr.co.sujungvillage.api

import kr.co.sujungvillage.data.*
import retrofit2.Call
import retrofit2.http.*

interface CommunityService {
    // 커뮤니티 상세 내용 가져오기
    @GET("/api/common/community/getPostDetail")
    fun commDetail(
        @Header("user_id") user_id: String,
        @Query("postId") postId: Long,
    ): Call<CommDetailResultDTO>

    // 커뮤니티 글 쓰기
    @POST("/api/common/community/writePost")
    fun commWrite(
        @Header("user_id") userId: String?,
        @Body commWriteInfo: CommWriteDTO,
    ): Call<CommWriteResultDTO>

    //커뮤니티 댓글 쓰기
    @POST("/api/common/community/writeComment")
    fun commComment(
        @Header("user_id") user_id: String,
        @Body commCommentInfo:CommDetailCommentsWriteDTO
    ): Call<CommDetailCommentWriteResultDTO>
}