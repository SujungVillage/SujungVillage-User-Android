package kr.co.sujungvillage.api

import kr.co.sujungvillage.data.LoginDTO
import kr.co.sujungvillage.data.LoginResultDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    // 재사생 로그인
    @POST("/api/student/login")
    fun login(
        @Body loginInfo: LoginDTO,
    ): Call<LoginResultDTO>
}