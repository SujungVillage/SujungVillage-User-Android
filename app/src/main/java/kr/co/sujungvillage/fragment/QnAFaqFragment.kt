package kr.co.sujungvillage.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.co.sujungvillage.R
import kr.co.sujungvillage.databinding.FragmentQnAFaqBinding

class QnAFaqFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentQnAFaqBinding.inflate(layoutInflater, container, false)



        return binding.root
    }
}