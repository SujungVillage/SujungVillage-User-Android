package kr.co.sujungvillage.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import kr.co.sujungvillage.*
import kr.co.sujungvillage.data.*
import kr.co.sujungvillage.databinding.FragmentHomeBinding
import kr.co.sujungvillage.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    var dayRollcall: MutableList<Int>? = mutableListOf()
    var idRollcall: MutableList<Long>? = mutableListOf()
    var dayStayout: MutableList<Int>? = mutableListOf()
    var idStayout: MutableList<Long>? = mutableListOf()
    var dayApplied: MutableList<Int>? = mutableListOf()
    var idApplied: MutableList<Long>? = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)

        // 재사생 학번, 토큰 불러오기
        val shared = this.activity?.getSharedPreferences("SujungVillage", Context.MODE_PRIVATE)
        val studentNum = shared?.getString("studentNum", "error").toString()
        val token = shared?.getString("token", "error").toString()

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
        RetrofitBuilder.homeApi.homeInfo(token, binding.calendar.currentDate.year.toString(), binding.calendar.currentDate.month.toString()).enqueue(object: Callback<HomeInfoResultDTO> {
            override fun onResponse(call: Call<HomeInfoResultDTO>, response: Response<HomeInfoResultDTO>) {
                Log.d("HOME_INFO", "홈 화면 정보 조회 성공")
                Log.d("HOME_INFO", "user : " + response.body()?.residentInfo.toString())
                Log.d("HOME_INFO", "roll-call days : " + response.body()?.rollcallDays.toString())
                Log.d("HOME_INFO", "applied roll-call days : " + response.body()?.appliedDays.toString())
                Log.d("HOME_INFO", "applied stayout days : " + response.body()?.stayoutDays.toString())
                dayRollcall?.clear()
                idRollcall?.clear()
                for (rollcallDay in response.body()?.rollcallDays!!) {
                    dayRollcall?.add(rollcallDay.day)
                    idRollcall?.add(rollcallDay.id)
                }
                dayStayout?.clear()
                idStayout?.clear()
                for (stayoutDay in response.body()?.stayoutDays!!) {
                    dayStayout?.add(stayoutDay.day)
                    idStayout?.add(stayoutDay.id)
                }
                dayApplied?.clear()
                idApplied?.clear()
                for (appliedDay in response.body()?.appliedDays!!) {
                    dayApplied?.add(appliedDay.day)
                    idApplied?.add(appliedDay.id)
                }

                // 유저 정보 반영
                binding.textName.text = response.body()?.residentInfo?.name
                binding.textDormitory.text = response.body()?.residentInfo?.dormitory + " 기숙사 " + response.body()?.residentInfo?.address
                binding.textRewards.text = "상점 : ${response.body()?.residentInfo?.plusLMP}점    |    벌점 : ${response.body()?.residentInfo?.minusLMP}점"

                // 캘린더 정보 반영
                val defaultDecorator = DefaultDecorator(this@HomeFragment)
                val todayDecorator = TodayDecorator(this@HomeFragment, dayStayout)
                val rollcallDecorator = RollcallDecorator(this@HomeFragment, dayRollcall, binding.calendar.currentDate.month)
                val stayoutDecorator = StayoutDecorator(this@HomeFragment, dayStayout, binding.calendar.currentDate.month)
                val missDecorator = MissDecorator(this@HomeFragment, dayRollcall, dayApplied, binding.calendar.currentDate.month)
                binding.calendar.addDecorators(defaultDecorator, todayDecorator, rollcallDecorator, stayoutDecorator, missDecorator)
            }

            override fun onFailure(call: Call<HomeInfoResultDTO>, t: Throwable) {
                Log.d("HOME_INFO", "홈 화면 정보 조회 실패")
                Log.d("HOME_INFO", t.message.toString())
            }
        })

        // 캘린더 좌우 버튼 연결 (홈 화면 정보 조회 API 활용)
        binding.calendar.setOnMonthChangedListener { widget, date ->
            RetrofitBuilder.homeApi.homeInfo(token, date.year.toString(), date.month.toString()).enqueue(object: Callback<HomeInfoResultDTO> {
                override fun onResponse(call: Call<HomeInfoResultDTO>, response: Response<HomeInfoResultDTO>) {
                    Log.d("HOME_INFO", "캘린더 정보 조회 성공")
                    Log.d("HOME_INFO", "roll-call days : " + response.body()?.rollcallDays.toString())
                    Log.d("HOME_INFO", "applied roll-call days : " + response.body()?.appliedDays.toString())
                    Log.d("HOME_INFO", "applied stayout days : " + response.body()?.stayoutDays.toString())
                    dayRollcall?.clear()
                    idRollcall?.clear()
                    for (rollcallDay in response.body()?.rollcallDays!!) {
                        dayRollcall?.add(rollcallDay.day)
                        idRollcall?.add(rollcallDay.id)
                    }
                    dayStayout?.clear()
                    idStayout?.clear()
                    for (stayoutDay in response.body()?.stayoutDays!!) {
                        dayStayout?.add(stayoutDay.day)
                        idStayout?.add(stayoutDay.id)
                    }
                    dayApplied?.clear()
                    idApplied?.clear()
                    for (appliedDay in response.body()?.appliedDays!!) {
                        dayApplied?.add(appliedDay.day)
                        idApplied?.add(appliedDay.id)
                    }

                    // 캘린더 정보 반영
                    val defaultDecorator = DefaultDecorator(this@HomeFragment)
                    val todayDecorator = TodayDecorator(this@HomeFragment, dayStayout)
                    val rollcallDecorator = RollcallDecorator(this@HomeFragment, dayRollcall, binding.calendar.currentDate.month)
                    val stayoutDecorator = StayoutDecorator(this@HomeFragment, dayStayout, binding.calendar.currentDate.month)
                    val missDecorator = MissDecorator(this@HomeFragment, dayRollcall, dayApplied, binding.calendar.currentDate.month)
                    binding.calendar.addDecorators(defaultDecorator, todayDecorator, rollcallDecorator, stayoutDecorator, missDecorator)
                }

                override fun onFailure(call: Call<HomeInfoResultDTO>, t: Throwable) {
                    Log.d("HOME_INFO", "캘린더 정보 조회 실패")
                    Log.d("HOME_INFO", t.message.toString())
                }
            })
        }

        // 날짜 클릭 이벤트
        binding.calendar.setOnDateChangedListener { widget, date, selected ->
            // 외박 신청 날짜를 클릭한 경우
            if (dayStayout?.contains(date.day) == true) {
                // 외박 신청 조회 API 연결
                val stayoutId = idStayout!!.get(dayStayout!!.indexOf(date.day))
                RetrofitBuilder.stayoutApi.stayoutCheck(token, stayoutId).enqueue(object: Callback<StayoutCheckResultDTO> {
                    override fun onResponse(call: Call<StayoutCheckResultDTO>, response: Response<StayoutCheckResultDTO>) {
                        // null 값이 반환되면 무시
                        if (response.body()?.id == null) {
                            Log.d("STAYOUT_CHECK", "외박 신청이 없는 날짜입니다.")
                            return
                        }

                        // 외박 예정일이면 Alert Dialog 생성
                        val builder = AlertDialog.Builder(context)
                        builder.setTitle("${response.body()?.date} 외박\n")
                        builder.setMessage("행선지 : ${response.body()?.destination}" +
                                "\n사유 : ${response.body()?.reason}" +
                                "\n긴급 전화번호 : ${response.body()?.emergencyNumber}")
                        builder.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, i ->
                            dialog.cancel()
                        })
                        // 오늘 또는 오늘 이후 날짜만 외박 취소 가능
                        if (date.isAfter(CalendarDay.today()) || date.equals(CalendarDay.today())) {
                            builder.setNegativeButton("외박 취소", DialogInterface.OnClickListener { dialog, i ->
                                // 외박 취소 API 연결
                                RetrofitBuilder.stayoutApi.stayoutCancel(token, stayoutId).enqueue(object : Callback<Void> {
                                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                        Log.d("STAYOUT_CANCEL", "외박 취소 성공")
                                        Log.d("STAYOUT_CANCEL", "response : " + response.body().toString())
                                        dialog.cancel()
                                        Toast.makeText(this@HomeFragment.activity, "외박이 취소되었습니다.", Toast.LENGTH_SHORT).show()
                                    }

                                    override fun onFailure(call: Call<Void>, t: Throwable) {
                                        Log.e("STAYOUT_CANCEL", "외박 취소 실패")
                                        Log.e("STAYOUT_CANCEL", t.message.toString())
                                    }
                                })
                            })
                        }
                        builder.show()
                    }

                    override fun onFailure(call: Call<StayoutCheckResultDTO>, t: Throwable) {
                        Log.e("STAYOUT_CHECK", "외박 신청 조회 실패")
                        Log.e("STAYOUT_CHECK", t.message.toString())
                    }
                })
            }

            // 점호 날짜를 클릭한 경우
            else if (dayRollcall?.contains(date.day) == true) {
                // 점호에 참여한 경우
                if (dayApplied?.contains(date.day) == true) {
                    // 점호 신청 조회 API 연결
                    val appliedId = idApplied!!.get(dayApplied!!.indexOf(date.day))
                    RetrofitBuilder.rollcallApi.appliedRollcallCheck(token, appliedId!!).enqueue(object: Callback<AppliedRollcallCheckResultDTO> {
                        override fun onResponse(call: Call<AppliedRollcallCheckResultDTO>, response: Response<AppliedRollcallCheckResultDTO>) {
                            Log.d("APPLIED_ROLLCALL_CHECK", "점호 신청 조회")
                            Log.d("APPLIED_ROLLCALL_CHECK", "response : " + response.body().toString())
                            Log.d("APPLIED_ROLLCALL_CHECK", "code : " + response.code().toString())
                            Log.d("APPLIED_ROLLCALL_CHECK", "message : " + response.message().toString())

                            // 점호 참여 Alert Dialog 생성
                            val builder = AlertDialog.Builder(context)
                            builder.setTitle("${date.date} 점호")
                            builder.setMessage("참여 시각 : ${response.body()?.date?.subSequence(0, 10)} ${response.body()?.date?.subSequence(11, 19)}" +
                                "\n참여 위치 : ${response.body()?.location}")
                            builder.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, i ->
                                dialog.cancel()
                            })
                            builder.show()
                        }

                        override fun onFailure(call: Call<AppliedRollcallCheckResultDTO>, t: Throwable) {
                            Log.e("APPLIED_ROLLCALL_CHECK", "점호 신청 조회 실패")
                            Log.e("APPLIED_ROLLCALL_CHECK", t.message.toString())
                        }
                    })
                    return@setOnDateChangedListener
                }

                // 무단 외박인 경우
                if (date.isBefore(CalendarDay.today())) {
                    // 무단 외박 Alert Dialog 생성
                    val builder = AlertDialog.Builder(context)
                    builder.setTitle("${date.date} 점호")
                    builder.setMessage("해당 점호에 불참하셨습니다.")
                    builder.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, i ->
                        dialog.cancel()
                    })
                    builder.show()
                    return@setOnDateChangedListener
                }

                // 점호일 조회 API 연결
                val rollcallId = idRollcall!!.get(dayRollcall!!.indexOf(date.day))
                RetrofitBuilder.rollcallApi.rollcallCheck(token, rollcallId!!).enqueue(object: Callback<RollcallCheckResultDTO> {
                    override fun onResponse(call: Call<RollcallCheckResultDTO>, response: Response<RollcallCheckResultDTO>) {
                        Log.d("ROLLCALL_CHECK", "response : " + response.body().toString())
                        Log.d("ROLLCALL_CHECK", "error code & body : " + response.code() + " " + response.errorBody())

                        // null 값이 반환되면 무시
                        if (response.body()?.id == null) {
                            Log.d("ROLLCALL_CHECK", "점호가 없는 날짜입니다.")
                            return
                        }

                        // 점호 Alert Dialog 생성
                        val builder = AlertDialog.Builder(context)
                        builder.setTitle("${response.body()?.start?.subSequence(0, 10)} 점호")
                        builder.setMessage("시작 시간 : ${response.body()?.start?.subSequence(11, 19)}" +
                                "\n종료 시간 : ${response.body()?.end?.subSequence(11, 19)}" +
                                "\n점호 대상 : ${response.body()?.dormitory.toString()} 기숙사")
                        builder.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, i ->
                            dialog.cancel()
                        })
                        builder.show()
                    }

                    override fun onFailure(call: Call<RollcallCheckResultDTO>, t: Throwable) {
                        Log.e("ROLLCALL_CHECK", "점호일 조회 실패")
                        Log.e("ROLLCALL_CHECK", t.message.toString())
                    }
                })
            }
        }

        return binding.root
    }
}

