package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.*
import com.example.ui.components.AnnouncementTicker
import com.example.ui.theme.*
import com.example.viewmodel.ScreenDestination
import com.example.viewmodel.TopUpViewModel

@Composable
fun HomeScreen(
    viewModel: TopUpViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val recentOrders by viewModel.liveRecentOrders.collectAsState()

    // Pulse animation for live order indicator
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // Notice Ticker
        item {
            AnnouncementTicker(modifier = Modifier.testTag("announcement_ticker"))
        }

        // Hero Promotional Banner
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 10.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .clickable { viewModel.openTopUpWithPackage() }
                    .testTag("hero_promo_banner")
            ) {
                Image(
                    painter = painterResource(id = R.drawable.banner_promo),
                    contentDescription = "Promo Banner",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                )

                // Dark gradient overlay with text
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color(0xCC0A0E17))
                            )
                        )
                        .padding(12.dp),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(ElectricViolet)
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = "AI ডেলিভারি",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "১ সেকেন্ডে ডায়মন্ড!",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = BrightGold
                            )
                        }
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = "কিভাবে UNIPIN দিয়ে ১ সেকেন্ডে ডায়মন্ড কিনবেন?",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }

        // Community Buttons: Support Telegram & Join Group (as seen in video)
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Support Telegram
                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/fftopbd"))
                        try { context.startActivity(intent) } catch (e: Exception) {}
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                        .testTag("support_telegram_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Support Telegram",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "SUPPORT Telegram",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                // Join Group
                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/fftopbd_group"))
                        try { context.startActivity(intent) } catch (e: Exception) {}
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                        .testTag("join_group_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.Groups,
                        contentDescription = "Join Group",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "JOIN Group",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }

        // SECTION 1: SPECIAL DISCOUNT OFFER (Horizontal Cards)
        item {
            Column(modifier = Modifier.padding(top = 16.dp)) {
                SectionTitleHeader(title = "SPECIAL DISCOUNT OFFER")

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(CatalogData.SPECIAL_OFFERS) { offer ->
                        SpecialOfferCard(
                            offer = offer,
                            onClick = { viewModel.openTopUpWithPackage(offer) }
                        )
                    }
                }
            }
        }

        // SECTION 2: TOPUP (Grid of cards)
        item {
            Column(modifier = Modifier.padding(top = 16.dp)) {
                SectionTitleHeader(title = "TOPUP")
            }
        }

        // 3-Column Grid for TOPUP Categories
        val topUpChunks = CatalogData.TOPUP_CATEGORIES.chunked(3)
        items(topUpChunks) { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 5.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (item in rowItems) {
                    Box(modifier = Modifier.weight(1f)) {
                        CategoryGridCard(
                            item = item,
                            onClick = {
                                when (item.id) {
                                    "c_spin" -> viewModel.navigateTo(ScreenDestination.FREE_SPIN)
                                    "c_giveaway" -> {
                                        viewModel.selectPackage(CatalogData.FF_PACKAGES[2]) // 25 diamond
                                        viewModel.navigateTo(ScreenDestination.TOPUP_DETAIL)
                                    }
                                    else -> viewModel.openTopUpWithPackage()
                                }
                            }
                        )
                    }
                }
                // Fill remaining empty columns if row is not full
                if (rowItems.size < 3) {
                    for (i in 0 until (3 - rowItems.size)) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }

        // SECTION 3: SOCIAL MEDIA AND SUBSCRIPTIONS SERVICES
        item {
            Column(modifier = Modifier.padding(top = 20.dp)) {
                SectionTitleHeader(title = "SOCIAL MEDIA AND SUBSCRIPTIONS SERVICES")
            }
        }

        val subChunks = CatalogData.SUBSCRIPTION_SERVICES.chunked(3)
        items(subChunks) { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 5.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (item in rowItems) {
                    Box(modifier = Modifier.weight(1f)) {
                        CategoryGridCard(
                            item = item,
                            onClick = {
                                // Direct order or package selection
                                viewModel.selectPackage(
                                    TopUpPackage(
                                        id = item.id,
                                        title = item.titleEnglish,
                                        subtitle = item.titleBangla,
                                        priceBdt = when {
                                            item.id.contains("netflix") -> 280
                                            item.id.contains("canva") -> 120
                                            item.id.contains("yt") -> 150
                                            item.id.contains("chatgpt") -> 650
                                            item.id.contains("telegram") -> 450
                                            else -> 99
                                        },
                                        tag = item.badge
                                    )
                                )
                                viewModel.navigateTo(ScreenDestination.TOPUP_DETAIL)
                            }
                        )
                    }
                }
                if (rowItems.size < 3) {
                    for (i in 0 until (3 - rowItems.size)) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }

        // SECTION 4: Recent Orders (Live ticker)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, start = 14.dp, end = 14.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(34.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFFFCE7F3))
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Security",
                                tint = Color(0xFFDB2777),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Recent Orders",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(SuccessGreen.copy(alpha = pulseAlpha))
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Live",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SuccessGreen
                                )
                            }
                        }
                    }

                    // Live Refresh Button
                    IconButton(
                        onClick = { viewModel.refreshLiveFeed() },
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .testTag("refresh_live_feed")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
            }
        }

        // Live Orders List items
        items(recentOrders) { orderItem ->
            LiveOrderItemRow(orderItem = orderItem)
        }
    }
}

