package com.food.factory.User

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.food.factory.Admin.Fragments.Adapter.OrderAdapter
import com.food.factory.Chat.ChatActivity
import com.food.factory.R
import com.food.factory.User.Model.OrderModel
import com.food.factory.Variable

class UserOrderAdapter (private val addHotelList: ArrayList<OrderModel>) :
    RecyclerView.Adapter<UserOrderAdapter.ViewHolder>() {

    private lateinit var mListener: onItemClickListener
    private var context: Context? = null

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.orderlist, parent, false)

        context = parent.getContext();

        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentEmp = addHotelList[position]
        holder.menuName.text = currentEmp.orderId
        holder.menuPrice.text = "Amount Price "+currentEmp.paymentPrice
        holder.menuPrice.text = "Amount Price "+currentEmp.paymentPrice
        holder.menuRatting.text =currentEmp.status
        holder.check.setOnClickListener {

        }

        holder.chat.setOnClickListener {

            val intent = Intent(context, ChatActivity::class.java)
//                            //put extras
            Variable.oId = currentEmp.orderId
//                            intent.putExtra("empId", menuList[position].orderId)
            context?.startActivity(intent)
//
        }
        holder.qr.setOnClickListener {

            val intent = Intent(context, PaymentQR::class.java)
//                            //put extras
            Variable.paymentID = currentEmp.paymentId.toString()
//                            intent.putExtra("empId", menuList[position].orderId)
            context?.startActivity(intent)
//
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
        val chat : CardView = itemView.findViewById(R.id.chat)
        val qr : CardView = itemView.findViewById(R.id.qr)
        val menuRatting : TextView = itemView.findViewById(R.id.textt)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }

    }



}