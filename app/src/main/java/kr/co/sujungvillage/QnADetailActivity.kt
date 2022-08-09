package kr.co.sujungvillage

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import kr.co.sujungvillage.adapter.ViewPagerAdapter
import kr.co.sujungvillage.databinding.ActivityAlarmBinding
import kr.co.sujungvillage.databinding.ActivityMainBinding


class QnADetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlarmBinding
    private val tabTitleArray = arrayOf(
        "FAQ",
        "내질문"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 뒤로가기 버튼 연결
        binding.btnBack.setOnClickListener { finish() }

    }
}