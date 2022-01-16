package com.codarch.teddybearkindergarten

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.codarch.teddybearkindergarten.data.CheckDatabaseHandler
import com.codarch.teddybearkindergarten.data.StudentCheckModel
import com.codarch.teddybearkindergarten.data.StudentDatabaseHandler
import com.codarch.teddybearkindergarten.data.StudentModel
import java.time.LocalDate
import java.util.*
import android.os.Bundle as Bundle1

const val PREFS_FILENAME = "com.codarch.teddybearkindergarten.prefs"
const val KEY_ID = "ID"
const val KEY_NAME = "NAME"
const val KEY_PARENT_NAME = "PARENTNAME"
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


        val preferences = getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
        /*
        var sit = "Gelecek"
        if (preferences.getInt(KEY_CHECK, 1) == 0)
            sit = "Gelmeyecek"

        Toast.makeText(applicationContext, "Öğrenci İsmi : ${preferences.getString(KEY_NAME, "")}\n", Toast.LENGTH_SHORT).show()*/
        checkListCreator()

    }


    fun register(view: View) {
        val intent = Intent(
            applicationContext,
            LoginActivity::class.java
        )
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun parentControl(view: View) {
        checkListCreator()
        val intent = Intent(
            applicationContext,
            ParentControl::class.java
        )
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun kindergartenControl(view: View) {
        checkListCreator()
        val intent = Intent(
            applicationContext,
            KindergartenControl::class.java
        )
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun checkScreen(view: View) {
        checkListCreator()
        val intent = Intent(
            applicationContext,
            CheckStudent::class.java
        )
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun activities(view: View) {
        val intent = Intent(
            applicationContext,
            Activities::class.java
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

        val config = Configuration()
        config.setLocale(locale)

        val context: Context = createConfigurationContext(config)
        val resources: Resources = context.resources
        resources.displayMetrics

        //val config: Configuration = Configuration()
        //config.setLocale(locale)

        // baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkListCreator() {

        var checkDay: String = LocalDate.now().toString()

        val studentDatabaseHandler: StudentDatabaseHandler = StudentDatabaseHandler(this)
        val checkDatabaseHandler: CheckDatabaseHandler = CheckDatabaseHandler(this)
        val studentList: MutableList<StudentModel> = studentDatabaseHandler.viewEmployee()

        var student: StudentCheckModel

        for (i in studentList) {

            student = StudentCheckModel(checkDay, i.studentName, i.parentName, null, null, i.id)

            if (checkDatabaseHandler.isExists(student).not()) {

                val status = checkDatabaseHandler.addEmployee(student)

                if (status > -1) {
                    Toast.makeText(applicationContext, "Database Check", Toast.LENGTH_SHORT).show()
                }
            }
        }

        checkDay = LocalDate.now().plusDays(1).toString()

        for (i in studentList) {

            student = StudentCheckModel(checkDay, i.studentName, i.parentName, null, null, i.id)

            if (checkDatabaseHandler.isExists(student).not()) {

                val status = checkDatabaseHandler.addEmployee(student)

                if (status > -1) {
                    Toast.makeText(applicationContext, "Database Check", Toast.LENGTH_SHORT).show()
                }
            }
        }

        checkDay = LocalDate.now().plusDays(2).toString()


        for (i in studentList) {

            student = StudentCheckModel(checkDay, i.studentName, i.parentName, null, null, i.id)

            if (checkDatabaseHandler.isExists(student).not()) {

                val status = checkDatabaseHandler.addEmployee(student)

                if (status > -1) {
                    Toast.makeText(applicationContext, "Database Check", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}