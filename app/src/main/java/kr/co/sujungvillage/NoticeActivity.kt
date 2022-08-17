package kr.co.sujungvillage

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kr.co.sujungvillage.CommDetailActivity.Companion.token
import kr.co.sujungvillage.adapter.NoticeAdapter
import kr.co.sujungvillage.data.NoticeRequestResultDTO
import kr.co.sujungvillage.databinding.ActivityNoticeBinding
import kr.co.sujungvillage.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoticeActivity : AppCompatActivity() {

    val binding by lazy { ActivityNoticeBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 재사생 학번 불러오기
        val shared = this.getSharedPreferences("SujungVillage", Context.MODE_PRIVATE)
        val token = shared?.getString("token", "error").toString()

        // 뒤로가기 버튼 연결
        binding.btnBack.setOnClickListener { finish() }

        // 공지사항 리스트 불러오기
        loadNoticeData(token)

        // Swipe Refresh 버튼 연결
        binding.swipe.setOnRefreshListener {
            loadNoticeData(token)
            binding.swipe.isRefreshing = false
        }
        binding.scroll.viewTreeObserver.addOnScrollChangedListener {
            binding.swipe.isEnabled=(binding.scroll.scrollY==0)
        }
    }

    // 공지사항 리스트 불러오기 함수
    fun loadNoticeData(token: String) {
        // 공지사항 리스트 조회 API 연결
        RetrofitBuilder.noticeApi.noticeRequest(token, "전체").enqueue(object: Callback<List<NoticeRequestResultDTO>> {
            override fun onResponse(call: Call<List<NoticeRequestResultDTO>>, response: Response<List<NoticeRequestResultDTO>>) {
                Log.d("NOTICE_REQUEST", "size of notice list : " + response.body()?.size.toString())

                // 공지사항이 존재하지 않는 경우
                if (response.body()?.size == 0) {
                    binding.textNone.visibility = View.VISIBLE
                    return
                } else {
                    binding.textNone.visibility = View.GONE
                }

                val noticeList: MutableList<NoticeRequestResultDTO> = mutableListOf()
                for (info in response.body()!!) {
                    var notice = NoticeRequestResultDTO(info.id, info.title, info.dormitory, info.date)
                    noticeList.add(notice)
                }
                var adapter = NoticeAdapter()
                adapter.noticeList = noticeList
                binding.recyclerNotice.adapter = adapter
                binding.recyclerNotice.layoutManager = LinearLayoutManager(this@NoticeActivity)
            }

            override fun onFailure(call: Call<List<NoticeRequestResultDTO>>, t: Throwable) {
                Toast.makeText(this@NoticeActivity, "공지사항 조회 오류가 발생하였습니다.", Toast.LENGTH_SHORT).show()
                Log.e("NOTICE_REQUEST", "공지사항 리스트 조회 실패")
                Log.e("NOTICE_REQUEST", t.message.toString())
            }
        })
    }
}