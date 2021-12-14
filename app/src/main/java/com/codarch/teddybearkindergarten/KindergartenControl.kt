package com.codarch.teddybearkindergarten

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codarch.teddybearkindergarten.data.Adapter
import com.codarch.teddybearkindergarten.data.model.StudentCheckModel


class KindergartenControl : AppCompatActivity(), Adapter.AdapterCallback {
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kindergarten)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val adapter =
            Adapter(getModels(), this)
        recyclerView.adapter = adapter
    }


    @SuppressLint("Recycle")
    fun getModels(): MutableList<StudentCheckModel> {

        val models = mutableListOf(StudentCheckModel("", 1, 1))

        try {

            val databaseCheck = this.openOrCreateDatabase("StudentsCheck", MODE_PRIVATE, null)

            databaseCheck.execSQL("CREATE TABLE IF NOT EXISTS studentsCheck (id INTEGER PRIMARY KEY, parentName VARCHAR,parentCheck BOOLEAN, schoolCheck BOOLEAN)")
            //models.add(StudentCheckModel("", 1,1 ))

            val cursor = databaseCheck.rawQuery("SELECT * FROM studentsCheck", null)
            val studentNameIx = cursor.getColumnIndex("studentName")
            val parentCheckIx = cursor.getColumnIndex("parentCheck")
            val schoolCheckIx = cursor.getColumnIndex("schoolCheck")

            while (cursor.moveToNext()) {
                if (models[0].studentName == "") {
                    models.removeAt(0)
                    models.add(
                        0,
                        StudentCheckModel(
                            cursor.getString(studentNameIx),
                            cursor.getInt(parentCheckIx),
                            cursor.getInt(schoolCheckIx)
                        )
                    )
                } else {
                    models.add(
                        StudentCheckModel(
                            cursor.getString(studentNameIx),
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

    @SuppressLint("Recycle")
    override fun onClickX(position: Int) {
        val databaseCheck = this.openOrCreateDatabase("StudentsCheck", MODE_PRIVATE, null)
        databaseCheck.execSQL("CREATE TABLE IF NOT EXISTS studentsCheck (id INTEGER PRIMARY KEY,parentName VARCHAR,parentCheck BOOLEAN, schoolCheck BOOLEAN)")

        val cursor = databaseCheck.rawQuery("SELECT * FROM studentsCheck", null)

        //variables for cursor
        val idIx = cursor.getColumnIndex("id")
        val studentNameIx = cursor.getColumnIndex("studentName")
        val parentCheckIx = cursor.getColumnIndex("parentCheck")
        val schoolCheckIx = cursor.getColumnIndex("schoolCheck")

        databaseCheck.execSQL("UPDATE studentsCheck SET schoolCheck = 0 WHERE id = ${position + 1}")

    }

    @SuppressLint("Recycle")
    override fun onClickCheck(position: Int) {
        val databaseCheck = this.openOrCreateDatabase("StudentsCheck", MODE_PRIVATE, null)
        databaseCheck.execSQL("CREATE TABLE IF NOT EXISTS studentsCheck (id INTEGER PRIMARY KEY,parentName VARCHAR,parentCheck BOOLEAN, schoolCheck BOOLEAN)")

        val cursor = databaseCheck.rawQuery("SELECT * FROM studentsCheck", null)

        //variables for cursor
        val idIx = cursor.getColumnIndex("id")
        val studentNameIx = cursor.getColumnIndex("studentName")
        val parentCheckIx = cursor.getColumnIndex("parentCheck")
        val schoolCheckIx = cursor.getColumnIndex("schoolCheck")

        databaseCheck.execSQL("UPDATE studentsCheck SET schoolCheck = 1 WHERE id = ${position + 1}")

    }
}
