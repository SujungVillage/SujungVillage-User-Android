package kr.co.sujungvillage.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import kr.co.sujungvillage.fragment.AppAlarmFragment
import kr.co.sujungvillage.fragment.CommAlarmFragment

private const val NUM_TABS = 2

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager,lifecycle) {


    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return AppAlarmFragment()
            1 -> return CommAlarmFragment()
        }
        return AppAlarmFragment()
    }
}