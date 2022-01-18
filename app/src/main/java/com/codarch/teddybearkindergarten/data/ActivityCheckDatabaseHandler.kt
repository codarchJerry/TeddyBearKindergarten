package com.codarch.teddybearkindergarten.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

//creating the database logic, extending the SQLiteOpenHelper base class
class ActivityCheckDatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "ActivityCheckDatabase"

        private const val TABLE_CONTACTS = "activityCheckTable"

        private const val KEY_ID = "_id"
        private const val KEY_ACTIVITY_ID = "activityId"
        private const val KEY_PARENT_CONTROL = "parentControl"
        private const val KEY_STUDENT_ID = "studentId"


    }

    override fun onCreate(db: SQLiteDatabase?) {
        //creating table with fields
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_ACTIVITY_ID + " INT,"
                + KEY_PARENT_CONTROL + " INT,"
                + KEY_STUDENT_ID + " INT" + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACTS")
        onCreate(db)
    }

    fun addEmployee(emp: ActivityCheckModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_ACTIVITY_ID, emp.activityId)
        contentValues.put(KEY_PARENT_CONTROL, emp.parentControl)
        contentValues.put(KEY_STUDENT_ID, emp.studentId)

        // Inserting employee details using insert query.
        val success = db.insert(TABLE_CONTACTS, null, contentValues)
        //2nd argument is String containing nullColumnHack

        db.close() // Closing database connection
        return success
    }


    @SuppressLint("Range")
    fun viewEmployee(): MutableList<ActivityCheckModel> {

        val empList: MutableList<ActivityCheckModel> = ArrayList()

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
        var activityId: Int
        var parentControl: Int
        var studentId: Int


        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                activityId = cursor.getInt(cursor.getColumnIndex(KEY_ACTIVITY_ID))
                parentControl = cursor.getInt(cursor.getColumnIndex(KEY_PARENT_CONTROL))
                studentId = cursor.getInt(cursor.getColumnIndex(KEY_STUDENT_ID))

                val emp = ActivityCheckModel(id, activityId, parentControl, studentId)
                empList.add(emp)

            } while (cursor.moveToNext())
        }
        return empList
    }

    fun updateEmployee(emp: ActivityCheckModel): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_ACTIVITY_ID, emp.activityId)
        contentValues.put(KEY_PARENT_CONTROL, emp.parentControl)
        contentValues.put(KEY_STUDENT_ID, emp.studentId)

        println(contentValues)

        //val success = db.update(TABLE_CONTACTS, contentValues, " ${KEY_DATE} = ${emp.date} AND ${KEY_STUDENT_ID} = ${emp.studentId}", null)
        val success = db.update(
            TABLE_CONTACTS,
            contentValues,
            KEY_STUDENT_ID + " = ? AND " + KEY_ACTIVITY_ID + " = ?",
            arrayOf(emp.studentId.toString(), emp.activityId.toString())
        )
        //2nd argument is String containing nullColumnHack

        // Closing database connection
        db.close()
        return success
    }

    /*fun deleteEmployee(emp: StudentCheckModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        //contentValues.put(KEY_STUDENT_ID, emp.studentId) // EmpModelClass id
        // Deleting Row
        val success = db.delete(TABLE_CONTACTS, KEY_STUDENT_ID + "=" + emp.studentId, null)
        //2nd argument is String containing nullColumnHack

        // Closing database connection
        db.close()
        return success
    }*/

    @SuppressLint("Range")
    fun isExists(emp: ActivityCheckModel): Boolean {

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
                if (emp.activityId == cursor.getInt(cursor.getColumnIndex(KEY_ACTIVITY_ID)) &&
                    emp.studentId == cursor.getInt(cursor.getColumnIndex(KEY_STUDENT_ID))
                ) {
                    return true
                }
            } while (cursor.moveToNext())
        }
        return false
    }

    @SuppressLint("Range")
    fun getById(activityIdCheck: Int?, studentIdCheck: Int?): ActivityCheckModel {

        val selectQuery = "SELECT  * FROM $TABLE_CONTACTS"

        val db = this.readableDatabase

        var id: Int
        var activityId: Int
        var parentControl: Int
        var studentId: Int

        // Cursor is used to read the record one by one. Add them to data model class.
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ActivityCheckModel(null, null, null, null)
        }

        if (cursor.moveToFirst()) {
            do {
                if (activityIdCheck == cursor.getInt(cursor.getColumnIndex(KEY_ACTIVITY_ID)) && studentIdCheck == cursor.getInt(cursor.getColumnIndex(KEY_STUDENT_ID))) {

                    id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                    activityId = cursor.getInt(cursor.getColumnIndex(KEY_ACTIVITY_ID))
                    parentControl = cursor.getInt(cursor.getColumnIndex(KEY_PARENT_CONTROL))
                    studentId = cursor.getInt(cursor.getColumnIndex(KEY_STUDENT_ID))

                    val emp = ActivityCheckModel(id, activityId, parentControl, studentId)
                    return emp
                }

            } while (cursor.moveToNext())
        }

        return ActivityCheckModel(null, null, null, null)
    }

}

