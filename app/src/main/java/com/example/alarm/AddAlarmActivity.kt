package com.example.alarm

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import kotlinx.android.synthetic.main.activity_add_alarm.*

class AddAlarmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_alarm)

        setupViews()
    }

    private fun setupViews() {
        btn_add.setOnClickListener {

            val newAlarm = Alarm(
                name = alarm_name.text.toString(),
                time = alarm_time.hour.toString().padStart(2, '0') + ":" + alarm_time.minute.toString().padStart(2, '0'),
                status = true
            )

            AsyncTask.execute {
                AppDatabase.getInstance(applicationContext)!!
                    .getAlarmDao().insertAlarm(newAlarm)

                runOnUiThread {
                    finish()
                }
            }
        }
    }
}
