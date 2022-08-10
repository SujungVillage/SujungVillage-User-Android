package kr.co.sujungvillage

import android.content.Context
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kr.co.sujungvillage.data.MyqDetailGetResultDTO
import kr.co.sujungvillage.databinding.ActivityQnAdetailBinding
import kr.co.sujungvillage.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class QnADetailActivity : AppCompatActivity() {
    val binding by lazy { ActivityQnAdetailBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 재사생 학번 불러오기
        val shared = this.getSharedPreferences("SujungVillage", Context.MODE_PRIVATE)
        val studentNum = shared?.getString("studentNum", "error").toString()

        // 이전 페이지(QnAMyqFragment)에서 questionId 전달 받기
        val questionId = intent.getLongExtra("questionId", -1)

        // 뒤로가기 버튼 연결
        binding.btnBack.setOnClickListener { finish() }

        // 내 질문 상세 조회 API 연결
        RetrofitBuilder.qnaApi.myqDetailGet(studentNum.toLong(), questionId).enqueue(object: Callback<MyqDetailGetResultDTO> {
            override fun onResponse(call: Call<MyqDetailGetResultDTO>, response: Response<MyqDetailGetResultDTO>) {
                Log.d("MY_QUESTION_DETAIL", "내 질문 상세 조회 성공")
                Log.d("MY_QUESTION_DETAIL", response.body().toString())

                binding.textTitle.text = response.body()?.question?.title
                binding.textDate.text = "${response.body()?.question?.reqDate?.subSequence(0, 10)} ${response.body()?.question?.reqDate?.subSequence(11, 16)}"
                binding.textContent.text = response.body()?.question?.content

                if (response.body()?.answer == null) {
                    binding.layoutAnswer.visibility = View.GONE
                    binding.layoutQuestion.setBackgroundResource(R.drawable.style_qna_detail_answer)
                    return
                }

                binding.textAnswerDate.text = "${response.body()?.answer?.regDate?.subSequence(0, 10)} ${response.body()?.answer?.regDate?.subSequence(11, 16)}"
                binding.textAnswer.text = response.body()?.answer?.content
            }

            override fun onFailure(call: Call<MyqDetailGetResultDTO>, t: Throwable) {
                Log.e("MY_QUESTION_DETAIL", "내 질문 상세 조회 실패")
                Log.e("MY_QUESTION_DETAIL", t.message.toString())
            }
        })
    }
}