package com.codarch.teddybearkindergarten

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.codarch.teddybearkindergarten.data.StudentDatabaseHandler
import com.codarch.teddybearkindergarten.data.StudentModel
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE
import java.util.regex.Matcher
import java.util.regex.Pattern


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val button = findViewById<Button>(R.id.loginButton)

        button.setOnClickListener {
            addRecord()
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
            isValidPassword(userPassword) -> {
                Toast.makeText(
                    applicationContext,
                    "Password must contain at least :\n1 Lowercase \n1 Uppercase \n1 Number\nMinimum 8 Character\n No special characters",
                    Toast.LENGTH_SHORT,
                ).show()
                return FALSE
            }
            else -> return TRUE
        }
    }

    private fun isValidPassword(password: String?): Boolean {

        val pattern: Pattern
        val matcher: Matcher
        val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?!.*[@#\$%^&+=])(?=\\S+$).{4,}$"
        pattern = Pattern.compile(PASSWORD_PATTERN)
        matcher = pattern.matcher(password)
        return matcher.matches().not()

    }

    private fun addRecord() {

        val preferences = getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
        val editor = preferences.edit()

        val studentName = findViewById<EditText>(R.id.studentName).text.toString()
        val parentName = findViewById<EditText>(R.id.parentName).text.toString()
        val phone = findViewById<EditText>(R.id.parentPhoneNumber).text.toString()
        val address = findViewById<EditText>(R.id.homeAddress).text.toString()
        val password = findViewById<EditText>(R.id.password).text.toString()

        val databaseHandler: StudentDatabaseHandler = StudentDatabaseHandler(this)

        var student: StudentModel = StudentModel(0, studentName, parentName, phone, address, password)

        if (checkInfos() == true) {
            if (databaseHandler.isExists(student)) {
                Toast.makeText(applicationContext, "Bu öğrenci zaten mevcut", Toast.LENGTH_SHORT).show()
            } else {
                val status = databaseHandler.addEmployee(student)
                if (status > -1) {
                    editor.putInt(KEY_ID, databaseHandler.getByName(studentName, parentName).id.toString().toInt())
                    editor.putString(KEY_NAME, studentName)
                    editor.putString(KEY_PARENT_NAME, parentName)
                    editor.apply()
                    Toast.makeText(applicationContext, "Kayıt Başarılı", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}