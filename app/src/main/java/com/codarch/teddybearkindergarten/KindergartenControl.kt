package com.codarch.teddybearkindergarten

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codarch.teddybearkindergarten.data.Adapter
import com.codarch.teddybearkindergarten.data.CheckDatabaseHandler
import com.codarch.teddybearkindergarten.data.DatePickerHelper
import com.codarch.teddybearkindergarten.data.StudentCheckModel
import java.time.LocalDate
import java.util.*

class KindergartenControl : AppCompatActivity(), Adapter.AdapterCallback {
    @SuppressLint("WrongViewCast")

    lateinit var datePicker: DatePickerHelper

    @RequiresApi(Build.VERSION_CODES.O)
    var checkDay: String = LocalDate.now().toString()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kindergarten)

        val date = findViewById<TextView>(R.id.dateText2)
        date.text = checkDay

        datePicker = DatePickerHelper(this, true)
        val datePickerButton = findViewById<Button>(R.id.datePickerButton2)

        setupListofDataIntoRecyclerView()

        datePickerButton.setOnClickListener {
            showDatePickerDialog()
            setupListofDataIntoRecyclerView()
        }
    }

    fun setupListofDataIntoRecyclerView() {

        val checkDatabaseHandler: CheckDatabaseHandler = CheckDatabaseHandler(this)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        var adapter = Adapter(checkDatabaseHandler.viewEmployee(checkDay), this)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        adapter.update(checkDatabaseHandler.viewEmployee(checkDay))
    }


    /*@SuppressLint("Recycle")
    fun getModels(): MutableList<StudentCheckModel> {

        val models = mutableListOf(StudentCheckModel("", null, null, null, null))

        try {


            val databaseCheck = this.openOrCreateDatabase("StudentsCheck", MODE_PRIVATE, null)


            databaseCheck.execSQL("CREATE TABLE IF NOT EXISTS studentsCheck (id INTEGER PRIMARY KEY, day DATE, studentName VARCHAR, parentCheck INT, schoolCheck INT, studentId INT)")

            val cursor = databaseCheck.rawQuery(
                "SELECT * FROM studentsCheck WHERE day = '${checkDay}' ORDER BY studentId ASC",
                null
            )
            val dayIx = cursor.getColumnIndex("day")
            val studentNameIx = cursor.getColumnIndex("studentName")
            val parentCheckIx = cursor.getColumnIndex("parentCheck")
            val schoolCheckIx = cursor.getColumnIndex("schoolCheck")
            val studentIdIx = cursor.getColumnIndex("studentId")


            while (cursor.moveToNext()) {
                if (models[0].studentName == "") {
                    models.removeAt(0)
                    models.add(
                        0,
                        StudentCheckModel(
                            cursor.getString(studentNameIx),
                            cursor.getString(dayIx),
                            cursor.getInt(parentCheckIx),
                            cursor.getInt(schoolCheckIx),
                            cursor.getInt(studentIdIx)
                        )
                    )
                } else {
                    models.add(
                        StudentCheckModel(
                            cursor.getString(studentNameIx),
                            cursor.getString(dayIx),
                            cursor.getInt(parentCheckIx),
                            cursor.getInt(schoolCheckIx),
                            cursor.getInt(studentIdIx)
                        )
                    )
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return models
    }
*/
    @SuppressLint("Recycle")
    override fun onClickX(student: StudentCheckModel) {

        val checkDatabaseHandler: CheckDatabaseHandler = CheckDatabaseHandler(this)

        //val databaseCheck = this.openOrCreateDatabase("StudentsCheck", MODE_PRIVATE, null)
        //databaseCheck.execSQL("CREATE TABLE IF NOT EXISTS studentsCheck (id INTEGER PRIMARY KEY, day DATE, studentName VARCHAR, parentCheck INT, schoolCheck INT, studentId INT)")
        /*val cursor = databaseCheck.rawQuery("SELECT * FROM studentsCheck", null)


        //variables for cursor
        val idIx = cursor.getColumnIndex("id")
        val dayIx = cursor.getColumnIndex("day")
        val studentNameIx = cursor.getColumnIndex("studentName")
        val parentCheckIx = cursor.getColumnIndex("parentCheck")
        val schoolCheckIx = cursor.getColumnIndex("schoolCheck")*/

        var id: Int? = student.studentId

        var student: StudentCheckModel = checkDatabaseHandler.getByDate(id, checkDay)

        println("/////////////////CHECK //////////////////// " + student.studentName + " - " + student.studentId + " - " + student.date + " - " + student.parentCheck)

        student.schoolCheck = 0

        println("/////////////////CHECK 2//////////////////// " + student.studentName + " - " + student.studentId + " - " + student.date + " - " + student.parentCheck)

        var status = checkDatabaseHandler.updateEmployee(student)

        if (status > -1) {
            Toast.makeText(applicationContext, "Record Updated.", Toast.LENGTH_LONG).show()
        }

        //databaseCheck.execSQL("UPDATE studentsCheck SET schoolCheck = 0 WHERE studentId = ${position + 1} AND day = '$checkDay'")

    }

    @SuppressLint("Recycle")
    override fun onClickCheck(student: StudentCheckModel) {

        val checkDatabaseHandler: CheckDatabaseHandler = CheckDatabaseHandler(this)

        var id: Int? = student.studentId

        var student: StudentCheckModel = checkDatabaseHandler.getByDate(id, checkDay)

        println("/////////////////CHECK //////////////////// " + student.studentName + " - " + student.studentId + " - " + student.date + " - " + student.parentCheck)

        student.schoolCheck = 1

        println("/////////////////CHECK 2//////////////////// " + student.studentName + " - " + student.studentId + " - " + student.date + " - " + student.parentCheck)

        var status = checkDatabaseHandler.updateEmployee(student)

        if (status > -1) {
            Toast.makeText(applicationContext, "Record Updated.", Toast.LENGTH_LONG).show()
        }

        //val databaseCheck = this.openOrCreateDatabase("StudentsCheck", MODE_PRIVATE, null)
        //databaseCheck.execSQL("CREATE TABLE IF NOT EXISTS studentsCheck (id INTEGER PRIMARY KEY, day DATE, studentName VARCHAR, parentCheck INT, schoolCheck INT, studentId INT)")

        /*val cursor = databaseCheck.rawQuery("SELECT * FROM studentsCheck", null)

        //variables for cursor
        val idIx = cursor.getColumnIndex("id")
        val dayIx = cursor.getColumnIndex("day")
        val studentNameIx = cursor.getColumnIndex("studentName")
        val parentCheckIx = cursor.getColumnIndex("parentCheck")
        val schoolCheckIx = cursor.getColumnIndex("schoolCheck")*/

        //databaseCheck.execSQL("UPDATE studentsCheck SET schoolCheck = 1 WHERE studentId = ${position + 1} AND day = '$checkDay'")

    }

    @SuppressLint("Recycle")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun showDatePickerDialog() {

        val databaseCheck = this.openOrCreateDatabase("StudentsCheck", MODE_PRIVATE, null)
        databaseCheck.execSQL("CREATE TABLE IF NOT EXISTS studentsCheck (id INTEGER PRIMARY KEY, day DATE, studentName VARCHAR, parentCheck INT, schoolCheck INT, studentId INT)")


        val cal = Calendar.getInstance()
        val d = cal.get(Calendar.DAY_OF_MONTH)
        val m = cal.get(Calendar.MONTH)
        val y = cal.get(Calendar.YEAR)

        val dateText = findViewById<TextView>(R.id.dateText2)

        val minDate = Calendar.getInstance()
        val maxDate = Calendar.getInstance()

        minDate.add(Calendar.DAY_OF_WEEK, -7)
        datePicker.setMinDate(minDate.timeInMillis)

        maxDate.add(Calendar.DAY_OF_WEEK, 7)
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
                println("tusa basildi: " + "${year}-${monthStr}-${dayStr}")

            }
        })
    }
}
