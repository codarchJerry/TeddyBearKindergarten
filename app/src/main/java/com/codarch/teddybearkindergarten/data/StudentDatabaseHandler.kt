package com.codarch.teddybearkindergarten.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

//creating the database logic, extending the SQLiteOpenHelper base class
class StudentDatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "StudentDatabase"

        private const val TABLE_CONTACTS = "studentTable"

        private const val KEY_ID = "_id"
        private const val KEY_STUDENT_NAME = "studentName"
        private const val KEY_PARENT_NAME = "parentName"
        private const val KEY_PHONE = "phone"
        private const val KEY_ADDRESS = "address"
        private const val KEY_PASSWORD = "password"

    }

    override fun onCreate(db: SQLiteDatabase?) {


        //creating table with fields
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_STUDENT_NAME + " VARCHAR," + KEY_PARENT_NAME + " VARCHAR," + KEY_PHONE + " VARCHAR," + KEY_ADDRESS + " VARCHAR," + KEY_PASSWORD + " VARCHAR" + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACTS")
        onCreate(db)
    }

    fun addEmployee(emp: StudentModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_STUDENT_NAME, emp.studentName)
        contentValues.put(KEY_PARENT_NAME, emp.parentName)
        contentValues.put(KEY_PHONE, emp.phone)
        contentValues.put(KEY_ADDRESS, emp.address)
        contentValues.put(KEY_PASSWORD, emp.password)


        // Inserting employee details using insert query.
        val success = db.insert(TABLE_CONTACTS, null, contentValues)
        //2nd argument is String containing nullColumnHack

        db.close() // Closing database connection
        return success
    }


    @SuppressLint("Range")
    fun viewEmployee(): MutableList<StudentModel> {

        val empList: MutableList<StudentModel> = ArrayList()

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
        var studentName: String
        var parentName: String
        var phone: String
        var address: String
        var password: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                studentName = cursor.getString(cursor.getColumnIndex(KEY_STUDENT_NAME))
                parentName = cursor.getString(cursor.getColumnIndex(KEY_PARENT_NAME))
                phone = cursor.getString(cursor.getColumnIndex(KEY_PHONE))
                address = cursor.getString(cursor.getColumnIndex(KEY_ADDRESS))
                password = cursor.getString(cursor.getColumnIndex(KEY_PASSWORD))

                val emp = StudentModel(id, studentName, parentName, phone, address, password)
                empList.add(emp)

            } while (cursor.moveToNext())
        }
        return empList
    }

    fun updateEmployee(emp: StudentModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_STUDENT_NAME, emp.studentName)
        contentValues.put(KEY_PARENT_NAME, emp.parentName)
        contentValues.put(KEY_PHONE, emp.phone)
        contentValues.put(KEY_ADDRESS, emp.address)
        contentValues.put(KEY_PASSWORD, emp.password)

        val success = db.update(TABLE_CONTACTS, contentValues, KEY_ID + "=" + emp.id, null)
        //2nd argument is String containing nullColumnHack

        // Closing database connection
        db.close()
        return success
    }

    fun deleteEmployee(emp: StudentModel): Int {
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

    @SuppressLint("Range")
    fun isExists(emp: StudentModel): Boolean {

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
    }

    @SuppressLint("Range")
    fun getByName(sName: String, pName: String): StudentModel {

        val selectQuery = "SELECT  * FROM ${StudentDatabaseHandler.TABLE_CONTACTS}"

        val db = this.readableDatabase

        var id: Int
        var studentName: String
        var parentName: String
        var phone: String
        var address: String
        var password: String

        // Cursor is used to read the record one by one. Add them to data model class.
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return StudentModel(null, null, null, null, null, null)
        }

        if (cursor.moveToFirst()) {
            do {
                if (sName.equals(cursor.getString(cursor.getColumnIndex(StudentDatabaseHandler.KEY_STUDENT_NAME))) &&
                    pName.equals(cursor.getString(cursor.getColumnIndex(StudentDatabaseHandler.KEY_PARENT_NAME)))
                ) {

                    id = cursor.getInt(cursor.getColumnIndex(StudentDatabaseHandler.KEY_ID))
                    studentName = cursor.getString(cursor.getColumnIndex(StudentDatabaseHandler.KEY_STUDENT_NAME))
                    parentName = cursor.getString(cursor.getColumnIndex(StudentDatabaseHandler.KEY_PARENT_NAME))
                    phone = cursor.getString(cursor.getColumnIndex(StudentDatabaseHandler.KEY_PHONE))
                    address = cursor.getString(cursor.getColumnIndex(StudentDatabaseHandler.KEY_ADDRESS))
                    password = cursor.getString(cursor.getColumnIndex(StudentDatabaseHandler.KEY_PASSWORD))

                    val emp = StudentModel(id, studentName, parentName, phone, address, password)

                    return emp
                }
            } while (cursor.moveToNext())
        }
        return StudentModel(null, null, null, null, null, null)
    }

}

