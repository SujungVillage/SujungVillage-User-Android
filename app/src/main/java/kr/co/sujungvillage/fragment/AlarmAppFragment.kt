package kr.co.sujungvillage.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kr.co.sujungvillage.adapter.AlarmAppAdapter
import kr.co.sujungvillage.data.Alarm
import kr.co.sujungvillage.databinding.FragmentAlarmAppBinding

class AlarmAppFragment : Fragment() {
    private lateinit var binding: FragmentAlarmAppBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentAlarmAppBinding.inflate(inflater,container,false)

        // 알림 불러오기
        val shared = this.activity?.getSharedPreferences("SujungVillage", Context.MODE_PRIVATE)
        val count = shared?.getInt("appAlarm", 0)

        val alarmList: MutableList<Alarm> = mutableListOf()
        if (count!! > 0) {
            for (i: Int in count downTo 1) {
                val title = shared.getString("appAlarmTitle${i}", "알림 제목 오류")
                val content = shared.getString("appAlarmBody${i}", "알림 내용 오류")
                val isRead = shared.getBoolean("appAlarmRead${i}", true)
                val date = shared.getString("appAlarmDate${i}", "날짜 오류")
                alarmList.add(Alarm(i.toLong(), title.toString(), content.toString(), isRead, date.toString()))
            }

            var adapter = AlarmAppAdapter()
            adapter.alarmList = alarmList
            binding.recycleAlarm.adapter = adapter
            binding.recycleAlarm.layoutManager = LinearLayoutManager(activity)
        }

        return binding.root
    }
}