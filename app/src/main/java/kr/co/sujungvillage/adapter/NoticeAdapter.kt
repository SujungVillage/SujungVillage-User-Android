package kr.co.sujungvillage.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kr.co.sujungvillage.NoticeDetailActivity
import kr.co.sujungvillage.data.NoticeRequestResultDTO
import kr.co.sujungvillage.databinding.ListitemNoticePostBinding

class NoticeAdapter : RecyclerView.Adapter<NoticeHolder>() {
    var noticeList = mutableListOf<NoticeRequestResultDTO>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeHolder {
        val binding = ListitemNoticePostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoticeHolder(binding)
    }

    override fun onBindViewHolder(holder: NoticeHolder, position: Int) {
        val notice = noticeList.get(position)
        holder.setNotice(notice)
    }

    override fun getItemCount(): Int {
        return noticeList.size
    }
}

class NoticeHolder(val binding: ListitemNoticePostBinding): RecyclerView.ViewHolder(binding.root) {
    fun setNotice(notice: NoticeRequestResultDTO) {
        binding.textId.text = "${notice.id}"
        binding.textDormitory.text = notice.dormitory
        binding.textTitle.text = "${notice.title}"
        binding.textDate.text = "${notice.date.subSequence(0, 4)}.${notice.date.subSequence(5, 7)}.${notice.date.subSequence(8, 10)}"

        // 공지사항 클릭 시 상세 액티비티 생성
        binding.root.setOnClickListener {
            val intent = Intent(binding.root.context, NoticeDetailActivity::class.java)
            intent.putExtra("noticeId", notice.id)
            startActivity(binding.root.context, intent, null)
        }
    }
}