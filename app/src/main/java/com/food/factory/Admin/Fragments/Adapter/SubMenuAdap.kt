package com.food.factory.Admin.Fragments.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.food.factory.Admin.Fragments.HotelDetail
import com.food.factory.Admin.Fragments.Model.SaveSubMenu
import com.food.factory.R
import com.food.factory.User.MenuAdap
import com.food.factory.User.MenuList
import com.food.factory.User.Model.CartModel
import com.food.factory.Variable
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SubMenuAdap (private val addHotelList: ArrayList<SaveSubMenu>) :
    RecyclerView.Adapter<SubMenuAdap.ViewHolder>() {
    private lateinit var dbRef: DatabaseReference
    lateinit var auth: FirebaseAuth

    var cartId = ""
//    private lateinit var mListener: onItemClickListener
    private var context: Context? = null

//    interface onItemClickListener {
//        fun onItemClick(position: Int)
//    }

//    fun setOnItemClickListener(clickListener: Any) {
//        mListener = clickListener
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.submodel, parent, false)
//        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.menu_rv, parent, false)
//        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_menu, parent, false)
        auth= FirebaseAuth.getInstance()

        context = parent.getContext();

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var  a = 1


        val currentEmp = addHotelList[position]
        holder.menuName.text = currentEmp.subMenuName
//        holder.menuPrice.text ="₹ "+  currentEmp.subMenuType
//        holder.menuDes.text = currentEmp.menuDescription
//        context?.let {
        context?.let {
            Glide.with(it)
                .load(currentEmp.subMenuImage)
                .into(holder.menuImage)
        }

        holder.relativeLay.setOnClickListener {

            val intent = Intent(context, MenuList::class.java)
            //put extras
            Variable.sub_menu_Id = addHotelList[position].subMenuId
//            intent.putExtra("empId", hotelList[position].hotelId)
//                            intent.putExtra("empName", empList[position].hotelName)
//                            intent.putExtra("empAge", empList[position].hotelAge)
//                            intent.putExtra("empSalary", empList[position].hotelSalary)
            context?.startActivity(intent)


//            val intent = Intent(this, MenuList::class.java)
//            startActivity(intent)
        }
//            val rate = 2
////            holder.menuRatting.rating = rate.toFloat()
//
//            holder.addtocart.setOnClickListener {
//
//                holder.addtocart.visibility = View.GONE
//                holder.relativeLay.visibility = View.VISIBLE
//                dbRef = FirebaseDatabase.getInstance().getReference("Cart")
//
//                val menu = CartModel(currentEmp.hotelId,
//                    auth.currentUser.phoneNumber,currentEmp.menuId,currentEmp.menuPrice,"1",currentEmp.menuName,currentEmp.menuImage)
//                cartId = dbRef.push().key!!
//
//
//
//                dbRef.child(cartId).setValue(menu)
//                    .addOnCompleteListener {
////                        Toast.makeText(context, "Data inserted successfully", Toast.LENGTH_LONG).show()
//
////                         cartId = dbRef.push().key!!
//
//
//                    }.addOnFailureListener { err ->
//                        Toast.makeText(context, "Error ${err.message}", Toast.LENGTH_LONG).show()
//                    }
//
//            }
//            holder.add.setOnClickListener {
//
//                a = a+1
//                val menu = CartModel(currentEmp.hotelId,auth.currentUser.phoneNumber,currentEmp.menuId,currentEmp.menuPrice,
//                    a.toString(),currentEmp.menuName,currentEmp.menuImage)
//                dbRef.child(cartId).setValue(menu)
//                    .addOnCompleteListener {
////                        Toast.makeText(context, "Data updated successfully", Toast.LENGTH_LONG).show()
//
////                         cartId = dbRef.push().key!!
//
//
//                    }.addOnFailureListener { err ->
//                        Toast.makeText(context, "Error ${err.message}", Toast.LENGTH_LONG).show()
//                    }
//                holder.currentvalue.text = a.toString();
//
//            }
//
//            holder.minus.setOnClickListener {
//                if (a == 1 || a <1){
//                    holder.currentvalue.text = "1";
//
//                }else if (a > 1)
//                    a =a -1
//                val menu = CartModel(currentEmp.hotelId,auth.currentUser.phoneNumber,currentEmp.menuId,currentEmp.menuPrice,
//                    a.toString(),currentEmp.menuName,currentEmp.menuImage)
//                dbRef.child(cartId).setValue(menu)
//                    .addOnCompleteListener {
////                        Toast.makeText(context, "Data updated successfully", Toast.LENGTH_LONG).show()
//
////                         cartId = dbRef.push().key!!
//
//
//                    }.addOnFailureListener { err ->
//                        Toast.makeText(context, "Error ${err.message}", Toast.LENGTH_LONG).show()
//                    }
//
//                holder.currentvalue.text = a.toString();
//            }
//
//        }
//
    }

    override fun getItemCount(): Int {
        return addHotelList.size
    }

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val menuName: TextView = itemView.findViewById(R.id.textSub)
        //        val menuName: TextView = itemView.findViewById(R.id.menuTitle)
//        val menuDes: TextView = itemView.findViewById(R.id.text)
//        val menuPrice: TextView = itemView.findViewById(R.id.price)
        val menuImage: ImageView = itemView.findViewById(R.id.subImage)
//        val addtocart: CardView = itemView.findViewById(R.id.addtocart)
        //        val menuRatting: RatingBar = itemView.findViewById(R.id.rattingBar)
        val relativeLay: RelativeLayout = itemView.findViewById(R.id.RV)
//        val add: ImageView = itemView.findViewById(R.id.add)
//        val minus: ImageView = itemView.findViewById(R.id.subtract)
//        val currentvalue: TextView = itemView.findView/ById(R.id.textt)


//        init {
//            itemView.setOnClickListener {
//                clickListener.onItemClick(adapterPosition)
//            }
        }

    }

