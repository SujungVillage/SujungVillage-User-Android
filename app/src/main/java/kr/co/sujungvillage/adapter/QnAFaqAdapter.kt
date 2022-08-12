package kr.co.sujungvillage.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.sujungvillage.R
import kr.co.sujungvillage.data.FaqGetResultDTO
import kr.co.sujungvillage.databinding.ListitemQnaFaqBinding

class QnAFaqAdapter: RecyclerView.Adapter<QnAFaqHolder>() {
    var faqList = mutableListOf<FaqGetResultDTO>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QnAFaqHolder {
        val binding = ListitemQnaFaqBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return QnAFaqHolder(binding)
    }

    override fun onBindViewHolder(holder: QnAFaqHolder, position: Int) {
        val faq = faqList.get(position)
        holder.setFaq(faq)
    }

    override fun getItemCount(): Int {
        return faqList.size
    }
}

class QnAFaqHolder(val binding: ListitemQnaFaqBinding): RecyclerView.ViewHolder(binding.root) {
    fun setFaq(faq: FaqGetResultDTO) {
        binding.textQuestion.text = faq.question
        binding.textAnswer.text = faq.answer

        // FAQ 열고 닫기
        binding.root.setOnClickListener {
            // 닫혀 있는 경우
            if (binding.textAnswer.visibility == View.GONE) {
                binding.btnDetail.setImageResource(R.drawable.icon_qna_faq_open)
                binding.textAnswer.visibility = View.VISIBLE
            }
            // 열려 있는 경우
            else {
                binding.btnDetail.setImageResource(R.drawable.icon_qna_faq_close)
                binding.textAnswer.visibility = View.GONE
            }
        }
    }
}