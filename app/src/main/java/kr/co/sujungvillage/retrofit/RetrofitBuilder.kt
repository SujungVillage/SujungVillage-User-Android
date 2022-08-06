package kr.co.sujungvillage.retrofit

import com.google.gson.GsonBuilder
import kr.co.sujungvillage.BuildConfig.BASE_URL
import kr.co.sujungvillage.api.CommunityService
import kr.co.sujungvillage.api.HomeService
import kr.co.sujungvillage.api.RollcallService
import kr.co.sujungvillage.api.StayoutService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    // 사용할 API 인터페이스 선언
    var stayoutApi: StayoutService
    var communityApi: CommunityService
    var homeApi: HomeService
    var rollcallApi: RollcallService

    val gson = GsonBuilder().setLenient().create()

    init {
        // API 서버 연결
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        stayoutApi = retrofit.create(StayoutService::class.java)
        communityApi = retrofit.create(CommunityService::class.java)
        homeApi = retrofit.create(HomeService::class.java)
        rollcallApi = retrofit.create(RollcallService::class.java)
    }
}