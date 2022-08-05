package kr.co.sujungvillage.api

import kr.co.sujungvillage.data.CommWriteDTO
import kr.co.sujungvillage.data.CommWriteResultDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface CommWriteService {
    //커뮤니티 글 쓰기
    @POST("/api/common/community/writePost")
    fun commWrite(
        @Header("user_id") userId: String?,
        @Body commWriteInfo: CommWriteDTO,
    ): Call<CommWriteResultDTO>
}