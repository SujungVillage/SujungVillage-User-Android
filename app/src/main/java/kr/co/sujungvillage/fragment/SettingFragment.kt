package kr.co.sujungvillage.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.co.sujungvillage.AlarmActivity
import kr.co.sujungvillage.data.HomeInfoResultDTO
import kr.co.sujungvillage.databinding.FragmentSettingBinding
import kr.co.sujungvillage.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentSettingBinding.inflate(layoutInflater)

        // 사용자 학번 가져오기
        val studentNum = "20180001"

        // 알림 버튼 연결
        binding.btnAlarm.setOnClickListener {
            var intent = Intent(this.activity, AlarmActivity::class.java)
            startActivity(intent)
        }

        // 설정 화면 정보 조회 API 연결 (홈 화면 정보 조회 API 활용)
        RetrofitBuilder.homeApi.homeInfo("20180001", "2022", "8").enqueue(object:
            Callback<HomeInfoResultDTO> {
            override fun onResponse(call: Call<HomeInfoResultDTO>, response: Response<HomeInfoResultDTO>) {
                Log.d("SETTING_INFO", "설정 화면 정보 조회 성공")
                Log.d("SETTING_INFO", "user : " + response.body()?.residentInfo.toString())

                // 유저 정보 반영
                binding.textName.text = response.body()?.residentInfo?.name
                binding.textEmail.text = studentNum + "@sungshin.ac.kr"
                binding.textAddress.text = response.body()?.residentInfo?.dormitory + " | " + response.body()?.residentInfo?.address
                }

            override fun onFailure(call: Call<HomeInfoResultDTO>, t: Throwable) {
                Log.d("HOME_INFO", "설정 화면 정보 조회 실패")
                Log.d("HOME_INFO", t.message.toString())
            }
        })

        return binding.root
    }
}