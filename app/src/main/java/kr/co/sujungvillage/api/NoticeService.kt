package kr.co.sujungvillage.api

import kr.co.sujungvillage.data.NoticeDetailResultDTO
import kr.co.sujungvillage.data.NoticeRequestResultDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NoticeService {
    // 공지사항 제목 리스트 조회
    @GET("/api/common/announcement/getAnnouncementTitles")
    fun noticeRequest(
        @Header("jwt_token") token: String,
        @Query("dormitoryName") dormitory: String,
    ): Call<List<NoticeRequestResultDTO>>

    // 공지사항 상세 조회
    @GET("/api/common/announcement/getAnnouncement")
    fun noticeDetailRequest(
        @Header("jwt_token") token: String,
        @Query("announcementId") noticeId: Long,
    ): Call<NoticeDetailResultDTO>
}