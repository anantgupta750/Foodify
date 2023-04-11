package com.food.factory.Admin.Fragments.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.food.factory.Admin.Fragments.Model.AddMenuModel
import com.food.factory.R

class MenuAdapter(private val addHotelList: ArrayList<AddMenuModel>) :
    RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    private lateinit var mListener: onItemClickListener
    private var context: Context? = null

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.menu_rv, parent, false)
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_menu, parent, false)

        context = parent.getContext();

        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentEmp = addHotelList[position]
        holder.menuName.text = currentEmp.menuName
        holder.menuPrice.text = currentEmp.menuPrice
        holder.menuDes.text = currentEmp.menuDescription
        context?.let {
            Glide.with(it)
                .load(currentEmp.menuImage)
                .into(holder.menuImage)
//            val rate=2
            val rating: Float = currentEmp.menuRatting!!.toFloat()
            holder.menuRatting.rating =   rating     }
//
    }

    override fun getItemCount(): Int {
        return addHotelList.size
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val menuName : TextView = itemView.findViewById(R.id.menuTitle)
        val menuDes : TextView = itemView.findViewById(R.id.menuDes)
        val menuPrice : TextView = itemView.findViewById(R.id.menuPrice)
        val menuImage : ImageView = itemView.findViewById(R.id.menuImage)
        val menuRatting : RatingBar = itemView.findViewById(R.id.rattingBar)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }

    }

}