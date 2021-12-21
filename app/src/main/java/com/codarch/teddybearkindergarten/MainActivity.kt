package com.codarch.teddybearkindergarten

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDate
import java.util.*
import android.os.Bundle as Bundle1

const val PREFS_FILENAME = "com.codarch.teddybearkindergarten.prefs"
const val KEY_NAME = "NAME"
const val KEY_ID = "ID"
const val KEY_PHONE = "PHONE"
const val KEY_CHECK = "CHECK"

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle1?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val translateButton = findViewById<Button>(R.id.translateButton)

        translateButton.setOnClickListener {
            ShowLanguageDialog()
        }

        try {
            val database = this.openOrCreateDatabase("Students", MODE_PRIVATE, null)
            val databaseCheck = this.openOrCreateDatabase("StudentsCheck", MODE_PRIVATE, null)
            database.execSQL("CREATE TABLE IF NOT EXISTS students (id INTEGER PRIMARY KEY, studentName VARCHAR, parentName VARCHAR, parentPhoneNumber VARCHAR, homeAddress VARCHAR, password VARCHAR)")
            databaseCheck.execSQL("CREATE TABLE IF NOT EXISTS studentsCheck (id INTEGER PRIMARY KEY, day DATE, studentName VARCHAR, parentCheck INT, schoolCheck INT, studentId INT)")

            /*database.execSQL("INSERT INTO students (studentName, parentName, parentPhoneNumber, homeAddress, password) VALUES ('Student 1 Name','Parent 1 Name','Phone 1 Number','1 Address','1Password')")
            databaseCheck.execSQL("INSERT INTO studentsCheck (day, studentName, parentCheck, schoolCheck, studentId) VALUES ('2021-12-21','Student 1 Name',0,1,1)")
            databaseCheck.execSQL("INSERT INTO studentsCheck (day, studentName, parentCheck, schoolCheck, studentId) VALUES ('2021-12-22','Student 11 Name',1,0,1)")
            databaseCheck.execSQL("INSERT INTO studentsCheck (day, studentName, parentCheck, schoolCheck, studentId) VALUES ('2021-12-23','Student 111 Name',0,1,1)")
            databaseCheck.execSQL("INSERT INTO studentsCheck (day, studentName, parentCheck, schoolCheck, studentId) VALUES ('2021-12-24','Student 1111 Name',1,0,1)")

            database.execSQL("INSERT INTO students (studentName, parentName, parentPhoneNumber, homeAddress, password) VALUES ('Student 2 Name','Parent 2 Name','Phone 2 Number','2 Address','2Password')")
            databaseCheck.execSQL("INSERT INTO studentsCheck (day, studentName, parentCheck, schoolCheck, studentId) VALUES ('2021-12-21','Student 2 Name',0,1,2)")
            databaseCheck.execSQL("INSERT INTO studentsCheck (day, studentName, parentCheck, schoolCheck, studentId) VALUES ('2021-12-22','Student 22 Name',1,0,2)")
            databaseCheck.execSQL("INSERT INTO studentsCheck (day, studentName, parentCheck, schoolCheck, studentId) VALUES ('2021-12-23','Student 222 Name',0,1,2)")
            databaseCheck.execSQL("INSERT INTO studentsCheck (day, studentName, parentCheck, schoolCheck, studentId) VALUES ('2021-12-24','Student 2222 Name',1,0,2)")

            database.execSQL("INSERT INTO students (studentName, parentName, parentPhoneNumber, homeAddress, password) VALUES ('Student 3 Name','Parent 3 Name','Phone 3 Number','3 Address','3Password')")
            databaseCheck.execSQL("INSERT INTO studentsCheck (day, studentName, parentCheck, schoolCheck, studentId) VALUES ('2021-12-21','Student 3 Name',0,0,3)")
            databaseCheck.execSQL("INSERT INTO studentsCheck (day, studentName, parentCheck, schoolCheck, studentId) VALUES ('2021-12-22','Student 33 Name',1,0,3)")
            databaseCheck.execSQL("INSERT INTO studentsCheck (day, studentName, parentCheck, schoolCheck, studentId) VALUES ('2021-12-23','Student 333 Name',0,0,3)")
            databaseCheck.execSQL("INSERT INTO studentsCheck (day, studentName, parentCheck, schoolCheck, studentId) VALUES ('2021-12-24','Student 3333 Name',1,0,3)")

            database.execSQL("INSERT INTO students (studentName, parentName, parentPhoneNumber, homeAddress, password) VALUES ('Student 4 Name','Parent 4 Name','Phone 4 Number','4 Address','4Password')")
            databaseCheck.execSQL("INSERT INTO studentsCheck (day, studentName, parentCheck, schoolCheck, studentId) VALUES ('2021-12-21','Student 4 Name',0,1,4)")
            databaseCheck.execSQL("INSERT INTO studentsCheck (day, studentName, parentCheck, schoolCheck, studentId) VALUES ('2021-12-22','Student 44 Name',1,0,4)")
            databaseCheck.execSQL("INSERT INTO studentsCheck (day, studentName, parentCheck, schoolCheck, studentId) VALUES ('2021-12-23','Student 444 Name',1,1,4)")
            databaseCheck.execSQL("INSERT INTO studentsCheck (day, studentName, parentCheck, schoolCheck, studentId) VALUES ('2021-12-24','Student 4444 Name',1,0,4)")

            database.execSQL("INSERT INTO students (studentName, parentName, parentPhoneNumber, homeAddress, password) VALUES ('Student 5 Name','Parent 5 Name','Phone 5 Number','5 Address','5Password')")
            databaseCheck.execSQL("INSERT INTO studentsCheck (day, studentName, parentCheck, schoolCheck, studentId) VALUES ('2021-12-21','Student 5 Name',1,0,5)")
            databaseCheck.execSQL("INSERT INTO studentsCheck (day, studentName, parentCheck, schoolCheck, studentId) VALUES ('2021-12-22','Student 55 Name',1,1,5)")
            databaseCheck.execSQL("INSERT INTO studentsCheck (day, studentName, parentCheck, schoolCheck, studentId) VALUES ('2021-12-23','Student 555 Name',1,0,5)")
            databaseCheck.execSQL("INSERT INTO studentsCheck (day, studentName, parentCheck, schoolCheck, studentId) VALUES ('2021-12-24','Student 5555 Name',0,0,5)")

            database.execSQL("INSERT INTO students (studentName, parentName, parentPhoneNumber, homeAddress, password) VALUES ('Student 6 Name','Parent 6 Name','Phone 6 Number','6 Address','6Password')")
            databaseCheck.execSQL("INSERT INTO studentsCheck (day, studentName, parentCheck, schoolCheck, studentId) VALUES ('2021-12-21','Student 6 Name',0,0,6)")
            databaseCheck.execSQL("INSERT INTO studentsCheck (day, studentName, parentCheck, schoolCheck, studentId) VALUES ('2021-12-22','Student 66 Name',1,1,6)")
            databaseCheck.execSQL("INSERT INTO studentsCheck (day, studentName, parentCheck, schoolCheck, studentId) VALUES ('2021-12-23','Student 666 Name',0,1,6)")
            databaseCheck.execSQL("INSERT INTO studentsCheck (day, studentName, parentCheck, schoolCheck, studentId) VALUES ('2021-12-24','Student 6666 Name',1,0,6)")

            database.execSQL("INSERT INTO students (studentName, parentName, parentPhoneNumber, homeAddress, password) VALUES ('Student 7 Name','Parent 7 Name','Phone 7 Number','7 Address','7Password')")
            databaseCheck.execSQL("INSERT INTO studentsCheck (day, studentName, parentCheck, schoolCheck, studentId) VALUES ('2021-12-21','Student 7 Name',1,0,7)")
            databaseCheck.execSQL("INSERT INTO studentsCheck (day, studentName, parentCheck, schoolCheck, studentId) VALUES ('2021-12-22','Student 77 Name',1,1,7)")
            databaseCheck.execSQL("INSERT INTO studentsCheck (day, studentName, parentCheck, schoolCheck, studentId) VALUES ('2021-12-23','Student 777 Name',0,1,7)")
            databaseCheck.execSQL("INSERT INTO studentsCheck (day, studentName, parentCheck, schoolCheck, studentId) VALUES ('2021-12-24','Student 7777 Name',0,0,7)")*/


        } catch (e: Exception) {
            e.printStackTrace()
        }

        val preferences = getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
        var sit = "Gelecek"
        if (preferences.getInt(KEY_CHECK, 1) == 0)
            sit = "Gelmeyecek"

        Toast.makeText(applicationContext, "Öğrenci İsmi : ${preferences.getString(KEY_NAME, "")}\n" , Toast.LENGTH_SHORT).show()
    }


    fun register(view: View) {
        val intent = Intent(
            applicationContext,
            LoginActivity::class.java
        )
        startActivity(intent)
    }

    fun parentControl(view: View) {
        val intent = Intent(
            applicationContext,
            ParentControl::class.java
        )
        startActivity(intent)
    }

    fun kindergartenControl(view: View) {
        val intent = Intent(
            applicationContext,
            KindergartenControl::class.java
        )
        startActivity(intent)
    }

    fun checkScreen(view: View) {
        val intent = Intent(
            applicationContext,
            CheckStudent::class.java
        )
        startActivity(intent)
    }

    private fun ShowLanguageDialog() {

        val ListItems = arrayOf("English", "Türkçe")

        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle("Dil Seçiniz")
        builder.setItems(ListItems) { dialog, which ->

            when (which) {
                0 -> {
                    SetLocale("en")
                    recreate()
                }
                1 -> {
                    SetLocale("tr")
                    recreate()
                }

            }
            dialog.dismiss()

        }

        val mDialog: AlertDialog = builder.create()
        mDialog.show()

    }

    private fun SetLocale(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config: Configuration = Configuration()
        config.setLocale(locale)

        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
    }
}