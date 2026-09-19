package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.*
import com.example.viewmodel.ScreenDestination
import com.example.viewmodel.TopUpViewModel
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun FreeSpinScreen(
    viewModel: TopUpViewModel,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    var isSpinning by remember { mutableStateOf(false) }
    var currentRotation by remember { mutableStateOf(0f) }
    var wonReward by remember { mutableStateOf<Int?>(null) }

    val rotationAnimation = remember { Animatable(0f) }

    val rewards = listOf(5, 10, 15, 25, 50, 100)
    val colors = listOf(
        Color(0xFF7C3AED),
        Color(0xFF2563EB),
        Color(0xFF0284C7),
        Color(0xFF059669),
        Color(0xFFD97706),
        Color(0xFFDC2626)
    )

    fun spinWheel() {
        if (isSpinning) return
        isSpinning = true
        coroutineScope.launch {
            val randomTurns = 5 + Random.nextInt(5)
            val selectedRewardIndex = Random.nextInt(rewards.size)
            val segmentAngle = 360f / rewards.size
            val targetRotation = currentRotation + (randomTurns * 360f) + (selectedRewardIndex * segmentAngle)

            rotationAnimation.animateTo(
                targetValue = targetRotation,
                animationSpec = tween(
                    durationMillis = 3500,
                    easing = FastOutSlowInEasing
                )
            )

            currentRotation = targetRotation % 360f
            val won = rewards[selectedRewardIndex]
            wonReward = won
            viewModel.spinWheelAndReward(won)
            isSpinning = false
        }
    }

    wonReward?.let { reward ->
        Dialog(onDismissRequest = { wonReward = null }) {
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Color(0xFF0F172A),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(24.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                            .background(BrightGold.copy(alpha = 0.2f))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Diamond,
                            contentDescription = null,
                            tint = BrightGold,
                            modifier = Modifier.size(44.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "🎉 অভিনন্দন!",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "আপনি জিতে নিয়েছেন $reward ফ্রি ডায়মন্ড সমমূল্যের বোনাস!",
                        fontSize = 14.sp,
                        color = BrightGold,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "টাকাটি আপনার ওয়ালেট ব্যালেন্সে স্বয়ংক্রিয়ভাবে যোগ হয়েছে।",
                        fontSize = 12.sp,
                        color = Color(0xFFCBD5E1),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            wonReward = null
                            viewModel.navigateTo(ScreenDestination.TOPUP_DETAIL)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = ElectricViolet),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                    ) {
                        Text("এখনই ডায়মন্ড টপআপ করুন", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 2.dp
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                ) {
                    IconButton(onClick = { viewModel.navigateTo(ScreenDestination.HOME) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                    Text(
                        text = "লাকি স্পিন হুইল (FREE SPIN)",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF0F172A), Color(0xFF1E1B4B))
                    )
                )
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "দৈনিক ফ্রি ডায়মন্ড স্পিন",
                fontSize = 22.sp,
                fontWeight = FontWeight.Black,
                color = BrightGold
            )
            Text(
                text = "চাকা ঘুরিয়ে জিতে নিন ১০০ পর্যন্ত ফ্রি ডায়মন্ড!",
                fontSize = 13.sp,
                color = Color(0xFFCBD5E1)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Wheel Container
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(280.dp)
            ) {
                // Wheel Canvas
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .rotate(rotationAnimation.value)
                ) {
                    val diameter = size.minDimension
                    val radius = diameter / 2f
                    val center = Offset(size.width / 2f, size.height / 2f)
                    val sweepAngle = 360f / rewards.size

                    for (i in rewards.indices) {
                        drawArc(
                            color = colors[i % colors.size],
                            startAngle = i * sweepAngle,
                            sweepAngle = sweepAngle,
                            useCenter = true,
                            size = Size(diameter, diameter),
                            topLeft = Offset((size.width - diameter) / 2f, (size.height - diameter) / 2f)
                        )
                    }

                    // Outer Rim
                    drawCircle(
                        color = BrightGold,
                        radius = radius,
                        center = center,
                        style = Stroke(width = 6.dp.toPx())
                    )
                }

                // Center Pin/Hub
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(Color.Black)
                ) {
                    Icon(
                        imageVector = Icons.Default.Diamond,
                        contentDescription = null,
                        tint = DiamondCyan,
                        modifier = Modifier.size(30.dp)
                    )
                }

                // Top Pointer Needle
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Pointer",
                    tint = BrightGold,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .offset(y = (-14).dp)
                        .size(48.dp)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Spin Action Button
            Button(
                onClick = { spinWheel() },
                enabled = !isSpinning,
                colors = ButtonDefaults.buttonColors(
                    containerColor = BrightGold,
                    disabledContainerColor = Color(0xFF64748B)
                ),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("spin_wheel_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Casino,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isSpinning) "ঘুরছে..." else "স্পিন করুন (SPIN NOW)",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0x33FFFFFF)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "স্পিনের নিয়মাবলী:",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = BrightGold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "• প্রতি ২৪ ঘন্টায় একবার বিনামূল্যে স্পিন করতে পারবেন।\n• প্রাপ্ত ডায়মন্ডের সমমূল্য সরাসরি ওয়ালেটে অ্যাড হবে।\n• কোনো ভুয়া অ্যাকাউন্ট থেকে স্পিন করলে পয়েন্ট বাতিল করা হবে।",
                        fontSize = 11.sp,
                        color = Color(0xFFE2E8F0),
                        lineHeight = 16.sp
                    )
                }
            }
        }
    }
}
