package com.fredrickjasin.freelancer_app.UI.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.fredrickjasin.freelancer_app.UI.Screens.Dashboard.DashboardScreen
import com.fredrickjasin.freelancer_app.UI.Screens.Settings.SettingsScreen
import com.fredrickjasin.freelancer_app.UI.Screens.Profile.ProfileScreen
import com.fredrickjasin.freelancer_app.UI.Users.ClientProfileScreen
import com.fredrickjasin.freelancer_app.UI.Users.FreelancerProfileScreen
import com.fredrickjasin.freelancer_app.viewmodel.FreelancersViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier) {

    val currentUser = FirebaseAuth.getInstance().currentUser
    // If user is already logged in, skip onboarding and login
    val startDest = if (currentUser != null) Routes.ChoosePage.name else Routes.OnboadingPage.name

    NavHost(
        navController = navController,
        startDestination = startDest
    ) {
        // --- AUTHENTICATION FLOW ---
        composable(Routes.OnboadingPage.name) {
            OnboadingScreen(navController, modifier)
        }
        composable(Routes.LoginPage.name) {
            LoginScreen(navController, modifier)
        }
        composable(Routes.SignUpPage.name) {
            RegistrationScreen(navController, modifier)
        }
        composable(Routes.ForgotPasswordPage.name) {
            ForgotPasswordScreen(navController, modifier)
        }

        // --- SELECTION FLOW ---
        composable(Routes.ChoosePage.name) { ChooseScreen(navController, modifier)
        }

        // --- FREELANCER PROFILE FLOW ---
        composable(
            route = "${Routes.FreelancersPage.name}/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            val freelancerViewModel: FreelancersViewModel = viewModel()

            FreelancerProfileScreen(
                navController = navController,
                userId = userId,
                viewModel = freelancerViewModel,
                modifier = modifier
            )
        }

        // --- CLIENT PROFILE FLOW ---
        // --- CLIENT PROFILE FLOW ---
        // AppNavigation.kt
        composable(
            route = "${Routes.ClientsPage.name}/{userId}", // Note the /{userId}
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            ClientProfileScreen(
                navController = navController,
                userId = userId, // Pass it to the screen
                modifier = modifier
            )
        }
        // --- MAIN APP CONTENT ---
        composable(Routes.HomePage.name) {
            HomeScreen(modifier, navController)
        }
        composable(Routes.MappickPage.name) {
            MapPickerScreen(modifier, navController)
        }
        composable(Routes.MapPage.name) {
            ProfileScreen(navController)
        }
        composable(Routes.DashboardPage.name) {
            DashboardScreen(navController)
        }
        composable(Routes.SettingsPage.name) {
            SettingsScreen(navController)
        }
    }
}