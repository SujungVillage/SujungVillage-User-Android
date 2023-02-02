package kr.co.sujungvillage.retrofit

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kr.co.sujungvillage.BuildConfig.BASE_URL
import kr.co.sujungvillage.api.* // ktlint-disable no-wildcard-imports
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    // 사용할 API 인터페이스 선언
    val communityApi: CommunityService
    val homeApi: HomeService
    val noticeApi: NoticeService
    val qnaApi: QnAService
    val rollcallApi: RollcallService
    val stayoutApi: StayoutService
    val loginApi: LoginService
    val rewardApi: RewardService
    val signupApi: SignUpService
    val userApi: UserService

    val gson: Gson = GsonBuilder().setLenient().create()

    private val client by lazy {
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            ).build()
    }

    init {
        // API 서버 연결
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        communityApi = retrofit.create(CommunityService::class.java)
        homeApi = retrofit.create(HomeService::class.java)
        noticeApi = retrofit.create(NoticeService::class.java)
        qnaApi = retrofit.create(QnAService::class.java)
        rollcallApi = retrofit.create(RollcallService::class.java)
        stayoutApi = retrofit.create(StayoutService::class.java)
        loginApi = retrofit.create(LoginService::class.java)
        rewardApi = retrofit.create(RewardService::class.java)
        signupApi = retrofit.create(SignUpService::class.java)
        userApi = retrofit.create(UserService::class.java)
    }
}
