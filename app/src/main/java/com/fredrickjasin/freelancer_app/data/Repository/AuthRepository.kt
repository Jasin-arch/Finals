package com.fredrickjasin.freelancer_app.data.Repository
import com.fredrickjasin.freelancer_app.data.Models.UserModel
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

class AuthRepository: AuthService {
    val supabase = createSupabaseClient(
        supabaseUrl = "https://gysmleptpcahhxoviqpo.supabase.co",
        supabaseKey = "sb_publishable_qn2rUEsgR7CAaQFNl1uBYQ_oVxQwGxV"
    )  {
        install(Postgrest)
        install(Auth)
    }


    override suspend fun registerUser(userDetails: UserModel)  {
        supabase.auth.signUpWith(Email) {
            email = userDetails.Email
            password = userDetails.Password
        }
    }

    override suspend fun loginUser(userDetails: UserModel)  {
        val user = supabase.auth.signInWith(Email) {
            email = userDetails.Email
            password = userDetails.Password
        }
    }

    override suspend fun resetPassword(email: String) {
        supabase.auth.resetPasswordForEmail(email = email)
    }

    override suspend fun getUserProfile(user: UserModel) {
//        TODO("Not yet implemented")
    }

    override suspend fun logoutUser() {
        supabase.auth.signOut()
    }

}