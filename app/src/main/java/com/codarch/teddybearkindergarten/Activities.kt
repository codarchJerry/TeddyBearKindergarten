package com.codarch.teddybearkindergarten

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codarch.teddybearkindergarten.data.*
import java.io.ByteArrayOutputStream


class Activities : AppCompatActivity(), AdapterActivity.AdapterCallback {

    @SuppressLint("WrongThread")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activities)

        /* val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
         var adapter = AdapterActivity(activityDatabaseHandler.viewEmployee(), this)

         recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
         recyclerView.adapter = adapter
         adapter.update(activityDatabaseHandler.viewEmployee())*/
        //temp()
        setupListofDataIntoRecyclerView()

    }

    fun temp() {
        val activityDatabaseHandler: ActivityDatabaseHandler = ActivityDatabaseHandler(this)

        var bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.uludag)
        var byteArray = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 10, byteArray)
        var byteImage = byteArray.toByteArray()

        activityDatabaseHandler.addEmployee(ActivityModel(0, "Uludağ", "2022-01-28", byteImage))

        bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.mardin)
        byteArray = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 10, byteArray)
        byteImage = byteArray.toByteArray()

        activityDatabaseHandler.addEmployee(ActivityModel(0, "Mardin", "2022-02-8", byteImage))

        bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.efes)
        byteArray = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 10, byteArray)
        byteImage = byteArray.toByteArray()

        activityDatabaseHandler.addEmployee(ActivityModel(0, "Efes", "2022-03-11", byteImage))

        bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.kapadokya)
        byteArray = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 10, byteArray)
        byteImage = byteArray.toByteArray()

        activityDatabaseHandler.addEmployee(ActivityModel(0, "Kapadokya", "2022-04-5", byteImage))

        bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.beypazari)
        byteArray = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 10, byteArray)
        byteImage = byteArray.toByteArray()

        activityDatabaseHandler.addEmployee(ActivityModel(0, "Beypazarı", "2022-05-1", byteImage))

        bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.uzungol)
        byteArray = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 10, byteArray)
        byteImage = byteArray.toByteArray()

        activityDatabaseHandler.addEmployee(ActivityModel(0, "Uzungöl", "2022-06-15", byteImage))

        bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.oludeniz)
        byteArray = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 10, byteArray)
        byteImage = byteArray.toByteArray()

        activityDatabaseHandler.addEmployee(ActivityModel(0, "Ölüdeniz", "2022-07-12", byteImage))
    }


    fun setupListofDataIntoRecyclerView() {

        val activityDatabaseHandler: ActivityDatabaseHandler = ActivityDatabaseHandler(this)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        var adapter = AdapterActivity(activityDatabaseHandler.viewEmployee(), this)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        adapter.update(activityDatabaseHandler.viewEmployee())
    }


    override fun onClickCheck(activity: ActivityModel) {
        var activityCheckDatabaseHandler = ActivityCheckDatabaseHandler(this)
        var preferences = getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
        var studentId = preferences.getInt(KEY_ID, 0)

        var activityCheckModel: ActivityCheckModel = activityCheckDatabaseHandler.getById(activity.id, studentId)

        activityCheckModel.parentControl = 1
        var status = activityCheckDatabaseHandler.updateEmployee(activityCheckModel)

        if (status > 0) {
            Toast.makeText(applicationContext, "Record Updated.", Toast.LENGTH_LONG).show()
        }

    }

    override fun onClickX(activity: ActivityModel) {
        var activityCheckDatabaseHandler = ActivityCheckDatabaseHandler(this)
        var preferences = getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
        var studentId = preferences.getInt(KEY_ID, 0)

        var activityCheckModel: ActivityCheckModel = activityCheckDatabaseHandler.getById(activity.id, studentId)

        activityCheckModel.parentControl = 0
        var status = activityCheckDatabaseHandler.updateEmployee(activityCheckModel)

        if (status > 0) {
            Toast.makeText(applicationContext, "Record Updated.", Toast.LENGTH_LONG).show()
        }
    }

}
