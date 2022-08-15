package kr.co.sujungvillage.adapter

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kr.co.sujungvillage.CommDetailActivity
import kr.co.sujungvillage.CommDetailActivity.Companion.commentIndex
import kr.co.sujungvillage.CommDetailActivity.Companion.studentNum
import kr.co.sujungvillage.CommDetailActivity.Companion.token
import kr.co.sujungvillage.base.hideKeyboard
import kr.co.sujungvillage.data.CommDetailCommentsRequest
import kr.co.sujungvillage.databinding.ListitemCommDetailBinding
import kr.co.sujungvillage.fragment.CommFragment
import kr.co.sujungvillage.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommDetailAdapter(val context:Context) :RecyclerView.Adapter<CommDetailHolder>(){
    var commDetailList=mutableListOf<CommDetailCommentsRequest>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommDetailHolder {
        val binding=ListitemCommDetailBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CommDetailHolder(binding,context)
    }

    override fun onBindViewHolder(holder: CommDetailHolder, position: Int) {
        val commDetail=commDetailList.get(position)
        holder.setCommDetail(commDetail)
    }

    override fun getItemCount(): Int {
        return commDetailList.size
    }

}
class CommDetailHolder(val binding:ListitemCommDetailBinding,val context: Context): RecyclerView.ViewHolder(binding.root){
    fun setCommDetail(commDetail: CommDetailCommentsRequest){
        binding.textName.text="익명${commentIndex?.indexOf(commDetail.writerId)?.plus(1)}" // 익명 처리해야함.
        val hour=commDetail.regDate?.subSequence(11,13).toString().toInt()
        val min=(commDetail.regDate?.subSequence(14,16).toString().toInt())

        binding.textCalDate.text="${commDetail.regDate?.subSequence(0,4)}/${commDetail.regDate?.subSequence(5, 7)}/${commDetail.regDate?.subSequence(8, 10)} ${hour}:${min}"
        binding.textContent.text="${commDetail.content}"

        //관리자인지 아닌지 마크 띄우기
        if(commDetail.id.toString().toInt()>=99990000){//관리자인 경우
            binding.textAdmin.visibility= View.VISIBLE
        }
        binding.root.setOnClickListener { binding.root.context.hideKeyboard(itemView) }

        //댓글 삭제 버튼
        if(commDetail.writerId==studentNum) {
            binding.textCommentDelete.visibility=View.VISIBLE
        }

        binding.textCommentDelete.setOnClickListener{
            //경고창 띄우기
            val builder= AlertDialog.Builder(context)
            builder.setTitle("정말 삭제하시겠습니까?")
                .setPositiveButton("확인", DialogInterface.OnClickListener{ dialog, id->
                    RetrofitBuilder.communityApi.commCommentDelete(token,commDetail.id).enqueue(object :
                        Callback<Void> {
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            Log.d("COMM_DELETE",response.message().toString())
                        }
                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Log.d("COMM_DELETE",t.message.toString())
                        }
                    })
                })
                .setNegativeButton("취소",
                    DialogInterface.OnClickListener{ dialog, id->
                        Log.d("COMM_DELETE","글 삭제 취소")
                    })
            builder.show()
        }
    }
}