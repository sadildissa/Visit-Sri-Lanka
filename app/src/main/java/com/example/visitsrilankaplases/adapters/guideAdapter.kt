package com.example.visitsrilankaplases.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.visitsrilankaplases.R
import com.example.visitsrilankaplases.models.GuideModel

class guideAdapter (private val guideList : ArrayList<GuideModel>) :
    RecyclerView.Adapter<guideAdapter.ViewHolder>(){

    private lateinit var mListener:onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.guide_list_item, parent, false)
        return ViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentguide = guideList[position]
        holder.tvGuideName.text = currentguide.guideName
    }

    override fun getItemCount(): Int {
        return guideList.size
    }

    class ViewHolder (itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val tvGuideName: TextView = itemView.findViewById(R.id.tvguideName)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }

        }
    }
}