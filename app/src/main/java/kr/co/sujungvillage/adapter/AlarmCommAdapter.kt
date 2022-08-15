package kr.co.sujungvillage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.sujungvillage.data.Alarm
import kr.co.sujungvillage.databinding.ListitemAlarmCommBinding

class AlarmCommAdapter: RecyclerView.Adapter<AlarmCommHolder>() {
    var alarmList = mutableListOf<Alarm>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmCommHolder {
        val binding = ListitemAlarmCommBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlarmCommHolder(binding)
    }

    override fun onBindViewHolder(holder: AlarmCommHolder, position: Int) {
        val alarm = alarmList.get(position)
        holder.setAlarm(alarm)
    }

    override fun getItemCount(): Int {
        return alarmList.size
    }
}

class AlarmCommHolder(val binding: ListitemAlarmCommBinding): RecyclerView.ViewHolder(binding.root) {
    fun setAlarm(alarm: Alarm) {
        binding.textTitle.text = alarm.title
        binding.textContent.text = alarm.content
        binding.textDate.text = alarm.date
    }
}