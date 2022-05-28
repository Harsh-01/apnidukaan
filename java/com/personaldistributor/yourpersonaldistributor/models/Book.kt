package com.personaldistributor.yourpersonaldistributor.models

import android.os.Parcel
import android.os.Parcelable

class Book (

   val productName : String,
   val productCompany : String,
   val productImage : Int,
     var productEnquiry : Boolean,      //false
   var productLocation : String,                //0
     var productUnits : Int,           //0
     var productShops : Int,            //0
     var productTime : String,          //" " -> null
   var productMargin :Int,
   var productDemo : Int,
   var productMsp : Int
//   var prodcutDateAdded : Long
//   val productPrice : String,
//   val productRating : String,
): Parcelable{
   object Statified{
     var marginComparator: Comparator<Book> = Comparator{book1, book2 ->
       val bookOne = book1.productMargin
       val bookTwo = book2.productMargin
       bookOne.compareTo(bookTwo)
     }
     var costComparator: Comparator<Book> = Comparator{book1, book2 ->
       val bookOne = book1.productMsp
       val bookTwo = book2.productMsp
       bookOne.compareTo(bookTwo)
     }
     var demoComparator: Comparator<Book> = Comparator{book1, book2 ->
       val bookOne = book1.productDemo
       val bookTwo = book2.productDemo
       bookOne.compareTo(bookTwo)
     }
   }

  override fun writeToParcel(dest: Parcel?, flags: Int) {
    TODO("Not yet implemented")
  }

  override fun describeContents(): Int {
    TODO("Not yet implemented")
  }
}




