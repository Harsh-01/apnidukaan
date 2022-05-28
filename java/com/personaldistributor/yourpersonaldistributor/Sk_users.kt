package com.personaldistributor.yourpersonaldistributor

class Sk_users(){
    var email = ""
    var agent_code = ""
    var setPass = ""
    var username= ""
    var mobile = ""
    var payID = ""
    var shop_name = ""
    var address = ""
    var postal_address = ""
    var shop_size = ""
    var buisness_segment = ""
    var shop_type = ""
    var shop_inflence = ""
    var isAgent = false
    var agentName = ""
    var profilepic : String = ""
    var shop_pic : String = ""
    var aadharCardFront : String = ""
    var aadharCardBack : String = ""

    constructor(email:String, agent_code: String, setPass: String, username: String ,mobile: String, payID: String, shop_name: String, address:String,
                postal_address:String, shop_size:String, buisness_segment:String, shop_type:String, shop_influence:String, isAgent:Boolean, agentName:String,
                profilepic: String, shop_pic:String, aadharCardFront:String,
                aadharCardBack:String) : this() {
        this.email = email
        this.agent_code = agent_code
        this.setPass = setPass
        this.username = username
        this.mobile = mobile
        this.payID = payID
        this.shop_name = shop_name
        this.address = address
        this.postal_address =postal_address
        this.shop_size = shop_size
        this.buisness_segment = buisness_segment
        this.shop_type = shop_type
        this.shop_inflence = shop_influence
        this.isAgent = isAgent
        this.agentName = agentName
        this.profilepic = profilepic
        this.shop_pic = shop_pic
        this.aadharCardFront = aadharCardFront
        this.aadharCardBack = aadharCardBack
    }
}