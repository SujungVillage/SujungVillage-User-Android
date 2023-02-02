package kr.co.sujungvillage

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
        val token = shared?.getString("token", "error").toString()

        // 이전 페이지(QnAMyqFragment)에서 questionId 전달 받기
        val questionId = intent.getLongExtra("questionId", -1)

        // 뒤로가기 버튼 연결
        binding.btnBack.setOnClickListener { finish() }

        // 삭제하기 버튼 연결
        binding.btnDelete.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("정말 삭제하시겠습니까?")
            builder.setPositiveButton(
                "확인",
                DialogInterface.OnClickListener { dialog, i ->
                    // 질문 삭제 API 연결
                    RetrofitBuilder.qnaApi.myqDelete(token, questionId)
                        .enqueue(object : Callback<Void> {
                            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                Log.d("MY_QUESTION_DELETE", "내 질문 삭제 성공")
                                Log.d("MY_QUESTION_DELETE", "response : " + response.body())

                                Toast.makeText(
                                    this@QnADetailActivity,
                                    "삭제되었습니다.",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                finish()
                            }

                            override fun onFailure(call: Call<Void>, t: Throwable) {
                                Log.e("MY_QUESTION_DELETE", "내 질문 삭제 실패")
                                Log.e("MY_QUESTION_DELETE", t.message.toString())
                            }
                        })
                }
            )
            builder.setNegativeButton(
                "취소",
                DialogInterface.OnClickListener { dialog, i ->
                    dialog.cancel()
                }
            )
            builder.show()
        }

        // 내 질문 상세 조회 API 연결
        RetrofitBuilder.qnaApi.myqDetailGet(token, questionId)
            .enqueue(object : Callback<MyqDetailGetResultDTO> {
                override fun onResponse(
                    call: Call<MyqDetailGetResultDTO>,
                    response: Response<MyqDetailGetResultDTO>
                ) {
                    if (response.isSuccessful) {
                        Log.d("MY_QUESTION_DETAIL", "내 질문 상세 조회 성공")
                        Log.d("MY_QUESTION_DETAIL", response.body().toString())

                        binding.textTitle.text = response.body()?.question?.title
                        binding.textDate.text = "${
                        response.body()?.question?.reqDate?.subSequence(
                            0,
                            10
                        )
                        } ${response.body()?.question?.reqDate?.subSequence(11, 16)}"
                        binding.textWriter.text =
                            "작성자 : ${if (response.body()?.question?.isAnonymous == true) "익명" else response.body()?.question?.name + "(" + response.body()?.question?.userId + ")"}"
                        binding.textContent.text = response.body()?.question?.content

                        if (response.body()?.answer == null) {
                            binding.layoutAnswer.visibility = View.GONE
                            binding.layoutQuestion.setBackgroundResource(R.drawable.style_qna_detail_answer)
                            return
                        } else {
                            binding.btnDelete.visibility = View.GONE
                        }

                        binding.textAnswerDate.text = "${
                        response.body()?.answer?.regDate?.subSequence(
                            0,
                            10
                        )
                        } ${response.body()?.answer?.regDate?.subSequence(11, 16)}"
                        binding.textAnswer.text = response.body()?.answer?.content
                    } else {
                        val builder =
                            androidx.appcompat.app.AlertDialog.Builder(this@QnADetailActivity)
                        builder.setTitle("글이 존재하지 않습니다.")
                            .setPositiveButton(
                                "확인",
                                DialogInterface.OnClickListener { dialog, id ->
                                    Log.d("COMM_DETAIL", "글이 존재하지 않음")
                                    finish()
                                }
                            )
                        builder.show()
                    }
                }

                override fun onFailure(call: Call<MyqDetailGetResultDTO>, t: Throwable) {
                    Log.e("MY_QUESTION_DETAIL", "내 질문 상세 조회 실패")
                    Log.e("MY_QUESTION_DETAIL", t.message.toString())

                    Toast.makeText(this@QnADetailActivity, "불러올 수 없는 질문입니다.", Toast.LENGTH_SHORT)
                        .show()
                    finish()
                }
            })
    }
}
