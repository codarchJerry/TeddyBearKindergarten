package com.codarch.teddybearkindergarten.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory


//creating the database logic, extending the SQLiteOpenHelper base class
class ActivityDatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "ActivityDatabase"

        private const val TABLE_CONTACTS = "activityTable"

        private const val KEY_ID = "_id"
        private const val KEY_NAME = "activityName"
        private const val KEY_DATE = "activityDate"
        private const val KEY_IMAGE = "activityImage"

    }

    override fun onCreate(db: SQLiteDatabase?) {


        //creating table with fields
        val CREATE_CONTACTS_TABLE =
            ("CREATE TABLE " + TABLE_CONTACTS + "(" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NAME + " VARCHAR, " + KEY_DATE + " VARCHAR, " + KEY_IMAGE + " BLOB" + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACTS")
        onCreate(db)
    }

    fun addEmployee(emp: ActivityModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, emp.activityName)
        contentValues.put(KEY_DATE, emp.activityDate)
        contentValues.put(KEY_IMAGE, emp.activityImage)

        // Inserting employee details using insert query.
        val success = db.insert(TABLE_CONTACTS, null, contentValues)
        //2nd argument is String containing nullColumnHack

        db.close() // Closing database connection
        return success
    }

    @SuppressLint("Range")
    fun viewEmployee(): MutableList<ActivityModel> {

        val empList: MutableList<ActivityModel> = ArrayList()

        // Query to select all the records from the table.
        val selectQuery = "SELECT  * FROM $TABLE_CONTACTS"

        val db = this.readableDatabase
        // Cursor is used to read the record one by one. Add them to data model class.
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var activityName: String
        var activityDate: String
        var activityImage: ByteArray


        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                activityName = cursor.getString(cursor.getColumnIndex(KEY_NAME))
                activityDate = cursor.getString(cursor.getColumnIndex(KEY_DATE))
                activityImage = cursor.getBlob(cursor.getColumnIndex(KEY_IMAGE))

                val emp = ActivityModel(id, activityName, activityDate, activityImage)
                empList.add(emp)

            } while (cursor.moveToNext())
        }
        return empList
    }

    fun getImage(id: Int): Bitmap? {
        val MyDB = this.writableDatabase
        val cursor = MyDB.rawQuery("Select * from tableimage where $KEY_ID = $id", null)
        cursor.moveToFirst()
        val bitmap = cursor.getBlob(1)
        return BitmapFactory.decodeByteArray(bitmap, 0, bitmap.size)
    }

    fun updateEmployee(emp: ActivityModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, emp.activityName)
        contentValues.put(KEY_DATE, emp.activityDate)
        contentValues.put(KEY_IMAGE, emp.activityImage)

        val success = db.update(TABLE_CONTACTS, contentValues, KEY_ID + "=" + emp.id, null)
        //2nd argument is String containing nullColumnHack

        // Closing database connection
        db.close()
        return success
    }

    fun deleteEmployee(emp: ActivityModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.id) // EmpModelClass id
        // Deleting Row
        val success = db.delete(TABLE_CONTACTS, KEY_ID + "=" + emp.id, null)
        //2nd argument is String containing nullColumnHack

        // Closing database connection
        db.close()
        return success
    }

    /*@SuppressLint("Range")
    fun isExists(emp: ActivityModel): Boolean {

        // Query to select all the records from the table.
        val selectQuery = "SELECT  * FROM $TABLE_CONTACTS"

        val db = this.readableDatabase
        // Cursor is used to read the record one by one. Add them to data model class.
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return false
        }

        if (cursor.moveToFirst()) {
            do {
                if (emp.studentName == cursor.getString(cursor.getColumnIndex(KEY_STUDENT_NAME)) && emp.parentName == cursor.getString(
                        cursor.getColumnIndex(KEY_PARENT_NAME)
                    )
                ) {
                    return true
                }
            } while (cursor.moveToNext())
        }
        return false
    }*/

    /* @SuppressLint("Range")
     fun getByName(sName: String, pName: String): ActivityModel {

         val selectQuery = "SELECT  * FROM ${StudentDatabaseHandler.TABLE_CONTACTS}"

         val db = this.readableDatabase

         var id: Int
         var activityName: String
         var activityDate: String
         var activityImage: ByteArray

         // Cursor is used to read the record one by one. Add them to data model class.
         var cursor: Cursor? = null

         try {
             cursor = db.rawQuery(selectQuery, null)
         } catch (e: SQLiteException) {
             db.execSQL(selectQuery)
             return ActivityModel(null, null, null, null, null, null)
         }

         if (cursor.moveToFirst()) {
             do {
                 if (sName.equals(cursor.getString(cursor.getColumnIndex(StudentDatabaseHandler.KEY_STUDENT_NAME))) &&
                     pName.equals(cursor.getString(cursor.getColumnIndex(StudentDatabaseHandler.KEY_PARENT_NAME)))
                 ) {

                     id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                     activityName = cursor.getString(cursor.getColumnIndex(KEY_NAME))
                     activityDate = cursor.getString(cursor.getColumnIndex(KEY_DATE))
                     activityImage = cursor.getBlob(cursor.getColumnIndex(KEY_IMAGE))

                     val emp = ActivityModel(id, activityName, activityDate, activityImage)

                     return emp
                 }
             } while (cursor.moveToNext())
         }
         return ActivityModel(null, null, null, null)
     }*/

}

