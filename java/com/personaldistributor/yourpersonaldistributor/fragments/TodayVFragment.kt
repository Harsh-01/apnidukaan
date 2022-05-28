package com.personaldistributor.yourpersonaldistributor.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.personaldistributor.yourpersonaldistributor.R

/**
 * A simple [Fragment] subclass.
 */
class TodayVFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_today_v, container, false)
    }


}
