package kr.co.sujungvillage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.sujungvillage.data.CommDetailComments
import kr.co.sujungvillage.databinding.ListitemCommDetailBinding

class CommDetailAdapter :RecyclerView.Adapter<CommDetailHolder>(){
    var commDetailList= mutableListOf<CommDetailComments>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommDetailHolder {
        val binding=ListitemCommDetailBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CommDetailHolder(binding)
    }

    override fun onBindViewHolder(holder: CommDetailHolder, position: Int) {
        val commDetail=commDetailList.get(position)
        holder.setCommDetail(commDetail)
    }

    override fun getItemCount(): Int {
        return commDetailList.size
    }

}
class CommDetailHolder(val binding:ListitemCommDetailBinding):RecyclerView.ViewHolder(binding.root){
    fun setCommDetail(commDetail: CommDetailComments){
        binding.textName.text="${commDetail.writerId}"//익명처리해야함.
        binding.textCalDate.text="${commDetail.regDate.subSequence(0,4)}.${commDetail.regDate.subSequence(5, 7)}.${commDetail.regDate.subSequence(8, 10)}"
        binding.textContent.text="${commDetail.content}"
    }
}