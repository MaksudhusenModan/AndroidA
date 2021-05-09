package com.omi.birthday

import java.util.*

class AgeCalculation {
    private var startYear = 0
    private var startMonth = 0
    private var startDay = 0
    private var endYear = 0
    private var endMonth = 0
    private var endDay = 0
    private var resYear = 0
    private var resMonth = 0
    private var resDay = 0
    private val start: Calendar? = null
    private var end: Calendar? = null
    val currentDate: String
        get() {
            end = Calendar.getInstance()
            endYear = end!!.get(Calendar.YEAR)
            endMonth = end!!.get(Calendar.MONTH)
            endMonth++
            endDay = end!!.get(Calendar.DAY_OF_MONTH)
            return "$endDay:$endMonth:$endYear"
        }

    fun setDateOfBirth(sYear: Int, sMonth: Int, sDay: Int) {
        startYear = sYear
        startMonth = sMonth
        startDay = sDay
    }

    fun calcualteYear() {
        resYear = endYear - startYear /*/ (365)*/
    }

    fun calcualteMonth() {
        if (endMonth >= startMonth) {
            resMonth = endMonth - startMonth
        } else {
            resMonth = endMonth - startMonth
            resMonth = 12 + resMonth
            resYear--
        }
    }

    fun calcualteDay() {
        if (endDay >= startDay) {
            resDay = endDay - startDay
        } else {
            resDay = endDay - startDay
            resDay = 30 + resDay
            if (resMonth == 0) {
                resMonth = 11
                resYear--
            } else {
                resMonth--
            }
        }
    }

    fun getYear(): String {
        return resYear.toString()
    }

    fun getMonth(): String {
        return resMonth.toString()
    }

    fun getDays(): String {
        return resDay.toString()
    }

    fun getBirthday(): String {
        return startMonth.toString() + "/" + startDay.toString()
    }

    val result: String
        get() = "$resYear:$resDay:$resMonth"


    fun dOBResult(): String {
        var msg = "Your lived $resYear Years"
        if (resMonth != 0) {
            msg =msg+", "+ resMonth + " Month"
        }
        if (resDay != 0) {
            msg = msg+" and " + resDay + " days"
        }
        return msg
    }
}