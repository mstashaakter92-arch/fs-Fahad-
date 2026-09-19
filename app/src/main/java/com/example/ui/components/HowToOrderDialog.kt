package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.ElectricViolet
import com.example.ui.theme.SuccessGreen

@Composable
fun HowToOrderDialog(
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.HelpOutline,
                            contentDescription = "Help",
                            tint = ElectricViolet,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "কিভাবে অর্ডার করবেন?",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                GuideStepItem(
                    stepNumber = "১",
                    title = "প্যাকেজ নির্বাচন করুন",
                    desc = "আপনার পছন্দের ডায়মন্ড বা উইকলি/মান্থলি মেম্বারশিপ প্যাকেজে ট্যাপ করুন।"
                )

                Spacer(modifier = Modifier.height(12.dp))

                GuideStepItem(
                    stepNumber = "২",
                    title = "প্লেয়ার আইডি (UID) দিন",
                    desc = "গেম প্রোফাইল থেকে প্লেয়ার ইউআইডি কপি করে বসান এবং 'নাম চেক করুন' এ চাপুন।"
                )

                Spacer(modifier = Modifier.height(12.dp))

                GuideStepItem(
                    stepNumber = "৩",
                    title = "পেমেন্ট মেথড বেছে নিন",
                    desc = "ওয়ালেট ব্যালেন্স অথবা বিকাশ, নগদ, রকেট দিয়ে অর্ডার কনফার্ম করুন।"
                )

                Spacer(modifier = Modifier.height(12.dp))

                GuideStepItem(
                    stepNumber = "৪",
                    title = "ইনস্ট্যান্ট AI ডেলিভারি",
                    desc = "১ থেকে ১০ সেকেন্ডের মধ্যে আপনার ফ্রি ফায়ার আইডিতে ডায়মন্ড যোগ হয়ে যাবে!"
                )

                Spacer(modifier = Modifier.height(18.dp))

                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = ElectricViolet),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .testTag("understand_button")
                ) {
                    Text(
                        text = "বুঝেছি, অর্ডার করুন",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun GuideStepItem(
    stepNumber: String,
    title: String,
    desc: String
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(26.dp)
                .clip(CircleShape)
                .background(ElectricViolet)
        ) {
            Text(
                text = stepNumber,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = desc,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 16.sp
            )
        }
    }
}
