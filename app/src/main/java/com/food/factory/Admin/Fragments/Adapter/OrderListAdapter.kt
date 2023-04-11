package com.food.factory.Admin.Fragments.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.food.factory.R
import com.food.factory.User.CartAdapter
import com.food.factory.User.Model.CartModel
import com.food.factory.User.Model.OrderModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class OrderListAdapter(private val addHotelList: ArrayList<CartModel>) :
    RecyclerView.Adapter<OrderListAdapter.ViewHolder>() {
    private lateinit var dbRef: DatabaseReference
    lateinit var auth: FirebaseAuth

    var cartId = ""
    private lateinit var mListener: onItemClickListener
    private var context: Context? = null

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener) {
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cart_sub_list, parent, false)
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.orderlist_admin, parent, false)
//        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_menu, parent, false)
        auth= FirebaseAuth.getInstance()

        context = parent.getContext();

        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var  a = 1
        dbRef = FirebaseDatabase.getInstance().getReference("Cart")


        val currentEmp = addHotelList[position]
        holder.menuName.text = currentEmp.dishName
        holder.menuPrice.text = "₹"+ currentEmp.totalPrice
        holder.currentvalue.text = currentEmp.plateNo

        context?.let {
            Glide.with(it)
                .load(currentEmp.image)
                .into(holder.menuImage)
        }

        holder.add.setOnClickListener {

            a = a+1
            val menu = CartModel(currentEmp.hotelId,auth.currentUser.phoneNumber,currentEmp.dishId,currentEmp.totalPrice,
                a.toString(),currentEmp.dishName,currentEmp.image)
            dbRef.child(cartId).setValue(menu)
                .addOnCompleteListener {
                    Toast.makeText(context, "Data updated successfully", Toast.LENGTH_LONG).show()

//                         cartId = dbRef.push().key!!


                }.addOnFailureListener { err ->
                    Toast.makeText(context, "Error ${err.message}", Toast.LENGTH_LONG).show()
                }
            holder.currentvalue.text = a.toString();

        }

        holder.minus.setOnClickListener {
            if (a == 1 || a <1){
                holder.currentvalue.text = "1";

            }else if (a > 1)
                a =a -1
            val menu = CartModel(currentEmp.hotelId,auth.currentUser.phoneNumber,currentEmp.dishId,currentEmp.totalPrice,
                a.toString(),currentEmp.dishName,currentEmp.image)
            dbRef.child(cartId).setValue(menu)
                .addOnCompleteListener {
                    Toast.makeText(context, "Data updated successfully", Toast.LENGTH_LONG).show()

//                         cartId = dbRef.push().key!!


                }.addOnFailureListener { err ->
                    Toast.makeText(context, "Error ${err.message}", Toast.LENGTH_LONG).show()
                }

            holder.currentvalue.text = a.toString();
        }


//        currentEmp.dishPrice +

//
    }

    override fun getItemCount(): Int {
        return addHotelList.size
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {



        val menuName: TextView = itemView.findViewById(R.id.productcatName)
        //        val menuName: TextView = itemView.findViewById(R.id.menuTitle)
        val menuDes: TextView = itemView.findViewById(R.id.text)
        val menuPrice: TextView = itemView.findViewById(R.id.price)
        val menuImage: ImageView = itemView.findViewById(R.id.image)
        val addtocart: CardView = itemView.findViewById(R.id.addtocart)
        //        val menuRatting: RatingBar = itemView.findViewById(R.id.rattingBar)
        val relativeLay: CardView = itemView.findViewById(R.id.updown)
        val add: ImageView = itemView.findViewById(R.id.add)
        val minus: ImageView = itemView.findViewById(R.id.subtract)
        val currentvalue: TextView = itemView.findViewById(R.id.textt)



//        val menuName: TextView = itemView.findViewById(R.id.dishname)
//        val menuDes: TextView = itemView.findViewById(R.id.dishprice)
//        val menuPrice: TextView = itemView.findViewById(R.id.dishqty)
//        val menuImage: ImageView = itemView.findViewById(R.id.menuImage)
//        val addtocart: Button = itemView.findViewById(R.id.addtocart)
//        val menuRatting: RatingBar = itemView.findViewById(R.id.rattingBar)
//        val relativeLay: RelativeLayout = itemView.findViewById(R.id.sssss)
//        val add: ImageView = itemView.findViewById(R.id.add)
//        val minus: ImageView = itemView.findViewById(R.id.minus)
//        val currentvalue: TextView = itemView.findViewById(R.id.textt)


        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }

    }

}