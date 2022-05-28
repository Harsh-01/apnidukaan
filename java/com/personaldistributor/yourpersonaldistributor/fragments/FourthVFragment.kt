package com.personaldistributor.yourpersonaldistributor.fragments


import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.personaldistributor.yourpersonaldistributor.DashboardRecyclerAdapter

import com.personaldistributor.yourpersonaldistributor.R
import com.personaldistributor.yourpersonaldistributor.models.Book
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class FourthVFragment : Fragment() {
    lateinit var sharedText : SharedPreferences
    //    lateinit var sharedCount : SharedPreferences
    // Inflate the layout for this fragment
    lateinit var recyclerDashboard: RecyclerView

    lateinit var layoutManager: RecyclerView.LayoutManager

    lateinit var progressLayout: RelativeLayout

    lateinit var progressbBar: ProgressBar

    lateinit var recyclerAdapter : DashboardRecyclerAdapter

    lateinit var post : TextView
    lateinit var favourites : ImageView
//    lateinit var chat:ImageView

    lateinit var toolbarFragment: Toolbar

    var email:String = ""
    var subject:String=""
    var body:String=""
    var chooserTitle :String = "Adarsh"

    var postCount = 0


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
    val displayList = ArrayList<Book>()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment


        val view =inflater.inflate(R.layout.fragment_fourth_v, container, false)
        setHasOptionsMenu(true)
        recyclerDashboard = view.findViewById(R.id.recyclerDashboard)
        layoutManager = GridLayoutManager(activity,2)
        post = view.findViewById(R.id.post)
//        chat=view.findViewById(R.id.chat)
//        toolbarFragment = view.findViewById<Toolbar>(R.id.toolbar)
//        (context as Activity).setActionBar(toolbarFragment)
        displayList.addAll(bookInfoList)

        recyclerAdapter = DashboardRecyclerAdapter(activity as Context, displayList)
        recyclerDashboard.adapter = recyclerAdapter
        recyclerDashboard.layoutManager = layoutManager



//        favourites = recyclerAdapter.favourites
        sharedText = (activity as Context).getSharedPreferences(getString(R.string.preference_post_text), Context.MODE_PRIVATE)
//        sharedCount = (activity as Context).getSharedPreferences(getString(R.string.preferences_post_count), Context.MODE_PRIVATE)
        // progressLayout = view.findViewById(R.id.progressLayout)

        //progressbBar = view.findViewById(R.id.progressBar)

        // progressLayout.visibility = View.VISIBLE

        post.setOnClickListener{
            val builder = AlertDialog.Builder(activity as Context)

            val dialogLayout = layoutInflater.inflate(R.layout.edit_text_layout1,null)
            val editText = dialogLayout.findViewById<EditText>(R.id.et_edittext1)

            with(builder){
                setTitle("Write a post..")
                setPositiveButton("Post"){dialog, which ->
//                    post.text = editText.text.toString()
                    sharedText.edit().putString("PostText", editText.text.toString()).apply()

//                    sharedCount.edit().putInt("PostCount", postCount).apply()
                    postCount += 1
                    val someFragment: Fragment = PostFragment()
                    val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
                    transaction.replace(
                        R.id.home_search,
                        someFragment
                    ) // give your fragment container id in first parameter

                    transaction.addToBackStack(null) // if written, this transaction will be added to backstack

                    transaction.commit()
                    Toast.makeText(activity as Context, "Post successful", Toast.LENGTH_SHORT).show()


                }
                setNegativeButton("Cancel"){dialog, which ->
//                     post.text ="Post your requirement..."
                    Toast.makeText(activity as Context, "Post cancelled", Toast.LENGTH_SHORT).show()

                }
                setView(dialogLayout)
                show()
            }
        }
//        chat.setOnClickListener{
//
//            val uri: Uri = Uri.parse("mailto:$email")
//                .buildUpon()
//                .appendQueryParameter("subject", subject)
//              .appendQueryParameter("body", body)
//               .build()
//
//           val emailIntent = Intent(Intent.ACTION_SENDTO, uri)
//          startActivity(Intent.createChooser(emailIntent, chooserTitle))
//
//        }
//        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerDashboard)
//        recyclerView.addOnItemTouchListener(
//            RecyclerItemClickListener(context, recyclerView, object : AdapterView.OnItemClickListener() {
//                fun onItemClick(view: View?, position: Int) {
//                    // do whatever
//                }
//
//                fun onLongItemClick(view: View?, position: Int) {
//                    // do whatever
//                }
//            })
//        )
//        favourites.setOnClickListener{
//
//
//            val uri: Uri = Uri.parse("mailto:$email")
//                .buildUpon()
//                .appendQueryParameter("subject", subject)
//                .appendQueryParameter("body", body)
//                .build()
//
//            val emailIntent = Intent(Intent.ACTION_SENDTO, uri)
//            startActivity(Intent.createChooser(emailIntent, chooserTitle))
//
//        }


        return view
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        setHasOptionsMenu(true)
        inflater.inflate(R.menu.menu, menu)
        val menuItem = menu.findItem(R.id.search_bar)

        if(menuItem != null){
            val searchView = menuItem.actionView as SearchView
            val editText = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
            editText.hint = "Search Product.."

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {

                    if(newText!!.isNotEmpty()){
                        displayList.clear()
                        val search = newText.toLowerCase(Locale.getDefault())

                        bookInfoList.forEach {

                            if(it.productName.toLowerCase(Locale.getDefault()).contains(search)){
                                displayList.add(it)
                            }else if(it.productCompany.toLowerCase(Locale.getDefault()).contains(search)){
                                displayList.add(it)
                            }
                        }

                        recyclerDashboard.adapter!!.notifyDataSetChanged()
                    }else{
                        displayList .clear()
                        displayList.addAll(bookInfoList)
                        recyclerDashboard.adapter!!.notifyDataSetChanged()
                    }

                    return true
                }

            })
        }else{
            Toast.makeText(activity as Context, "Search not visible", Toast.LENGTH_SHORT ).show()
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setHasOptionsMenu(true)
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }
//    override fun onResume() {
//        super.onResume()
//        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
//    }


}
