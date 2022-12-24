package kr.co.sujungvillage.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.sujungvillage.adapter.QnAFaqAdapter
import kr.co.sujungvillage.data.FaqGetResultDTO
import kr.co.sujungvillage.databinding.FragmentQnAFaqBinding
import kr.co.sujungvillage.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QnAFaqFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentQnAFaqBinding.inflate(layoutInflater, container, false)

        // 재사생 학번 불러오기
        val shared = this.activity?.getSharedPreferences("SujungVillage", Context.MODE_PRIVATE)
        val token = shared?.getString("token", "error").toString()

        // FAQ 리스트 불러오기
        loadFaqData(token, binding.recycleFaq, binding.textExist)

        // Swipe Refresh 버튼 연결
        binding.swipe.setOnRefreshListener {
            loadFaqData(token, binding.recycleFaq, binding.textExist)
            binding.swipe.isRefreshing = false
        }
        binding.scroll.viewTreeObserver.addOnScrollChangedListener {
            binding.swipe.isEnabled = (binding.scroll.scrollY == 0)
        }
        return binding.root
    }

    fun loadFaqData(token: String, recycleFaq: RecyclerView, text: TextView) {
        // FAQ 리스트 조회 API 연결
        RetrofitBuilder.qnaApi.faqGet(token).enqueue(object : Callback<List<FaqGetResultDTO>> {
            override fun onResponse(
                call: Call<List<FaqGetResultDTO>>,
                response: Response<List<FaqGetResultDTO>>
            ) {
                Log.d("FAQ_GET", "FAQ 리스트 조회 성공")
                Log.d("FAQ_GET", response.body().toString())

                // 작성된 질문이 없는 경우
                if (response.body()?.size == 0) {
                    text.visibility = View.VISIBLE
                } else {
                    text.visibility = View.GONE
                }

                // 어댑터 연결
                val faqList: MutableList<FaqGetResultDTO> = mutableListOf()
                for (info in response.body()!!) {
                    faqList.add(
                        FaqGetResultDTO(
                            info.id,
                            info.userId,
                            info.question,
                            info.answer,
                            info.date,
                            info.modDate
                        )
                    )
                }
                val adapter = QnAFaqAdapter()
                adapter.faqList = faqList
                recycleFaq.adapter = adapter
                recycleFaq.layoutManager = LinearLayoutManager(activity)
            }

            override fun onFailure(call: Call<List<FaqGetResultDTO>>, t: Throwable) {
                Log.e("FAQ_GET", "FAQ 리스트 조회 실패")
                Log.e("FAQ_GET", t.message.toString())
            }
        })
    }
}
