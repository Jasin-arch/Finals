package com.fredrickjasin.freelancer_app.UI.Screens.Choose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.fredrickjasin.freelancer_app.UI.components.pagepadding
import com.fredrickjasin.freelancer_app.ui.theme.LogIn
import com.fredrickjasin.freelancer_app.ui.theme.freelancerscards

@Composable
fun ChooseScreen(
    navController: NavHostController,
    modifier: Modifier,
) {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(pagepadding)
    ) {

        Text(
            text = "Welcome Please Choose your Area",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = LogIn
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        // ✅ Card 1
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .clickable {
                    // TODO: navigate to Client side
                    // navController.navigate(...)
                },
            colors = CardDefaults.cardColors(freelancerscards)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "I am a Client",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = LogIn
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // ✅ Card 2
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .clickable {
                    // TODO: navigate to Freelancer side
                    // navController.navigate(...)
                },
            colors = CardDefaults.cardColors()
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "I am a Freelancer",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = LogIn
                )
            }
        }
    }
}