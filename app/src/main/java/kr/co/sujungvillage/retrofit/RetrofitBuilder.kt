package kr.co.sujungvillage.retrofit

import com.google.gson.GsonBuilder
import kr.co.sujungvillage.BuildConfig.BASE_URL
import kr.co.sujungvillage.api.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    // 사용할 API 인터페이스 선언

    var communityApi: CommunityService
    var homeApi: HomeService
    var noticeApi: NoticeService
    var qnaApi: QnAService
    var rollcallApi: RollcallService
    var stayoutApi: StayoutService
    var loginApi: LoginService

    val gson = GsonBuilder().setLenient().create()

    init {
        // API 서버 연결
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        communityApi = retrofit.create(CommunityService::class.java)
        homeApi = retrofit.create(HomeService::class.java)
        noticeApi = retrofit.create(NoticeService::class.java)
        qnaApi = retrofit.create(QnAService::class.java)
        rollcallApi = retrofit.create(RollcallService::class.java)
        stayoutApi = retrofit.create(StayoutService::class.java)
        loginApi = retrofit.create(LoginService::class.java)
    }
}