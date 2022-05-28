package com.personaldistributor.yourpersonaldistributor.fragments


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.personaldistributor.yourpersonaldistributor.AgentDetails
import com.personaldistributor.yourpersonaldistributor.DashboardRecyclerAdapter
import com.personaldistributor.yourpersonaldistributor.PostActivity
import com.personaldistributor.yourpersonaldistributor.R
import com.personaldistributor.yourpersonaldistributor.models.Book
import kotlinx.android.synthetic.main.fragment_fourth.*
import java.util.*
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 */
class FourthFragment : Fragment() {
    lateinit var sharedText : SharedPreferences
//    lateinit var sharedCount : SharedPreferences
    // Inflate the layout for this fragment
    lateinit var recyclerDashboard: RecyclerView

    lateinit var layoutManager: RecyclerView.LayoutManager

    lateinit var progressLayout: RelativeLayout

    lateinit var progressbBar: ProgressBar

    lateinit var recyclerAdapter : DashboardRecyclerAdapter

    lateinit var post : TextView
    lateinit var homepage:TextView
    lateinit var aboutus : TextView
    lateinit var contactus : TextView
    lateinit var testimonial : TextView
    lateinit var favourites : ImageView
//    lateinit var chat:ImageView

    lateinit var toolbarFragment:Toolbar

     var email:String = ""
     var subject:String=""
    var body:String=""
     var chooserTitle :String = "Adarsh"

    var postCount = 0


    val bookInfoList = arrayListOf<Book>(
        Book("Samyang","Nestle",R.drawable.koreannoodles, false, "", 0,0, "",120,7,500),
        Book("Agristar Genuine Oil","Tafe",R.drawable.engineoil, false, "", 0, 0, "",100,5,525),
        Book("Earpods","Boat",R.drawable.earbuds, false,"",0,0,"",110, 6,550),
        Book("Singfong","Nestle",R.drawable.koreannoodles1, false, "", 0, 0, "",130,8,575),
        Book("Elemis","Breckwell",R.drawable.americanfacewash, false , "", 0, 0,"",140,9,600),
        Book("Shouvy","Jukovik",R.drawable.japanisoap1, false, "", 0, 0,"",150,10,625),
        Book("Doms Pencils","Doms",R.drawable.pencils,false,"",0,0,"",160,11,650),
        Book("Dracefu","Idarta",R.drawable.japanisoap,false,"",0,0,"",170,12,675),
        Book("Cremia","Creams",R.drawable.japanicream,false,"",0,0,"",180,13,700),
        Book("Burger","Nestle",R.drawable.burger,false,"",0,0,"",190,14,725),
        Book("Chings","Chingana",R.drawable.japaninoodles,false,"",0,0,"",200,15,750),
        Book("Gold Spa","Loreal",R.drawable.goldspa,false,"",0,0,"",150,13,560),
        Book("Perfume","Villain",R.drawable.villain, false, "", 0,0, "",120,7,500),
        Book("Wildstone Perfumes","Wildstone",R.drawable.wildstone, false, "", 0, 0, "",100,5,525),
        Book("Perfume","Chanel",R.drawable.chanel, false,"",0,0,"",110, 6,550),
        Book("Loreal Creams","Loreal",R.drawable.cream4,false, "", 0, 0, "",130,8,575),
        Book("Nykaa Creams","Nykaa",R.drawable.cream2, false , "", 0, 0,"",140,9,600),
        Book("Lifebuoy","Lifebuoy",R.drawable.soap1, false, "", 0, 0,"",150,10,625),
        Book("Hamam","Hamam",R.drawable.soap2,false,"",0,0,"",160,11,650),
        Book("Facewash","Neutrogena",R.drawable.wash1,false,"",0,0,"",170,12,675),
        Book("Beardo","Beardo",R.drawable.wash2,false,"",0,0,"",180,13,700),
        Book("Buds 2.0","Real-Me",R.drawable.pods3,false,"",0,0,"",190,14,725),
        Book("Buds 1.0","XXX",R.drawable.pods2,false,"",0,0,"",200,15,750),
        Book("Coffe","Wana",R.drawable.coffee1,false,"",0,0,"",150,13,560),
        Book("Old Monk","XXX",R.drawable.alc1, false, "", 0,0, "",120,7,500),
        Book("Macaroni","Zippy",R.drawable.edi1, false, "", 0, 0, "",100,5,525),
        Book("Kamet","Kamet",R.drawable.alc2, false,"",0,0,"",110, 6,550),
        Book("Oil","Fortune",R.drawable.edi2,false, "", 0, 0, "",130,8,575),
        Book("Moroccanoil","Moroccanoil",R.drawable.smp1, false , "", 0, 0,"",140,9,600),
        Book("Goggles","XYZ",R.drawable.goog1, false, "", 0, 0,"",150,10,625),
        Book("Pen Drives","PrintStop",R.drawable.acc1,false,"",0,0,"",160,11,650),
        Book("Memory Card","Sandisk",R.drawable.acc2,false,"",0,0,"",170,12,675),
        Book("USB","Prozis",R.drawable.acc3,false,"",0,0,"",180,13,700),
        Book("Slippers","Flite",R.drawable.slp1,false,"",0,0,"",190,14,725),
        Book("Chings","Chicks",R.drawable.srt1,false,"",0,0,"",200,15,750),
        Book("Shoes","Nike",R.drawable.shoes2,false,"",0,0,"",150,13,560),
        Book("Shoes","Nike",R.drawable.shoes3,false,"",0,0,"",150,13,560),
        Book("Shoes","Woodland",R.drawable.shoes4,false,"",0,0,"",150,13,560)




    )
    val displayList = ArrayList<Book>()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment


