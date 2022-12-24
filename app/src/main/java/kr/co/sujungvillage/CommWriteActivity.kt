package kr.co.sujungvillage

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kr.co.sujungvillage.base.hideKeyboard
import kr.co.sujungvillage.data.CommWriteDTO
import kr.co.sujungvillage.data.CommWriteResultDTO
import kr.co.sujungvillage.databinding.ActivityCommWriteBinding
import kr.co.sujungvillage.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommWriteActivity : AppCompatActivity() {

    val binding by lazy { ActivityCommWriteBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 재사생 학번 불러오기
        val shared = this.getSharedPreferences("SujungVillage", Context.MODE_PRIVATE)
        val studentNum = shared?.getString("studentNum", "error").toString()
        val token = shared?.getString("token", "error").toString()

        // 키보드 내리기
        binding.layout.setOnClickListener { this.hideKeyboard() }
        binding.linear.setOnClickListener { this.hideKeyboard() }
        binding.linearInfo.setOnClickListener { this.hideKeyboard() }

        // 뒤로가기 버튼 연결
        binding.btnBack.setOnClickListener { finish() }

        // 등록 버튼 연결
        binding.btnUpload.setOnClickListener {
            // 버튼 클릭시 제목과 내용 받아오기.
            val title = binding.editTitle.text.toString().trim() // 앞뒤 공백 제거
            val content = binding.editContent.text.toString().trim() // 앞뒤 공백 제거

            // 내용이 null인지 확인 필요
            if (title.isEmpty()) {
                if (content.isEmpty()) {
                    Toast.makeText(this, "제목과 내용을 입력하세요. $title, $content", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(this, "제목을 입력하세요. $title, $content", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                if (content.isEmpty()) {
                    Toast.makeText(this, "내용을 입력하세요. $title, $content", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    // 서버에 보내기
                    val commWriteInfo = CommWriteDTO(title, content)
                    RetrofitBuilder.communityApi.commWrite(token, commWriteInfo)
                        .enqueue(object : Callback<CommWriteResultDTO> {
                            override fun onResponse(
                                call: Call<CommWriteResultDTO>,
                                response: Response<CommWriteResultDTO>
                            ) {
                                Log.d("COMM_WRITE", response.body().toString())
                                setResult(RESULT_OK)
                                finish()
                            }

                            override fun onFailure(call: Call<CommWriteResultDTO>, t: Throwable) {
                                Log.d("COMMW_RITE", t.message.toString())
                            }
                        })
                }
            }
        }
    }
}
