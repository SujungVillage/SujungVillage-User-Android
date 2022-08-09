package kr.co.sujungvillage.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kr.co.sujungvillage.AlarmActivity
import kr.co.sujungvillage.CommWriteActivity
import kr.co.sujungvillage.R
import kr.co.sujungvillage.databinding.FragmentCommBinding

class CommFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentCommBinding.inflate(inflater,container,false)

        // 알림 버튼 연결
        binding.btnAlarm.setOnClickListener {
            var intent = Intent(this.activity, AlarmActivity::class.java)
            startActivity(intent)
        }

        // 기숙사 스피너 연결 및 커스텀
        binding.spinnerDormitory.adapter = ArrayAdapter.createFromResource(requireContext(), R.array.dormitory, R.layout.spinner_comm_dormitory)

        // 글 작성 버튼 연결
        binding.btnWrite.setOnClickListener {
            var intent = Intent(this.activity, CommWriteActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }
}