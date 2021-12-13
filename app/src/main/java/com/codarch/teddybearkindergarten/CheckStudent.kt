package com.codarch.teddybearkindergarten

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codarch.teddybearkindergarten.data.model.AdapterCheck
import com.codarch.teddybearkindergarten.data.model.StudentCheckModel

class CheckStudent : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check)

        val recyclerViewCheck = findViewById<RecyclerView>(R.id.recyclerViewCheck)
        recyclerViewCheck.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val adapter =
            AdapterCheck(getModels())

        recyclerViewCheck.adapter = adapter
    }

    @SuppressLint("Recycle")
    fun getModels(): MutableList<StudentCheckModel> {

        val models = mutableListOf(StudentCheckModel("", "",1, 1))

        try {

            val databaseCheck = this.openOrCreateDatabase("StudentsCheck", MODE_PRIVATE, null)

            databaseCheck.execSQL("CREATE TABLE IF NOT EXISTS studentsCheck (id INTEGER PRIMARY KEY, studentName VARCHAR,parentName VARCHAR, parentCheck BOOLEAN, schoolCheck BOOLEAN)")
            //models.add(StudentCheckModel("", 1,1 ))

            val cursor = databaseCheck.rawQuery("SELECT * FROM studentsCheck", null)
            val studentNameIx = cursor.getColumnIndex("studentName")
            val parentNameIx = cursor.getColumnIndex("parentName")
            val parentCheckIx = cursor.getColumnIndex("parentCheck")
            val schoolCheckIx = cursor.getColumnIndex("schoolCheck")

            while (cursor.moveToNext()) {
                if (models[0].studentName == "") {
                    models.removeAt(0)
                    models.add(
                        0,
                        StudentCheckModel(
                            cursor.getString(studentNameIx),
                            cursor.getString(parentNameIx),
                            cursor.getInt(parentCheckIx),
                            cursor.getInt(schoolCheckIx)
                        )
                    )
                } else {
                    models.add(
                        StudentCheckModel(
                            cursor.getString(studentNameIx),
                            cursor.getString(parentNameIx),
                            cursor.getInt(parentCheckIx),
                            cursor.getInt(schoolCheckIx)
                        )
                    )
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return models
    }
}