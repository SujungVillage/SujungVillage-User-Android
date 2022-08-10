package kr.co.sujungvillage.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import kr.co.sujungvillage.AlarmActivity
import kr.co.sujungvillage.CommWriteActivity
import kr.co.sujungvillage.R
import kr.co.sujungvillage.adapter.commAdapter
import kr.co.sujungvillage.data.CommDTO
import kr.co.sujungvillage.databinding.FragmentCommBinding
import kr.co.sujungvillage.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentCommBinding.inflate(inflater,container,false)

        // 재사생 학번 불러오기
        val shared = this.activity?.getSharedPreferences("SujungVillage", Context.MODE_PRIVATE)
        val studentNum = shared?.getString("studentNum", "20180001").toString()

        // 알림 버튼 연결
        binding.btnAlarm.setOnClickListener {
            var intent = Intent(this.activity, AlarmActivity::class.java)
            startActivity(intent)
        }

        // 기숙사 스피너 연결 및 커스텀
        binding.spinnerDormitory.adapter = ArrayAdapter.createFromResource(requireContext(), R.array.dormitory, R.layout.spinner_comm_dormitory)

        // 글 작성 버튼 연결
        binding.btnWrite.setOnClickListener {
            var intent = Intent(this.activity, CommWriteActivity::class.java)
            startActivity(intent)
        }

        RetrofitBuilder.communityApi.comm(studentNum).enqueue(object: Callback<List<CommDTO>>{
            override fun onResponse(call: Call<List<CommDTO>>, response: Response<List<CommDTO>>) {
                if(response.body()?.size==0){

                }
                val commList:MutableList<CommDTO> = mutableListOf()
                for(post in response.body()!!){
                    var comm=CommDTO(post.id,post.title,post.dormitory,post.regDate)
                    commList.add(comm)
                }
                val adapter=commAdapter()
                adapter.commList=commList
                binding.recycleComm.adapter=adapter
                binding.recycleComm.layoutManager=LinearLayoutManager(activity)//프래그먼트에선 this 대신 activity 써줌
                Log.d("COMM_FRAG", response.body().toString())
            }

            override fun onFailure(call: Call<List<CommDTO>>, t: Throwable) {
                Log.d("COMM_FRAG", "커뮤니티 프래그먼트 조회 실패")

            }

        })

        return binding.root
    }
}