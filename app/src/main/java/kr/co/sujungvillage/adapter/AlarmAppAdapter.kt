package kr.co.sujungvillage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.sujungvillage.data.Alarm
import kr.co.sujungvillage.databinding.ListitemAlarmAppBinding

class AlarmAppAdapter: RecyclerView.Adapter<AlarmAppHolder>() {
    var alarmList = mutableListOf<Alarm>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmAppHolder {
        val binding = ListitemAlarmAppBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlarmAppHolder(binding)
    }

    override fun onBindViewHolder(holder: AlarmAppHolder, position: Int) {
        val alarm = alarmList.get(position)
        holder.setAlarm(alarm)
    }

    override fun getItemCount(): Int {
        return alarmList.size
    }
}

class AlarmAppHolder(val binding: ListitemAlarmAppBinding): RecyclerView.ViewHolder(binding.root) {
    fun setAlarm(alarm: Alarm) {
        binding.textTitle.text = alarm.title
        binding.textContent.text = alarm.content
        binding.textDate.text = alarm.date
    }
}