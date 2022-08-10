package kr.co.sujungvillage

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kr.co.sujungvillage.base.hideKeyboard
import kr.co.sujungvillage.data.MyqWriteDTO
import kr.co.sujungvillage.data.MyqWriteResultDTO
import kr.co.sujungvillage.databinding.ActivityQnAwriteBinding
import kr.co.sujungvillage.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QnAWriteActivity : AppCompatActivity() {

    val binding by lazy { ActivityQnAwriteBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 재사생 학번 불러오기
        val shared = this.getSharedPreferences("SujungVillage", Context.MODE_PRIVATE)
        val studentNum = shared?.getString("studentNum", "error").toString()

        // 키보드 내리기
        binding.layout.setOnClickListener { this.hideKeyboard() }
        binding.linear.setOnClickListener { this.hideKeyboard() }

        // 뒤로가기 버튼 연결
        binding.btnBack.setOnClickListener { finish() }

        // 등록 버튼 연결
        binding.btnUpload.setOnClickListener {
            // 비어있는 항목이 존재하는 경우
            if (binding.editTitle.text.isEmpty()) {
                Toast.makeText(this, "제목을 작성해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (binding.editContent.text.isEmpty()) {
                Toast.makeText(this, "내용을 작성해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            var title = binding.editTitle.text.toString()
            var content = binding.editContent.text.toString()
            // ★★★ 익명 여부도 추가
            val questionInfo = MyqWriteDTO(title, content)

            // 질문 작성 API 연결
            RetrofitBuilder.qnaApi.myqWrite(studentNum, questionInfo).enqueue(object : Callback<MyqWriteResultDTO> {
                override fun onResponse(call: Call<MyqWriteResultDTO>, response: Response<MyqWriteResultDTO>) {
                    Log.d("MY_QUESTION_WRITE", "질문 작성 성공")
                    Log.d("MY_QUESTION_WRITE", response.body().toString())
                    Toast.makeText(this@QnAWriteActivity, "질문이 등록되었습니다.", Toast.LENGTH_SHORT).show()
                    finish()
                }

                override fun onFailure(call: Call<MyqWriteResultDTO>, t: Throwable) {
                    Log.e("MY_QUESTION_WRITE", "질문 작성 실패")
                    Log.e("MY_QUESTION_WRITE", t.message.toString())
                }
            })
        }
    }
}