package kr.co.sujungvillage.api

import kr.co.sujungvillage.data.RollcallCreateDTO
import kr.co.sujungvillage.data.SignUpDTO
import retrofit2.Call
import retrofit2.http.*

interface SignUpService {
    //아이디 중복 확인
    @GET("/api/common/isAvailableId")
    fun idCheck(
        @Query("id")user_id:String
    ): Call<String>

    //회원가입
    @POST("/api/student/signup")
    fun signUp(
        @Body signUpInfo: SignUpDTO
    ):Call<Void>
}

