package kr.co.sujungvillage

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import kr.co.sujungvillage.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySplashBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        loadSplashScreen()
    }

    private fun loadSplashScreen() {
        android.os.Handler(Looper.getMainLooper()).postDelayed({
            // ★★★ 만료되지 않은 로그인 토큰이 존재하면 MainActivity로 바로 이동
            val intent = Intent(this, LoginActivity::class.java)
            val options: ActivityOptions = ActivityOptions.makeSceneTransitionAnimation(
                this, binding.splashLogo, "main_icon"
            )
            startActivity(intent, options.toBundle())
        }, 4600)
        android.os.Handler(Looper.getMainLooper()).postDelayed({
            finish()
        }, 7000)
    }
}