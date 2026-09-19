package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        OrderEntity::class,
        TransactionEntity::class,
        UserProfileEntity::class,
        VoucherCodeEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun topUpDao(): TopUpDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "fftopup_database"
                )
                .fallbackToDestructiveMigration()
                .addCallback(DatabaseCallback(scope))
                .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateInitialData(database.topUpDao())
                    }
                }
            }

            suspend fun populateInitialData(dao: TopUpDao) {
                // Initialize default user
                dao.insertUserProfile(
                    UserProfileEntity(
                        id = 1,
                        name = "BD Pro Gamer",
                        phone = "01789-456123",
                        email = "gamerbd@gmail.com",
                        walletBalance = 250,
                        savedUid = "2849182910",
                        savedPlayerName = "꧁༺Viper_BD༻꧂"
                    )
                )

                // Add sample past order
                dao.insertOrder(
                    OrderEntity(
                        orderId = "#FF84920",
                        category = "FF UID TopUp",
                        title = "115 Diamond",
                        playerId = "2849182910",
                        playerName = "꧁༺Viper_BD༻꧂",
                        priceBdt = 78,
                        paymentMethod = "ওয়ালেট (Wallet)",
                        status = "Done",
                        deliveryCode = "AI-DEL-941829",
                        timestamp = System.currentTimeMillis() - 3600000 * 2
                    )
                )

                dao.insertOrder(
                    OrderEntity(
                        orderId = "#FF83109",
                        category = "Membership",
                        title = "Weekly Membership",
                        playerId = "2849182910",
                        playerName = "꧁༺Viper_BD༻꧂",
                        priceBdt = 155,
                        paymentMethod = "bKash",
                        status = "Done",
                        deliveryCode = "AI-DEL-883109",
                        timestamp = System.currentTimeMillis() - 3600000 * 24
                    )
                )

                // Add sample voucher
                dao.insertVoucherCode(
                    VoucherCodeEntity(
                        title = "UniPin Voucher BD - 100 Diamond",
                        code = "BDMB-8924-1928-4912",
                        pin = "8912",
                        category = "UniPin Voucher",
                        status = "Active",
                        timestamp = System.currentTimeMillis() - 1800000
                    )
                )
            }
        }
    }
}
