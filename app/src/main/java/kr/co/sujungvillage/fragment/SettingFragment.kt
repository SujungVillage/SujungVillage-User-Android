package kr.co.sujungvillage.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kr.co.sujungvillage.AlarmActivity
import kr.co.sujungvillage.LoginActivity
import kr.co.sujungvillage.data.HomeInfoResultDTO
import kr.co.sujungvillage.databinding.FragmentSettingBinding
import kr.co.sujungvillage.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class SettingFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentSettingBinding.inflate(layoutInflater)

        // 재사생 학번 불러오기
        val shared = this.activity?.getSharedPreferences("SujungVillage", Context.MODE_PRIVATE)
        val studentNum = shared?.getString("studentNum", "error").toString()
        val token = shared?.getString("token", "error").toString()

        // 알림 버튼 연결
        binding.btnAlarm.setOnClickListener {
            var intent = Intent(this.activity, AlarmActivity::class.java)
            startActivity(intent)
        }

        // 알람 버튼 초기화
        if(shared!!.getBoolean("alarm", false)) {
            binding.switchAlarm.isChecked = true
        }

        // 설정 화면 정보 조회 API 연결 (홈 화면 정보 조회 API 활용)
        RetrofitBuilder.homeApi.homeInfo(token, Calendar.YEAR.toString(), Calendar.MONTH.toString()).enqueue(object:
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

        // 알람 설정 버튼 연결
        binding.switchAlarm.setOnCheckedChangeListener { button, check ->
            val editor = shared?.edit()
            if (check) editor?.putBoolean("alarm", true)
            else editor?.putBoolean("alarm", false)
            editor?.apply()
        }
        // 성신 포탈 버튼 연결
        binding.layoutPortal.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://portal.sungshin.ac.kr/sso/login.jsp"))
            startActivity(intent)
        }
        // 앱 사용법 버튼 연결
        binding.layoutManual.setOnClickListener {
            Toast.makeText(this.activity, "준비 중인 기능입니다!", Toast.LENGTH_SHORT).show()
        }
        // 앱 문의하기 버튼 연결
        binding.layoutInquire.setOnClickListener {
            val email = Intent(Intent.ACTION_SEND)
            email.type = "plain/text"
            val address = arrayOf("sujungvillage@gmail.com")
            email.putExtra(Intent.EXTRA_EMAIL, address)
            startActivity(email)
        }
        // 로그아웃 버튼 연결
        binding.layoutLeave.setOnClickListener {
            Toast.makeText(this.activity, "로그아웃되었습니다.", Toast.LENGTH_SHORT).show()
            var intent = Intent(this.activity, LoginActivity::class.java)
            startActivity(intent)
            this.activity?.finish()
        }

        return binding.root
    }
}