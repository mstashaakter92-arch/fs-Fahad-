package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.CatalogData
import com.example.data.OrderEntity
import com.example.data.TopUpPackage
import com.example.ui.components.HowToOrderDialog
import com.example.ui.theme.*
import com.example.viewmodel.OrderPlacementState
import com.example.viewmodel.ScreenDestination
import com.example.viewmodel.TopUpViewModel

@Composable
fun TopUpDetailScreen(
    viewModel: TopUpViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val selectedPackage by viewModel.selectedPackage.collectAsState()
    val enteredUid by viewModel.enteredUid.collectAsState()
    val verifiedPlayerName by viewModel.verifiedPlayerName.collectAsState()
    val isVerifyingUid by viewModel.isVerifyingUid.collectAsState()
    val selectedPaymentMethod by viewModel.selectedPaymentMethod.collectAsState()
    val orderState by viewModel.orderState.collectAsState()
    val userProfile by viewModel.userProfile.collectAsState()
    val showHowToOrder by viewModel.showHowToOrder.collectAsState()

    var showSuccessDialog by remember { mutableStateOf<OrderEntity?>(null) }

    LaunchedEffect(orderState) {
        if (orderState is OrderPlacementState.Success) {
            showSuccessDialog = (orderState as OrderPlacementState.Success).order
            viewModel.resetOrderState()
        }
    }

    if (showHowToOrder) {
        HowToOrderDialog(onDismiss = { viewModel.setHowToOrder(false) })
    }

    showSuccessDialog?.let { order ->
        OrderSuccessDialog(
            order = order,
            onDismiss = { showSuccessDialog = null },
            onViewOrders = {
                showSuccessDialog = null
                viewModel.navigateTo(ScreenDestination.MY_ORDERS)
            }
        )
    }

    Scaffold(
        topBar = {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 2.dp,
                shadowElevation = 2.dp
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 8.dp, vertical = 10.dp)
                ) {
                    IconButton(
                        onClick = { viewModel.navigateTo(ScreenDestination.HOME) },
                        modifier = Modifier.testTag("back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                Brush.linearGradient(listOf(Color(0xFF581C87), Color(0xFF9333EA)))
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.SportsEsports,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Text(
                            text = "FF TopUp (BD)",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Bolt,
                                contentDescription = null,
                                tint = BrightGold,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = "২ সেকেন্ডে টপআপ",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = ElectricViolet
                            )
                        }
                    }
                }
            }
        },
        bottomBar = {
            // Checkout Bottom Bar
            Surface(
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp,
                shadowElevation = 10.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.navigationBars)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Column {
                        Text(
                            text = "মোট পরিশোধযোগ্য:",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "${selectedPackage?.priceBdt ?: 0} ৳",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            color = ElectricViolet
                        )
                    }

                    Button(
                        onClick = { viewModel.placeOrder() },
                        enabled = orderState !is OrderPlacementState.Processing && selectedPackage != null,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ElectricViolet,
                            disabledContainerColor = Color(0xFF94A3B8)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .height(48.dp)
                            .widthIn(min = 160.dp)
                            .testTag("confirm_order_button")
                    ) {
                        if (orderState is OrderPlacementState.Processing) {
                            CircularProgressIndicator(
                                color = Color.White,
                                strokeWidth = 2.dp,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("প্রসেসিং...", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        } else {
                            Icon(
                                imageVector = Icons.Default.FlashOn,
                                contentDescription = null,
                                tint = BrightGold,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "অর্ডার কনফার্ম করুন",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding),
            contentPadding = PaddingValues(14.dp)
        ) {
            // STEP 1: Select Recharge
            item {
                StepHeader(
                    stepNumber = "1",
                    title = "Select Recharge",
                    subtitle = "ডায়মন্ড প্যাকেজ সিলেক্ট করুন"
                )

                Spacer(modifier = Modifier.height(10.dp))
            }

            // 2-Column Grid for Recharge Packages (as seen in video)
            val packageChunks = CatalogData.FF_PACKAGES.chunked(2)
            items(packageChunks) { rowItems ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    for (pkg in rowItems) {
                        Box(modifier = Modifier.weight(1f)) {
                            PackageSelectCard(
                                pkg = pkg,
                                isSelected = selectedPackage?.id == pkg.id,
                                onClick = { viewModel.selectPackage(pkg) }
                            )
                        }
                    }
                    if (rowItems.size < 2) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }

            // How To Order Link
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 14.dp)
                        .clickable { viewModel.setHowToOrder(true) }
                        .testTag("how_to_order_link")
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayCircleOutline,
                        contentDescription = null,
                        tint = ElectricViolet,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "কিভাবে অর্ডার করবেন? ➔",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = ElectricViolet
                    )
                }
            }

            // STEP 2: Account Info
            item {
                StepHeader(
                    stepNumber = "2",
                    title = "Account Info",
                    subtitle = "আপনার ফ্রি ফায়ার আইডি তথ্য দিন"
                )

                Spacer(modifier = Modifier.height(10.dp))

                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "এখানে গেমের আইডি কোড দিন",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E293B)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // UID Input Field
                        OutlinedTextField(
                            value = enteredUid,
                            onValueChange = { viewModel.updateUid(it) },
                            placeholder = { Text("এখানে গেমের আইডি কোড দিন", fontSize = 13.sp) },
                            trailingIcon = {
                                IconButton(
                                    onClick = {
                                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                        val clip = clipboard.primaryClip
                                        if (clip != null && clip.itemCount > 0) {
                                            val text = clip.getItemAt(0).text.toString()
                                            viewModel.updateUid(text)
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ContentPaste,
                                        contentDescription = "Paste",
                                        tint = ElectricViolet
                                    )
                                }
                            },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = { viewModel.verifyPlayerName() }
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("player_uid_input")
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Name Checker Button (matching "আপনার গেম আইডির নাম চেক করুন" in video)
                        Button(
                            onClick = { viewModel.verifyPlayerName() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(44.dp)
                                .testTag("check_player_name_btn")
                        ) {
                            if (isVerifyingUid) {
                                CircularProgressIndicator(
                                    color = Color.White,
                                    strokeWidth = 2.dp,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("যাচাই করা হচ্ছে...", color = Color.White, fontSize = 13.sp)
                            } else {
                                Icon(
                                    imageVector = Icons.Default.VerifiedUser,
                                    contentDescription = null,
                                    tint = DiamondCyan,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "আপনার গেম আইডির নাম চেক করুন",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }

                        // Verified Player Banner
                        if (verifiedPlayerName.isNotBlank()) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFFF0FDF4))
                                    .border(1.dp, Color(0xFFBBF7D0), RoundedCornerShape(8.dp))
                                    .padding(horizontal = 12.dp, vertical = 8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = SuccessGreen,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = "যাচাইকৃত গেমার নাম:",
                                        fontSize = 11.sp,
                                        color = Color(0xFF166534)
                                    )
                                    Text(
                                        text = verifiedPlayerName,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF14532D)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Available Stock Badge (matching video: "Available Stock: 1361570")
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFFEFF6FF))
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Inventory2,
                                contentDescription = null,
                                tint = Color(0xFF2563EB),
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Available Stock: 13,61,570 Diamonds",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1E40AF)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            // STEP 3: Select Payment Method
            item {
                StepHeader(
                    stepNumber = "3",
                    title = "Select one option",
                    subtitle = "পেমেন্ট মাধ্যম বেছে নিন"
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CatalogData.PAYMENT_METHODS.forEach { method ->
                        val isSelected = selectedPaymentMethod == method.nameBangla ||
                                (method.id == "wallet" && selectedPaymentMethod.contains("ওয়ালেট"))

                        Card(
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) Color(0xFFFAF5FF) else Color.White
                            ),
                            border = if (isSelected) {
                                androidx.compose.foundation.BorderStroke(2.dp, ElectricViolet)
                            } else {
                                androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
                            },
                            modifier = Modifier
                                .weight(1f)
                                .clickable { viewModel.selectPaymentMethod(method.nameBangla) }
                                .testTag("payment_${method.id}")
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(vertical = 10.dp, horizontal = 2.dp)
                            ) {
                                Icon(
                                    imageVector = when (method.id) {
                                        "wallet" -> Icons.Default.AccountBalanceWallet
                                        "bkash" -> Icons.Default.Payment
                                        "nagad" -> Icons.Default.MonetizationOn
                                        "rocket" -> Icons.Default.Send
                                        else -> Icons.Default.CreditCard
                                    },
                                    contentDescription = method.nameBangla,
                                    tint = if (isSelected) ElectricViolet else Color(0xFF64748B),
                                    modifier = Modifier.size(22.dp)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = method.nameBangla,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) ElectricViolet else Color.Black,
                                    textAlign = TextAlign.Center
                                )
                                if (method.id == "wallet") {
                                    Text(
                                        text = "${userProfile?.walletBalance ?: 0} ৳",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = SuccessGreen
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}

@Composable
fun StepHeader(
    stepNumber: String,
    title: String,
    subtitle: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(Color.Black)
        ) {
            Text(
                text = stepNumber,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFF0F172A)
            )
            Text(
                text = subtitle,
                fontSize = 11.sp,
                color = Color(0xFF64748B)
            )
        }
    }
}

@Composable
fun PackageSelectCard(
    pkg: TopUpPackage,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFFF3E8FF) else Color.White
        ),
        border = if (isSelected) {
            androidx.compose.foundation.BorderStroke(2.dp, ElectricViolet)
        } else {
            androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
        },
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 3.dp else 1.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("package_${pkg.id}")
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 8.dp)
        ) {
            Text(
                text = pkg.title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) ElectricViolet else Color(0xFF0F172A),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "BDT ${pkg.priceBdt}",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) ElectricViolet else Color(0xFF64748B),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun OrderSuccessDialog(
    order: OrderEntity,
    onDismiss: () -> Unit,
    onViewOrders: () -> Unit
) {
    val context = LocalContext.current

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            tonalElevation = 8.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFDCFCE7))
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Success",
                        tint = SuccessGreen,
                        modifier = Modifier.size(40.dp)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "টপআপ সফল হয়েছে!",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF0F172A)
                )

                Text(
                    text = "AI ডেলিভারি সম্পন্ন হয়েছে",
                    fontSize = 12.sp,
                    color = SuccessGreen,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Receipt Box
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFFF8FAFC))
                        .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(10.dp))
                        .padding(12.dp)
                ) {
                    ReceiptRow(label = "Order ID:", value = order.orderId)
                    ReceiptRow(label = "আইটেম:", value = order.title)
                    ReceiptRow(label = "প্লেয়ার UID:", value = order.playerId)
                    ReceiptRow(label = "গেমার নাম:", value = order.playerName)
                    ReceiptRow(label = "টাকার পরিমাণ:", value = "${order.priceBdt} ৳")
                    ReceiptRow(label = "পেমেন্ট মেথড:", value = order.paymentMethod)
                    ReceiptRow(label = "ডেলিভারি কোড:", value = order.deliveryCode)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText("TopUp Receipt", "Order: ${order.orderId}\nItem: ${order.title}\nUID: ${order.playerId}\nCode: ${order.deliveryCode}")
                            clipboard.setPrimaryClip(clip)
                        },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                    ) {
                        Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("কপি", fontSize = 13.sp)
                    }

                    Button(
                        onClick = onViewOrders,
                        colors = ButtonDefaults.buttonColors(containerColor = ElectricViolet),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                            .testTag("view_orders_btn")
                    ) {
                        Text("আমার অর্ডার", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun ReceiptRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 12.sp, color = Color(0xFF64748B))
        Text(text = value, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
    }
}
