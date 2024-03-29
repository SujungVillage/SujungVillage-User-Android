package kr.co.sujungvillage.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import kr.co.sujungvillage.fragment.AlarmAppFragment
import kr.co.sujungvillage.fragment.AlarmCommFragment

private const val NUM_TABS = 2

class AlarmPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return AlarmAppFragment()
            1 -> return AlarmCommFragment()
        }
        return AlarmAppFragment()
    }
}
