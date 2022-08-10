package kr.co.sujungvillage.api

import kr.co.sujungvillage.data.FaqGetResultDTO
import kr.co.sujungvillage.data.MyqGetResultDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface QnAService {
    // ★★★ FAQ 질문 리스트 조회 (서버에 수정 요청한 상태)
    @GET("/api/common/QnA/getAllFaqQuestion")
    fun faqGet(
        @Header("user_id") userId: String,
    ): Call<List<FaqGetResultDTO>>

    // 내 질문 조회
    @GET("/api/student/QnA/getMyQuestions")
    fun myqGet(
        @Header("user_id") userId: String,
    ): Call<List<MyqGetResultDTO>>
}