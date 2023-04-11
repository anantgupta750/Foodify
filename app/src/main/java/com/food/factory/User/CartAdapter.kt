package com.food.factory.User

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.food.factory.R
import com.food.factory.User.Model.CartModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class CartAdapter (private val addHotelList: ArrayList<CartModel>) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {
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
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cartmenu, parent, false)
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

          var c =
              Integer.parseInt(currentEmp.totalPrice )* Integer.parseInt(currentEmp.plateNo)

        holder.p.text = c.toString()
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
//                    Toast.makeText(context, "Data updated successfully", Toast.LENGTH_LONG).show()

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

        holder.deleteImage.setOnClickListener {

            val ref = FirebaseDatabase.getInstance().reference
            val applesQuery = ref.child("Cart").orderByChild("dishId").equalTo(currentEmp.dishId)

            applesQuery.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (appleSnapshot in dataSnapshot.children) {
                        appleSnapshot.ref.removeValue()
                        notifyDataSetChanged()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
//                    Log.e(TAG, "onCancelled", databaseError.toException())
                }
            })

//            val dbRef = FirebaseDatabase.getInstance().getReference("Cart").orderByChild("dishId").equalTo(currentEmp.dishId)
//            val mTask = dbRef.ref.removeValue()
//
//            mTask.addOnSuccessListener {
//                notifyDataSetChanged()
////                Toast.makeText(this, "Employee data deleted", Toast.LENGTH_LONG).show()
//
////                val intent = Intent(this, MainActivity3::class.java)
////                finish()
////                startActivity(intent)
//            }.addOnFailureListener{ error ->
////                Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
//            }
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
        val deleteImage: ImageView = itemView.findViewById(R.id.delete)
        val addtocart: CardView = itemView.findViewById(R.id.addtocart)
        //        val menuRatting: RatingBar = itemView.findViewById(R.id.rattingBar)
        val relativeLay: CardView = itemView.findViewById(R.id.updown)
        val add: ImageView = itemView.findViewById(R.id.add)
        val minus: ImageView = itemView.findViewById(R.id.subtract)
        val currentvalue: TextView = itemView.findViewById(R.id.textt)
        val p: TextView = itemView.findViewById(R.id.p)



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