package com.example.alarm

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_alarm.view.*
import java.util.*
import kotlin.collections.ArrayList

class AlarmsAdapter(
    private val items: ArrayList<Alarm> = arrayListOf(),
    private val onSwitchClick: (Alarm) -> Unit,
    private val onItemLongClick: (Alarm) -> Unit,
    private val onItemClick: (Alarm) -> Unit,
    private val context: Context
) : RecyclerView.Adapter<AlarmsAdapter.ItemViewHolder>() {
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_alarm, parent, false)

        return ItemViewHolder(view)
    }

    fun addItems(data: List<Alarm>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bindItem(alarm: Alarm) {
            view.alarm_name.text = alarm.name
            view.alarm_time.text = alarm.time
            view.alarm_status.isChecked = alarm.status

            view.alarm_status.setOnClickListener {
                onSwitchClick(alarm)
            }

            view.setOnLongClickListener {
                onItemLongClick(alarm)
                return@setOnLongClickListener true
            }

            view.setOnClickListener {
                onItemClick(alarm)
            }
        }
    }
}