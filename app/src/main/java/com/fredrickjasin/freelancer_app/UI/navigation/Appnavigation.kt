package com.fredrickjasin.freelancer_app.UI.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fredrickjasin.freelancer_app.UI.Onboading.OnboadingScreen
import com.fredrickjasin.freelancer_app.UI.Screens.ForgotPassword.ForgotPasswordScreen
import com.fredrickjasin.freelancer_app.UI.Screens.Login.LoginScreen
import com.fredrickjasin.freelancer_app.UI.Screens.SignUp.RegistrationScreen

@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier ){
    NavHost(
        navController = navController,
        startDestination = Routes.OnboadingPage.name
    ){
        composable (Routes.OnboadingPage.name) { OnboadingScreen( navController, modifier)}
        composable (Routes.LoginPage.name){ LoginScreen(navController, modifier ) }
        composable (Routes.SignUpPage.name){ RegistrationScreen(navController, modifier ) }
        composable (Routes.ForgotPasswordPage.name){ ForgotPasswordScreen(navController, modifier) }
    }
}