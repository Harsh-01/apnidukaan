package com.personaldistributor.yourpersonaldistributor.fragments


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.personaldistributor.yourpersonaldistributor.PostAdapter

import com.personaldistributor.yourpersonaldistributor.R
import com.personaldistributor.yourpersonaldistributor.models.Post

/**
 * A simple [Fragment] subclass.
 */
class PostFragment : Fragment() {
    lateinit var sharedText : SharedPreferences
//    lateinit var sharedCount : SharedPreferences

    lateinit var recyclerDashboard1 : RecyclerView

    lateinit var layoutManager: RecyclerView.LayoutManager

    lateinit var recyclerAdapter : PostAdapter

    var postInfoList = arrayListOf<Post>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_post, container, false)
        // Inflate the layout for this fragment
//        sharedCount = (activity as Context).getSharedPreferences(getString(R.string.preferences_post_count), Context.MODE_PRIVATE)
        sharedText = (activity as Context).getSharedPreferences(getString(R.string.preference_post_text), Context.MODE_PRIVATE)

//        val index = sharedCount.getInt("PostCount", 1)
        val postText = sharedText.getString("PostText", "New Post..")
        recyclerAdapter = PostAdapter(activity as Context,postInfoList)

        postInfoList.add(recyclerAdapter.itemCount,Post("Adarsh", "At2642", postText as String))
//        postInfoList = arrayListOf<Post>(
//            Post("Adarsh","AT1234", postText as String)
//        )
        recyclerDashboard1 = view.findViewById(R.id.recyclerDashboard1)
        layoutManager = LinearLayoutManager(activity)


        recyclerDashboard1.adapter = recyclerAdapter
        recyclerDashboard1.setHasFixedSize(true)
        recyclerDashboard1.layoutManager= layoutManager
        return view
    }


}
