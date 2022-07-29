package kr.co.sujungvillage

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kr.co.sujungvillage.base.hideKeyboard
import kr.co.sujungvillage.data.StayoutCodeDTO
import kr.co.sujungvillage.data.StayoutCreateDTO
import kr.co.sujungvillage.databinding.ActivityStayoutBinding
import kr.co.sujungvillage.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class StayoutActivity : AppCompatActivity() {

    val binding by lazy { ActivityStayoutBinding.inflate(layoutInflater) }

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

        /*
        val destination = "천안"
        val reason = "본가"
        val emergency = "01011111111"
        val date = "2022-07-29"
        var stayoutInfo = StayoutCreateDTO(destination, reason, emergency, date)

        RetrofitBuilder.stayoutApi.applyStayout("1234", stayoutInfo).enqueue(object: Callback<StayoutCodeDTO> {
            override fun onResponse(call: Call<StayoutCodeDTO>, response: Response<StayoutCodeDTO>) {
                Toast.makeText(this@StayoutActivity, "성공", Toast.LENGTH_SHORT).show()
                Log.d("STAYOUT_CREATE", response.body().toString())
            }

            override fun onFailure(call: Call<StayoutCodeDTO>, t: Throwable) {
                Toast.makeText(this@StayoutActivity, "성공", Toast.LENGTH_SHORT).show()
                Log.d("STAYOUT_CREATE", "외박 신청 생성 실패")
            }
        })
        */
    }
}