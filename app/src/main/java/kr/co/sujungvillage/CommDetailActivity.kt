package kr.co.sujungvillage

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kr.co.sujungvillage.adapter.CommDetailAdapter
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

        // 뒤로가기 버튼 연결
        binding.btnBack.setOnClickListener { finish() }

        // Api 연결**어댑터 연결
        var postId = 40L // 이전 페이지(commFragment)에서 intent로 넘겨 받음
        RetrofitBuilder.communityApi.commDetail(studentNum,postId).enqueue(object: Callback<CommDetailResultDTO>{

            override fun onResponse(call: Call<CommDetailResultDTO>, response: Response<CommDetailResultDTO>) {
                Log.d("COMMDETAIL",response.body().toString())
                binding.textTitle.text=response.body()?.title
                binding.textCalDate.text=response.body()?.regDate
                binding.textContent.text=response.body()?.content
                //어댑터 연결
                /*val commentList:MutableList<CommDetailCommentsRequest> = mutableListOf()
                for(info in response.body()?.comments!!){
                    var comment=CommDetailCommentsRequest(info.id,info.writerId,info.content,info.regDate,info.modDate)
                    commentList.add(comment)
                }
                var adapter=CommDetailAdapter()
                adapter.commDetailList=commentList
                binding.recyclerComment.adapter=adapter
                binding.recyclerComment.layoutManager=LinearLayoutManager(this@CommDetailActivity)*/
            }

            override fun onFailure(call: Call<CommDetailResultDTO>, t: Throwable) {
                Log.d("COMMDETAIL",t.message.toString())
            }
        })

        binding.btnCommentSubmit.setOnClickListener{
            val comment=binding.editComment.text.toString().trim()
            if(comment.isEmpty()){
                Toast.makeText(this,"제목과 내용을 입력하세요. ${comment}",Toast.LENGTH_SHORT).show()
            }
            else{//POST
                val commCommentInfo= CommDetailCommentsWriteDTO(postId,comment)
                RetrofitBuilder.communityApi.commComment(userId,commCommentInfo).enqueue(object:Callback<CommDetailCommentWriteResultDTO>{
                    override fun onResponse(call: Call<CommDetailCommentWriteResultDTO>, response: Response<CommDetailCommentWriteResultDTO>) {
                        Toast.makeText(this@CommDetailActivity,"댓글작성 성공 ${comment}",Toast.LENGTH_SHORT).show()
                        Log.d("COMM_COMMENT",response.body().toString())
                    }

                    override fun onFailure(call: Call<CommDetailCommentWriteResultDTO>, t: Throwable) {
                        Toast.makeText(this@CommDetailActivity,"댓글작성 실패 ${comment}",Toast.LENGTH_SHORT).show()
                        Log.d("COMME_COMMENT",t.message.toString())
                    }

                })
            }
        }
    }
}