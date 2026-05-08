package com.fredrickjasin.freelancer_app.UI.Screens.Jobs
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fredrickjasin.freelancer_app.data.Models.Job
import com.fredrickjasin.freelancer_app.data.Repository.JobsRepository
import kotlinx.coroutines.launch

class JobsViewModel(
    private val repository: JobsRepository = JobsRepository()
): ViewModel() {

    var jobsList by mutableStateOf<List<Job>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf("")
        private set
    fun addJob(
        job: Job,
        onComplete:(Boolean,String)->Unit
    ){

        viewModelScope.launch {

            isLoading = true

            try {

                repository.addJob(job)

                onComplete(
                    true,
                    "Job Posted Successfully"
                )

            }catch (e:Exception){

                onComplete(
                    false,
                    e.message.toString()
                )

            }

            isLoading = false

        }

    }


    fun fetchJobs(){

        viewModelScope.launch {

            isLoading = true

            try {

                jobsList =
                    repository.fetchJobs()

            }catch (e:Exception){

                errorMessage =
                    e.message.toString()

            }

            isLoading = false

        }

    }

}