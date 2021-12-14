package com.codarch.teddybearkindergarten

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import android.os.Bundle as Bundle1

const val PREFS_FILENAME = "com.codarch.teddybearkindergarten.prefs"
const val KEY_NAME = "NAME"
const val KEY_PHONE = "PHONE"
const val KEY_CHECK = "CHECK"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle1?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val translateButton = findViewById<Button>(R.id.translateButton)

        LoadLanguage()

        translateButton.setOnClickListener {
            ShowLanguageDialog()
        }

        try {
            val database = this.openOrCreateDatabase("Students", MODE_PRIVATE, null)
            val databaseCheck = this.openOrCreateDatabase("StudentsCheck", MODE_PRIVATE, null)
            database.execSQL("CREATE TABLE IF NOT EXISTS students (id INTEGER PRIMARY KEY, studentName VARCHAR, parentName VARCHAR, parentPhoneNumber VARCHAR, homeAddress VARCHAR, password VARCHAR)")
            databaseCheck.execSQL("CREATE TABLE IF NOT EXISTS studentsCheck (id INTEGER PRIMARY KEY, studentName VARCHAR, parentCheck INT, schoolCheck INT)")
            /*database.execSQL("INSERT INTO students (studentName, parentName, parentPhoneNumber, homeAddress, password) VALUES ('Student Name','Parent Name','Phone Number','Address','Password')")
            database.execSQL("INSERT INTO students (studentName, parentName, parentPhoneNumber, homeAddress, password) VALUES ('Student 1 Name','Parent 1 Name','Phone 1 Number','Address 1','Password1')")
            databaseCheck.execSQL("INSERT INTO studentsCheck (studentName, parentCheck, schoolCheck) VALUES ('Student Name',1,1)")
            databaseCheck.execSQL("INSERT INTO studentsCheck (studentName, parentCheck, schoolCheck) VALUES ('Student 1 Name',1,1)")*/

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
        val editor: SharedPreferences.Editor =
            getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", lang)
        editor.apply()
    }

    private fun LoadLanguage() {
        val pref: SharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language: String? = pref.getString("My_Lang", "")
        if (language != null) {
            SetLocale(language)
        }
    }
}