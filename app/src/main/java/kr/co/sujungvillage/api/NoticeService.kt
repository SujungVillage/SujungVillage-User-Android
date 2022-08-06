package kr.co.sujungvillage.api

import kr.co.sujungvillage.data.NoticeRequestResultDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface NoticeService {
    // 공지사항 리스트 조회
    @GET("/api/common/getAnnouncementList")
    fun noticeRequest(
        @Header("user_id") userId: String,
    ): Call<List<NoticeRequestResultDTO>>
}