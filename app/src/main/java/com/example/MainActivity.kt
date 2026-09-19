package com.example

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.BottomNavBar
import com.example.ui.components.PromoNoticeDialog
import com.example.ui.components.SupportDialog
import com.example.ui.components.TopAppBarHeader
import com.example.ui.screens.*
import com.example.ui.theme.DarkNavy900
import com.example.ui.theme.ElectricViolet
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.ScreenDestination
import com.example.viewmodel.TopUpViewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        TopUpApp()
      }
    }
  }
}

@Composable
fun TopUpApp(
    viewModel: TopUpViewModel = viewModel()
) {
    val context = LocalContext.current
    val currentScreen by viewModel.currentScreen.collectAsState()
    val userProfile by viewModel.userProfile.collectAsState()
    val showNoticeDialog by viewModel.showNoticeDialog.collectAsState()
    val showSupportDialog by viewModel.showSupportDialog.collectAsState()
    val toastMessage by viewModel.toastMessage.collectAsState()

    LaunchedEffect(toastMessage) {
        toastMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.clearToast()
        }
    }

    // Telegram Offer Promo Dialog (matching the video's initial popup)
    if (showNoticeDialog) {
        PromoNoticeDialog(
            onDismiss = { viewModel.dismissNotice() },
            onTelegramJoin = {
                viewModel.dismissNotice()
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/fftopbd"))
                try { context.startActivity(intent) } catch (e: Exception) {}
            }
        )
    }

    // Support Dialog
    if (showSupportDialog) {
        SupportDialog(onDismiss = { viewModel.setSupportDialog(false) })
    }

    Scaffold(
        topBar = {
            // Show global header on Home, My Orders, Add Money, My Codes, Account
            if (currentScreen != ScreenDestination.TOPUP_DETAIL && currentScreen != ScreenDestination.FREE_SPIN) {
                TopAppBarHeader(
                    walletBalance = userProfile?.walletBalance ?: 0,
                    userName = userProfile?.name ?: "Gamer",
                    onAddMoneyClick = { viewModel.navigateTo(ScreenDestination.ADD_MONEY) },
                    onSupportClick = { viewModel.setSupportDialog(true) },
                    onProfileClick = { viewModel.navigateTo(ScreenDestination.ACCOUNT) },
                    onLogoClick = { viewModel.navigateTo(ScreenDestination.HOME) }
                )
            }
        },
        bottomBar = {
            // Show bottom navigation on main tabs
            if (currentScreen != ScreenDestination.TOPUP_DETAIL && currentScreen != ScreenDestination.FREE_SPIN) {
                BottomNavBar(
                    currentScreen = currentScreen,
                    onNavigate = { viewModel.navigateTo(it) }
                )
            }
        },
        floatingActionButton = {
            // Floating Headphone Customer Support Icon (visible on home/topup just like in video)
            if (currentScreen != ScreenDestination.FREE_SPIN) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(bottom = if (currentScreen == ScreenDestination.TOPUP_DETAIL) 70.dp else 12.dp)
                        .size(48.dp)
                        .shadow(8.dp, CircleShape)
                        .clip(CircleShape)
                        .background(Color(0xFF0F172A))
                        .clickable { viewModel.setSupportDialog(true) }
                        .testTag("floating_support_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Headphones,
                        contentDescription = "Support",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentScreen) {
                ScreenDestination.HOME -> HomeScreen(viewModel = viewModel)
                ScreenDestination.TOPUP_DETAIL -> TopUpDetailScreen(viewModel = viewModel)
                ScreenDestination.MY_ORDERS -> MyOrdersScreen(viewModel = viewModel)
                ScreenDestination.ADD_MONEY -> AddMoneyScreen(viewModel = viewModel)
                ScreenDestination.MY_CODES -> MyCodesScreen(viewModel = viewModel)
                ScreenDestination.ACCOUNT -> MyAccountScreen(viewModel = viewModel)
                ScreenDestination.FREE_SPIN -> FreeSpinScreen(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  Text(text = "Hello $name!", modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  MyApplicationTheme { Greeting("Android") }
}

