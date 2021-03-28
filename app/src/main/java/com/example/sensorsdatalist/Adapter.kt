package com.example.sensorsdatalist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class Adapter : ListAdapter<SensorData, Adapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(getItem(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvX by lazy { itemView.findViewById<TextView>(R.id.tvX) }
        private val tvY by lazy { itemView.findViewById<TextView>(R.id.tvY) }
        private val tvZ by lazy { itemView.findViewById<TextView>(R.id.tvZ) }

        fun bind(element: SensorData) {
            tvX.text = element.x.format(2)
            tvY.text = element.y.format(2)
            tvZ.text = element.z.format(2)
        }
    }

    private fun Float.format(digits: Int) = "%.${digits}f".format(this)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SensorData>() {
            override fun areItemsTheSame(oldItem: SensorData, newItem: SensorData) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: SensorData, newItem: SensorData) =
                oldItem == newItem
        }
    }
}
