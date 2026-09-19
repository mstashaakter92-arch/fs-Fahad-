package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.ElectricViolet
import com.example.viewmodel.ScreenDestination

@Composable
fun BottomNavBar(
    currentScreen: ScreenDestination,
    onNavigate: (ScreenDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp,
        shadowElevation = 12.dp,
        modifier = modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp, horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            // Home
            NavItem(
                title = "Home",
                titleBangla = "হোম",
                icon = Icons.Default.Home,
                selected = currentScreen == ScreenDestination.HOME,
                onClick = { onNavigate(ScreenDestination.HOME) },
                testTag = "nav_home"
            )

            // My Orders
            NavItem(
                title = "My Orders",
                titleBangla = "মাই অর্ডার",
                icon = Icons.Default.ReceiptLong,
                selected = currentScreen == ScreenDestination.MY_ORDERS,
                onClick = { onNavigate(ScreenDestination.MY_ORDERS) },
                testTag = "nav_orders"
            )

            // Centered Add Money Action Button
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .offset(y = (-10).dp)
                    .clickable { onNavigate(ScreenDestination.ADD_MONEY) }
                    .testTag("nav_add_money")
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(52.dp)
                        .shadow(8.dp, CircleShape)
                        .clip(CircleShape)
                        .background(if (currentScreen == ScreenDestination.ADD_MONEY) Color(0xFF0284C7) else Color.Black)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Money",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Add Money",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (currentScreen == ScreenDestination.ADD_MONEY) ElectricViolet else MaterialTheme.colorScheme.onSurface
                )
            }

            // My Codes
            NavItem(
                title = "My Codes",
                titleBangla = "মাই কোড",
                icon = Icons.Default.QrCode,
                selected = currentScreen == ScreenDestination.MY_CODES,
                onClick = { onNavigate(ScreenDestination.MY_CODES) },
                testTag = "nav_codes"
            )

            // My Account
            NavItem(
                title = "My Account",
                titleBangla = "অ্যাকাউন্ট",
                icon = Icons.Default.Person,
                selected = currentScreen == ScreenDestination.ACCOUNT,
                onClick = { onNavigate(ScreenDestination.ACCOUNT) },
                testTag = "nav_account"
            )
        }
    }
}

@Composable
private fun NavItem(
    title: String,
    titleBangla: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    selected: Boolean,
    onClick: () -> Unit,
    testTag: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 4.dp)
            .testTag(testTag)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = if (selected) ElectricViolet else MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = title,
            fontSize = 11.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            color = if (selected) ElectricViolet else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
