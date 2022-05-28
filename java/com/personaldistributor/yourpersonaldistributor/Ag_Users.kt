package com.personaldistributor.yourpersonaldistributor

class Ag_Users{
    var email = ""
    var agent_code = ""
    var setPass = ""
    var username= ""
    var mobile = ""
    var user_age = ""
    var gender = ""
    var address = ""
    var isAgent = false
    var profilepic : String = ""
    var panCard : String = ""
    var aadharCardFront : String = ""
    var aadharCardBack : String = ""
    var contract : String  = ""
    var check : String = ""
    var todayShops : String = ""
    var todayDistance : String = ""
    var todaysIncome : String = ""
    var todaysTime : String = ""
    var todaysBonus : String = ""
    var weeklyShops : String = ""
    var weeklyDistance : String = ""
    var weeklyIncome : String = ""
    var weeklyTime : String = ""
    var weeklyBonus : String = ""
    var monthlyShops : String = ""
    var monthlyDistance : String = ""
    var monthlyIncome : String = ""
    var monthlyTime : String = ""
    var monthlyBonus : String = ""
    var totalShops : String = ""
    var totalDistance : String = ""
    var totalIncome : String = ""
    var totalTime : String = ""
    var totalBonus : String = ""



    constructor(){

    }


    constructor(email:String,agent_code: String, setPass: String, username: String ,mobile: String, user_age: String, gender: String, address:String,
                isAgent:Boolean, profilepic: String, panCard:String, aadharCardFront:String,
                    aadharCardBack:String, contract:String, check:String, todayShops :String, todayDistance:String,todaysIncome :String,todaysTime:String,todaysBonus : String,weeklyShops : String, weeklyDistance : String,weeklyIncome : String,weeklyTime : String,
                weeklyBonus:String,monthlyShops : String,monthlyDistance : String,monthlyIncome : String, monthlyTime : String,monthlyBonus : String,
                totalShops : String,totalDistance : String, totalIncome : String,totalTime : String,totalBonus : String) : this(){
        this.email = email
        this.agent_code = agent_code
        this.setPass = setPass
        this.username = username
        this.mobile = mobile
        this.user_age = user_age
        this.gender = gender
        this.address = address
        this.isAgent = isAgent
        this.profilepic = profilepic
        this.panCard = panCard
        this.aadharCardFront = aadharCardFront
        this.aadharCardBack = aadharCardBack
        this.contract = contract
        this.check = check
        this.todayShops =todayShops
        this.todayDistance = todayDistance
        this.todaysIncome = todaysIncome
        this.todaysTime= todaysTime
        this.todaysBonus = todaysBonus
        this.weeklyShops = weeklyShops
        this.weeklyDistance = weeklyDistance
        this.weeklyIncome = weeklyIncome
        this.weeklyBonus = weeklyBonus
        this.weeklyTime = weeklyTime
        this.monthlyShops =monthlyShops
        this.monthlyDistance = monthlyDistance
        this.monthlyIncome = monthlyIncome
        this.monthlyBonus = monthlyBonus
        this.monthlyTime = monthlyTime
        this.totalShops = totalShops
        this.totalDistance = totalDistance
        this.totalIncome = totalIncome
        this.totalBonus= totalBonus
        this.totalTime = totalTime
    }

//    public fun getprofilepic() : String{
//        return profilepic
//    }
//    public fun setprofilepic(photo: String){
//        profilepic = photo
//    }
//    public fun getusername() : String{
//        return username
//    }
//    public fun setusername(name: String){
//        username = name
//    }
//    public fun getaddress() : String{
//        return address
//    }
//    public fun setaddress(address1: String){
//        address = address1
//    }
}
