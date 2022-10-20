package kr.co.sujungvillage.api

import kr.co.sujungvillage.data.LoginDTO
import kr.co.sujungvillage.data.LoginResultDTO
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
    @POST("/api/student/login")
    fun login(
        @Body loginInfo: LoginDTO,
    ): Call<LoginResultDTO>
}

