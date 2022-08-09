package kr.co.sujungvillage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.sujungvillage.base.hideKeyboard
import kr.co.sujungvillage.databinding.ActivityQnAwriteBinding

class QnAWriteActivity : AppCompatActivity() {

    val binding by lazy { ActivityQnAwriteBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 키보드 내리기
        binding.layout.setOnClickListener { this.hideKeyboard() }
        binding.linear.setOnClickListener { this.hideKeyboard() }

        // 뒤로가기 버튼 연결
        binding.btnBack.setOnClickListener { finish() }
    }
}