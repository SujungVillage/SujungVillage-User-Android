package kr.co.sujungvillage

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kr.co.sujungvillage.adapter.CommDetailAdapter
import kr.co.sujungvillage.base.hideKeyboard
import kr.co.sujungvillage.data.*
import kr.co.sujungvillage.databinding.ActivityCommDetailBinding
import kr.co.sujungvillage.databinding.ActivityNoticeBinding
import kr.co.sujungvillage.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommDetailActivity : AppCompatActivity() {

    val binding by lazy { ActivityCommDetailBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 재사생 학번 불러오기
        val shared = this.getSharedPreferences("SujungVillage", Context.MODE_PRIVATE)
        val studentNum = shared?.getString("studentNum", "error").toString()
        val token = shared?.getString("token", "error").toString()

        // 이전 페이지 CommFragment 에서 postId 전달 받기
        val postId = intent.getLongExtra("postId",-1)
        val dormitory=intent.getStringExtra("dormitory")
        // 키보드 내리기
        binding.layoutToolbar.setOnClickListener { this.hideKeyboard() }
        binding.linearPost.setOnClickListener { this.hideKeyboard() }
        binding.linearButton.setOnClickListener { this.hideKeyboard() }
        binding.linearComment.setOnClickListener { this.hideKeyboard() }
        binding.recyclerComment.setOnClickListener { this.hideKeyboard() }

        //툴바 타이틀 설정
        binding.textToolbarTitle.text="${dormitory} 게시판"
        // 뒤로가기 버튼 연결
        binding.btnBack.setOnClickListener { finish() }

        // Api 연결
        refresh(token,studentNum,postId)

        // 댓글 전송 버튼 클릭시
        binding.btnCommentSubmit.setOnClickListener{
            val comment=binding.editComment.text.toString().trim()
            binding.editComment.text.clear()//edittext 지우기
            hideKeyboard()

            if(comment.isEmpty()){
                Toast.makeText(this,"댓글을 입력하세요.",Toast.LENGTH_SHORT).show()
            }
            else{//POST
                val commCommentInfo= CommDetailCommentsWriteDTO(postId,comment)
                RetrofitBuilder.communityApi.commComment(token,commCommentInfo).enqueue(object:Callback<CommDetailCommentWriteResultDTO>{
                    override fun onResponse(call: Call<CommDetailCommentWriteResultDTO>, response: Response<CommDetailCommentWriteResultDTO>) {
                        Log.d("COMM_COMMENT", response.body().toString())
                        refresh(token,studentNum,postId)
                    }

                    override fun onFailure(call: Call<CommDetailCommentWriteResultDTO>, t: Throwable) {
                        Log.e("COMM_COMMENT", t.message.toString())
                    }
                })
            }
        }
    }
    private fun refresh(token:String,studentNum:String,postId:Long){
        RetrofitBuilder.communityApi.commDetail(token,postId).enqueue(object: Callback<CommDetailResultDTO>{

            override fun onResponse(call: Call<CommDetailResultDTO>, response: Response<CommDetailResultDTO>) {
                Log.d("COMM_DETAIL",response.body().toString())
                Log.d("COMM_DETAIL",response.code().toString())
                Log.d("COMM_DETAIL",response.message())
                binding.textTitle.text=response.body()?.title
                binding.textCalDate.text="${response.body()?.regDate?.subSequence(0,4)}/${response.body()?.regDate?.subSequence(5, 7)}/${response.body()?.regDate?.subSequence(8, 10)} ${response.body()?.regDate?.subSequence(11,13).toString()}:${response.body()?.regDate?.subSequence(14,16).toString()}"
                binding.textContent.text=response.body()?.content
                //글 작성자 id 와 studentNum이 같으면 삭제 버튼 보이게
                if(studentNum==response.body()?.writerId){
                    binding.btnDelete.visibility= View.VISIBLE
                }
                //어댑터 연결

                val commentList:MutableList<CommDetailCommentsRequest> = mutableListOf()
                var commentCount=0
                for(info in response.body()?.comments!!){
                    commentCount++
                    var comment=CommDetailCommentsRequest(info.id,info.postId,info.writerId,info.content,info.regDate,info.modDate)
                    commentList.add(comment)
                }
                var adapter=CommDetailAdapter()
                adapter.commDetailList=commentList
                binding.recyclerComment.adapter=adapter
                binding.recyclerComment.layoutManager=LinearLayoutManager(this@CommDetailActivity)

                binding.textCommentCount.text=commentCount.toString()
            }

            override fun onFailure(call: Call<CommDetailResultDTO>, t: Throwable) {
                Log.e("COMM_DETAIL", t.message.toString())
            }
        })
    }
}