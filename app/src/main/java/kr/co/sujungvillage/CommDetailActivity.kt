package kr.co.sujungvillage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kr.co.sujungvillage.data.CommDetailResultDTO
import kr.co.sujungvillage.databinding.ActivityCommDetailBinding
import kr.co.sujungvillage.databinding.ActivityNoticeBinding
import kr.co.sujungvillage.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommDetailActivity : AppCompatActivity() {

    val binding by lazy { ActivityCommDetailBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 뒤로가기 버튼 연결
        binding.btnBack.setOnClickListener { finish() }
        //Api 연결**어댑터 연결
        var userId="20180001"
        var postId="40"//이전 페이지(commFragment)에서 intent로 넘겨 받음
        RetrofitBuilder.communityApi.commDetail(userId,postId).enqueue(object: Callback<CommDetailResultDTO>{
            override fun onResponse(call: Call<CommDetailResultDTO>, response: Response<CommDetailResultDTO>) {
                //어댑터 연결
                Log.d("COMMDETAIL",response.body().toString())

            }

            override fun onFailure(call: Call<CommDetailResultDTO>, t: Throwable) {
                Log.d("COMMDETAIL","커뮤니티 상세 조회 실패")
            }
        })
    }
}