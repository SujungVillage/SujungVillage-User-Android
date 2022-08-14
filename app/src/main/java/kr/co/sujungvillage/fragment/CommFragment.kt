package kr.co.sujungvillage.fragment

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.Instrumentation
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
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kr.co.sujungvillage.AlarmActivity
import kr.co.sujungvillage.CommDetailActivity
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

    companion object{
        var dormitory="전체"
        var studentNum=""
    }
    var commList: MutableList<CommDTO> = mutableListOf()
    var token=""
    var _binding: FragmentCommBinding? = null
    val binding get() = _binding!!

    private val startForResult=registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){result: ActivityResult ->
        if(result.resultCode==RESULT_OK){
            refresh()
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCommBinding.inflate(inflater,container,false)

        var searchText=""
        // 재사생 학번 불러오기
        val shared = this.activity?.getSharedPreferences("SujungVillage", Context.MODE_PRIVATE)
        studentNum = shared?.getString("studentNum", "error").toString()
        token = shared?.getString("token", "error").toString()

        // 키보드 내리기
        binding.linear.setOnClickListener { this.hideKeyboard() }

        // 알림 버튼 연결
        binding.btnAlarm.setOnClickListener {
            var intent = Intent(this.activity, AlarmActivity::class.java)
            startActivity(intent)
        }

        // 검색 기능
        binding.btnSearch.setOnClickListener{//돋보기 클릭
            binding.editSearch.visibility=View.VISIBLE
            binding.btnSearch.visibility=View.INVISIBLE
            binding.btnDelete.visibility=View.VISIBLE
            searchText=""
            binding.editSearch.text.clear()
            searchText = binding.editSearch.text.toString().trim()
            hideKeyboard()
            // 검색 api 연결
            searchRefresh(searchText)
            // 키보드 엔터 -> 검색으로 변경
           binding.editSearch.setOnKeyListener{view, keyCode, event->
               if(event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                   searchText = binding.editSearch.text.toString().trim()
                   if (searchText.isEmpty()) { }
                   else {
                       hideKeyboard()
                       // 검색 api 연결
                       searchRefresh(searchText)
                   }
               }
               false
           }
        }

        binding.editSearch.addTextChangedListener {//검색창 입력 실시간
            searchText = binding.editSearch.text.toString().trim()
            searchRefresh(searchText)
        }

        //검색 취소
        binding.btnDelete.setOnClickListener{
            this.hideKeyboard()
            binding.editSearch.visibility=View.INVISIBLE
            binding.btnSearch.visibility=View.VISIBLE
            binding.btnDelete.visibility=View.INVISIBLE
            binding.editSearch.text.clear()
            searchText=""
        }

        // 기숙사 스피너 연결 및 커스텀
        dormitory = binding.spinnerDormitory.getSelectedItem().toString()
        var data=listOf("전체","성미료","성미관","풍림","엠시티","그레이스","이율","장수")
        binding.spinnerDormitory.adapter = ArrayAdapter.createFromResource(requireContext(), R.array.dormitory, R.layout.spinner_comm_dormitory)
        binding.spinnerDormitory.onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                dormitory=data.get(p2)
                Log.d("COMM_FRAG",dormitory)
                refresh()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        Log.d("COMM_FRAG",dormitory)
        //리프레시
        binding.swipe.setOnRefreshListener {
            if(searchText.isEmpty()){//검색어가 없는 경우-> 그냥 refresh
                refresh()
                binding.swipe.isRefreshing=false
            }
            else{//검색어가 있는 경우 searchRefresh
                searchRefresh(searchText)
                binding.swipe.isRefreshing=false
            }
        }
        // 글 작성 버튼 연결
        binding.btnWrite.setOnClickListener {
            var intent = Intent(this.activity, CommWriteActivity::class.java)
            startForResult.launch(intent)
        }
        refresh()

        return binding.root
    }
    private fun refresh(){
        RetrofitBuilder.communityApi.comm(token,dormitory).enqueue(object: Callback<List<CommDTO>>{
            override fun onResponse(call: Call<List<CommDTO>>, response: Response<List<CommDTO>>) {
                binding.textExist.visibility=View.GONE
                if(response.body()?.size==0){
                    binding.textExist.visibility=View.VISIBLE
                }
                commList = mutableListOf()
                for(post in response.body()!!){
                    var comm=CommDTO(post.id,post.title,post.content,post.writerId,post.regDate,post.numOfComments)
                    commList.add(comm)
                }
                val adapter=CommAdapter()
                adapter.commList=commList
                binding.recycleComm.adapter=adapter
                binding.recycleComm.layoutManager=LinearLayoutManager(activity)//프래그먼트에선 this 대신 activity 써줌
                Log.d("COMM_FRAG", response.body().toString())
            }
            override fun onFailure(call: Call<List<CommDTO>>, t: Throwable) {
                Log.e("COMM_FRAG", "커뮤니티 프래그먼트 조회 실패")
                Log.e("COMM_FRAG", t.message.toString())
            }
        })
        return
    }

    private fun searchRefresh(searchText:String){
        RetrofitBuilder.communityApi.commSearch(token, CommFragment.dormitory, searchText)
            .enqueue(object : Callback<List<CommDTO>> {
                override fun onResponse(call: Call<List<CommDTO>>, response: Response<List<CommDTO>>) {
                    binding.textExist.visibility=View.GONE
                    if (response.body()?.size == 0) {
                        binding.textExist.visibility=View.VISIBLE
                    }
                    commList = mutableListOf()
                    for (post in response.body()!!) {
                        var comm = CommDTO(post.id,post.title,post.content,post.writerId,post.regDate,post.numOfComments)
                        commList.add(comm)
                    }
                    val adapter = CommAdapter()
                    adapter.commList = commList
                    binding.recycleComm.adapter = adapter
                    binding.recycleComm.layoutManager = LinearLayoutManager(activity)//프래그먼트에선 this 대신 activity 써줌
                    Log.d("COMM_FRAG", response.body().toString())
                }

                override fun onFailure(call: Call<List<CommDTO>>, t: Throwable) {
                    Log.e("COMM_FRAG", "커뮤니티 프래그먼트 조회 실패")
                    Log.e("COMM_FRAG", t.message.toString())
                }

            })
    }
}