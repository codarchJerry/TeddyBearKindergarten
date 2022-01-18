package com.codarch.teddybearkindergarten.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

//creating the database logic, extending the SQLiteOpenHelper base class
class CheckDatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "CheckDatabase"

        private const val TABLE_CONTACTS = "checkTable"

        private const val KEY_ID = "_id"
        private const val KEY_DATE = "date"
        private const val KEY_STUDENT_NAME = "studentName"
        private const val KEY_PARENT_NAME = "parentName"
        private const val KEY_PARENT_CHECK = "parentCheck"
        private const val KEY_SCHOOL_CHECK = "schoolCheck"
        private const val KEY_STUDENT_ID = "studentId"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        //creating table with fields
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_DATE + " VARCHAR,"
                + KEY_STUDENT_NAME + " VARCHAR,"
                + KEY_PARENT_NAME + " VARCHAR,"
                + KEY_PARENT_CHECK + " INT,"
                + KEY_SCHOOL_CHECK + " INT,"
                + KEY_STUDENT_ID + " INT" + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACTS")
        onCreate(db)
    }

    fun addEmployee(emp: StudentCheckModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_DATE, emp.date)
        contentValues.put(KEY_STUDENT_NAME, emp.studentName)
        contentValues.put(KEY_PARENT_NAME, emp.parentName)
        contentValues.put(KEY_PARENT_CHECK, emp.parentCheck)
        contentValues.put(KEY_SCHOOL_CHECK, emp.schoolCheck)
        contentValues.put(KEY_STUDENT_ID, emp.studentId)

        // Inserting employee details using insert query.
        val success = db.insert(TABLE_CONTACTS, null, contentValues)
        //2nd argument is String containing nullColumnHack

        db.close() // Closing database connection
        return success
    }


    @SuppressLint("Range")
    fun viewEmployee(day: String): MutableList<StudentCheckModel> {

        val empList: MutableList<StudentCheckModel> = ArrayList()

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
        var date: String
        var studentName: String
        var parentName: String
        var parentCheck: Int
        var schoolCheck: Int
        var studentId: Int

        if (cursor.moveToFirst()) {
            do {
                if (day == cursor.getString(cursor.getColumnIndex(KEY_DATE))) {
                    id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                    date = cursor.getString(cursor.getColumnIndex(KEY_DATE))
                    studentName = cursor.getString(cursor.getColumnIndex(KEY_STUDENT_NAME))
                    parentName = cursor.getString(cursor.getColumnIndex(KEY_PARENT_NAME))
                    parentCheck = cursor.getInt(cursor.getColumnIndex(KEY_PARENT_CHECK))
                    schoolCheck = cursor.getInt(cursor.getColumnIndex(KEY_SCHOOL_CHECK))
                    studentId = cursor.getInt(cursor.getColumnIndex(KEY_STUDENT_ID))

                    val emp = StudentCheckModel(date, studentName, parentName, parentCheck, schoolCheck, studentId)
                    empList.add(emp)
                }


            } while (cursor.moveToNext())
        }
        return empList
    }

    fun updateEmployee(emp: StudentCheckModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(KEY_DATE, emp.date)
        contentValues.put(KEY_STUDENT_NAME, emp.studentName)
        contentValues.put(KEY_PARENT_NAME, emp.parentName)
        contentValues.put(KEY_PARENT_CHECK, emp.parentCheck)
        contentValues.put(KEY_SCHOOL_CHECK, emp.schoolCheck)
        contentValues.put(KEY_STUDENT_ID, emp.studentId)

        println(contentValues)

        //val success = db.update(TABLE_CONTACTS, contentValues, " ${KEY_DATE} = ${emp.date} AND ${KEY_STUDENT_ID} = ${emp.studentId}", null)
        val success = db.update(TABLE_CONTACTS, contentValues, KEY_DATE + " = ? AND " + KEY_STUDENT_ID + " = ?", arrayOf(emp.date, emp.studentId.toString()))
        //2nd argument is String containing nullColumnHack

        // Closing database connection
        db.close()
        return success
    }

    fun deleteEmployee(emp: StudentCheckModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_STUDENT_ID, emp.studentId) // EmpModelClass id
        // Deleting Row
        val success = db.delete(TABLE_CONTACTS, KEY_STUDENT_ID + "=" + emp.studentId, null)
        //2nd argument is String containing nullColumnHack

        // Closing database connection
        db.close()
        return success
    }

    @SuppressLint("Range")
    fun isExists(emp: StudentCheckModel): Boolean {

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
                if (emp.studentName == cursor.getString(cursor.getColumnIndex(KEY_STUDENT_NAME)) &&
                    emp.parentName == cursor.getString(cursor.getColumnIndex(KEY_PARENT_NAME)) &&
                    emp.date == cursor.getString(cursor.getColumnIndex(KEY_DATE))
                ) {
                    return true
                }
            } while (cursor.moveToNext())
        }
        return false
    }

    @SuppressLint("Range")
    fun getByDate(id: Int?, day: String): StudentCheckModel {

        val selectQuery = "SELECT  * FROM $TABLE_CONTACTS"

        val db = this.readableDatabase

        var date: String
        var studentName: String
        var parentName: String
        var parentCheck: Int
        var schoolCheck: Int
        var studentId: Int

        // Cursor is used to read the record one by one. Add them to data model class.
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return StudentCheckModel(null, null, null, null, null, null)
        }


        if (cursor.moveToFirst()) {
            do {
                if (id == cursor.getInt(cursor.getColumnIndex(KEY_STUDENT_ID)) && day == cursor.getString(cursor.getColumnIndex(KEY_DATE))) {


                    date = cursor.getString(cursor.getColumnIndex(KEY_DATE))
                    studentName = cursor.getString(cursor.getColumnIndex(KEY_STUDENT_NAME))
                    parentName = cursor.getString(cursor.getColumnIndex(KEY_PARENT_NAME))
                    parentCheck = cursor.getInt(cursor.getColumnIndex(KEY_PARENT_CHECK))
                    schoolCheck = cursor.getInt(cursor.getColumnIndex(KEY_SCHOOL_CHECK))
                    studentId = cursor.getInt(cursor.getColumnIndex(KEY_STUDENT_ID))


                    val emp = StudentCheckModel(date, studentName, parentName, parentCheck, schoolCheck, studentId)

                    return emp
                }
            } while (cursor.moveToNext())
        }
        return StudentCheckModel(null, null, null, null, null, null)
    }

}

