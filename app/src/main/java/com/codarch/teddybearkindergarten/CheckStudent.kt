package com.codarch.teddybearkindergarten

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codarch.teddybearkindergarten.data.AdapterCheck
import com.codarch.teddybearkindergarten.data.CheckDatabaseHandler
import com.codarch.teddybearkindergarten.data.DatePickerHelper
import java.time.LocalDate
import java.util.*

class CheckStudent : AppCompatActivity() {

    lateinit var datePicker: DatePickerHelper

    @RequiresApi(Build.VERSION_CODES.O)
    var checkDay: String = LocalDate.now().toString()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check)

        val date = findViewById<TextView>(R.id.dateText3)
        date.text = checkDay

        datePicker = DatePickerHelper(this, true)
        val datePickerButton = findViewById<Button>(R.id.datePickerButton3)

        datePickerButton.setOnClickListener {
            showDatePickerDialog()
            setupListofDataIntoRecyclerView()
        }

        setupListofDataIntoRecyclerView()
    }

    override fun onUserInteraction() {
        setupListofDataIntoRecyclerView()
        super.onUserInteraction()
    }

    private fun setupListofDataIntoRecyclerView() {

        val checkDatabaseHandler: CheckDatabaseHandler = CheckDatabaseHandler(this)
        val recyclerViewCheck = findViewById<RecyclerView>(R.id.recyclerViewCheck)
        val adapter = AdapterCheck(checkDatabaseHandler.viewEmployee(checkDay))

        recyclerViewCheck.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerViewCheck.adapter = adapter
        adapter.update(checkDatabaseHandler.viewEmployee(checkDay))
    }

    @SuppressLint("Recycle")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun showDatePickerDialog() {

        val cal = Calendar.getInstance()
        val d = cal.get(Calendar.DAY_OF_MONTH)
        val m = cal.get(Calendar.MONTH)
        val y = cal.get(Calendar.YEAR)

        val dateText = findViewById<TextView>(R.id.dateText3)

        val minDate = Calendar.getInstance()
        val maxDate = Calendar.getInstance()

        minDate.add(Calendar.DAY_OF_WEEK, -14)
        datePicker.setMinDate(minDate.timeInMillis)

        maxDate.add(Calendar.DAY_OF_WEEK, 14)
        datePicker.setMaxDate(maxDate.timeInMillis)

        datePicker.showDialog(d, m, y, object : DatePickerHelper.Callback {
            @SuppressLint("SetTextI18n")
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDateSelected(dayofMonth: Int, month: Int, year: Int) {

                val dayStr = if (dayofMonth < 10) "0${dayofMonth}" else "$dayofMonth"
                val mon = month + 1
                val monthStr = if (mon < 10) "0${mon}" else "${mon}"

                checkDay = "${year}-${monthStr}-${dayStr}"
                dateText.text = "${year}-${monthStr}-${dayStr}"
                setupListofDataIntoRecyclerView()
            }
        })


    }
}