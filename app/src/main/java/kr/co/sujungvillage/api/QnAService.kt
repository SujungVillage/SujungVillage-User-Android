package kr.co.sujungvillage.api

import kr.co.sujungvillage.data.* // ktlint-disable no-wildcard-imports
import retrofit2.Call
import retrofit2.http.* // ktlint-disable no-wildcard-imports

interface QnAService {
    // FAQ 질문 리스트 조회
    @GET("/api/common/qna/getAllFaq")
    fun faqGet(
        @Header("jwt_token") token: String
    ): Call<List<FaqGetResultDTO>>

    // 내 질문 조회
    @GET("/api/student/qna/getMyQnas")
    fun myqGet(
        @Header("jwt_token") token: String
    ): Call<List<MyqGetResultDTO>>

    // 내 질문 상세 조회
    @GET("/api/common/qna/getQna")
    fun myqDetailGet(
        @Header("jwt_token") token: String,
        @Query("questionId") questionId: Long
    ): Call<MyqDetailGetResultDTO>

    // 질문 작성
    @POST("/api/student/qna/writeQuestion")
    fun myqWrite(
        @Header("jwt_token") token: String,
        @Body questionInfo: MyqWriteDTO
    ): Call<MyqWriteResultDTO>

    // 질문 삭제
    @DELETE("/api/common/qna/deleteQuestion")
    fun myqDelete(
        @Header("jwt_token") token: String,
        @Query("questionId") questionId: Long
    ): Call<Void>
}
