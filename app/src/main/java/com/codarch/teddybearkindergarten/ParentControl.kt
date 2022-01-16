package com.codarch.teddybearkindergarten

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.codarch.teddybearkindergarten.data.CheckDatabaseHandler
import com.codarch.teddybearkindergarten.data.DatePickerHelper
import com.codarch.teddybearkindergarten.data.StudentCheckModel
import java.time.LocalDate
import java.util.*


class ParentControl : AppCompatActivity() {
    @SuppressLint("WrongViewCast", "CommitPrefEdits", "Recycle")

    lateinit var datePicker: DatePickerHelper

    @RequiresApi(Build.VERSION_CODES.O)
    var checkDay: String = LocalDate.now().toString()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent_control)

        val date = findViewById<TextView>(R.id.dateText)
        date.text = checkDay

        datePicker = DatePickerHelper(this, true)

        val datePickerButton = findViewById<Button>(R.id.datePickerButton)
        datePickerButton.setOnClickListener {
            showDatePickerDialog()
        }

        val checkDatabaseHandler: CheckDatabaseHandler = CheckDatabaseHandler(this)

        val studentName: TextView = findViewById<TextView>(R.id.studentName)
        val situationText: TextView = findViewById<TextView>(R.id.situation)

        val preferences = getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
        val editor = preferences.edit()

        val checkButton = findViewById<ImageView>(R.id.checkButton)
        val xButton = findViewById<ImageView>(R.id.xButton)

        if (preferences.getInt(KEY_CHECK, 1) == 1) {
            situationText.text = getString(R.string.parentCheckFeedback)

        } else {
            situationText.text = getString(R.string.parentXFeedback)
        }

        studentName.text = preferences.getString(KEY_NAME, "")

        checkButton.setOnClickListener {

            var id = preferences.getInt(KEY_ID, 1)
            var student: StudentCheckModel = checkDatabaseHandler.getByDate(id, checkDay)

            println("/////////////////CHECK //////////////////// " + student.studentName + " - " + student.studentId + " - " + student.date + " - " + student.parentCheck)

            student.parentCheck = 1

            println("/////////////////CHECK 2//////////////////// " + student.studentName + " - " + student.studentId + " - " + student.date + " - " + student.parentCheck)

            var status = checkDatabaseHandler.updateEmployee(student)

            if (status > -1) {
                Toast.makeText(applicationContext, "Record Updated.", Toast.LENGTH_LONG).show()
            }

            /* status = checkDatabaseHandler.deleteEmployee(student)
             println("SSSSSSSSSSSSSSSSSTATUSSSSSSSSSSSSSSS: " + status)*/

            editor.putInt(KEY_CHECK, 1)
            editor.apply()

            Toast.makeText(applicationContext, getString(R.string.parentCheckFeedback), Toast.LENGTH_SHORT).show()

            situationText.text = getString(R.string.parentCheckFeedback)

            findViewById<TextView>(R.id.textView8).setTextColor(Color.parseColor("#489644"))
            findViewById<TextView>(R.id.textView9).setTextColor(Color.parseColor("#675F59"))
        }

        xButton.setOnClickListener {
            var id = preferences.getInt(KEY_ID, 1)
            var student: StudentCheckModel = checkDatabaseHandler.getByDate(id, checkDay)

            println("/////////////////XXXXXX//////////////////// " + student.studentName + " - " + student.studentId + " - " + student.date + " - " + student.parentCheck)

            student.parentCheck = 0

            println("/////////////////XXXXXX 2//////////////////// " + student.studentName + " - " + student.studentId + " - " + student.date + " - " + student.parentCheck)

            var status = checkDatabaseHandler.updateEmployee(student)

            if (status > -1) {
                Toast.makeText(applicationContext, "Record Updated.", Toast.LENGTH_LONG).show()
            }

            editor.putInt(KEY_CHECK, 0)
            editor.apply()

            Toast.makeText(applicationContext, getString(R.string.parentXFeedback), Toast.LENGTH_SHORT).show()

            situationText.text = getString(R.string.parentXFeedback)

            findViewById<TextView>(R.id.textView8).setTextColor(Color.parseColor("#675F59"))
            findViewById<TextView>(R.id.textView9).setTextColor(Color.parseColor("#e93b2d"))
        }
    }


    @SuppressLint("Recycle")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun showDatePickerDialog() {

        val cal = Calendar.getInstance()
        val d = cal.get(Calendar.DAY_OF_MONTH)
        val m = cal.get(Calendar.MONTH)
        val y = cal.get(Calendar.YEAR)

        val dateText = findViewById<TextView>(R.id.dateText)

        val minDate = Calendar.getInstance()
        val maxDate = Calendar.getInstance()

        minDate.set(Calendar.HOUR_OF_DAY, 0)
        minDate.set(Calendar.MINUTE, 0)
        minDate.set(Calendar.SECOND, 0)
        datePicker.setMinDate(minDate.timeInMillis)

        maxDate.add(Calendar.DAY_OF_WEEK, 7)
        datePicker.setMaxDate(maxDate.timeInMillis)

        datePicker.showDialog(d, m, y, object : DatePickerHelper.Callback {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDateSelected(dayofMonth: Int, month: Int, year: Int) {
                val dayStr = if (dayofMonth < 10) "0${dayofMonth}" else "${dayofMonth}"
                val mon = month + 1
                val monthStr = if (mon < 10) "0${mon}" else "${mon}"
                checkDay = "${year}-${monthStr}-${dayStr}"
                dateText.text = "${year}-${monthStr}-${dayStr}"
            }
        })
    }
}