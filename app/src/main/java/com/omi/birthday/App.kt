package com.omi.birthday

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import java.text.SimpleDateFormat
import java.util.*

class App : Application() {


    companion object {
        var ageCalculation: AgeCalculation? = null
        var sharedPreferences: SharedPreferences? = null
        private val sharedPrefFile = "MrOmiPreference"
        fun insertData(key: String, value: String) {
            val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
            editor.putString(key, value)
            editor.apply()
            editor.commit()
        }

        fun AgeCalculationValue(): AgeCalculation {
            if (ageCalculation == null) {
                ageCalculation = AgeCalculation()
                val dob = arrayOf(App.sharedPreferences!!.getString("Dob", "")!!.split("/"))

                ageCalculation!!.setDateOfBirth(
                    dob[0][2].toString().toInt(),
                    dob[0][0].toString().toInt(),
                    dob[0][1].toString().toInt()
                )
                ageCalculation!!.currentDate
                ageCalculation!!.calcualteYear()
                ageCalculation!!.calcualteMonth()
                ageCalculation!!.calcualteDay()
                return ageCalculation!!
            }
            return ageCalculation!!
        }

        fun isBirthday(birthday: String): Boolean {
            val myFormat = "M/dd" //In which you need put here
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            val today = sdf.format(Date())
            if (today.equals(birthday))
                return true
            else
                return false

        }
    }

    override fun onCreate() {
        super.onCreate()
        sharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
    }

    fun getPref(): SharedPreferences {
        return sharedPreferences!!
    }


}