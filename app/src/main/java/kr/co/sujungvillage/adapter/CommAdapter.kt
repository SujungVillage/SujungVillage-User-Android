package kr.co.sujungvillage.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kr.co.sujungvillage.CommDetailActivity
import kr.co.sujungvillage.data.CommDTO
import kr.co.sujungvillage.databinding.ListitemCommBinding
import kr.co.sujungvillage.fragment.CommFragment.Companion.dormitory

class CommAdapter : RecyclerView.Adapter<CommHolder>() {
    var commList = mutableListOf<CommDTO>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommHolder {
        val binding =
            ListitemCommBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommHolder(binding)
    }

    override fun onBindViewHolder(holder: CommHolder, position: Int) {
        val comm = commList.get(position)
        holder.setComm(comm)
    }

    override fun getItemCount(): Int {
        return commList.size
    }
}

class CommHolder(val binding: ListitemCommBinding) : RecyclerView.ViewHolder(binding.root) {
    fun setComm(comm: CommDTO) {
        binding.textTitle.text = comm.title
        binding.textDate.text = "${comm.regDate.subSequence(0, 4)}.${
        comm.regDate.subSequence(
            5,
            7
        )
        }.${comm.regDate.subSequence(8, 10)}"
        binding.textContent.text = comm.content
        binding.commentCount.text = "${comm.numOfComments}"

        // 관리자인지 아닌지 마크 띄우기 writerId 받아와서 고치기
        try {
            if (comm.writerId.toInt() >= 99990000) { // 관리자인 경우
                binding.textAdmin.visibility = View.VISIBLE
            }
        } catch (e: NumberFormatException) {
        }

        // 게시글 클릭 시 상세 액티비티 생성
        binding.root.setOnClickListener {
            val intent = Intent(binding.root.context, CommDetailActivity::class.java)
            intent.putExtra("postId", comm.id)
            intent.putExtra("dormitory", dormitory)
            ContextCompat.startActivity(binding.root.context, intent, null)
        }
    }
}
