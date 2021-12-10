package com.codarch.teddybearkindergarten.data.model
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codarch.teddybearkindergarten.R


class AdapterCheck(private val studentList: MutableList<StudentCheckModel>) : RecyclerView.Adapter<AdapterCheck.ModelViewHolder>() {

    class ModelViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val studentName: TextView = view.findViewById(R.id.student_name_card_check)


        fun bindItems(item: StudentCheckModel) {
            studentName.text = item.studentName
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.check_card, parent, false)
        return ModelViewHolder(view)

    }

    override fun getItemCount(): Int {
        return studentList.size

    }

    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) {
        holder.bindItems(studentList[position])


    }


}