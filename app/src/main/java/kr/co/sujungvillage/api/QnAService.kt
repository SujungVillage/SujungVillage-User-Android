package kr.co.sujungvillage.api

import kr.co.sujungvillage.data.*
import retrofit2.Call
import retrofit2.http.*

interface QnAService {
    // FAQ 질문 리스트 조회
    @GET("/api/common/QnA/getAllFaqQuestion")
    fun faqGet(
        @Header("user_id") userId: String,
    ): Call<List<FaqGetResultDTO>>

    // 내 질문 조회
    @GET("/api/student/QnA/getMyQuestions")
    fun myqGet(
        @Header("user_id") userId: String,
    ): Call<List<MyqGetResultDTO>>

    // 내 질문 상세 조회
    @GET("/api/common/QnA/getDetailedQnA")
    fun myqDetailGet(
        @Header("user_id") userId: Number,
        @Query("questionId") questionId: Number,
    ): Call<MyqDetailGetResultDTO>

    // 질문 작성
    @POST("/api/student/QnA/writeQuestion")
    fun myqWrite(
        @Header("user_id") userId: String,
        @Body questionInfo: MyqWriteDTO,
    ): Call<MyqWriteResultDTO>
}