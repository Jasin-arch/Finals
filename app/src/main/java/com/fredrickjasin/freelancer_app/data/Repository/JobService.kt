package com.fredrickjasin.freelancer_app.data.Repository

import com.fredrickjasin.freelancer_app.data.Models.Job


interface JobService {

    fun saveJob(job: Job, callback:(Boolean,String)->Unit)

    fun getJobs(callback:(MutableList<Job>)->Unit)

}