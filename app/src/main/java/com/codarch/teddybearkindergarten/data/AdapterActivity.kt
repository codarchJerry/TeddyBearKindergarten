package com.codarch.teddybearkindergarten.data

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codarch.teddybearkindergarten.R


class AdapterActivity(
    private var activityList: MutableList<ActivityModel>, context: Context
) :

    RecyclerView.Adapter<AdapterActivity.LandmarkHolder>() {

    private var adapterCallback: AdapterActivity.AdapterCallback? = context as AdapterActivity.AdapterCallback?

    class LandmarkHolder(view: View) : RecyclerView.ViewHolder(view) {

        val activityName: TextView = view.findViewById(R.id.activityName)
        val activityDate: TextView = view.findViewById(R.id.dateText)
        val activityImage: ImageView = view.findViewById(R.id.activityImage)


        fun bindItems(item: ActivityModel) {
            val bitmapp: ByteArray? = item.activityImage
            val imagee = bitmapp?.size?.let { BitmapFactory.decodeByteArray(bitmapp, 0, it) }

            activityImage.setImageBitmap(imagee)
            activityName.text = item.activityName
            activityDate.text = item.activityDate
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LandmarkHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_card, parent, false)
        return LandmarkHolder(view)

    }

    override fun getItemCount(): Int {
        return activityList.size

    }

    override fun onBindViewHolder(holder: LandmarkHolder, position: Int) {

        holder.bindItems(activityList[position])
        val xButton: Button = holder.itemView.findViewById<Button>(R.id.xButton)
        val checkButton: Button = holder.itemView.findViewById<Button>(R.id.checkButton)

        /*xButton.setOnClickListener {

            try {
                adapterCallback!!.onClickX(activityList[position])
                holder.itemView.findViewById<TextView>(R.id.activityName)
                    .setTextColor(Color.parseColor("#e93b2d"))

            } catch (e: ClassCastException) {
            }
        }

        checkButton.setOnClickListener {

            try {
                adapterCallback!!.onClickCheck(activityList[position])
                holder.itemView.findViewById<TextView>(R.id.activityName)
                    .setTextColor(Color.parseColor("#489644"))
            } catch (e: ClassCastException) {
            }
        }*/
    }

    interface AdapterCallback {
        fun onClickCheck(activity: ActivityModel)
        fun onClickX(activity: ActivityModel)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(modelList: MutableList<ActivityModel>) {
        activityList = modelList
        notifyDataSetChanged()
    }


}
