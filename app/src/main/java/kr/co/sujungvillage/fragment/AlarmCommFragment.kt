package kr.co.sujungvillage.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kr.co.sujungvillage.adapter.AlarmAppAdapter
import kr.co.sujungvillage.data.Alarm
import kr.co.sujungvillage.databinding.FragmentAlarmCommBinding

class AlarmCommFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAlarmCommBinding.inflate(inflater, container, false)

        // 알림 불러오기
        val shared = this.activity?.getSharedPreferences("SujungVillage", Context.MODE_PRIVATE)
        val count = shared?.getInt("commAlarm", 0)

        val alarmList: MutableList<Alarm> = mutableListOf()
        if (count!! > 0) {
            for (i: Int in count downTo 1) {
                val title = shared.getString("commAlarmTitle$i", "알림 제목 오류")
                val content = shared.getString("commAlarmBody$i", "알림 내용 오류")
                val isRead = shared.getBoolean("commAlarmRead$i", false)
                val date = shared.getString("commAlarmDate$i", "날짜 오류")
                alarmList.add(
                    Alarm(
                        i.toLong(),
                        title.toString(),
                        content.toString(),
                        isRead,
                        date.toString()
                    )
                )
                val editor = shared.edit()
                editor.putBoolean("commAlarmRead$i", true)
                editor.apply()
            }

            var adapter = AlarmAppAdapter()
            adapter.alarmList = alarmList
            binding.recycleAlarm.adapter = adapter
            binding.recycleAlarm.layoutManager = LinearLayoutManager(activity)
        }

        return binding.root
    }
}
