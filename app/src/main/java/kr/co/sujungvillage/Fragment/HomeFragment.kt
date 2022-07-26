package kr.co.sujungvillage.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.co.sujungvillage.AlarmActivity
import kr.co.sujungvillage.R
import kr.co.sujungvillage.StayoutActivity
import kr.co.sujungvillage.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)

        // lottie 이미지 회전
        binding.imgWave.rotationX = 180f

        // 외박 신청 버튼 연결
        binding.btnStayout.setOnClickListener {
            var intent = Intent(this.activity, StayoutActivity::class.java)
            startActivity(intent)
        }

        //알림 버튼 연결
        binding.btnAlarm.setOnClickListener {
            var intent = Intent(this.activity, AlarmActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }
}