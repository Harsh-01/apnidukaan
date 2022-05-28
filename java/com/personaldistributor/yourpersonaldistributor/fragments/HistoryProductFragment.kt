package com.personaldistributor.yourpersonaldistributor.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.personaldistributor.yourpersonaldistributor.DashboardRecyclerAdapter

import com.personaldistributor.yourpersonaldistributor.R
import com.personaldistributor.yourpersonaldistributor.models.Book
import com.personaldistributor.yourpersonaldistributor.util.ConnectionManager
import org.json.JSONException
import java.lang.reflect.Method


class HistoryProductFragment : Fragment() {

    lateinit var recyclerDashboard: RecyclerView

    lateinit var layoutManager: RecyclerView.LayoutManager

    lateinit var progressLayout: RelativeLayout

    lateinit var progressbBar: ProgressBar

    lateinit var recyclerAdapter : DashboardRecyclerAdapter


    val bookInfoList = arrayListOf<Book>(
        Book("Samyang","Nestle",R.drawable.koreannoodles, false, "", 0,0, "",100,5,500),
        Book("Agristar Genuine Oil","Tafe",R.drawable.engineoil, false, "", 0, 0, "",110,6,525),
        Book("Earpods","Boat",R.drawable.earbuds, false,"",0,0,"",120, 7,550),
        Book("Singfong","Nestle",R.drawable.koreannoodles1, false, "", 0, 0, "",130,8,575),
        Book("Elemis","Breckwell",R.drawable.americanfacewash, false , "", 0, 0,"",140,9,600),
        Book("Shouvy","Jukovik",R.drawable.japanisoap1, false, "", 0, 0,"",150,10,625),
        Book("Doms Pencils","Doms",R.drawable.pencils,false,"",0,0,"",160,11,650),
        Book("Dracefu","Idarta",R.drawable.japanisoap,false,"",0,0,"",170,12,675),
        Book("Cremia","Creams",R.drawable.japanicream,false,"",0,0,"",180,13,700),
        Book("Burger","Nestle",R.drawable.burger,false,"",0,0,"",190,14,725),
        Book("Chings","Chingana",R.drawable.japaninoodles,false,"",0,0,"",200,15,750)
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =inflater.inflate(R.layout.fragment_history_product, container, false)

        recyclerDashboard = view.findViewById(R.id.recyclerDashboard)
        layoutManager = GridLayoutManager(activity,2)

       // progressLayout = view.findViewById(R.id.progressLayout)

        //progressbBar = view.findViewById(R.id.progressBar)

       // progressLayout.visibility = View.VISIBLE

        recyclerAdapter = DashboardRecyclerAdapter(activity as Context,bookInfoList)
        recyclerDashboard.adapter = recyclerAdapter
        recyclerDashboard.layoutManager = layoutManager

        return view
    }

}