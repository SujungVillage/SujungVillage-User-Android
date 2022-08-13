package kr.co.sujungvillage

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kr.co.sujungvillage.adapter.RewardAdapter
import kr.co.sujungvillage.data.RewardGetResultDTO
import kr.co.sujungvillage.databinding.ActivityRewardBinding
import kr.co.sujungvillage.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RewardActivity : AppCompatActivity() {

    val binding by lazy { ActivityRewardBinding.inflate(layoutInflater) }

    companion object {
        var rewardCount = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 재사생 학번, 토큰 불러오기
        val shared = this.getSharedPreferences("SujungVillage", Context.MODE_PRIVATE)
        val token = shared?.getString("token", "error").toString()

        var totalReward = 0
        var totalPenalty = 0

        // 뒤로가기 버튼 연결
        binding.btnBack.setOnClickListener { finish() }

        // 상벌점 내역 리스트 조회 API 연결
        RetrofitBuilder.rewardApi.rewardGet(token).enqueue(object: Callback<List<RewardGetResultDTO>> {
            override fun onResponse(call: Call<List<RewardGetResultDTO>>, response: Response<List<RewardGetResultDTO>>) {
                Log.d("REWARD_GET", "상벌점 내역 리스트 조회 성공")

                val rewardList: MutableList<RewardGetResultDTO> = mutableListOf()

                // 상벌점 내역이 존재하지 않는 경우
                if (response.body()?.size == 0) {
                    rewardList.add(RewardGetResultDTO(-1L, "", 0, "상벌점 내역이 없습니다.", ""))
                }
                // 상벌점 내역이 존재하는 경우
                else {
                    rewardCount = response.body()?.size!!

                    for (reward in response.body()!!) {
                        if (reward.score < 0) totalPenalty -= reward.score
                        else totalReward += reward.score
                        rewardList.add(RewardGetResultDTO(reward.id, reward.userId, reward.score, reward.reason, reward.date))
                    }

                    // 상점 벌점 초기화
                    binding.textReward.text = "${totalReward}점"
                    binding.textPenalty.text = "${totalPenalty}점"
                }

                var adapter = RewardAdapter()
                adapter.rewardList = rewardList
                binding.recyclerReward.adapter = adapter
                binding.recyclerReward.layoutManager = LinearLayoutManager(this@RewardActivity)
            }

            override fun onFailure(call: Call<List<RewardGetResultDTO>>, t: Throwable) {
                Log.e("REWARD_GET", "상벌점 내역 리스트 조회 실패")
                Log.e("REWARD_GET", t.message.toString())
            }
        })

        setContentView(binding.root)
    }
}