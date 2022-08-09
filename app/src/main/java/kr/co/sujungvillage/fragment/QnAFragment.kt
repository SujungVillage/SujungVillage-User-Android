package kr.co.sujungvillage.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import kr.co.sujungvillage.adapter.QnAPagerAdapter
import kr.co.sujungvillage.databinding.ActivityAlarmBinding
import kr.co.sujungvillage.databinding.FragmentQnABinding

class QnAFragment : Fragment() {
    private val tabTitleArray = arrayOf("FAQ", "내 질문")

    val binding by lazy { FragmentQnABinding.inflate(layoutInflater) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentQnABinding.inflate(inflater, container, false)

        // 프래그먼트 전환 상황을 저장하지 않음 (아니면 오류 발생)
        binding.viewPager.isSaveEnabled = false

        // viewPager와 tabLayout 설정이 되어 있지 않은 경우 설정
        if (binding.viewPager.adapter == null) {
            binding.viewPager.adapter = this.activity?.let { QnAPagerAdapter(childFragmentManager, lifecycle) }

            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                tab.text = tabTitleArray[position]
            }.attach()
        }

        return binding.root
    }
}