package kr.co.sujungvillage.fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import kr.co.sujungvillage.*
import kr.co.sujungvillage.data.HomeInfoResultDTO
import kr.co.sujungvillage.databinding.FragmentHomeBinding
import kr.co.sujungvillage.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)

        // ★★★ 재사생 학번 불러오기
        val studentNum = "20180001"

        // lottie 이미지 회전
        binding.imgWave.rotationX = 180f

        // 알림 버튼 연결
        binding.btnAlarm.setOnClickListener {
            var intent = Intent(this.activity, AlarmActivity::class.java)
            startActivity(intent)
        }

        // 홈화면 주요 기능 버튼 연결
        // 1. 외박 신청 버튼 연결
        binding.btnStayout.setOnClickListener {
            var intent = Intent(this.activity, StayoutActivity::class.java)
            startActivity(intent)
        }
        // 2. 점호 버튼 연결
        binding.btnRollCall.setOnClickListener {
            var intent = Intent(this.activity, RollcallActivity::class.java)
            startActivity(intent)
        }
        // 3. 공지사항 버튼 연결
        binding.btnNotice.setOnClickListener {
            var intent = Intent(this.activity, NoticeActivity::class.java)
            startActivity(intent)
        }
        // 4. 상벌점 조회 버튼 연결
        binding.btnReward.setOnClickListener {
            var intent = Intent(this.activity, RewardActivity::class.java)
            startActivity(intent)
        }

        // 학생 홈 화면 정보 조회 API 연결
        RetrofitBuilder.homeApi.homeInfo(studentNum, binding.calendar.currentDate.year.toString(), binding.calendar.currentDate.month.toString()).enqueue(object: Callback<HomeInfoResultDTO> {
            override fun onResponse(call: Call<HomeInfoResultDTO>, response: Response<HomeInfoResultDTO>) {
                Log.d("HOME_INFO", "홈 화면 정보 조회 성공")
                Log.d("HOME_INFO", "user : " + response.body()?.residentInfo.toString())
                Log.d("HOME_INFO", "roll-call days : " + response.body()?.rollcallDays.toString())
                Log.d("HOME_INFO", "applied roll-call days : " + response.body()?.appliedDays.toString())
                Log.d("HOME_INFO", "applied stayout days : " + response.body()?.staoutDays.toString())

                // 유저 정보 반영
                binding.textName.text = response.body()?.residentInfo?.name
                binding.textDormitory.text = response.body()?.residentInfo?.dormitory + " 기숙사 " + response.body()?.residentInfo?.address
                binding.textRewards.text = "상점 : ${response.body()?.residentInfo?.plusLMP}점    |    벌점 : ${response.body()?.residentInfo?.minusLMP}점"

                // 캘린더 정보 반영
                val rollcallDecorator = RollcallDecorator(this@HomeFragment, response.body()!!.rollcallDays)
                val stayoutDecorator = StayoutDecorator(this@HomeFragment, response.body()!!.staoutDays)
                val todayDecorator = TodayDecorator(this@HomeFragment)
                binding.calendar.addDecorators(rollcallDecorator, stayoutDecorator, todayDecorator)
            }

            override fun onFailure(call: Call<HomeInfoResultDTO>, t: Throwable) {
                Log.d("HOME_INFO", "홈 화면 정보 조회 실패")
                Log.d("HOME_INFO", t.message.toString())
            }
        })

        // 캘린더 좌우 버튼 연결 (홈 화면 정보 조회 API 활용)
        binding.calendar.setOnMonthChangedListener { widget, date ->
            RetrofitBuilder.homeApi.homeInfo(studentNum, date.year.toString(), date.month.toString()).enqueue(object: Callback<HomeInfoResultDTO> {
                override fun onResponse(call: Call<HomeInfoResultDTO>, response: Response<HomeInfoResultDTO>) {
                    Log.d("HOME_INFO", "캘린더 정보 조회 성공")
                    Log.d("HOME_INFO", "roll-call days : " + response.body()?.rollcallDays.toString())
                    Log.d("HOME_INFO", "applied roll-call days : " + response.body()?.appliedDays.toString())
                    Log.d("HOME_INFO", "applied stayout days : " + response.body()?.staoutDays.toString())

                    // 캘린더 정보 반영
                    val rollcallDecorator = RollcallDecorator(this@HomeFragment, response.body()!!.rollcallDays)
                    val stayoutDecorator = StayoutDecorator(this@HomeFragment, response.body()!!.staoutDays)
                    val todayDecorator = TodayDecorator(this@HomeFragment)
                    binding.calendar.addDecorators(rollcallDecorator, stayoutDecorator, todayDecorator)
                }

                override fun onFailure(call: Call<HomeInfoResultDTO>, t: Throwable) {
                    Log.d("HOME_INFO", "캘린더 정보 조회 실패")
                    Log.d("HOME_INFO", t.message.toString())
                }
            })
        }

        return binding.root
    }
}

// 점호일 커스텀 함수
class RollcallDecorator(context: HomeFragment, days: List<Int>): DayViewDecorator {
    val rollcallDrawable = context.resources.getDrawable(R.drawable.style_home_cal_rollcall)
    val days = days

    override fun shouldDecorate(day: CalendarDay?): Boolean { // 커스텀 여부 반환
        return days.contains(day?.day)
    }

    override fun decorate(view: DayViewFacade?) { // 커스텀 설정
        view?.setBackgroundDrawable(rollcallDrawable)
    }
}

// 외박 신청일 커스텀 함수
class StayoutDecorator(context: HomeFragment, days: List<Int>): DayViewDecorator {
    val stayoutDrawable = context.resources.getDrawable(R.drawable.style_home_cal_stayout)
    val days = days

    override fun shouldDecorate(day: CalendarDay?): Boolean { // 커스텀 여부 반환
        return days.contains(day?.day)
    }

    override fun decorate(view: DayViewFacade?) { // 커스텀 설정
        view?.setBackgroundDrawable(stayoutDrawable)
    }
}

// 오늘 커스텀 함수
class TodayDecorator(context: HomeFragment): DayViewDecorator {
    override fun shouldDecorate(day: CalendarDay?): Boolean { // 커스텀 여부 반환
        return return day?.equals(CalendarDay.today())!!
    }

    override fun decorate(view: DayViewFacade?) { // 커스텀 설정
        view?.addSpan(object:ForegroundColorSpan(Color.parseColor("#FFFFA114")) {})
    }
}