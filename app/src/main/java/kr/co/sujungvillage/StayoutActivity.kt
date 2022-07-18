package kr.co.sujungvillage

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kr.co.sujungvillage.base.hideKeyboard
import kr.co.sujungvillage.databinding.ActivityStayoutBinding


class StayoutActivity : AppCompatActivity() {

    val binding by lazy { ActivityStayoutBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 키보드 내리기
        binding.layout.setOnClickListener { this.hideKeyboard() }
        binding.linearForm.setOnClickListener { this.hideKeyboard() }

        // 뒤로가기 버튼 클릭 시 액티비티 종료
        binding.btnBack.setOnClickListener { this.finish() }

        // 외박 유형 스피너 연결 및 커스텀
        binding.spinnerType.adapter = ArrayAdapter.createFromResource(this, R.array.stayout_type, R.layout.item_stayout_type)
        binding.spinnerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(position) {
                    // position - 0 : 단기 외박, 1 : 장기 외박
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) { }

        }

        setContentView(binding.root)
    }
}