package kr.co.sujungvillage

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kr.co.sujungvillage.base.hideKeyboard
import kr.co.sujungvillage.data.StayoutCreateDTO
import kr.co.sujungvillage.data.StayoutCreateResultDTO
import kr.co.sujungvillage.databinding.ActivityStayoutBinding
import kr.co.sujungvillage.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class StayoutActivity : AppCompatActivity() {

    val binding by lazy { ActivityStayoutBinding.inflate(layoutInflater) }

    var startDate = ""
    var endDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 키보드 내리기
        binding.layout.setOnClickListener { this.hideKeyboard() }
        binding.linearForm.setOnClickListener { this.hideKeyboard() }

        // 뒤로가기 버튼 클릭 시 액티비티 종료
        binding.btnBack.setOnClickListener { this.finish() }

        // 외박 유형 스피너 연결 및 커스텀
        binding.spinnerType.adapter = ArrayAdapter.createFromResource(this, R.array.stayout_type, R.layout.spinner_stayout_type)
        binding.spinnerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(position) {
                    // position - 0 : 단기 외박, 1 : 장기 외박
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) { }
        }

        // 시작일 캘린터 버튼 연결 (팝업 캘린더에서 날짜 선택)
        binding.btnStartCalendar.setOnClickListener {
            val cal = Calendar.getInstance() // 캘린더뷰 생성
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, day ->
                // month 두 자릿수로 저장
                if (month < 10) startDate = "${year}-0${month + 1}"
                else startDate = "${year}-${month + 1}"
                // day 두 자릿수로 저장
                if (day < 10) startDate += "-0${day}"
                else startDate += "-${day}"
                binding.textStartDate.text = startDate
                binding.textStartDate.setTextColor(ContextCompat.getColor(this, R.color.gray_600))
            }
            DatePickerDialog(this, dateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        // 종료일 캘린더 버튼 연결 (팝업 캘린더에서 날짜 선택)
        binding.btnEndCalendar.setOnClickListener {
            val cal = Calendar.getInstance() // 캘린더뷰 생성
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, day ->
                // month 두 자릿수로 저장
                if (month < 10) endDate = "${year}-0${month + 1}"
                else endDate = "${year}-${month + 1}"
                // day 두 자릿수로 저장
                if (day < 10) endDate += "-0${day}"
                else endDate += "-${day}"
                binding.textEndDate.text = endDate
                binding.textEndDate.setTextColor(ContextCompat.getColor(this, R.color.gray_600))
            }
            DatePickerDialog(this, dateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        // 신청하기 버튼 연결
        binding.btnSend.setOnClickListener {
            // 정보를 입력하지 않은 항목이 존재하는 경우
            if (startDate.isEmpty() || endDate.isEmpty()) {
                Toast.makeText(this, "시작일과 종료일을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (binding.editDestination.text.isEmpty()) {
                Toast.makeText(this, "행선지를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (binding.editReason.text.isEmpty()) {
                Toast.makeText(this, "사유를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (binding.editNumber.text.isEmpty()) {
                Toast.makeText(this, "긴급 전화번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // 종료일과 시작일의 연도와 달이 동일하지 않은 경우
            if (startDate.subSequence(0, 7) != endDate.subSequence(0, 7)) {
                Toast.makeText(this, "종료일과 시작일이 같은 달이어야 합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // 종료일이 시작일보다 빠른 경우
            if (startDate.subSequence(8, 10).toString().toInt() > endDate.subSequence(8, 10).toString().toInt()) {
                Toast.makeText(this, "잘못된 종료일입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 외박 신청 API 연결
            val destination = binding.editDestination.text.toString()
            val reason = binding.editReason.text.toString()
            val emergency = binding.editNumber.text.toString()
            val date = binding.textStartDate.text.toString()
            // !!! 시작일, 종료일로 입력하도록 수정
            val stayoutInfo = StayoutCreateDTO(destination, reason, emergency, date)

            val userId = "20180001"
            RetrofitBuilder.stayoutApi.stayoutCreate(userId, stayoutInfo).enqueue(object: Callback<StayoutCreateResultDTO> {
                override fun onResponse(call: Call<StayoutCreateResultDTO>, response: Response<StayoutCreateResultDTO>) {
                    Log.d("STAYOUT_CREATE", "response : " + response.body().toString())
                }

                override fun onFailure(call: Call<StayoutCreateResultDTO>, t: Throwable) {
                    Toast.makeText(this@StayoutActivity, "오류가 발생하였습니다.", Toast.LENGTH_SHORT).show()
                    Log.d("STAYOUT_CREATE", "외박 신청 생성 실패")
                }
            })
        }
    }
}