        val view =inflater.inflate(R.layout.fragment_fourth, container, false)
        setHasOptionsMenu(true)
        recyclerDashboard = view.findViewById(R.id.recyclerDashboard)
        layoutManager = GridLayoutManager(activity,2)
        homepage = view.findViewById(R.id.homepage)
        aboutus = view.findViewById(R.id.aboutus)
        contactus = view.findViewById(R.id.contactus)
        testimonial = view.findViewById(R.id.testimonials)
        post = view.findViewById(R.id.btnPost)
//        chat=view.findViewById(R.id.chat)
//        toolbarFragment = view.findViewById<Toolbar>(R.id.toolbar)
//        (context as Activity).setActionBar(toolbarFragment)
        displayList.clear()
        displayList.addAll(bookInfoList)

        recyclerAdapter = DashboardRecyclerAdapter(activity as Context, displayList)
        recyclerDashboard.adapter = recyclerAdapter
        recyclerDashboard.layoutManager = layoutManager

        val imageSlider: ImageSlider = view.findViewById(R.id.slider)
        val slideModels: MutableList<SlideModel> = ArrayList()
        slideModels.add(SlideModel("https://lh3.googleusercontent.com/nwRcNWHuXmH0qllg5odqLqocgHpc3MRQV4Cg_nMv4s5W_plmsI98j5P_IK35Hgz_gY-Y-DXXu9EF2q-Q9sTqUCPk9EE=w1000","Coffee"));
        slideModels.add(SlideModel("https://darudelivery.com/wp-content/uploads/2018/06/2-The-Glenlivet-Archive-Single-Malt-Scotch-Whisky-21-Years-1.jpg","Alcohol"));
        slideModels.add(SlideModel("https://cdn.nrf.com/sites/default/files/styles/crop_1027_547/public/2020-09/mens%20cosmetics.png?itok=7EAMtlgN","Cream"));
        slideModels.add(SlideModel("https://www.incrediblehealth.com/wp-content/uploads/2020/12/hush-naidoo-yo01Z-9HQAw-unsplash-1024x683.jpg","Stethoscope"));
        slideModels.add(SlideModel("https://www.totalprestigemagazine.com/wp-content/uploads/2020/03/perfume.jpg","Perfume"));
        imageSlider.setImageList(slideModels, true)

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

