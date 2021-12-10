package com.codarch.teddybearkindergarten

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle as Bundle1

const val PREFS_FILENAME = "com.codarch.teddybearkindergarten.prefs"
const val KEY_NAME = "NAME"
const val KEY_PHONE = "PHONE"
const val KEY_CHECK = "CHECK"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle1?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            val database = this.openOrCreateDatabase("Students", MODE_PRIVATE, null)
            val databaseCheck = this.openOrCreateDatabase("StudentsCheck", MODE_PRIVATE, null)
            database.execSQL("CREATE TABLE IF NOT EXISTS students (id INTEGER PRIMARY KEY, studentName VARCHAR, parentName VARCHAR, parentPhoneNumber VARCHAR, homeAddress VARCHAR, password VARCHAR)")
            databaseCheck.execSQL("CREATE TABLE IF NOT EXISTS studentsCheck (id INTEGER PRIMARY KEY, studentName VARCHAR, parentCheck INT, schoolCheck INT)")
            }
        catch (e: Exception){
            e.printStackTrace()
        }


        val preferences = getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
        var sit = "Gelecek"
        if(preferences.getInt(KEY_CHECK,1) == 0)
            sit = "Gelmeyecek"

        Toast.makeText(applicationContext, "Öğrenci İsmi : ${preferences.getString(KEY_NAME, "")}\n" +
                "Telefon : ${preferences.getString(KEY_PHONE, "")}\n" + "Gelme durumu :  $sit\n", Toast.LENGTH_SHORT).show()
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
}