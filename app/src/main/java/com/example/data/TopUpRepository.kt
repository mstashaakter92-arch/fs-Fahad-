package com.example.data

import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

class TopUpRepository(private val dao: TopUpDao) {

    val orders: Flow<List<OrderEntity>> = dao.getAllOrders()
    val userProfile: Flow<UserProfileEntity?> = dao.getUserProfile()
    val transactions: Flow<List<TransactionEntity>> = dao.getAllTransactions()
    val voucherCodes: Flow<List<VoucherCodeEntity>> = dao.getAllVoucherCodes()

    suspend fun placeOrder(
        packageItem: TopUpPackage,
        playerId: String,
        playerName: String,
        paymentMethod: String,
        currentBalance: Int
    ): Result<OrderEntity> {
        val orderCode = "#FF" + (100000 + Random.nextInt(900000))
        val deliveryCode = "AI-DEL-" + (100000 + Random.nextInt(900000))

        if (paymentMethod.contains("Wallet", ignoreCase = true) || paymentMethod.contains("ওয়ালেট")) {
            if (currentBalance < packageItem.priceBdt) {
                return Result.failure(Exception("পর্যাপ্ত ওয়ালেট ব্যালেন্স নেই! দয়া করে অ্যাড মানি করুন।"))
            }
            dao.updateWalletBalance(currentBalance - packageItem.priceBdt)
        }

        val order = OrderEntity(
            orderId = orderCode,
            category = packageItem.tag ?: "FF TopUp",
            title = packageItem.title,
            playerId = playerId,
            playerName = playerName,
            priceBdt = packageItem.priceBdt,
            paymentMethod = paymentMethod,
            status = "Done",
            deliveryCode = deliveryCode,
            timestamp = System.currentTimeMillis()
        )

        dao.insertOrder(order)
        dao.updateSavedPlayer(playerId, playerName)

        // If it's a voucher or shell item, also add to voucher codes
        if (packageItem.id.contains("voucher") || packageItem.id.contains("unipin") || packageItem.id.contains("shell")) {
            dao.insertVoucherCode(
                VoucherCodeEntity(
                    title = packageItem.title,
                    code = "UPBD-" + (1000..9999).random() + "-" + (1000..9999).random() + "-" + (1000..9999).random(),
                    pin = (1000..9999).random().toString(),
                    category = "Voucher Code",
                    status = "Active"
                )
            )
        }

        return Result.success(order)
    }

    suspend fun addMoney(
        method: String,
        amount: Int,
        senderPhone: String,
        trxId: String,
        currentBalance: Int
    ): Boolean {
        if (amount <= 0 || trxId.isBlank() || senderPhone.isBlank()) return false

        dao.insertTransaction(
            TransactionEntity(
                trxId = trxId.uppercase().trim(),
                method = method,
                amount = amount,
                senderPhone = senderPhone.trim(),
                status = "Approved",
                timestamp = System.currentTimeMillis()
            )
        )

        dao.updateWalletBalance(currentBalance + amount)
        return true
    }

    suspend fun savePlayerId(uid: String, name: String) {
        dao.updateSavedPlayer(uid, name)
    }

    suspend fun addFreeSpinDiamonds(diamonds: Int, currentBalance: Int) {
        // Free diamonds rewarded from wheel directly to balance or as a bonus record
        dao.updateWalletBalance(currentBalance + (diamonds * 1)) // 1 diamond ~ 1 BDT bonus in-app
    }

    fun lookupPlayerName(uid: String): String {
        val cleanUid = uid.trim()
        if (cleanUid.length < 5) return "Invalid UID"
        val sampleNames = listOf(
            "꧁༺Viper_BD༻꧂",
            "★᭄ᴛᴏxɪᴄ࿐",
            "࿐亗ᏦᏆᏞᏞᎬᎡ亗࿐",
            "⚡FLASH_BD⚡",
            "亗 ɴ ᴏ ᴏ ʙ 亗",
            "☬AK-47_PRO☬",
            "✿ʙᴀʙʏ_ɢɪʀʟ✿",
            "༺LeGeNd_BD༻"
        )
        val hash = cleanUid.hashCode()
        val index = Math.abs(hash) % sampleNames.size
        return sampleNames[index]
    }
}
