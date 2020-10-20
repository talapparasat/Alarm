package com.example.alarm

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import android.app.AlertDialog
import java.util.*

class MainActivity : AppCompatActivity() {

    private val alarmsAdapter by lazy {
        AlarmsAdapter(
            onSwitchClick = { alarm ->
                toggleAlarm(alarm)
            },
            onItemClick = { alarm ->
                showLeftTimeAlert(alarm)
            },
            onItemLongClick = { alarm ->
                showDeleteDialog(alarm)
            },
            context = this.applicationContext
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()
    }

    override fun onResume() {
        super.onResume()

        loadData()
    }

    private fun setupViews() {
        list_view.layoutManager = LinearLayoutManager(this)
        list_view.adapter = alarmsAdapter

        loadData()

        btn_add.setOnClickListener {
            val intent = Intent(this, AddAlarmActivity::class.java)

            startActivity(intent)
        }
    }

    private fun loadData() {

        AsyncTask.execute {

            val alarms = AppDatabase.getInstance(applicationContext)!!
                .getAlarmDao().getAlarms()

            runOnUiThread {
                alarmsAdapter.addItems(alarms)
            }
        }
    }

    private fun toggleAlarm(alarm: Alarm) {
        AsyncTask.execute {

            AppDatabase.getInstance(applicationContext)!!
                .getAlarmDao().switchStatus(alarm.id!!, !alarm.status)

        }

        loadData()
    }

    private fun deleteAlarm(alarm: Alarm) {
        AsyncTask.execute {

            AppDatabase.getInstance(applicationContext)!!
                .getAlarmDao().delete(alarm)

        }

        loadData()
    }

    private fun showLeftTimeAlert(alarm: Alarm) {

        val c = Calendar.getInstance()

        val alarmTime = alarm.time.split(':')
        val alarmHour = alarmTime[0].toInt()
        val alarmMinute = alarmTime[1].toInt()

        val currentHour = c.get(Calendar.HOUR_OF_DAY)
        val currentMinute = c.get(Calendar.MINUTE)

        var leftTime = (alarmHour * 60 +  alarmMinute) - (currentHour * 60 + currentMinute)

        if(leftTime < 0) {
            leftTime += 1440
        }

        val leftHour = leftTime / 60
        val leftMinute = leftTime % 60

        val builder = AlertDialog.Builder(this)
        if(leftHour == 0) {
            if(leftMinute == 0) {
                builder.setTitle("Alarm now")
            } else {
                builder.setTitle("$leftMinute minutes left")
            }
        } else {
            if(leftMinute == 0) {
                builder.setTitle("$leftHour hours left")
            } else {
                builder.setTitle("$leftHour hours $leftMinute minutes left")
            }
        }

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
        }

        builder.show()

    }

    private fun showDeleteDialog(alarm: Alarm) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Do you want to delete?")

        builder.setPositiveButton("Yes") { dialog, which ->
            deleteAlarm(alarm)
        }

        builder.setNegativeButton("No") { dialog, which ->

        }

        builder.show()
    }
}
