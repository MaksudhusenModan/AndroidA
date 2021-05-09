package com.omi.birthday

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    val myCalendar: Calendar = Calendar.getInstance()

    //  private val sharedPrefFile = "MrOmiPreference"
    //var sharedPreferences: SharedPreferences? = null
    val NOTIFICATION_CHANNEL_ID = "10001"
    private val default_notification_channel_id = "default"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (App.sharedPreferences!!.contains("Name")) {
            showDashBoard(true)
            startAlarm(true, true)
        } else {
            showDashBoard(false)
        }
        txtDOB.setOnClickListener {
            var dialog=DatePickerDialog(
                this@MainActivity, date, myCalendar[Calendar.YEAR],
                myCalendar[Calendar.MONTH],
                myCalendar[Calendar.DAY_OF_MONTH]
            )
            dialog.datePicker.maxDate=Date().time
            dialog.show()


        }
        btnSubmit.setOnClickListener {
            when {
                edtName.text.isNullOrBlank() -> {
                    showSnackbar("Please enter your name")
                }
                txtDOB.text.isNullOrBlank() -> {
                    showSnackbar("Please select your DOB")
                }
                else -> {
                    showSnackbar("Your profile saved successfully")
                    insertData()
                    startAlarm(true, true)
                    showDashBoard(true)
                }
            }

        }
    }

    private fun showDashBoard(isShowDashboard: Boolean) {
        if (isShowDashboard) {
            llBaseUI.visibility = View.GONE
            llUserInfo.visibility = View.VISIBLE
            val typeface1 =
                Typeface.createFromAsset(assets, "font/MotionPicture_PersonalUseOnly.ttf")
          //  val typeface2 = Typeface.createFromAsset(assets, "font/comic.ttf")
            txtlbl.setTypeface(typeface1)
            txtlbl.text = "Hello, "
            txtUserName.text = App.sharedPreferences!!.getString("Name", "")
            //txtUserName.setTypeface(typeface2)
            var msg = ""
            if (App.isBirthday(App.AgeCalculationValue().getBirthday())) {
                msg = "Happy Birthday\n"
            }
            txtUserInfo.text = msg + App.AgeCalculationValue().dOBResult()
            val typeface = Typeface.createFromAsset(assets, "font/MTCORSVA.TTF")
            txtUserInfo.setTypeface(typeface)
        } else {
            llBaseUI.visibility = View.VISIBLE
        }

    }

    private fun showSnackbar(msg: String) {
        // Snackbar.make(getWindow().getDecorView().getRootView(), msg, Snackbar.LENGTH_LONG).show();
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }

    private fun insertData() {
        App.insertData("Name", edtName.text!!.trim().toString())
        App.insertData("Dob", txtDOB.text!!.trim().toString())
        /*val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
        editor.putString("Name", edtName.text!!.trim().toString())
        editor.putString("Dob", txtDOB.text!!.trim().toString())
        editor.apply()
        editor.commit()*/
    }

    var date =
        OnDateSetListener { view, year, monthOfYear, dayOfMonth -> // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel()
            val date = myCalendar.time

        }

    fun updateLabel() {
        val myFormat = "MM/dd/yyyy" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        txtDOB.setText(sdf.format(myCalendar.getTime()))
    }

    fun getTime(): String {
        val myFormat = "HH" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        return sdf.format(Date())
    }
    fun getTime(date:Date): String {
        val myFormat = "dd/MM/yyyy HH:mm:ss z"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        return sdf.format(date)
    }

    private fun startAlarm(isNotification: Boolean, isRepeat: Boolean) {
        val manager = getSystemService(ALARM_SERVICE) as AlarmManager
        val myIntent: Intent
        val pendingIntent: PendingIntent

        // SET TIME HERE
        val calendar = Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY] = 8//calendar.get(Calendar.HOUR_OF_DAY)
        calendar[Calendar.MINUTE] = 0//calendar.get(Calendar.MINUTE) + 2
        calendar[Calendar.SECOND]=0
        val m=getTime().toInt()
        if(m>8)
        {
            calendar[Calendar.DATE] = calendar.get(Calendar.DATE) + 1
        }
        myIntent = Intent(this@MainActivity, AlarmNotificationReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, 0)
        if (!isRepeat) manager[AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + 3000] =
            pendingIntent else manager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }
}