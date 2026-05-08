package com.fredrickjasin.freelancer_app.data.Models


data class Job(

    val id:String = "",

    val title:String = "",

    val description:String = "",

    val budget:String = "",

    val category:String = "",

    val location:String = "",

    val clientId:String = "",

    val clientName:String = "",

    val timestamp:Long = System.currentTimeMillis()

)