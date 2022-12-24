package kr.co.sujungvillage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kr.co.sujungvillage.databinding.ActivityMainBinding
import kr.co.sujungvillage.fragment.CommFragment
import kr.co.sujungvillage.fragment.HomeFragment
import kr.co.sujungvillage.fragment.QnAFragment
import kr.co.sujungvillage.fragment.SettingFragment

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val fragmentHome by lazy { HomeFragment() }
    private val fragmentComm by lazy { CommFragment() }
    private val fragmentQnA by lazy { QnAFragment() }
    private val fragmentSetting by lazy { SettingFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initNavigationBar()
    }

    // 네비게이션 바 초기 설정 함수
    private fun initNavigationBar() {
        binding.navigationBar.itemIconTintList = null // 아이콘 색상 그대로 유지
        binding.navigationBar.run {
            setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.home -> {
                        changeFragment(fragmentHome)
                    }
                    R.id.community -> {
                        changeFragment(fragmentComm)
                    }
                    R.id.qna -> {
                        changeFragment(fragmentQnA)
                    }
                    R.id.settings -> {
                        changeFragment(fragmentSetting)
                    }
                }
                true
            }
            // 홈 프래그먼트에서 시작
            selectedItemId = R.id.home
        }
    }

    // 프래그먼트 전환 함수
    fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame, fragment)
            .commit()
    }
}
