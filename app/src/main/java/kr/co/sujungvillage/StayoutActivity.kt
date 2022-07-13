package kr.co.sujungvillage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.sujungvillage.base.hideKeyboard
import kr.co.sujungvillage.databinding.ActivityStayoutBinding

class StayoutActivity : AppCompatActivity() {

    val binding by lazy { ActivityStayoutBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 키보드 내리기
        binding.layout.setOnClickListener { this.hideKeyboard() }
        binding.linearForm.setOnClickListener { this.hideKeyboard() }

        // 뒤로가기 버튼 클릭 시 액티비티 종료
        binding.btnBack.setOnClickListener { this.finish() }

        setContentView(binding.root)
    }
}