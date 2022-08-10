package kr.co.sujungvillage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.sujungvillage.data.CommDetailCommentsRequest
import kr.co.sujungvillage.databinding.ListitemCommDetailBinding

class CommDetailAdapter() :RecyclerView.Adapter<CommDetailHolder>(){
    var commDetailList=mutableListOf<CommDetailCommentsRequest>()

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
    fun setCommDetail(commDetail: CommDetailCommentsRequest){
        binding.textName.text="익명"//익명처리해야함.
        val hour=commDetail.regDate?.subSequence(11,13).toString().toInt()
        var hourResult=hour.toString()
        if (hour<10){
            hourResult="0${hour}"
        }
        val min=(commDetail.regDate?.subSequence(14,16).toString().toInt())
        var minResult=min.toString()
        if(min<10){
            minResult="0${min}"
        }
        binding.textCalDate.text="${commDetail.regDate?.subSequence(0,4)}/${commDetail.regDate?.subSequence(5, 7)}/${commDetail.regDate?.subSequence(8, 10)} ${hourResult}:${minResult}"
        binding.textContent.text="${commDetail.content}"
    }
}