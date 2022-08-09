package kr.co.sujungvillage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.sujungvillage.databinding.ActivityAlarmBinding


class QnADetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlarmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 뒤로가기 버튼 연결
        binding.btnBack.setOnClickListener { finish() }
    }
}