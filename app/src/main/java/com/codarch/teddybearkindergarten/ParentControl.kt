package com.codarch.teddybearkindergarten

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class ParentControl : AppCompatActivity() {
    @SuppressLint("WrongViewCast", "CommitPrefEdits")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent_control)

        val studentName: TextView = findViewById<TextView>(R.id.studentName)
        val dateText: TextView = findViewById<TextView>(R.id.date)
        val situationText: TextView = findViewById<TextView>(R.id.situation)
        val preferences = getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val checkButton = findViewById<ImageView>(R.id.checkButton)
        val xButton = findViewById<ImageView>(R.id.xButton)

        dateText.text = current.format(formatter)

        if (preferences.getInt(KEY_CHECK, 1) == 1) {
            situationText.text = getString(R.string.parentCheckFeedback)

        } else {
            situationText.text = getString(R.string.parentXFeedback)

        }

        studentName.text = preferences.getString(KEY_NAME, "")

        try {
            val databaseCheck = this.openOrCreateDatabase("StudentsCheck", MODE_PRIVATE, null)
            databaseCheck.execSQL("CREATE TABLE IF NOT EXISTS studentsCheck (id INTEGER PRIMARY KEY, studentName VARCHAR, parentCheck INT, schoolCheck INT)")

            checkButton.setOnClickListener {

                editor.putInt(KEY_CHECK, 1)
                editor.apply()
                databaseCheck.execSQL(
                    "UPDATE studentsCheck SET parentCheck = 1 WHERE studentName = '${
                        preferences.getString(
                            KEY_NAME,
                            ""
                        )
                    }'"
                )
                Toast.makeText(
                    applicationContext,
                    getString(R.string.parentCheckFeedback),
                    Toast.LENGTH_SHORT
                )
                    .show()
                situationText.text = getString(R.string.parentCheckFeedback)

                findViewById<TextView>(R.id.textView8).setTextColor(Color.parseColor("#489644"))
                findViewById<TextView>(R.id.textView9).setTextColor(Color.parseColor("#675F59"))

            }
            xButton.setOnClickListener {

                editor.putInt(KEY_CHECK, 0)
                editor.apply()
                databaseCheck.execSQL(
                    "UPDATE studentsCheck SET parentCheck = 0 WHERE studentName = '${
                        preferences.getString(
                            KEY_NAME,
                            ""
                        )
                    }'"
                )
                Toast.makeText(
                    applicationContext,
                    getString(R.string.parentXFeedback),
                    Toast.LENGTH_SHORT
                )
                    .show()
                situationText.text = getString(R.string.parentXFeedback)

                findViewById<TextView>(R.id.textView8).setTextColor(Color.parseColor("#675F59"))
                findViewById<TextView>(R.id.textView9).setTextColor(Color.parseColor("#e93b2d"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}