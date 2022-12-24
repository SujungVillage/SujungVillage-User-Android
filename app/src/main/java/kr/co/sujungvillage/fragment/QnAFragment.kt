package kr.co.sujungvillage.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import kr.co.sujungvillage.AlarmActivity
import kr.co.sujungvillage.adapter.QnAPagerAdapter
import kr.co.sujungvillage.databinding.FragmentQnABinding

class QnAFragment : Fragment() {
    private val tabTitleArray = arrayOf("FAQ", "내 질문")

    val binding by lazy { FragmentQnABinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentQnABinding.inflate(inflater, container, false)

        // 로컬에서 알림 읽음 여부 가져오기
        val shared = this.activity?.getSharedPreferences("SujungVillage", Context.MODE_PRIVATE)
        val read = shared?.getBoolean("alarmRead", true)

        // 프래그먼트 전환 상황을 저장하지 않음 (아니면 오류 발생)
        binding.viewPager.isSaveEnabled = false

        // 알림 버튼 연결
        binding.btnAlarm.setOnClickListener {
            var intent = Intent(this.activity, AlarmActivity::class.java)
            startActivity(intent)
        }

        // 읽음/안 읽음 처리
        if (!read!!) {
            binding.imgUnread.visibility = View.VISIBLE
        } else {
            binding.imgUnread.visibility = View.INVISIBLE
        }

        // viewPager와 tabLayout 연결
        binding.viewPager.adapter =
            this.activity?.let { QnAPagerAdapter(childFragmentManager, lifecycle) }

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitleArray[position]
        }.attach()

        return binding.root
    }
}
