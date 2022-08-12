package kr.co.sujungvillage.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.ACTION_DOWN
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kr.co.sujungvillage.AlarmActivity
import kr.co.sujungvillage.CommWriteActivity
import kr.co.sujungvillage.R
import kr.co.sujungvillage.adapter.CommAdapter
import kr.co.sujungvillage.base.hideKeyboard
import kr.co.sujungvillage.data.CommDTO
import kr.co.sujungvillage.databinding.FragmentCommBinding
import kr.co.sujungvillage.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommFragment : Fragment() {
    companion object{var dormitory="전체"}
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentCommBinding.inflate(inflater,container,false)

        // 재사생 학번 불러오기
        val shared = this.activity?.getSharedPreferences("SujungVillage", Context.MODE_PRIVATE)
        val studentNum = shared?.getString("studentNum", "20180001").toString()
        val token = shared?.getString("token", "error").toString()
        var searchText=""
        // 알림 버튼 연결
        binding.btnAlarm.setOnClickListener {
            var intent = Intent(this.activity, AlarmActivity::class.java)
            startActivity(intent)
        }

        //검색 기능
        binding.btnSearch.setOnClickListener{
            binding.editSearch.visibility=View.VISIBLE
            binding.btnSearch.visibility=View.INVISIBLE
            binding.btnDelete.visibility=View.VISIBLE
            //키보드 엔터->검색으로 변경
           binding.editSearch.setOnKeyListener{view,keyCode,event->
               if(event.action==KeyEvent.ACTION_DOWN && keyCode==KeyEvent.KEYCODE_ENTER){
                   searchText=binding.editSearch.text.toString().trim()
                   if(searchText.isEmpty()){
                   }
                   else {
                       hideKeyboard()
                       //검색 api 연결
                       binding.editSearch.text.clear()
                       RetrofitBuilder.communityApi.commSearch(token, dormitory, searchText)
                           .enqueue(object : Callback<List<CommDTO>> {
                               override fun onResponse(
                                   call: Call<List<CommDTO>>,
                                   response: Response<List<CommDTO>>
                               ) {
                                   binding.textExist.visibility=View.GONE
                                   if (response.body()?.size == 0) {
                                       binding.textExist.visibility=View.VISIBLE
                                   }
                                   val commList: MutableList<CommDTO> = mutableListOf()
                                   for (post in response.body()!!) {
                                       var comm =
                                           CommDTO(post.id, post.title, post.content, post.regDate)
                                       commList.add(comm)
                                   }
                                   val adapter = CommAdapter()
                                   adapter.commList = commList
                                   binding.recycleComm.adapter = adapter
                                   binding.recycleComm.layoutManager =
                                       LinearLayoutManager(activity)//프래그먼트에선 this 대신 activity 써줌
                                   Log.d("COMM_FRAG", response.body().toString())
                               }

                               override fun onFailure(call: Call<List<CommDTO>>, t: Throwable) {
                                   Log.d("COMM_FRAG", "커뮤니티 프래그먼트 조회 실패")

                               }

                           })
                   }
               }
               true
           }
        }

        binding.btnDelete.setOnClickListener{
            binding.editSearch.visibility=View.INVISIBLE
            binding.btnSearch.visibility=View.VISIBLE
            binding.btnDelete.visibility=View.INVISIBLE
        }

        // 기숙사 스피너 연결 및 커스텀
        dormitory = binding.spinnerDormitory.getSelectedItem().toString()
        var data=listOf("전체","성미료","성미관","풍림","엠시티","그레이스","이율","장수")
        binding.spinnerDormitory.adapter = ArrayAdapter.createFromResource(requireContext(), R.array.dormitory, R.layout.spinner_comm_dormitory)
        binding.spinnerDormitory.onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                dormitory=data.get(p2)
                Log.d("COMM_FRAG",dormitory)
                RetrofitBuilder.communityApi.comm(token,dormitory).enqueue(object: Callback<List<CommDTO>>{
                    override fun onResponse(call: Call<List<CommDTO>>, response: Response<List<CommDTO>>) {
                        binding.textExist.visibility=View.GONE
                        if(response.body()?.size==0){
                            binding.textExist.visibility=View.VISIBLE
                        }

                        val commList:MutableList<CommDTO> = mutableListOf()
                        for(post in response.body()!!){
                            var comm=CommDTO(post.id,post.title,post.content,post.regDate)
                            commList.add(comm)
                        }
                        val adapter=CommAdapter()
                        adapter.commList=commList
                        binding.recycleComm.adapter=adapter
                        binding.recycleComm.layoutManager=LinearLayoutManager(activity)//프래그먼트에선 this 대신 activity 써줌
                        Log.d("COMM_FRAG", response.body().toString())
                    }
                    override fun onFailure(call: Call<List<CommDTO>>, t: Throwable) {
                        Log.d("COMM_FRAG", "커뮤니티 프래그먼트 조회 실패")
                    }
                })
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }


        Log.d("COMM_FRAG",dormitory)
        // 글 작성 버튼 연결
        binding.btnWrite.setOnClickListener {
            var intent = Intent(this.activity, CommWriteActivity::class.java)
            startActivity(intent)
        }

        RetrofitBuilder.communityApi.comm(token,dormitory).enqueue(object: Callback<List<CommDTO>>{
            override fun onResponse(call: Call<List<CommDTO>>, response: Response<List<CommDTO>>) {
                binding.textExist.visibility=View.GONE
                if(response.body()?.size==0){
                    binding.textExist.visibility=View.VISIBLE
                }
                val commList:MutableList<CommDTO> = mutableListOf()
                for(post in response.body()!!){
                    var comm=CommDTO(post.id,post.title,post.content,post.regDate)
                    commList.add(comm)
                }
                val adapter=CommAdapter()
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