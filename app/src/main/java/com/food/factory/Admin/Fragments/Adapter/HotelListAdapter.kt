package com.food.factory.Admin.Fragments.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.food.factory.*
import com.food.factory.Admin.Fragments.EditHotelDetail
import com.food.factory.Admin.Fragments.HotelDetail
import com.food.factory.Admin.Fragments.Model.AddHotelModel
import com.google.firebase.database.FirebaseDatabase

class HotelListAdapter(private val addHotelList: ArrayList<AddHotelModel>) :
    RecyclerView.Adapter<HotelListAdapter.ViewHolder>() {

    private lateinit var mListener: onItemClickListener
    private var context: Context? = null

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.emp_list, parent, false)
        context = parent.getContext();

        return ViewHolder(itemView, mListener)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentEmp = addHotelList[position]
        holder.tvEmpName.text = currentEmp.hotelName
        holder.tvEmpName.setOnClickListener {
            val intent = Intent(context, HotelDetail::class.java)
            //put extras
            Variable.hotelId = currentEmp.hotelId
//            intent.putExtra("empId", hotelList[position].hotelId)
//                            intent.putExtra("empName", empList[position].hotelName)
//                            intent.putExtra("empAge", empList[position].hotelAge)
//                            intent.putExtra("empSalary", empList[position].hotelSalary)
            context!!.startActivity(intent)

        }

        holder.edit.setOnClickListener {
            val intent = Intent(context, com.food.factory.Admin.EditHotelDetail::class.java)
            //put extras
            Variable.hotelId = currentEmp.hotelId
//            intent.putExtra("empId", hotelList[position].hotelId)
//                            intent.putExtra("empName", empList[position].hotelName)
//                            intent.putExtra("empAge", empList[position].hotelAge)
//                            intent.putExtra("empSalary", empList[position].hotelSalary)
            context!!.startActivity(intent)

        }

        holder.delete.setOnClickListener {
            val dbRef = currentEmp.hotelId?.let { it1 ->
                FirebaseDatabase.getInstance().getReference("Hotel").child(
                    it1
                )
            }
            val mTask = dbRef!!.removeValue()

            mTask.addOnSuccessListener {
                Toast.makeText(context, " Data deleted", Toast.LENGTH_LONG).show()

//                val intent = Intent(this, MainActivity3::class.java)
//                finish()
//                startActivity(intent)
            }.addOnFailureListener{ error ->
                Toast.makeText(context, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
            }

        }
    }

    override fun getItemCount(): Int {
        return addHotelList.size
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val tvEmpName : TextView = itemView.findViewById(R.id.tvEmpName)
        val edit : ImageView = itemView.findViewById(R.id.edit)
        val delete : ImageView = itemView.findViewById(R.id.remove)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }

    }

}