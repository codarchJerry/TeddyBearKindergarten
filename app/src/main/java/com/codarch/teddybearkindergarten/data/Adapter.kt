package com.codarch.teddybearkindergarten.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codarch.teddybearkindergarten.R
import com.codarch.teddybearkindergarten.data.model.StudentCheckModel


class Adapter(
    private val studentList: MutableList<StudentCheckModel>, context: Context
) :

    RecyclerView.Adapter<Adapter.LandmarkHolder>() {

    private var adapterCallback: Adapter.AdapterCallback? = context as Adapter.AdapterCallback?

    class LandmarkHolder(view: View) : RecyclerView.ViewHolder(view) {

        val studentName: TextView = view.findViewById(R.id.student_name_card)

        fun bindItems(item: StudentCheckModel) {
            studentName.text = item.studentName
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LandmarkHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return LandmarkHolder(view)

    }

    override fun getItemCount(): Int {
        return studentList.size

    }

    override fun onBindViewHolder(holder: LandmarkHolder, position: Int) {

        holder.bindItems(studentList[position])
        val xButton: Button = holder.itemView.findViewById<Button>(R.id.xButton)
        val checkButton: Button = holder.itemView.findViewById<Button>(R.id.checkButton)

        xButton.setOnClickListener {

                try {
                    adapterCallback!!.onClickX()
                } catch (e: ClassCastException) {
                }
        }

        checkButton.setOnClickListener {

                try {
                    adapterCallback!!.onClickCheck()
                } catch (e: ClassCastException) {
                }
        }

    }

    interface AdapterCallback {
        fun onClickCheck()
        fun onClickX()
    }


}
