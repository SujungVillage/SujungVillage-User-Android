package kr.co.sujungvillage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.sujungvillage.databinding.ActivityCommWriteBinding
import kr.co.sujungvillage.databinding.ActivityRollcallBinding

class RollcallActivity : AppCompatActivity() {

    val binding by lazy { ActivityRollcallBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // 뒤로가기 버튼 연결
        binding.btnBack.setOnClickListener { finish() }
    }
}