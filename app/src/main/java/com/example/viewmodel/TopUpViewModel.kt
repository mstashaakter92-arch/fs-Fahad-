package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.CatalogData
import com.example.data.OrderEntity
import com.example.data.RecentOrderFeedItem
import com.example.data.TopUpPackage
import com.example.data.TopUpRepository
import com.example.data.TransactionEntity
import com.example.data.UserProfileEntity
import com.example.data.VoucherCodeEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.random.Random

sealed interface OrderPlacementState {
    object Idle : OrderPlacementState
    object Processing : OrderPlacementState
    data class Success(val order: OrderEntity) : OrderPlacementState
    data class Error(val message: String) : OrderPlacementState
}

enum class ScreenDestination {
    HOME,
    TOPUP_DETAIL,
    MY_ORDERS,
    ADD_MONEY,
    MY_CODES,
    ACCOUNT,
    FREE_SPIN
}

class TopUpViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TopUpRepository

    init {
        val database = AppDatabase.getDatabase(application, viewModelScope)
        repository = TopUpRepository(database.topUpDao())
    }

    val orders: StateFlow<List<OrderEntity>> = repository.orders
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val userProfile: StateFlow<UserProfileEntity?> = repository.userProfile
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val transactions: StateFlow<List<TransactionEntity>> = repository.transactions
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val voucherCodes: StateFlow<List<VoucherCodeEntity>> = repository.voucherCodes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Navigation State
    private val _currentScreen = MutableStateFlow(ScreenDestination.HOME)
    val currentScreen: StateFlow<ScreenDestination> = _currentScreen.asStateFlow()

    // TopUp Flow State
    private val _selectedPackage = MutableStateFlow<TopUpPackage?>(CatalogData.FF_PACKAGES[0])
    val selectedPackage: StateFlow<TopUpPackage?> = _selectedPackage.asStateFlow()

    private val _enteredUid = MutableStateFlow("2849182910")
    val enteredUid: StateFlow<String> = _enteredUid.asStateFlow()

    private val _verifiedPlayerName = MutableStateFlow("꧁༺Viper_BD༻꧂")
    val verifiedPlayerName: StateFlow<String> = _verifiedPlayerName.asStateFlow()

    private val _isVerifyingUid = MutableStateFlow(false)
    val isVerifyingUid: StateFlow<Boolean> = _isVerifyingUid.asStateFlow()

    private val _selectedPaymentMethod = MutableStateFlow("ওয়ালেট")
    val selectedPaymentMethod: StateFlow<String> = _selectedPaymentMethod.asStateFlow()

    private val _orderState = MutableStateFlow<OrderPlacementState>(OrderPlacementState.Idle)
    val orderState: StateFlow<OrderPlacementState> = _orderState.asStateFlow()

    // Dialogs / Sheets
    private val _showNoticeDialog = MutableStateFlow(true)
    val showNoticeDialog: StateFlow<Boolean> = _showNoticeDialog.asStateFlow()

    private val _showSupportDialog = MutableStateFlow(false)
    val showSupportDialog: StateFlow<Boolean> = _showSupportDialog.asStateFlow()

    private val _showHowToOrder = MutableStateFlow(false)
    val showHowToOrder: StateFlow<Boolean> = _showHowToOrder.asStateFlow()

    // Live Orders Feed
    private val _liveRecentOrders = MutableStateFlow(CatalogData.DEFAULT_RECENT_ORDERS)
    val liveRecentOrders: StateFlow<List<RecentOrderFeedItem>> = _liveRecentOrders.asStateFlow()

    // Feedback Toast / SnackBar
    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    fun navigateTo(screen: ScreenDestination) {
        _currentScreen.value = screen
    }

    fun openTopUpWithPackage(pkg: TopUpPackage? = null) {
        if (pkg != null) {
            _selectedPackage.value = pkg
        } else if (_selectedPackage.value == null) {
            _selectedPackage.value = CatalogData.FF_PACKAGES[0]
        }
        _currentScreen.value = ScreenDestination.TOPUP_DETAIL
    }

    fun selectPackage(pkg: TopUpPackage) {
        _selectedPackage.value = pkg
    }

    fun updateUid(uid: String) {
        _enteredUid.value = uid
    }

    fun verifyPlayerName() {
        val uid = _enteredUid.value.trim()
        if (uid.length < 5) {
            _toastMessage.value = "অনুগ্রহ করে সঠিক গেম ইউআইডি (UID) দিন"
            return
        }
        viewModelScope.launch {
            _isVerifyingUid.value = true
            delay(600) // Realistic server lookup delay
            val name = repository.lookupPlayerName(uid)
            _verifiedPlayerName.value = name
            _isVerifyingUid.value = false
            _toastMessage.value = "গেমার নাম যাচাই সফল: $name"
        }
    }

    fun selectPaymentMethod(method: String) {
        _selectedPaymentMethod.value = method
    }

    fun placeOrder() {
        val pkg = _selectedPackage.value
        if (pkg == null) {
            _toastMessage.value = "দয়া করে একটি প্যাকেজ নির্বাচন করুন"
            return
        }
        val uid = _enteredUid.value.trim()
        if (uid.length < 5) {
            _toastMessage.value = "দয়া করে সঠিক প্লেয়ার আইডি (UID) দিন"
            return
        }
        val currentBalance = userProfile.value?.walletBalance ?: 0
        val paymentMethod = _selectedPaymentMethod.value

        viewModelScope.launch {
            _orderState.value = OrderPlacementState.Processing
            delay(1200) // AI delivery processing simulation

            val result = repository.placeOrder(
                packageItem = pkg,
                playerId = uid,
                playerName = _verifiedPlayerName.value.ifBlank { "Player_$uid" },
                paymentMethod = paymentMethod,
                currentBalance = currentBalance
            )

            result.fold(
                onSuccess = { order ->
                    _orderState.value = OrderPlacementState.Success(order)
                    // Prepend to recent live orders
                    val updated = listOf(
                        RecentOrderFeedItem(
                            id = System.currentTimeMillis().toString(),
                            customerName = userProfile.value?.name ?: "User",
                            itemName = pkg.title,
                            status = "Done",
                            timeAgo = "১ সেকেন্ড আগে"
                        )
                    ) + _liveRecentOrders.value
                    _liveRecentOrders.value = updated.take(20)
                },
                onFailure = { error ->
                    _orderState.value = OrderPlacementState.Error(error.message ?: "অর্ডার ব্যর্থ হয়েছে")
                }
            )
        }
    }

    fun resetOrderState() {
        _orderState.value = OrderPlacementState.Idle
    }

    fun submitAddMoney(
        method: String,
        amount: Int,
        senderPhone: String,
        trxId: String,
        onSuccess: () -> Unit
    ) {
        if (amount < 10) {
            _toastMessage.value = "সর্বনিম্ন ডিপোজিট ১০ টাকা"
            return
        }
        if (senderPhone.length < 10) {
            _toastMessage.value = "সঠিক মোবাইল নম্বর লিখুন"
            return
        }
        if (trxId.length < 5) {
            _toastMessage.value = "সঠিক Transaction ID (TrxID) লিখুন"
            return
        }

        viewModelScope.launch {
            val currentBal = userProfile.value?.walletBalance ?: 0
            val success = repository.addMoney(method, amount, senderPhone, trxId, currentBal)
            if (success) {
                _toastMessage.value = "সফলভাবে $amount ৳ ওয়ালেটে যোগ করা হয়েছে!"
                onSuccess()
            } else {
                _toastMessage.value = "লেনদেন ব্যর্থ হয়েছে, আবার চেষ্টা করুন"
            }
        }
    }

    fun spinWheelAndReward(wonDiamonds: Int) {
        viewModelScope.launch {
            val currentBal = userProfile.value?.walletBalance ?: 0
            repository.addFreeSpinDiamonds(wonDiamonds, currentBal)
            _toastMessage.value = "অভিনন্দন! আপনি $wonDiamonds ফ্রি ডায়মন্ড সমমূল্যের বোনাস জিতেছেন!"
        }
    }

    fun dismissNotice() {
        _showNoticeDialog.value = false
    }

    fun toggleNotice() {
        _showNoticeDialog.value = !_showNoticeDialog.value
    }

    fun setSupportDialog(visible: Boolean) {
        _showSupportDialog.value = visible
    }

    fun setHowToOrder(visible: Boolean) {
        _showHowToOrder.value = visible
    }

    fun clearToast() {
        _toastMessage.value = null
    }

    fun refreshLiveFeed() {
        viewModelScope.launch {
            val names = listOf("Rimon FF", "Shuvo Gamer", "Tanvir BD", "Mehedi 007", "Alif YT", "Hasan Khan")
            val items = listOf("25 Diamond", "115 Diamond", "Weekly", "Monthly", "505 Diamond", "Weekly Lite")
            val newItems = (1..3).map {
                RecentOrderFeedItem(
                    id = System.currentTimeMillis().toString() + it,
                    customerName = names.random(),
                    itemName = items.random(),
                    status = "Done",
                    timeAgo = "১ সেকেন্ড আগে"
                )
            }
            _liveRecentOrders.value = newItems + _liveRecentOrders.value.take(15)
            _toastMessage.value = "লাইভ অর্ডার আপডেট হয়েছে"
        }
    }
}
