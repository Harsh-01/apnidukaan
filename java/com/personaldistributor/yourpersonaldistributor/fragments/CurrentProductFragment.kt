package com.personaldistributor.yourpersonaldistributor.fragments


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.personaldistributor.yourpersonaldistributor.CounterActivity

import com.personaldistributor.yourpersonaldistributor.R

/**
 * A simple [Fragment] subclass.
 */
class CurrentProductFragment : Fragment() {
    lateinit var maggie: ImageButton
    lateinit var coffee: ImageButton
    lateinit var rin: ImageButton
    lateinit var dove: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_current_product, container, false)
        val act = CounterActivity()
        maggie = view.findViewById(R.id.maggie)
        coffee = view.findViewById(R.id.coffee)
        rin = view.findViewById(R.id.rin)
        dove = view.findViewById(R.id.dove)
        // Inflate the layout for this fragment
        maggie.setOnClickListener{
            startActivity(Intent(context as Activity, CounterActivity::class.java))
            act.mof(0)
        }
        coffee.setOnClickListener{
            startActivity(Intent(context as Activity, CounterActivity::class.java))
            act.mof(1)
        }
        rin.setOnClickListener{
            startActivity(Intent(context as Activity, CounterActivity::class.java))
            act.mof(2)
        }
        dove.setOnClickListener{
            startActivity(Intent(context as Activity, CounterActivity::class.java))
            act.mof(3)
        }
        return view
    }


}
