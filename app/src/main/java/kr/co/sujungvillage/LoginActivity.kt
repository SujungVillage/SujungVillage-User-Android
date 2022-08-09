package kr.co.sujungvillage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kr.co.sujungvillage.databinding.ActivityLoginBinding


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
            // Auth Web Application Client ID인데 노출되면 안 될 것 같아서 필요하게 되면 연락 요망
            // .requestIdToken("XXX")
            .requestEmail()
            .build()

        // 구글 로그인 클라이언트 객체 생성
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        // 로그인 버튼 연결
        binding.btnSignIn.setOnClickListener { signIn() }
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

                // ★★★ 토큰 로컬에 저장하고 로그인 API 연결 (학번은 저장 완료)
                val shared = getSharedPreferences("SujungVillage", Context.MODE_PRIVATE)
                val editor = shared.edit()
                editor.clear() // 에디터 초기화
                editor.putString("studentNum", account.email!!.subSequence(0, 8).toString())
                editor.apply()

                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            // 성신 구글 계정이 아닌 경우
            else {
                Toast.makeText(this, "성신 구글 계정으로만 로그인이 가능합니다.", Toast.LENGTH_SHORT).show()
                mGoogleSignInClient?.signOut()?.addOnCompleteListener(this) {
                    // 로그아웃
                    Log.d("GOOGLE_LOGOUT", "일반 구글 계정 로그아웃")
                }
            }
        } catch (e: ApiException) {
            Log.e("GOOGLE_LOGIN", "로그인 오류")
            Log.e("GOOGLE_LOGIN", e.statusCode.toString())
            Toast.makeText(this, "로그인 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}