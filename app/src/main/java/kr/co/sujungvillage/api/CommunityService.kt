package kr.co.sujungvillage.api

import kr.co.sujungvillage.data.*
import retrofit2.Call
import retrofit2.http.*

interface CommunityService {
    //커뮤니티 글 가져오기
    @GET("/api/common/community/getAllPost")
    fun comm(
        @Header("jwt_token") user_id:String,
        @Query("dormitoryName") dormitory:String
    ):Call<List<CommDTO>>

    // 커뮤니티 상세 내용 가져오기
    @GET("/api/common/community/getPost")
    fun commDetail(
        @Header("jwt_token") user_id: String,
        @Query("postId") postId: Long,
    ): Call<CommDetailResultDTO>

    // 커뮤니티 글 쓰기
    @POST("/api/common/community/writePost")
    fun commWrite(
        @Header("jwt_token") userId: String?,
        @Body commWriteInfo: CommWriteDTO,
    ): Call<CommWriteResultDTO>

    //커뮤니티 댓글 쓰기
    @POST("/api/common/community/writeComment")
    fun commComment(
        @Header("jwt_token") user_id: String,
        @Body commCommentInfo:CommDetailCommentsWriteDTO
    ): Call<CommDetailCommentWriteResultDTO>
}