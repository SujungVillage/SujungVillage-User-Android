package kr.co.sujungvillage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.sujungvillage.R
import kr.co.sujungvillage.RewardActivity.Companion.rewardCount
import kr.co.sujungvillage.data.RewardGetResultDTO
import kr.co.sujungvillage.databinding.ListitemRewardTableBinding

class RewardAdapter : RecyclerView.Adapter<RewardHolder>() {
    var rewardList = mutableListOf<RewardGetResultDTO>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RewardHolder {
        val binding =
            ListitemRewardTableBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RewardHolder(binding)
    }

    override fun onBindViewHolder(holder: RewardHolder, position: Int) {
        val reward = rewardList.get(position)
        holder.setReward(reward, position)
    }

    override fun getItemCount(): Int {
        return rewardList.size
    }
}

class RewardHolder(val binding: ListitemRewardTableBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun setReward(reward: RewardGetResultDTO, position: Int) {
        if (reward.id == -1L) {
            binding.textDate.text = ""
            binding.textScore.text = ""
            binding.textReason.text = reward.reason
            binding.textDate.setBackgroundResource(R.drawable.style_reward_table_bottom_left)
            binding.textReason.setBackgroundResource(R.drawable.style_reward_table_bottom_right)
            return
        }

        binding.textDate.text = "${reward.date.subSequence(0, 10)}"
        binding.textScore.text = reward.score.toString()
        binding.textReason.text = reward.reason

        if (position + 1 == rewardCount) {
            binding.textDate.setBackgroundResource(R.drawable.style_reward_table_bottom_left)
            binding.textReason.setBackgroundResource(R.drawable.style_reward_table_bottom_right)
        }
    }
}
