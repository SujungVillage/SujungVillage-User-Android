package kr.co.sujungvillage.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kr.co.sujungvillage.QnADetailActivity
import kr.co.sujungvillage.R
import kr.co.sujungvillage.data.MyqGetResultDTO
import kr.co.sujungvillage.databinding.ListitemQnaMyqBinding

class QnAMyqAdapter : RecyclerView.Adapter<QnAMyqHolder>() {
    var myqList = mutableListOf<MyqGetResultDTO>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QnAMyqHolder {
        val binding =
            ListitemQnaMyqBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return QnAMyqHolder(binding)
    }

    override fun onBindViewHolder(holder: QnAMyqHolder, position: Int) {
        val myq = myqList.get(position)
        holder.setMyq(myq)
    }

    override fun getItemCount(): Int {
        return myqList.size
    }
}

class QnAMyqHolder(val binding: ListitemQnaMyqBinding) : RecyclerView.ViewHolder(binding.root) {
    fun setMyq(myq: MyqGetResultDTO) {
        binding.textTitle.text = myq.title
        binding.textState.text = if (myq.isAnswered) "답변 완료" else "미답변"
        if (!myq.isAnswered) binding.textState.setBackgroundResource(R.drawable.style_qna_listitem_incomplete)

        // 내 질문 클릭 시 상세 액티비티 생성
        binding.root.setOnClickListener {
            var intent = Intent(binding.root.context, QnADetailActivity::class.java)
            intent.putExtra("questionId", myq.id)
            startActivity(binding.root.context, intent, null)
        }
    }
}
