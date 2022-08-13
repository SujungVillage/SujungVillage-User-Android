package kr.co.sujungvillage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginStart
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kr.co.sujungvillage.BuildConfig.CLIENT_ID
import kr.co.sujungvillage.data.LoginDTO
import kr.co.sujungvillage.data.LoginResultDTO
import kr.co.sujungvillage.databinding.ActivityLoginBinding
import kr.co.sujungvillage.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    val RC_SIGN_IN = 100
    var mGoogleSignInClient: GoogleSignInClient? = null

    override fun onStart() {
        super.onStart()
        // 로그인이 되어있는 경우 로그아웃
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null){
            mGoogleSignInClient?.signOut()?.addOnCompleteListener(this) {
                val shared = getSharedPreferences("SujungVillage", Context.MODE_PRIVATE)
                val editor = shared.edit()
                editor.remove("token")
                editor.remove("studentNum")
                editor.apply()
                // 로그아웃 성공
                Log.d("GOOGLE_LOGIN", "로그인 되어있던 계정 로그아웃 완료")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(binding.root)

        // 사용자 이메일 주소 요청
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(CLIENT_ID)
            .requestEmail()
            .build()

        // 구글 로그인 클라이언트 객체 생성
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        // 로그인 버튼 연결
        binding.btnSignIn.setOnClickListener { signIn() }
        val googleTextView: TextView = binding.btnSignIn.getChildAt(0) as TextView
        googleTextView.text = "성신 구글 계정으로 로그인"
    }

    // 구글 로그인 함수
    private fun signIn() {
        val signInIntent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    // 구글 로그인 결과 처리 함수
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // GoogleSignInClient.getSignInIntent 결과 반환
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    // 구글 로그인 결과 전달 함수
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            // 성신 구글 계정인 경우
            if (account.email?.endsWith("@sungshin.ac.kr") == true) {
                Log.d("GOOGLE_LOGIN", "구글 로그인 성공")
                Log.d("GOOGLE_LOGIN", "e-mail : " + account.email)
                Log.d("GOOGLE_LOGIN", "id : " + account.id)
                Log.d("GOOGLE_LOGIN", "token : " + account.idToken)

                // 재사생 로그인 API 연결
                RetrofitBuilder.loginApi.login(LoginDTO(account.idToken.toString())).enqueue(object: Callback<LoginResultDTO> {
                    override fun onResponse(call: Call<LoginResultDTO>, response: Response<LoginResultDTO>) {
                        Log.d("LOGIN", "로그인 성공")
                        Log.d("LOGIN", "token : " + response.body()?.token.toString())
                        Log.d("LOGIN", "code : " + response.code())

                        // 토큰, 학번 로컬에 저장하고 메인 화면으로 이동
                        val shared = getSharedPreferences("SujungVillage", Context.MODE_PRIVATE)
                        val editor = shared.edit()
                        editor.putString("studentNum", account.email!!.subSequence(0, 8).toString())
                        editor.putString("token", response.body()?.token)
                        editor.apply()

                        var intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                    override fun onFailure(call: Call<LoginResultDTO>, t: Throwable) {
                        Log.d("LOGIN", "로그인 실패")
                        Log.d("LOGIN", t.message.toString())
                    }
                })
            }
            // 성신 구글 계정이 아닌 경우
            else {
                Toast.makeText(this, "성신 구글 계정으로만 로그인이 가능합니다.", Toast.LENGTH_SHORT).show()
                // 로그아웃
                mGoogleSignInClient?.signOut()?.addOnCompleteListener(this) {
                    Log.d("GOOGLE_LOGOUT", "일반 구글 계정 로그아웃")
                }
            }
        } catch (e: ApiException) {
            Log.e("GOOGLE_LOGIN", "로그인 오류")
            Log.e("GOOGLE_LOGIN", e.toString())
            Log.e("GOOGLE_LOGIN", e.statusCode.toString())
            Log.e("GOOGLE_LOGIN", e.message.toString())
            Toast.makeText(this, "로그인 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}