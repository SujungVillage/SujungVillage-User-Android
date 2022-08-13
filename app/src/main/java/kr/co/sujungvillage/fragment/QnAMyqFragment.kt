package kr.co.sujungvillage.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.sujungvillage.QnAWriteActivity
import kr.co.sujungvillage.adapter.QnAMyqAdapter
import kr.co.sujungvillage.data.MyqGetResultDTO
import kr.co.sujungvillage.databinding.FragmentQnAMyqBinding
import kr.co.sujungvillage.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class QnAMyqFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentQnAMyqBinding.inflate(layoutInflater, container, false)

        // 재사생 정보 불러오기
        val shared = this.activity?.getSharedPreferences("SujungVillage", Context.MODE_PRIVATE)
        val token = shared?.getString("token", "error").toString()

        // 내 질문 리스트 불러오기
        loadQuestionData(token, binding.recycleQuestion)

        // Swipe Refresh 버튼 연결
        binding.swipe.setOnRefreshListener {
            loadQuestionData(token, binding.recycleQuestion)
            binding.swipe.isRefreshing = false
        }

        // 글쓰기 버튼 연결
        binding.btnWrite.setOnClickListener {
            var intent = Intent(this.activity, QnAWriteActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    // 내 질문 리스트 조회 함수
    fun loadQuestionData(token: String, recycleQuestion: RecyclerView) {
        // 내 질문 리스트 조회 API 연결
        RetrofitBuilder.qnaApi.myqGet(token).enqueue(object: Callback<List<MyqGetResultDTO>> {
            override fun onResponse(call: Call<List<MyqGetResultDTO>>, response: Response<List<MyqGetResultDTO>>) {
                Log.d("MY_QUESTION_GET", "내 질문 리스트 조회 성공")
                Log.d("MY_QUESTION_GET", "response : " + response.body().toString())

                // 어댑터 연결
                val myqList: MutableList<MyqGetResultDTO> = mutableListOf()
                for (info in response.body()!!) {
                    myqList.add(MyqGetResultDTO(info.id, info.title, info.date, info.isAnswered))
                }
                var adapter = QnAMyqAdapter()
                adapter.myqList = myqList
                recycleQuestion.adapter = adapter
                recycleQuestion.layoutManager = LinearLayoutManager(activity)
            }

            override fun onFailure(call: Call<List<MyqGetResultDTO>>, t: Throwable) {
                Log.e("MY_QUESTION_GET", "내 질문 리스트 조회 실패")
                Log.e("MY_QUESTION_GET", t.message.toString())
            }
        })
    }
}