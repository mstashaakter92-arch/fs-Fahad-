package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val orderId: String,
    val category: String,
    val title: String,
    val playerId: String,
    val playerName: String,
    val priceBdt: Int,
    val paymentMethod: String,
    val status: String = "Done", // "Done", "Processing"
    val deliveryCode: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val trxId: String,
    val method: String,
    val amount: Int,
    val senderPhone: String,
    val status: String = "Approved",
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey
    val id: Int = 1,
    val name: String = "BD Pro Gamer",
    val phone: String = "01789-456123",
    val email: String = "gamerbd@gmail.com",
    val walletBalance: Int = 250,
    val savedUid: String = "2849182910",
    val savedPlayerName: String = "꧁༺Viper_BD༻꧂"
)

@Entity(tableName = "voucher_codes")
data class VoucherCodeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val code: String,
    val pin: String = "",
    val category: String,
    val status: String = "Active", // "Active", "Redeemed"
    val timestamp: Long = System.currentTimeMillis()
)
