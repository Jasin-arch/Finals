package com.fredrickjasin.freelancer_app.UI.Onboading

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.fredrickjasin.freelancer_app.R
import com.fredrickjasin.freelancer_app.UI.components.LottiAnimationWidget
import com.fredrickjasin.freelancer_app.UI.components.pagepadding
import com.fredrickjasin.freelancer_app.UI.navigation.Routes
import com.fredrickjasin.freelancer_app.ui.theme.Both
import com.fredrickjasin.freelancer_app.ui.theme.LogIn

@Composable
fun OnboadingScreen(navController: NavHostController, modifier: Modifier){

//    Image(
//        painter = painterResource(id = R.drawable.),
//        contentDescription = "Background",
//        contentScale = ContentScale.Crop,
//        modifier = Modifier.fillMaxSize()
//    )
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(pagepadding)
            .fillMaxSize()
    ) {


        Text(
            text = "WELCOME TO OUR SERVICES",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = LogIn
            )
        )
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedButton(
            onClick = {navController.navigate(Routes.LoginPage.name)}
        ) {
            Text(text = "LOGIN TO GET STARTED",
                style = TextStyle(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = LogIn
                )


                )
        }

    }
}