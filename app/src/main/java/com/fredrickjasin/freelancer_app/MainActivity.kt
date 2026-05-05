package com.fredrickjasin.freelancer_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.fredrickjasin.freelancer_app.UI.Screens.Login.LoginScreen
import com.fredrickjasin.freelancer_app.UI.Screens.SignUp.RegistrationScreen
import com.fredrickjasin.freelancer_app.UI.navigation.AppNavigation
import com.fredrickjasin.freelancer_app.ui.theme.FreeLancer_AppTheme
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest


val supabase = createSupabaseClient(
    supabaseUrl = "https://gysmleptpcahhxoviqpo.supabase.co",
    supabaseKey = "sb_publishable_qn2rUEsgR7CAaQFNl1uBYQ_oVxQwGxV"
    ) {
    install(Postgrest)
    }


    class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        enableEdgeToEdge()
        setContent {
            FreeLancer_AppTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(navController, modifier = Modifier.padding(innerPadding))

                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FreeLancer_AppTheme {
    }
}