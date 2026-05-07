package com.fredrickjasin.freelancer_app.UI.Onboading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.fredrickjasin.freelancer_app.UI.components.LottiAnimationWidget
import com.fredrickjasin.freelancer_app.UI.components.pagepadding
import com.fredrickjasin.freelancer_app.UI.navigation.Routes
import com.fredrickjasin.freelancer_app.ui.theme.Both
import com.fredrickjasin.freelancer_app.ui.theme.LogIn
import com.fredrickjasin.freelancer_app.ui.theme.onboading

@Composable
fun OnboadingScreen(
    navController: NavHostController,
    modifier: Modifier)
{

    // Gradient background using ChooseScreen colors
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF6A11CB), Color(0xFF2575FC))
                )
            )
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(pagepadding)
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .shadow(8.dp, RoundedCornerShape(20.dp)),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f))
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = "WELCOME TO OUR SERVICES",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = LogIn // using the ChooseScreen main color
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Connect with top freelancers and get your work done effortlessly.",
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = Color.Gray
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp),
                        softWrap = true
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Login Button using gradient colors
            Button(
                onClick = { navController.navigate(Routes.LoginPage.name) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 32.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A11CB))
            ) {
                Text(
                    text = "LOGIN TO GET STARTED",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sign Up Button with gradient border
            OutlinedButton(
                onClick = { navController.navigate(Routes.SignUpPage.name) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 32.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    width = 2.dp,
                )
            ) {
                Text(
                    text = "SIGN UP",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }
        }
    }
}