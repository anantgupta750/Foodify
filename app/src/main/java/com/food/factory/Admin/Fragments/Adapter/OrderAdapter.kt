package com.food.factory.Admin.Fragments.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.food.factory.Admin.Fragments.Model.AddMenuModel
import com.food.factory.R
import com.food.factory.User.Model.OrderModel

class OrderAdapter (private val addHotelList: ArrayList<OrderModel>) :
    RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

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
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.orderlist, parent, false)

        context = parent.getContext();

        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentEmp = addHotelList[position]
        holder.menuName.text = currentEmp.orderId
        holder.menuPrice.text = "Amount Price "+currentEmp.paymentPrice
        holder.check.setOnClickListener {

        }
//        context?.let {
//            Glide.with(it)
//                .load(currentEmp.menuImage)
//                .into(holder.menuImage)
//            val rate=2
//            holder.menuRatting.rating = rate.toFloat()        }
//
    }

    override fun getItemCount(): Int {
        return addHotelList.size
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val menuName : TextView = itemView.findViewById(R.id.orderId)
        val menuPrice : TextView = itemView.findViewById(R.id.price)
        val check : CardView = itemView.findViewById(R.id.check)
//        val menuRatting : RatingBar = itemView.findViewById(R.id.rattingBar)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }

    }

}