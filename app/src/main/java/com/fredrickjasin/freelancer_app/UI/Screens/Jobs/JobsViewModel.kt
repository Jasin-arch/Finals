package com.fredrickjasin.freelancer_app.UI.Screens.Jobs
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fredrickjasin.freelancer_app.data.Models.Job
import com.fredrickjasin.freelancer_app.data.Models.Notification
import com.fredrickjasin.freelancer_app.data.Repository.JobsRepository
import com.fredrickjasin.freelancer_app.data.Repository.NotificationRepository
import com.fredrickjasin.freelancer_app.data.Repository.UserRepository
import kotlinx.coroutines.launch

class JobsViewModel(
    private val repository: JobsRepository = JobsRepository(),
    private val notificationRepository: NotificationRepository = NotificationRepository.getInstance(),
    private val userRepository: UserRepository = UserRepository()
): ViewModel() {

    var jobsList by mutableStateOf<List<Job>>(emptyList())
        private set

    var userRole by mutableStateOf<String?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    init {
        fetchUserRole()
    }

    fun fetchUserRole() {
        viewModelScope.launch {
            userRole = userRepository.getCurrentUserRole()
        }
    }

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

                // Notify freelancers
                notificationRepository.sendNotification(
                    Notification(
                        title = "New Job: ${job.title}",
                        message = "A new job in ${job.category} is available. Budget: ${job.budget}"
                    )
                )

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