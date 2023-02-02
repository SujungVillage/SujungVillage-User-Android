package kr.co.sujungvillage.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface UserService {
    // 탈퇴하기
    @GET("/api/common/deleteUser")
    fun deleteUse(
        @Header("jwt_token") token: String
    ): Call<Unit>
}
