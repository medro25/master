package com.witnovus.placefinder.model

class UserModel {

    var userName: String? = null
    var mobileNo: String? = null
    var emailId: String? = null
    var userId: String? = null

   constructor(){

    }

    constructor(userName:String,mobileNo:String,emailId:String,userId:String){
        this.userName = userName
        this.mobileNo = mobileNo
        this.emailId = emailId
        this.userId = userId
    }
}