                    val intent = Intent(activity as Context, PostActivity::class.java)
                    startActivity(intent)
//                    post.text = editText.text.toString()
//                    sharedText.edit().putString("PostText", editText.text.toString()).apply()
//
////                    sharedCount.edit().putInt("PostCount", postCount).apply()
//                    postCount += 1
//                    val someFragment: Fragment = PostFragment()
//                    val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
//                    transaction.replace(
//                        R.id.home_search,
//                        someFragment
//                    ) // give your fragment container id in first parameter
//
//                   transaction.addToBackStack(null) // if written, this transaction will be added to backstack
//
//                    transaction.commit()
//                    Toast.makeText(activity as Context, "Post successful", Toast.LENGTH_SHORT).show()


                }
                 setNegativeButton("Cancel"){dialog, which ->
//                     post.text ="Post your requirement..."
                     Toast.makeText(activity as Context, "Post cancelled", Toast.LENGTH_SHORT).show()

                 }
                setView(dialogLayout)
                show()
            }
        }
        homepage.setOnClickListener{
            homepageURL(view)
        }
        aboutus.setOnClickListener{
            aboutusURL(view)
        }
        contactus.setOnClickListener{
            contactusURL(view)
        }
        testimonial.setOnClickListener{
            testimonialURL(view)
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
    fun homepageURL(view:View){
        val url = "https://personaldistributor.com/index.html"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
    fun aboutusURL(view: View){
        val url = "https://personaldistributor.com/about.html"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
    fun contactusURL(view: View){
        val url = "https://personaldistributor.com/HTML/contact.html"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
    fun testimonialURL(view: View){
        val url = "https://personaldistributor.com/HTML/support.html"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val prefs = (activity as Context).getSharedPreferences("action_sort", Context.MODE_PRIVATE)
        val action_sort_margin = prefs.getString("action_sort_margin", "false")
        val action_sort_recent = prefs.getString("action_sort_recent", "false")
        val action_sort_ascending_cost = prefs.getString("action_sort_ascending_cost","true")
        val action_sort_descending_demo = prefs.getString("action_sort_descending_demo", "false")
        if(displayList != null){
            if(action_sort_margin!!.equals("true", ignoreCase = true)){
                Collections.sort(displayList as MutableList<Book>, Book.Statified.marginComparator)
                recyclerAdapter.notifyDataSetChanged()
            }else if(action_sort_ascending_cost!!.equals("true", ignoreCase = true)){
                Collections.sort(displayList as MutableList<Book>, Book.Statified.costComparator)
                recyclerAdapter.notifyDataSetChanged()
            }else if(action_sort_descending_demo!!.equals("true", ignoreCase = true)){
                Collections.sort(displayList as MutableList<Book>, Book.Statified.demoComparator)
                recyclerAdapter.notifyDataSetChanged()
            }
        }
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
            Toast.makeText(activity as Context, "Search not visible",Toast.LENGTH_SHORT ).show()
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val switcher = item.itemId
        if(switcher == R.id.action_sort_recent){
            //Include dateAdded for product
            val editor = (activity as Context).getSharedPreferences("action_sort", Context.MODE_PRIVATE).edit()
            editor.putString("action_sort_margin","false")
            editor.putString("action_sort_ascending_cost", "false")
            editor.putString("action_sort_descending_demo", "false")
            editor.putString("action_sort_recent", "true")
            editor.apply()
        }else if(switcher == R.id.action_sort_margin){
            val editor = (activity as Context).getSharedPreferences("action_sort", Context.MODE_PRIVATE).edit()
            editor.putString("action_sort_margin","true")
            editor.putString("action_sort_ascending_cost", "false")
            editor.putString("action_sort_descending_demo", "false")
            editor.putString("action_sort_recent", "false")
            editor.apply()
            if(displayList != null){
                Collections.sort(displayList as MutableList<Book>, Book.Statified.marginComparator)
            }
            recyclerAdapter.notifyDataSetChanged()
            return false
        }else if(switcher == R.id.action_sort_ascending_cost){
            val editor = (activity as Context).getSharedPreferences("action_sort", Context.MODE_PRIVATE).edit()
            editor.putString("action_sort_margin","false")
            editor.putString("action_sort_ascending_cost", "true")
            editor.putString("action_sort_descending_demo", "false")
            editor.putString("action_sort_recent", "false")
            editor.apply()
            if(displayList != null){
                Collections.sort(displayList as MutableList<Book>, Book.Statified.costComparator)
            }
            recyclerAdapter.notifyDataSetChanged()
            return false
        }else if(switcher == R.id.action_sort_descending_demo){
            val editor = (activity as Context).getSharedPreferences("action_sort", Context.MODE_PRIVATE).edit()
            editor.putString("action_sort_margin","false")
            editor.putString("action_sort_ascending_cost", "false")
            editor.putString("action_sort_descending_demo", "true")
            editor.putString("action_sort_recent", "false")
            editor.apply()
            if(displayList != null){
                Collections.sort(displayList as MutableList<Book>, Book.Statified.demoComparator)
            }
            recyclerAdapter.notifyDataSetChanged()
            return false
        }
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
