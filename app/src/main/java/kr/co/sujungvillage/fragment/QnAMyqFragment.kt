package kr.co.sujungvillage.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.co.sujungvillage.QnAWriteActivity
import kr.co.sujungvillage.R
import kr.co.sujungvillage.databinding.FragmentQnAMyqBinding

class QnAMyqFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentQnAMyqBinding.inflate(inflater, container, false)

        // 글쓰기 버튼 연결
        binding.btnWrite.setOnClickListener {
            var intent = Intent(this.activity, QnAWriteActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }
}