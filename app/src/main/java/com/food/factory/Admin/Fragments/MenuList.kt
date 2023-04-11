package com.food.factory.Admin.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.food.factory.Admin.Fragments.Adapter.HotelListAdapter
import com.food.factory.Admin.Fragments.Adapter.MenuAdapter
import com.food.factory.Admin.Fragments.Model.AddHotelModel
import com.food.factory.Admin.Fragments.Model.AddMenuModel
import com.food.factory.Admin.MenuEdit
import com.food.factory.MainActivity5
import com.food.factory.MenuModel
import com.food.factory.R
import com.food.factory.Variable
import com.google.firebase.database.*

class MenuList : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var menuRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var menuList: ArrayList<AddMenuModel>
    private lateinit var dbRef: DatabaseReference
    var thiscontext: Context? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_menu_list, container, false)
        thiscontext = context

        menuRecyclerView = view.findViewById(R.id.menuRv)
        menuRecyclerView.layoutManager = LinearLayoutManager(thiscontext)
        menuRecyclerView.setHasFixedSize(true)
        tvLoadingData = view.findViewById(R.id.tvLoadingData)

        menuList = arrayListOf<AddMenuModel>()

        filterData()
        return  view
    }


    private fun filterData() {

        val databaseReference = FirebaseDatabase.getInstance().getReference("Menu")


        databaseReference.orderByChild("subMenuId").equalTo(Variable.sub_menu_Id).addValueEventListener(object:
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    menuList.clear()
                    for (i in snapshot.children){
                        val menuitem = i.getValue(AddMenuModel::class.java)
                        menuList.add(menuitem!!)
                    }
                    val mAdapter = MenuAdapter(menuList)
                    menuRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : MenuAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {

                            val intent = Intent(thiscontext, MenuEdit::class.java)
                            //put extras
                            intent.putExtra("menuId", menuList[position].menuId)
//                            intent.putExtra("empName", empList[position].hotelName)
//                            intent.putExtra("empAge", empList[position].hotelAge)
//                            intent.putExtra("empSalary", empList[position].hotelSalary)
                            startActivity(intent)


//                            Toast.makeText(thiscontext,"Touched"+menuList.get(position).menuId,Toast.LENGTH_SHORT).show()
                        }

                    })

                    menuRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }




            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

}
