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
import com.fredrickjasin.freelancer_app.UI.Screens.Dashboard.DashboardScreen
import com.fredrickjasin.freelancer_app.UI.Screens.ForgotPassword.ForgotPasswordScreen
import com.fredrickjasin.freelancer_app.UI.Screens.Jobs.AddJobScreen
import com.fredrickjasin.freelancer_app.UI.Screens.Jobs.HomeJobsScreen
import com.fredrickjasin.freelancer_app.UI.Screens.Login.LoginScreen
import com.fredrickjasin.freelancer_app.UI.Screens.Profile.ProfileScreen
import com.fredrickjasin.freelancer_app.UI.Screens.Settings.SettingsScreen
import com.fredrickjasin.freelancer_app.UI.Screens.SignUp.RegistrationScreen
import com.fredrickjasin.freelancer_app.UI.Users.ClientProfileScreen
import com.fredrickjasin.freelancer_app.UI.Users.FreelancerProfileScreen
import com.fredrickjasin.freelancer_app.UI.Users.FrelancersViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier
) {

    NavHost(
        navController = navController,
        startDestination = Routes.LoginPage.name
    ) {


        composable( Routes.OnboadingPage.name) { OnboadingScreen( navController ,  modifier )}
        composable( Routes.LoginPage.name) { LoginScreen(navController, modifier  ) }
        composable( Routes.SignUpPage.name ) { RegistrationScreen( navController,  modifier  ) }
        composable( Routes.ForgotPasswordPage.name   ) { ForgotPasswordScreen( navController, modifier )}
        composable( Routes.ChoosePage.name ) { ChooseScreen(navController, modifier ) }
        composable(Routes.ClientsPage.name ) { ClientProfileScreen(navController ,modifier ) }
        composable( Routes.HomeJobPage.name ) { HomeJobsScreen( modifier, navController ) }
        composable( Routes.AddJobPage.name ) { AddJobScreen(modifier, navController )       }
        composable(route = "${Routes.FreelancersPage.name}/{userId}",arguments = listOf(navArgument("userId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val userId =
                backStackEntry.arguments
                    ?.getString("userId") ?: ""

            val freelancerViewModel:
                    FrelancersViewModel = viewModel()

            FreelancerProfileScreen(
                navController = navController,
                userId = userId,
                viewModel = freelancerViewModel,
                modifier = modifier
            )

        }


        composable(
            Routes.HomePage.name
        ) {

            HomeScreen(
                modifier,
                navController
            )

        }


        composable(
            Routes.MappickPage.name
        ) {

            MapPickerScreen(
                modifier,
                navController
            )

        }

        composable(
            Routes.MapPage.name
        ) {

            ProfileScreen(
                navController
            )

        }


        composable(
            Routes.DashboardPage.name
        ) {

            DashboardScreen(
                navController
            )

        }


        composable(
            Routes.SettingsPage.name
        ) {

            SettingsScreen(
                navController
            )

        }

    }

}