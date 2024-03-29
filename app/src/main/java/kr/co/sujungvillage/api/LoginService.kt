package kr.co.sujungvillage.api

import kr.co.sujungvillage.data.LoginDTO
import kr.co.sujungvillage.data.LoginResultDTO
import kr.co.sujungvillage.data.RequestTokenRefreshDto
import kr.co.sujungvillage.data.ResponseTokenRefreshDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
// 기존 학생 구글 로그인 API
/*
    // 재사생 로그인
    @POST("/api/student/login")
    fun login(
        @Body loginInfo: LoginDTO,
    ): Call<LoginResultDTO>
 */

    // 재사생 로그인
    @POST("api/student/login")
    fun login(
        @Body loginInfo: LoginDTO
    ): Call<LoginResultDTO>

    // 토큰 리프레쉬
    @POST("/api/common/refresh")
    fun tokenRefresh(
        @Body requestTokenRefreshDto: RequestTokenRefreshDto
    ): Call<ResponseTokenRefreshDto>
}
