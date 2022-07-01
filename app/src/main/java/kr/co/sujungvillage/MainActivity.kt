package kr.co.sujungvillage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import kr.co.sujungvillage.Fragment.CommFragment
import kr.co.sujungvillage.Fragment.HomeFragment
import kr.co.sujungvillage.Fragment.QnAFragment
import kr.co.sujungvillage.Fragment.SettingFragment
import kr.co.sujungvillage.databinding.ActivityMainBinding

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

    private fun initNavigationBar() {
        binding.navigationBar.run {
            setOnNavigationItemSelectedListener {
                when(it.itemId) {
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
            selectedItemId = R.id.home
        }
    }

    fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame, fragment)
            .commit()
    }
}