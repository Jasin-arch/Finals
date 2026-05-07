package com.fredrickjasin.freelancer_app.UI.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.fredrickjasin.freelancer_app.UI.HomeScreen
import com.fredrickjasin.freelancer_app.UI.Onboading.OnboadingScreen
import com.fredrickjasin.freelancer_app.UI.Screens.Choose.ChooseScreen
import com.fredrickjasin.freelancer_app.UI.Screens.Choose.MapPickerScreen
import com.fredrickjasin.freelancer_app.UI.Screens.ForgotPassword.ForgotPasswordScreen
import com.fredrickjasin.freelancer_app.UI.Screens.Login.LoginScreen
import com.fredrickjasin.freelancer_app.UI.Screens.SignUp.RegistrationScreen
import com.fredrickjasin.freelancer_app.UI.Screens.ratings.ReviewsScreen
import com.fredrickjasin.freelancer_app.UI.Screens.ratings.ReviewsViewModel
import com.fredrickjasin.freelancer_app.UI.Users.ClientProfileScreen
import com.fredrickjasin.freelancer_app.UI.Users.FreelancerProfileScreen
import com.fredrickjasin.freelancer_app.data.Repository.ReviewsRepository

//fun AppNavigation(navController: NavHostController, modifier: Modifier) {
//    NavHost(
//        navController = navController,
//        startDestination = Routes.ClientsPage.name
//    ){
//        composable(Routes.OnboadingPage.name
//        ) { OnboadingScreen(navController, modifier) }
//        composable(Routes.LoginPage.name) { LoginScreen(navController, modifier) }
//        composable(Routes.SignUpPage.name) { RegistrationScreen(navController, modifier) }
//        composable(Routes.ForgotPasswordPage.name) { ForgotPasswordScreen(navController, modifier) }
//        composable(Routes.ChoosePage.name) { ChooseScreen(navController, modifier) }
//        composable(Routes.FreelancersPage.name) { FreelancerProfileScreen(navController) }
//        composable(Routes.MappickPage.name) { MapPickerScreen(modifier, navController) }
//        composable(Routes.ClientsPage.name) { ClientProfileScreen(modifier, navController) }

        // Reviews Screen


@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier ){
    NavHost(
        navController = navController,
        startDestination = Routes.LoginPage.name
    ){
        composable (Routes.OnboadingPage.name) { OnboadingScreen( navController, modifier)}
        composable (Routes.LoginPage.name){ LoginScreen(navController, modifier ) }
        composable (Routes.SignUpPage.name){ RegistrationScreen(navController, modifier ) }
        composable (Routes.ForgotPasswordPage.name){ ForgotPasswordScreen(navController, modifier) }
        composable (Routes.ChoosePage.name){ ChooseScreen(navController, modifier) }
        composable (Routes.FreelancersPage.name){ FreelancerProfileScreen( navController) }
        composable (Routes.MappickPage.name){ MapPickerScreen(modifier, navController) }
        composable (Routes.ClientsPage.name){ ClientProfileScreen(modifier, navController) }
        composable (Routes.HomePage.name){ HomeScreen(navController, modifier ) }









    }
}