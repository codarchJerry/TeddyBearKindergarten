package com.codarch.teddybearkindergarten

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codarch.teddybearkindergarten.data.ActivityDatabaseHandler
import com.codarch.teddybearkindergarten.data.ActivityModel
import com.codarch.teddybearkindergarten.data.AdapterActivity


class Activities : AppCompatActivity(), AdapterActivity.AdapterCallback {

    @SuppressLint("WrongThread")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activities)

        val activityDatabaseHandler: ActivityDatabaseHandler = ActivityDatabaseHandler(this)

        //val image: ImageView = findViewById(R.id.imageLogo)

//        var bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.uludag)
//        var byteArray = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.PNG, 10, byteArray)
//        var byteImage = byteArray.toByteArray()
//
//        activityDatabaseHandler.addEmployee(ActivityModel(0, "Uludağ", "2022-01-28", byteImage))
//
//        bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.mardin)
//        byteArray = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.PNG, 10, byteArray)
//        byteImage = byteArray.toByteArray()
//
//        activityDatabaseHandler.addEmployee(ActivityModel(0, "Mardin", "2022-02-8", byteImage))
//
//        bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.efes)
//        byteArray = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.PNG, 10, byteArray)
//        byteImage = byteArray.toByteArray()
//
//        activityDatabaseHandler.addEmployee(ActivityModel(0, "Efes", "2022-03-11", byteImage))
//
//        bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.kapadokya)
//        byteArray = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.PNG, 10, byteArray)
//        byteImage = byteArray.toByteArray()
//
//        activityDatabaseHandler.addEmployee(ActivityModel(0, "Kapadokya", "2022-04-5", byteImage))
//
//        bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.beypazari)
//        byteArray = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.PNG, 10, byteArray)
//        byteImage = byteArray.toByteArray()
//
//        activityDatabaseHandler.addEmployee(ActivityModel(0, "Beypazarı", "2022-05-1", byteImage))
//
//        bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.uzungol)
//        byteArray = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.PNG, 10, byteArray)
//        byteImage = byteArray.toByteArray()
//
//        activityDatabaseHandler.addEmployee(ActivityModel(0, "Uzungöl", "2022-06-15", byteImage))
//
//        bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.oludeniz)
//        byteArray = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.PNG, 10, byteArray)
//        byteImage = byteArray.toByteArray()
//
//        activityDatabaseHandler.addEmployee(ActivityModel(0, "Ölüdeniz", "2022-07-12", byteImage))

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        var adapter = AdapterActivity(activityDatabaseHandler.viewEmployee(), this)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        adapter.update(activityDatabaseHandler.viewEmployee())

        //val bitmapp: ByteArray = byteImage
        //val imagee = BitmapFactory.decodeByteArray(bitmapp, 0, bitmapp.size)
        //image.setImageBitmap(imagee)


        /*val bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.teddybear)
        val byteArray = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray)
        val img = byteArray.toByteArray()

        imageView.setImageBitmap(bitmap)

        val bytes: ByteArray = cursor.getBlob(imageIx)
        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        binding.imageView.setImageBitmap(bitmap)

        whoamiwith.setImageResource(R.drawable.loginbtn);



        imageDB = DB.getImage(name)
        nameDB = DB.getName(name)
        imageView.setImageBitmap(imageDB)*/

        // setupListofDataIntoRecyclerView()

    }


    fun setupListofDataIntoRecyclerView() {

        val activityDatabaseHandler: ActivityDatabaseHandler = ActivityDatabaseHandler(this)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        var adapter = AdapterActivity(activityDatabaseHandler.viewEmployee(), this)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        adapter.update(activityDatabaseHandler.viewEmployee())
    }

    /*@SuppressLint("Recycle")
    fun getModels(): MutableList<StudentCheckModel> {

        val models = mutableListOf(StudentCheckModel("", null, null, null, null))

        try {


            val databaseCheck = this.openOrCreateDatabase("StudentsCheck", MODE_PRIVATE, null)


            databaseCheck.execSQL("CREATE TABLE IF NOT EXISTS studentsCheck (id INTEGER PRIMARY KEY, day DATE, studentName VARCHAR, parentCheck INT, schoolCheck INT, studentId INT)")

            val cursor = databaseCheck.rawQuery(
                "SELECT * FROM studentsCheck WHERE day = '${checkDay}' ORDER BY studentId ASC",
                null
            )
            val dayIx = cursor.getColumnIndex("day")
            val studentNameIx = cursor.getColumnIndex("studentName")
            val parentCheckIx = cursor.getColumnIndex("parentCheck")
            val schoolCheckIx = cursor.getColumnIndex("schoolCheck")
            val studentIdIx = cursor.getColumnIndex("studentId")


            while (cursor.moveToNext()) {
                if (models[0].studentName == "") {
                    models.removeAt(0)
                    models.add(
                        0,
                        StudentCheckModel(
                            cursor.getString(studentNameIx),
                            cursor.getString(dayIx),
                            cursor.getInt(parentCheckIx),
                            cursor.getInt(schoolCheckIx),
                            cursor.getInt(studentIdIx)
                        )
                    )
                } else {
                    models.add(
                        StudentCheckModel(
                            cursor.getString(studentNameIx),
                            cursor.getString(dayIx),
                            cursor.getInt(parentCheckIx),
                            cursor.getInt(schoolCheckIx),
                            cursor.getInt(studentIdIx)
                        )
                    )
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return models
    }
*/

    /* override fun onClickX(activity: AdapterActivity) {

         /*val checkDatabaseHandler: CheckDatabaseHandler = CheckDatabaseHandler(this)

         //val databaseCheck = this.openOrCreateDatabase("StudentsCheck", MODE_PRIVATE, null)
         //databaseCheck.execSQL("CREATE TABLE IF NOT EXISTS studentsCheck (id INTEGER PRIMARY KEY, day DATE, studentName VARCHAR, parentCheck INT, schoolCheck INT, studentId INT)")
         /*val cursor = databaseCheck.rawQuery("SELECT * FROM studentsCheck", null)


         //variables for cursor
         val idIx = cursor.getColumnIndex("id")
         val dayIx = cursor.getColumnIndex("day")
         val studentNameIx = cursor.getColumnIndex("studentName")
         val parentCheckIx = cursor.getColumnIndex("parentCheck")
         val schoolCheckIx = cursor.getColumnIndex("schoolCheck")*/

         var id: Int? = student.studentId

         var student: StudentCheckModel = checkDatabaseHandler.getByDate(id)

         println("/////////////////CHECK //////////////////// " + student.studentName + " - " + student.studentId + " - " + student.date + " - " + student.parentCheck)

         student.schoolCheck = 0

         println("/////////////////CHECK 2//////////////////// " + student.studentName + " - " + student.studentId + " - " + student.date + " - " + student.parentCheck)

         var status = checkDatabaseHandler.updateEmployee(student)

         if (status > -1) {
             Toast.makeText(applicationContext, "Record Updated.", Toast.LENGTH_LONG).show()
         }*/

         //databaseCheck.execSQL("UPDATE studentsCheck SET schoolCheck = 0 WHERE studentId = ${position + 1} AND day = '$checkDay'")

     }*/

    /* @SuppressLint("Recycle")
     override fun onClickCheck(student: StudentCheckModel) {

         /* val checkDatabaseHandler: CheckDatabaseHandler = CheckDatabaseHandler(this)

          var id: Int? = student.studentId

          var student: StudentCheckModel = checkDatabaseHandler.getByDate(id, checkDay)

          println("/////////////////CHECK //////////////////// " + student.studentName + " - " + student.studentId + " - " + student.date + " - " + student.parentCheck)

          student.schoolCheck = 1

          println("/////////////////CHECK 2//////////////////// " + student.studentName + " - " + student.studentId + " - " + student.date + " - " + student.parentCheck)

          var status = checkDatabaseHandler.updateEmployee(student)

          if (status > -1) {
              Toast.makeText(applicationContext, "Record Updated.", Toast.LENGTH_LONG).show()
          }*/

         //val databaseCheck = this.openOrCreateDatabase("StudentsCheck", MODE_PRIVATE, null)
         //databaseCheck.execSQL("CREATE TABLE IF NOT EXISTS studentsCheck (id INTEGER PRIMARY KEY, day DATE, studentName VARCHAR, parentCheck INT, schoolCheck INT, studentId INT)")

         /*val cursor = databaseCheck.rawQuery("SELECT * FROM studentsCheck", null)

         //variables for cursor
         val idIx = cursor.getColumnIndex("id")
         val dayIx = cursor.getColumnIndex("day")
         val studentNameIx = cursor.getColumnIndex("studentName")
         val parentCheckIx = cursor.getColumnIndex("parentCheck")
         val schoolCheckIx = cursor.getColumnIndex("schoolCheck")*/

         //databaseCheck.execSQL("UPDATE studentsCheck SET schoolCheck = 1 WHERE studentId = ${position + 1} AND day = '$checkDay'")

     }*/


    override fun onClickCheck(activity: ActivityModel) {
        TODO("Not yet implemented")
    }

    override fun onClickX(activity: ActivityModel) {
        TODO("Not yet implemented")
    }

}
