package kr.co.sujungvillage

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kr.co.sujungvillage.data.NoticeDetailResultDTO
import kr.co.sujungvillage.databinding.ActivityNoticeDetailBinding
import kr.co.sujungvillage.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoticeDetailActivity : AppCompatActivity() {

    val binding by lazy { ActivityNoticeDetailBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 재사생 학번 불러오기
        val shared = this.getSharedPreferences("SujungVillage", Context.MODE_PRIVATE)
        val studentNum = shared?.getString("studentNum", "error").toString()

        // NoticeActivity에서 공지사항 ID 전달 받기
        val noticeId = intent.getLongExtra("noticeId", -1)

        // 뒤로가기 버튼 연결
        binding.btnBack.setOnClickListener { finish() }

        // 공지사항 상세 조회 API 연결
        RetrofitBuilder.noticeApi.noticeDetailRequest(studentNum, noticeId).enqueue(object: Callback<NoticeDetailResultDTO> {
            override fun onResponse(call: Call<NoticeDetailResultDTO>, response: Response<NoticeDetailResultDTO>) {
                Log.d("NOTICE_DETAIL_REQUEST", "id : " + response.body()?.id)
                Log.d("NOTICE_DETAIL_REQUEST", "writer : " + response.body()?.name)
                Log.d("NOTICE_DETAIL_REQUEST", "title : " + response.body()?.title)
                Log.d("NOTICE_DETAIL_REQUEST", "content : " + response.body()?.content)
                Log.d("NOTICE_DETAIL_REQUEST", "dormitory : " + response.body()?.dormitory)
                Log.d("NOTICE_DETAIL_REQUEST", "register date : " + response.body()?.regDate)
                Log.d("NOTICE_DETAIL_REQUEST", "modify date : " + response.body()?.modDate)

                binding.textDormitory.text = response.body()?.dormitory
                binding.textTitle.text = response.body()?.title
                binding.textContent.text = response.body()?.content
            }

            override fun onFailure(call: Call<NoticeDetailResultDTO>, t: Throwable) {
                Toast.makeText(this@NoticeDetailActivity, "공지사항 상세 조회 오류가 발생하였습니다.", Toast.LENGTH_SHORT).show()
                Log.d("NOTICE_DETAIL_REQUEST", "공지사항 상세 조회 실패")
                finish()
            }
        })
    }
}