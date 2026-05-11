package com.fredrickjasin.freelancer_app.UI.Screens.Payments

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.fredrickjasin.freelancer_app.data.Models.Transaction
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: PaymentViewModel = viewModel()
) {
    val context = LocalContext.current
    val wallet by viewModel.wallet.collectAsState()
    val transactions by viewModel.transactions.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var showDepositDialog by remember { mutableStateOf(false) }
    var showWithdrawDialog by remember { mutableStateOf(false) }
    var amountText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Wallet", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF6A11CB))
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF5F7FB))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                    .background(
                        Brush.verticalGradient(
                            listOf(Color(0xFF6A11CB), Color(0xFF2575FC))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Total Balance", color = Color.White.copy(alpha = 0.8f), fontSize = 16.sp)
                    Text(
                        "Ksh ${String.format("%.2f", wallet.balance)}",
                        color = Color.White,
                        fontSize = 42.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }

            // Quick Actions
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                PaymentActionItem(Icons.Default.Add, "Deposit", Color(0xFF4CAF50)) {
                    showDepositDialog = true
                }
                PaymentActionItem(Icons.Default.ArrowOutward, "Withdraw", Color(0xFFFF9800)) {
                    showWithdrawDialog = true
                }
                PaymentActionItem(Icons.Default.History, "Refresh", Color(0xFF2196F3)) {
                    viewModel.loadPaymentData()
                }
            }

            Text(
                "Transaction History",
                modifier = Modifier.padding(16.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black
            )

            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFF6A11CB))
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(transactions) { transaction ->
                        TransactionItem(transaction)
                    }
                }
            }
        }
    }

    // --- DEPOSIT DIALOG (Launches M-Pesa STK) ---
    if (showDepositDialog) {
        AlertDialog(
            onDismissRequest = { showDepositDialog = false },
            title = { Text("Deposit via Mobile Money") },
            text = {
                Column {
                    Text(
                        "Confirming will open your SIM ToolKit to complete your payment.",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    OutlinedTextField(
                        value = amountText,
                        onValueChange = { amountText = it },
                        label = { Text("Amount (Ksh)") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val amount = amountText.toDoubleOrNull()
                        if (amount != null && amount > 0) {
                            // 1. Process standard viewmodel logic
                            viewModel.deposit(amount)

                            // 2. Launch SIM ToolKit (STK Intent)
                            try {
                                val simToolKitLaunchIntent = context.packageManager
                                    .getLaunchIntentForPackage("com.android.stk")

                                if (simToolKitLaunchIntent != null) {
                                    context.startActivity(simToolKitLaunchIntent)
                                    Toast.makeText(context, "Opening SIM ToolKit...", Toast.LENGTH_LONG).show()
                                } else {
                                    // Fallback: If direct package launch is restricted/missing, dial M-Pesa USSD
                                    val ussdIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "*334%23"))
                                    context.startActivity(ussdIntent)
                                    Toast.makeText(context, "STK App not found. Dialing USSD code...", Toast.LENGTH_LONG).show()
                                }
                            } catch (e: Exception) {
                                Toast.makeText(context, "Error opening payment gateway: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                            }

                            showDepositDialog = false
                            amountText = ""
                        } else {
                            Toast.makeText(context, "Please enter a valid amount", Toast.LENGTH_SHORT).show()
                        }
                    }
                ) { Text("Open STK") }
            },
            dismissButton = {
                TextButton(onClick = { showDepositDialog = false }) { Text("Cancel") }
            }
        )
    }

    // --- WITHDRAW DIALOG (Launches SMS or Direct Dialer) ---
    if (showWithdrawDialog) {
        AlertDialog(
            onDismissRequest = { showWithdrawDialog = false },
            title = { Text("Withdraw Funds") },
            text = {
                Column {
                    Text(
                        "An SMS notification with transfer instructions will be generated.",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    OutlinedTextField(
                        value = amountText,
                        onValueChange = { amountText = it },
                        label = { Text("Amount (Ksh)") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val amount = amountText.toDoubleOrNull()
                        if (amount != null && amount > 0) {
                            // 1. Process standard viewmodel logic
                            viewModel.withdraw(amount)

                            // 2. Open SMS Intent to confirm withdrawal request
                            try {
                                val uri = Uri.parse("smsto:07456789") // Replace with your company/agent support number
                                val smsIntent = Intent(Intent.ACTION_SENDTO, uri).apply {
                                    putExtra("sms_body", "Withdrawal Request: Ksh $amount from SkillLink Africa Wallet.")
                                }
                                context.startActivity(smsIntent)
                            } catch (e: Exception) {
                                Toast.makeText(context, "Unable to send SMS.", Toast.LENGTH_SHORT).show()
                            }

                            showWithdrawDialog = false
                            amountText = ""
                        } else {
                            Toast.makeText(context, "Please enter a valid amount", Toast.LENGTH_SHORT).show()
                        }
                    }
                ) { Text("Confirm & SMS") }
            },
            dismissButton = {
                TextButton(onClick = { showWithdrawDialog = false }) { Text("Cancel") }
            }
        )
    }
}

@Composable
fun PaymentActionItem(icon: ImageVector, label: String, color: Color, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .size(56.dp)
                .background(color.copy(alpha = 0.1f), CircleShape)
        ) {
            Icon(icon, contentDescription = label, tint = color)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(label, fontSize = 12.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun TransactionItem(transaction: Transaction) {
    val isIncoming = transaction.type == "deposit" || transaction.toUserId.isNotEmpty() && transaction.fromUserId != FirebaseAuth.getInstance().currentUser?.uid

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        if (isIncoming) Color(0xFFE8F5E9) else Color(0xFFFFEBEE),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    if (isIncoming) Icons.Default.CallReceived else Icons.Default.CallMade,
                    contentDescription = null,
                    tint = if (isIncoming) Color(0xFF4CAF50) else Color(0xFFF44336)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(transaction.description, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(
                    SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault()).format(transaction.timestamp.toDate()),
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
            Text(
                "${if (isIncoming) "+" else "-"}$${String.format("%.2f", transaction.amount)}",
                fontWeight = FontWeight.ExtraBold,
                color = if (isIncoming) Color(0xFF4CAF50) else Color(0xFFF44336)
            )
        }
    }
}