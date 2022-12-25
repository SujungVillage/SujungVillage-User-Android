package kr.co.sujungvillage.retrofit // ktlint-disable filename

import android.content.Intent
import android.util.Log
import kr.co.sujungvillage.App
import kr.co.sujungvillage.LoginActivity
import kr.co.sujungvillage.base.showSnackbar
import kr.co.sujungvillage.data.RequestTokenRefreshDto
import kr.co.sujungvillage.data.ResponseTokenRefreshDto
import kr.co.sujungvillage.retrofit.RetrofitBuilder.loginApi
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Call
import retrofit2.Callback

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request) // proceed 통해서 서버통신 응답값 확인

        val refreshToken = App.prefs.refresh
        val userId = App.prefs.id

        when (response.code) {
            EXPIRED_TOKEN_CODE -> {
                if (userId == null || refreshToken == null) {
                    intentToLoginActivity()
                    return response
                }

                loginApi.tokenRefresh(RequestTokenRefreshDto(userId, refreshToken)).enqueue(object :
                        Callback<ResponseTokenRefreshDto> {
                        override fun onResponse(
                            call: Call<ResponseTokenRefreshDto>,
                            response: retrofit2.Response<ResponseTokenRefreshDto>
                        ) {
                            if (response.isSuccessful) {
                                App.prefs.token = response.body()?.jwtToken
                            } else {
                                Log.e("REFRESH_TOKEN_FAIL", "response : $response")
                                intentToLoginActivity()
                            }
                        }

                        override fun onFailure(call: Call<ResponseTokenRefreshDto>, t: Throwable) {
                            Log.e("REFRESH_TOKEN_FAIL", "throwable : $t")
                        }
                    })
            }
            INVALID_TOKEN_CODE -> intentToLoginActivity()
        }

        return response
    }

    fun intentToLoginActivity() {
        val intent = Intent(App.context, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        App.context.startActivity(intent)
    }

    companion object {
        private const val EXPIRED_TOKEN_CODE = 401
        private const val INVALID_TOKEN_CODE = 403
    }
}