// 디폴트 커스텀 함수
class DefaultDecorator(context: HomeFragment): DayViewDecorator {
    val defaultDrawable = context.resources.getDrawable(R.drawable.style_home_cal_default)

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return true
    }

    override fun decorate(view: DayViewFacade?) {
        view?.setBackgroundDrawable(defaultDrawable)
    }
}

// 오늘 커스텀 함수
class TodayDecorator(context: HomeFragment, stayoutDays: MutableList<Int>?): DayViewDecorator {
    val stayoutDays = stayoutDays

    override fun shouldDecorate(day: CalendarDay?): Boolean { // 커스텀 여부 반환
        return day?.equals(CalendarDay.today())!!
    }

    override fun decorate(view: DayViewFacade?) { // 커스텀 설정
        if (stayoutDays?.contains(CalendarDay.today().day) == true) view?.addSpan(object:ForegroundColorSpan(Color.parseColor("#FFFFFFFF")) {})
        else view?.addSpan(object:ForegroundColorSpan(Color.parseColor("#FFFFA114")) {})
    }
}

// 점호일 커스텀 함수
class RollcallDecorator(context: HomeFragment, rollcallDays: MutableList<Int>?, month: Int): DayViewDecorator {
    val rollcallDrawable = context.resources.getDrawable(R.drawable.style_home_cal_rollcall)
    val rollcallDays = rollcallDays
    val month = month

