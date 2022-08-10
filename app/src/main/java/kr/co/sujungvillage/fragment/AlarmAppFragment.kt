package kr.co.sujungvillage.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.co.sujungvillage.databinding.FragmentAppAlarmBinding

class AlarmAppFragment : Fragment() {
    private lateinit var binding: FragmentAppAlarmBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentAppAlarmBinding.inflate(inflater,container,false)
        return binding.root
    }
}