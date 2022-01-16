package com.codarch.teddybearkindergarten

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.codarch.teddybearkindergarten.data.StudentDatabaseHandler
import com.codarch.teddybearkindergarten.data.StudentModel
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE
import java.util.regex.Matcher
import java.util.regex.Pattern


class LoginActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val button = findViewById<Button>(R.id.loginButton)

        button.setOnClickListener {
            //register()
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


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("Recycle")
    /*private fun register() {

        try {
            val database = this.openOrCreateDatabase("Students", MODE_PRIVATE, null)
            val databaseCheck = this.openOrCreateDatabase("StudentsCheck", MODE_PRIVATE, null)

            val preferences = getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
            val editor = preferences.edit()

            database.execSQL("CREATE TABLE IF NOT EXISTS students (id INTEGER PRIMARY KEY, studentName VARCHAR, parentName VARCHAR, parentPhoneNumber VARCHAR, homeAddress VARCHAR, password VARCHAR)")
            databaseCheck.execSQL("CREATE TABLE IF NOT EXISTS studentsCheck (id INTEGER PRIMARY KEY, day DATE, studentName VARCHAR, parentCheck INT, schoolCheck INT, studentId INT)")


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
            val passwordIx = cursor.getColumnIndex("password")


            if (preferences.getString(KEY_NAME, "").equals("")) {
                database.execSQL("INSERT INTO students (studentName, parentName, parentPhoneNumber, homeAddress, password) VALUES ('${studentName}','${parentName}','${parentPhoneNumber}','${homeAddress}','${userPassword}')")

                val tempCursor1 = database.rawQuery(
                    "SELECT * FROM students", null
                )
                val tempIdIx = tempCursor1.getColumnIndex("id")

                while (tempCursor1.moveToNext()) {
                    if (tempCursor1.isLast) {
                        databaseCheck.execSQL(
                            "INSERT INTO studentsCheck (day, studentName, parentCheck, schoolCheck, studentId) VALUES ('${
                                LocalDate.now()
                            }','${studentName}', null, null,${tempCursor1.getInt(tempIdIx)})"
                        )
                        break
                    } else
                        continue

                }

                editor.putString(KEY_NAME, studentName)
                editor.putInt(KEY_ID, tempCursor1.getInt(tempIdIx))
                editor.putString(KEY_PHONE, parentPhoneNumber)
                editor.putInt(KEY_CHECK, 1)
                editor.apply()
                tempCursor1.close()
                Toast.makeText(
                    applicationContext,
                    getString(R.string.registerSuccess),
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(
                    applicationContext,
                    ParentControl::class.java
                )
                startActivity(intent)
                finish()

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
                        editor.putInt(KEY_ID, cursor.getInt(idIx))
                        editor.putString(KEY_PHONE, parentPhoneNumber)
                        editor.putInt(KEY_CHECK, 1)
                        editor.apply()
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.loginSuccess),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        val intent = Intent(
                            applicationContext,
                            ParentControl::class.java
                        )
                        startActivity(intent)
                        finish()
                        break

                    } else if (cursor.getString(studentNameIx)
                            .equals(studentName) && cursor.getString(
                            parentNameIx
                        ).equals(parentName)
                    ) {

                        Toast.makeText(
                            applicationContext,
                            getString(R.string.sameName),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        break
                    } else if (cursor.isLast) {

                        database.execSQL("INSERT INTO students (studentName, parentName, parentPhoneNumber, homeAddress, password) VALUES ('${studentName}','${parentName}','${parentPhoneNumber}','${homeAddress}','${userPassword}')")
                        databaseCheck.execSQL(
                            "INSERT INTO studentsCheck (day, studentName, parentCheck, schoolCheck, studentId) VALUES ('${
                                LocalDate.now()
                            }','${studentName}', null, null,${
                                cursor.getInt(idIx) + 1
                            })"
                        )
                        editor.putString(KEY_NAME, studentName)
                        editor.putInt(KEY_ID, cursor.getInt(idIx))
                        editor.putString(KEY_PHONE, parentPhoneNumber)
                        editor.putInt(KEY_CHECK, 1)
                        editor.apply()
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.registerSuccess),
                            Toast.LENGTH_SHORT
                        )
                            .show()

                        val intent = Intent(
                            applicationContext,
                            ParentControl::class.java
                        )
                        startActivity(intent)
                        finish()
                        break
                    }
                }

            }

            cursor.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }*/

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