    override fun shouldDecorate(day: CalendarDay?): Boolean { // 커스텀 여부 반환
        return rollcallDays?.contains(day!!.day) == true && day?.month == month
    }
    override fun decorate(view: DayViewFacade?) { // 커스텀 설정
        view?.setBackgroundDrawable(rollcallDrawable)
    }
}

// 외박 신청일 커스텀 함수
class StayoutDecorator(context: HomeFragment, stayoutDays: MutableList<Int>?, month: Int): DayViewDecorator {
    val stayoutDrawable = context.resources.getDrawable(R.drawable.style_home_cal_stayout)
    val stayoutDays = stayoutDays
    val month = month

    override fun shouldDecorate(day: CalendarDay?): Boolean { // 커스텀 여부 반환
        return stayoutDays?.contains(day?.day) == true && day?.month == month
    }
    override fun decorate(view: DayViewFacade?) { // 커스텀 설정
        view?.setBackgroundDrawable(stayoutDrawable)
    }
}

// 무단 외박일 커스텀 함수
class MissDecorator(context: HomeFragment, rollcallDays: MutableList<Int>?, appliedDays: MutableList<Int>?, month: Int): DayViewDecorator {
    val missDrawable = context.resources.getDrawable(R.drawable.style_home_cal_miss)
    val today = CalendarDay.today()
    val rollcallDays = rollcallDays
    val appliedDays = appliedDays
    val month = month

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return day?.isBefore(today)!! && day?.month == month && rollcallDays?.contains(day.day) == true && !appliedDays?.contains(day.day)!!
    }
    override fun decorate(view: DayViewFacade?) {
        view?.setBackgroundDrawable(missDrawable)
    }
}
