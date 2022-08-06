package kr.co.sujungvillage.api

import kr.co.sujungvillage.data.NoticeDetailResultDTO
import kr.co.sujungvillage.data.NoticeRequestResultDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NoticeService {
    // 공지사항 리스트 조회
    @GET("/api/common/getAnnouncementList")
    fun noticeRequest(
        @Header("user_id") userId: String,
    ): Call<List<NoticeRequestResultDTO>>

    // 공지사항 상세 조회
    @GET("/api/common/getDetailedAnnouncement")
    fun noticeDetailRequest(
        @Header("user_id") userId: String,
        @Query("notice_id") noticeId: Long,
    ): Call<NoticeDetailResultDTO>
}