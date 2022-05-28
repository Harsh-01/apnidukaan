package com.personaldistributor.yourpersonaldistributor

import android.net.Uri

class Ag_UsersUrl (){
    lateinit var profilepic : Uri
    lateinit var panCard : Uri
    lateinit var aadharCardFront : Uri
    lateinit var aadharCardBack : Uri
    lateinit var contract : Uri
    lateinit var check : Uri
    var countCode = 0

    constructor(downloadUri: Uri, code : Int) : this() {
        if(code == 0){
            this.profilepic = downloadUri
        }else if(code == 1){
            this.panCard = downloadUri
        }else if(code == 2 && countCode == 0){
            countCode = countCode + 1
            this.aadharCardFront = downloadUri
        }else if (code == 2 && countCode> 0){
            this.aadharCardBack = downloadUri
            countCode =0
        }else if(code == 3){
            this.contract = downloadUri
        }else if(code == 4){
            this.check= downloadUri
        }

    }
}