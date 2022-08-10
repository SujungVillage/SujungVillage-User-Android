package kr.co.sujungvillage.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kr.co.sujungvillage.R
import kr.co.sujungvillage.adapter.QnAFaqAdapter
import kr.co.sujungvillage.data.FaqGetResultDTO
import kr.co.sujungvillage.databinding.FragmentQnAFaqBinding
import kr.co.sujungvillage.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QnAFaqFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentQnAFaqBinding.inflate(layoutInflater, container, false)

        // 재사생 학번 불러오기
        val shared = this.activity?.getSharedPreferences("SujungVillage", Context.MODE_PRIVATE)
        val studentNum = shared?.getString("studentNum", "error").toString()

        // FAQ 리스트 조회 API 연결
        RetrofitBuilder.qnaApi.faqGet(studentNum).enqueue(object : Callback<List<FaqGetResultDTO>> {
            override fun onResponse(call: Call<List<FaqGetResultDTO>>, response: Response<List<FaqGetResultDTO>>) {
                Log.d("FAQ_GET", "FAQ 리스트 조회 성공")
                Log.d("FAQ_GET", response.body().toString())

                // 어댑터 연결
                val faqList: MutableList<FaqGetResultDTO> = mutableListOf()
                for (info in response.body()!!) {
                    faqList.add(FaqGetResultDTO(info.id, info.question, info.dormitory))
                }
                val adapter = QnAFaqAdapter()
                adapter.faqList = faqList
                binding.recylceFaq.adapter = adapter
                binding.recylceFaq.layoutManager = LinearLayoutManager(activity)
            }

            override fun onFailure(call: Call<List<FaqGetResultDTO>>, t: Throwable) {
                Log.e("FAQ_GET", "FAQ 리스트 조회 실패")
                Log.e("FAQ_GET", t.message.toString())
            }
        })

        return binding.root
    }
}