@Composable
fun SectionTitleHeader(title: String) {
    Text(
        text = title,
        fontSize = 15.sp,
        fontWeight = FontWeight.Black,
        color = Color(0xFF1E293B),
        letterSpacing = 0.5.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 6.dp)
    )
}

@Composable
fun SpecialOfferCard(
    offer: TopUpPackage,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .width(135.dp)
            .clickable(onClick = onClick)
            .testTag("offer_${offer.id}")
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Gradient Header with AI Delivery Badge
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(84.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color(0xFF6B21A8), Color(0xFF9333EA))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                // AI Delivery Tag
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 4.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0x66000000))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "AI ডেলিভারি",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Icon(
                    imageVector = Icons.Default.Diamond,
                    contentDescription = "Diamond",
                    tint = DiamondCyan,
                    modifier = Modifier.size(36.dp)
                )
            }

            // Title & Subtitle
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(6.dp)
            ) {
                Text(
                    text = offer.title,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${offer.priceBdt} ৳",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    color = ElectricViolet
                )
            }
        }
    }
}

@Composable
fun CategoryGridCard(
    item: ServiceCategoryItem,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .clickable(onClick = onClick)
            .testTag("cat_${item.id}")
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            // Visual Card Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .background(
                        Brush.linearGradient(
                            colors = when {
                                item.id.contains("netflix") -> listOf(Color(0xFFE50914), Color(0xFF831010))
                                item.id.contains("canva") -> listOf(Color(0xFF00C4CC), Color(0xFF7D2AE8))
                                item.id.contains("youtube") -> listOf(Color(0xFFFF0000), Color(0xFF990000))
                                item.id.contains("chatgpt") -> listOf(Color(0xFF10A37F), Color(0xFF0D5F4A))
                                item.id.contains("telegram") -> listOf(Color(0xFF229ED9), Color(0xFF0E70A0))
                                item.id.contains("spin") -> listOf(Color(0xFFF59E0B), Color(0xFFD97706))
                                else -> listOf(Color(0xFF581C87), Color(0xFF7E22CE))
                            }
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                // Top Badge
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 4.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0x66000000))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = item.badge,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                // Center Icon
                val iconVector = when (item.iconType) {
                    "giveaway" -> Icons.Default.CardGiftcard
                    "ff_uid" -> Icons.Default.SportsEsports
                    "weekly", "weekly_lite" -> Icons.Default.WorkspacePremium
                    "monthly", "membership" -> Icons.Default.Star
                    "levelup" -> Icons.Default.TrendingUp
                    "globe" -> Icons.Default.Language
                    "heart" -> Icons.Default.Favorite
                    "voucher" -> Icons.Default.ConfirmationNumber
                    "shell" -> Icons.Default.Token
                    "spin" -> Icons.Default.Casino
                    "netflix" -> Icons.Default.Tv
                    "canva" -> Icons.Default.Brush
                    "youtube" -> Icons.Default.PlayCircle
                    "chatgpt" -> Icons.Default.SmartToy
                    "telegram" -> Icons.Default.Send
                    "vpn" -> Icons.Default.VpnKey
                    else -> Icons.Default.ThumbUp
                }

                Icon(
                    imageVector = iconVector,
                    contentDescription = item.titleEnglish,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }

            // Labels
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 4.dp, vertical = 2.dp)
            ) {
                Text(
                    text = item.titleBangla,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = item.titleEnglish,
                    fontSize = 9.sp,
                    color = Color(0xFF64748B),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun LiveOrderItemRow(orderItem: RecentOrderFeedItem) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFF1F5F9))
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = ElectricViolet,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Text(
                        text = orderItem.customerName,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A)
                    )
                    Text(
                        text = orderItem.itemName,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF64748B)
                    )
                }
            }

            // Done Status pill (matching the green '✓ Done' in the video)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFDCFCE7))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Done",
                    tint = SuccessGreen,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Done",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = SuccessGreen
                )
            }
        }
    }
}
