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
import com.food.factory.AddSubMenu
import com.food.factory.Admin.Activity_For_Menu
import com.food.factory.Admin.Fragments.Adapter.MenuAdapter
import com.food.factory.Admin.Fragments.Adapter.SubMenuAdapter
import com.food.factory.Admin.Fragments.Model.AddMenuModel
import com.food.factory.Admin.Fragments.Model.SaveSubMenu
import com.food.factory.R
import com.food.factory.Variable
import com.google.firebase.database.*

class Sub_Menu_List : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var menuRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var menuList: ArrayList<SaveSubMenu>
    private lateinit var dbRef: DatabaseReference
    var thiscontext: Context? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_sub__menu__list, container, false)

        thiscontext = context

        menuRecyclerView = view.findViewById(R.id.submenuRv)
        menuRecyclerView.layoutManager = LinearLayoutManager(thiscontext)
        menuRecyclerView.setHasFixedSize(true)
        tvLoadingData = view.findViewById(R.id.tvLoadingData)

        menuList = arrayListOf<SaveSubMenu>()

        filterData()

        return  view
    }
    private fun filterData() {

        val databaseReference = FirebaseDatabase.getInstance().getReference("Sub_Menu")


        databaseReference.orderByChild("hotelId").equalTo(Variable.hotelId).addValueEventListener(object:
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                menuList.clear()
                if(snapshot.exists()){
//                    studentsList.clear()
                    for (i in snapshot.children){
                        val menuitem = i.getValue(SaveSubMenu::class.java)
                        menuList.add(menuitem!!)
                    }
                    val mAdapter = SubMenuAdapter(menuList)
                    menuRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : SubMenuAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {


//                            val intent = Intent(thiscontext, Activity_For_Menu::class.java)
//                            //put extras
//                            Variable.sub_menu_Id = menuList[position].subMenuId
////                            intent.putExtra("empId", hotelList[position].hotelId)
////                            intent.putExtra("empName", empList[position].hotelName)
////                            intent.putExtra("empAge", empList[position].hotelAge)
////                            intent.putExtra("empSalary", empList[position].hotelSalary)
//                            thiscontext!!.startActivity(intent)

//                            Toast.makeText(thiscontext,"Touched", Toast.LENGTH_SHORT).show()
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