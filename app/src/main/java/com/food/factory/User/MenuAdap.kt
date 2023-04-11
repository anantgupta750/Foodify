package com.food.factory.User

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.food.factory.Admin.Fragments.Adapter.MenuAdapter
import com.food.factory.Admin.Fragments.Model.AddMenuModel
import com.food.factory.R
import com.food.factory.User.Model.CartModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MenuAdap(private val addHotelList: ArrayList<AddMenuModel>) :
    RecyclerView.Adapter<MenuAdap.ViewHolder>() {
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
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.menu, parent, false)
//        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.menu_rv, parent, false)
//        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_menu, parent, false)
        auth= FirebaseAuth.getInstance()

        context = parent.getContext();

        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var  a = 1


        val currentEmp = addHotelList[position]
        holder.menuName.text = currentEmp.menuName
        holder.menuPrice.text ="₹ "+  currentEmp.menuPrice
        holder.menuDes.text = currentEmp.menuDescription
        context?.let {
            Glide.with(it)
                .load(currentEmp.menuImage)
                .into(holder.menuImage)
            val rate = 2
//            holder.menuRatting.rating = rate.toFloat()

            holder.addtocart.setOnClickListener {

                holder.addtocart.visibility = View.GONE
                holder.relativeLay.visibility = View.VISIBLE
                dbRef = FirebaseDatabase.getInstance().getReference("Cart")

                val menu = CartModel(currentEmp.hotelId,
                    auth.currentUser.phoneNumber,currentEmp.menuId,currentEmp.menuPrice,"1",currentEmp.menuName,currentEmp.menuImage,"pending")
                 cartId = dbRef.push().key!!



                dbRef.child(cartId).setValue(menu)
                    .addOnCompleteListener {
//                        Toast.makeText(context, "Data inserted successfully", Toast.LENGTH_LONG).show()

//                         cartId = dbRef.push().key!!


                    }.addOnFailureListener { err ->
                        Toast.makeText(context, "Error ${err.message}", Toast.LENGTH_LONG).show()
                    }

            }
            holder.add.setOnClickListener {

                a = a+1
                val menu = CartModel(currentEmp.hotelId,auth.currentUser.phoneNumber,currentEmp.menuId,currentEmp.menuPrice,
                    a.toString(),currentEmp.menuName,currentEmp.menuImage,"pending")
                dbRef.child(cartId).setValue(menu)
                    .addOnCompleteListener {
//                        Toast.makeText(context, "Data updated successfully", Toast.LENGTH_LONG).show()

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
                val menu = CartModel(currentEmp.hotelId,auth.currentUser.phoneNumber,currentEmp.menuId,currentEmp.menuPrice,
                    a.toString(),currentEmp.menuName,currentEmp.menuImage,"pending")
                dbRef.child(cartId).setValue(menu)
                    .addOnCompleteListener {
//                        Toast.makeText(context, "Data updated successfully", Toast.LENGTH_LONG).show()

//                         cartId = dbRef.push().key!!


                    }.addOnFailureListener { err ->
                        Toast.makeText(context, "Error ${err.message}", Toast.LENGTH_LONG).show()
                    }

                holder.currentvalue.text = a.toString();
            }

        }
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


        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }

    }

}