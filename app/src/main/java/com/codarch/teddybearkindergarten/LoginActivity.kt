package com.codarch.teddybearkindergarten

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE


class LoginActivity : AppCompatActivity() {
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val button = findViewById<Button>(R.id.loginButton)

        button.setOnClickListener {
            if (checkInfos() == true)
                register()
        }


    }

    private fun checkInfos(): Boolean? {
        val studentName = findViewById<EditText>(R.id.studentName).text.toString()
        val parentName = findViewById<EditText>(R.id.parentName).text.toString()
        val parentPhoneNumber = findViewById<EditText>(R.id.parentPhoneNumber).text.toString()
        val homeAddress = findViewById<EditText>(R.id.homeAddress).text.toString()
        val userPassword = findViewById<EditText>(R.id.password).text.toString()

        when {
            studentName.isBlank() -> {
                Toast.makeText(
                    applicationContext,
                    "Please enter " + "Student Name",
                    Toast.LENGTH_SHORT
                )
                    .show()
                return FALSE
            }
            parentName.isBlank() -> {
                Toast.makeText(
                    applicationContext,
                    "Please enter " + "Parent Name",
                    Toast.LENGTH_SHORT
                )
                    .show()
                return FALSE
            }
            parentPhoneNumber.isBlank() -> {
                Toast.makeText(
                    applicationContext,
                    "Please enter " + "Phone Number",
                    Toast.LENGTH_SHORT
                )
                    .show()
                return FALSE
            }
            homeAddress.isBlank() -> {
                Toast.makeText(applicationContext, "Please enter " + "Address", Toast.LENGTH_SHORT)
                    .show()
                return FALSE
            }
            userPassword.isBlank() -> {
                Toast.makeText(applicationContext, "Please enter " + "Password", Toast.LENGTH_SHORT)
                    .show()
                return FALSE
            }
            else -> return TRUE
        }
    }

    @SuppressLint("Recycle")
    private fun register() {

        try {
            val database = this.openOrCreateDatabase("Students", MODE_PRIVATE, null)
            val databaseCheck = this.openOrCreateDatabase("StudentsCheck", MODE_PRIVATE, null)

            val preferences = getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
            val editor = preferences.edit()

            database.execSQL("CREATE TABLE IF NOT EXISTS students (id INTEGER PRIMARY KEY, studentName VARCHAR, parentName VARCHAR, parentPhoneNumber VARCHAR, homeAddress VARCHAR, password VARCHAR)")
            databaseCheck.execSQL("CREATE TABLE IF NOT EXISTS studentsCheck (id INTEGER PRIMARY KEY, studentName VARCHAR,  parentName VARCHAR, parentCheck INT, schoolCheck INT)")

            //variables for database
            val studentName = findViewById<EditText>(R.id.studentName).text.toString()
            val parentName = findViewById<EditText>(R.id.parentName).text.toString()
            val parentPhoneNumber = findViewById<EditText>(R.id.parentPhoneNumber).text.toString()
            val homeAddress = findViewById<EditText>(R.id.homeAddress).text.toString()
            val userPassword = findViewById<EditText>(R.id.password).text.toString()
            val cursor = database.rawQuery("SELECT * FROM students", null)

            //variables for cursor
            val idIx = cursor.getColumnIndex("id")
            val studentNameIx = cursor.getColumnIndex("studentName")
            val parentNameIx = cursor.getColumnIndex("parentName")
            val phoneNumberIx = cursor.getColumnIndex("parentPhoneNumber")
            val homeAddressIx = cursor.getColumnIndex("homeAddress")
            val passwordIx = cursor.getColumnIndex("password")

            if (preferences.getString(KEY_NAME, "").equals("")) {
                database.execSQL("INSERT INTO students (studentName, parentName, parentPhoneNumber, homeAddress, password) VALUES ('${studentName}','${parentName}','${parentPhoneNumber}','${homeAddress}','${userPassword}')")
                databaseCheck.execSQL("INSERT INTO studentsCheck (studentName, parentName, parentCheck, schoolCheck) VALUES ('${studentName}','${parentName}',1,1)")
                editor.putString(KEY_NAME, studentName)
                editor.putString(KEY_PHONE, parentPhoneNumber)
                editor.putInt(KEY_CHECK, 1)
                editor.apply()
                Toast.makeText(applicationContext, "Kayıt Başarılı...", Toast.LENGTH_SHORT).show()

            }
//leave
            else {
                while (cursor.moveToNext()) {
                    if (cursor.getString(studentNameIx).equals(studentName) && cursor.getString(
                            phoneNumberIx
                        ).equals(parentPhoneNumber) && cursor.getString(passwordIx)
                            .equals(userPassword)
                    ) {

                        editor.putString(KEY_NAME, studentName)
                        editor.putString(KEY_PHONE, parentPhoneNumber)
                        editor.putInt(KEY_CHECK, 1)
                        editor.apply()
                        Toast.makeText(applicationContext, "Giriş Başarılı...", Toast.LENGTH_SHORT)
                            .show()
                        println("Name: " + cursor.getString(studentNameIx))
                        println("Parent Name: " + cursor.getString(parentNameIx))
                        println("Phone: " + cursor.getString(phoneNumberIx))
                        break

                    } else if (cursor.isLast) {

                        database.execSQL("INSERT INTO students (studentName, parentName, parentPhoneNumber, homeAddress, password) VALUES ('${studentName}','${parentName}','${parentPhoneNumber}','${homeAddress}','${userPassword}')")
                        databaseCheck.execSQL("INSERT INTO studentsCheck (studentName, parentName, parentCheck, schoolCheck) VALUES ('${studentName}','${parentName}',1,1)")
                        editor.putString(KEY_NAME, studentName)
                        editor.putString(KEY_PHONE, parentPhoneNumber)
                        editor.putInt(KEY_CHECK, 1)
                        editor.apply()
                        Toast.makeText(applicationContext, "Kayıt Başarılı...", Toast.LENGTH_SHORT)
                            .show()
                        println("Name: " + cursor.getString(studentNameIx))
                        println("Parent Name: " + cursor.getString(parentNameIx))
                        println("Phone: " + cursor.getString(phoneNumberIx))
                        break
                    }
                }


            }
//populate table


            /*database.execSQL("UPDATE musicians SET age = 61 WHERE name = 'Lars'");
            database.execSQL("UPDATE musicians SET name = 'Kirk Hammett' WHERE id = 3");
            database.execSQL("DELETE FROM musicians WHERE id = 2");
            Cursor cursor = database . rawQuery ("SELECT * FROM musicians WHERE name = 'James'", null)*/

            while (cursor.moveToNext()) {
                println("Id: " + cursor.getInt(idIx))
                println("Name: " + cursor.getString(studentNameIx))
                println("Parent Name: " + cursor.getString(parentNameIx))
                println("Phone: " + cursor.getString(phoneNumberIx))
                println("Address: " + cursor.getString(homeAddressIx))
                println("Password: " + cursor.getString(passwordIx))
            }
            cursor.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}