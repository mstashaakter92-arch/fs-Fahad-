package com.example.data

data class TopUpPackage(
    val id: String,
    val title: String,
    val subtitle: String = "",
    val priceBdt: Int,
    val diamonds: Int = 0,
    val tag: String? = null,
    val isPopular: Boolean = false
)

data class ServiceCategoryItem(
    val id: String,
    val titleBangla: String,
    val titleEnglish: String,
    val badge: String = "AI ডেলিভারি",
    val iconType: String,
    val route: String = "topup_flow",
    val description: String = ""
)

data class RecentOrderFeedItem(
    val id: String,
    val customerName: String,
    val itemName: String,
    val status: String = "Done",
    val timeAgo: String = "১ সেকেন্ড আগে"
)

object CatalogData {
    val SPECIAL_OFFERS = listOf(
        TopUpPackage("sp_weekly", "স্পেশাল অফার উইকলি", "Weekly Offer", 145, diamonds = 450, tag = "OFFER"),
        TopUpPackage("sp_monthly", "স্পেশাল অফার Monthly", "Monthly Offer", 750, diamonds = 2600, tag = "HOT"),
        TopUpPackage("sp_weekly_lite", "স্পেশাল অফার উইকলি লাইট", "Weekly Lite Offer", 42, diamonds = 100, tag = "SAVE")
    )

    val FF_PACKAGES = listOf(
        TopUpPackage("ff_weekly", "Weekly", "সাপ্তাহিক মেম্বারশিপ", 155, tag = "POPULAR", isPopular = true),
        TopUpPackage("ff_monthly", "Monthly", "মাসিক মেম্বারশিপ", 770, tag = "BEST VALUE"),
        TopUpPackage("ff_25", "25 Diamond", "ইনস্ট্যান্ট টপআপ", 21),
        TopUpPackage("ff_50", "50 Diamond", "ইনস্ট্যান্ট টপআপ", 36),
        TopUpPackage("ff_75", "75 Diamond", "ইনস্ট্যান্ট টপআপ", 58),
        TopUpPackage("ff_100", "100 Diamond", "ইনস্ট্যান্ট টপআপ", 72),
        TopUpPackage("ff_115", "115 Diamond", "ইনস্ট্যান্ট টপআপ", 78, isPopular = true),
        TopUpPackage("ff_240", "240 Diamond", "ইনস্ট্যান্ট টপআপ", 155, tag = "HOT"),
        TopUpPackage("ff_355", "355 Diamond", "ইনস্ট্যান্ট টপআপ", 237),
        TopUpPackage("ff_480", "480 Diamond", "ইনস্ট্যান্ট টপআপ", 316),
        TopUpPackage("ff_505", "505 Diamond", "ইনস্ট্যান্ট টপআপ", 338, tag = "POPULAR"),
        TopUpPackage("ff_610", "610 Diamond", "ইনস্ট্যান্ট টপআপ", 390),
        TopUpPackage("ff_725", "725 Diamond", "ইনস্ট্যান্ট টপআপ", 479),
        TopUpPackage("ff_850", "850 Diamond", "ইনস্ট্যান্ট টপআপ", 558),
        TopUpPackage("ff_1090", "1090 Diamond", "ইনস্ট্যান্ট টপআপ", 716),
        TopUpPackage("ff_1240", "1240 Diamond", "ইনস্ট্যান্ট টপআপ", 780),
        TopUpPackage("ff_1595", "1595 Diamond", "ইনস্ট্যান্ট টপআপ", 1037),
        TopUpPackage("ff_1720", "1720 Diamond", "ইনস্ট্যান্ট টপআপ", 1116),
        TopUpPackage("ff_1850", "1850 Diamond", "ইনস্ট্যান্ট টপআপ", 1200),
        TopUpPackage("ff_2090", "2090 Diamond", "ইনস্ট্যান্ট টপআপ", 1358),
        TopUpPackage("ff_2530", "2530 Diamond", "ইনস্ট্যান্ট টপআপ", 1570),
        TopUpPackage("ff_3770", "3770 Diamond", "মেগা প্যাক", 2410),
        TopUpPackage("ff_5060", "5060 Diamond", "ভিআইপি বান্ডিল", 3140),
        TopUpPackage("ff_7590", "7590 Diamond", "মেগা বান্ডিল", 4710),
        TopUpPackage("ff_10120", "10120 Diamond", "আল্টিমেট প্যাক", 6280)
    )

    val TOPUP_CATEGORIES = listOf(
        ServiceCategoryItem("c_giveaway", "25 DIAMOND GIVEAWAY", "25 DIAMOND", "ফ্রি গিভওয়ে", "giveaway"),
        ServiceCategoryItem("c_ff_uid", "FF UID টপআপ বাংলাদেশ", "FF TopUp (BD)", "AI ডেলিভারি", "ff_uid"),
        ServiceCategoryItem("c_weekly_lite", "উইকলি লাইট", "Weekly Lite (BD Server)", "AI ডেলিভারি", "weekly_lite"),
        ServiceCategoryItem("c_weekly", "উইকলি", "Weekly", "AI ডেলিভারি", "weekly"),
        ServiceCategoryItem("c_monthly", "মান্থলি", "Monthly", "AI ডেলিভারি", "monthly"),
        ServiceCategoryItem("c_membership", "MEMBERSHIP", "Weekly/Monthly Offer", "AI ডেলিভারি", "membership"),
        ServiceCategoryItem("c_levelup", "লেভেল আপ পাস", "New Level Up Pass BD", "AI ডেলিভারি", "levelup"),
        ServiceCategoryItem("c_indo", "ইন্দোনেশিয়া", "Indonesia Server (UID)", "AI ডেলিভারি", "globe"),
        ServiceCategoryItem("c_ff_like", "ফ্রি ফায়ার লাইক", "FF Like", "AI ডেলিভারি", "heart"),
        ServiceCategoryItem("c_unipin", "UniPin ভাউচার", "Unipin Voucher (BD)", "ভাউচার পিন", "voucher"),
        ServiceCategoryItem("c_shell", "GARENA MALAYSIAN SHELL", "MY SHELL", "অটো কোড", "shell"),
        ServiceCategoryItem("c_spin", "লাকি স্পিন", "FREE SPIN", "ফ্রি ডায়মন্ড", "spin")
    )

    val SUBSCRIPTION_SERVICES = listOf(
        ServiceCategoryItem("s_netflix", "Netflix প্রিমিয়াম", "Netflix 1 Month", "১ মাস", "netflix", description = "4K Ultra HD Single Screen Profile"),
        ServiceCategoryItem("s_canva", "Canva প্রো", "CANVA PRO", "লাইফটাইম / ১ বছর", "canva", description = "Canva Pro Invite / Premium Access"),
        ServiceCategoryItem("s_yt", "ইউটিউব প্রিমিয়াম", "YOUTUBE PREMIUM (1 Month)", "১ মাস", "youtube", description = "Ad-free Background Playback"),
        ServiceCategoryItem("s_capcut", "CapCut প্রিমিয়াম", "CAPCUT PREMIUM 1 MONTH", "১ মাস", "capcut", description = "All Pro templates and AI tools"),
        ServiceCategoryItem("s_crunchyroll", "Crunchyroll প্রিমিয়াম", "CRUNCHYROLL PREMIUM", "১ মাস", "crunchyroll", description = "Anime streaming Mega Fan tier"),
        ServiceCategoryItem("s_telegram", "Telegram প্রিমিয়াম", "TELEGRAM", "৩ মাস / ১ মাস", "telegram", description = "4GB upload, voice-to-text, stars"),
        ServiceCategoryItem("s_chatgpt", "ChatGPT Plus প্রিমিয়াম", "CHATGPT PLUS", "১ মাস", "chatgpt", description = "GPT-4o, DALL-E, Advanced Voice"),
        ServiceCategoryItem("s_tiktok_like", "TikTok ভিডিও লাইক", "TIKTOK VIDEO LIKE", "ইনস্ট্যান্ট", "tiktok", description = "High quality real video likes"),
        ServiceCategoryItem("s_tiktok_follow", "TikTok ফলোয়ার", "TIKTOK ACCOUNT FOLLOWERS", "নন-ড্রপ", "tiktok", description = "Fast non-drop profile followers"),
        ServiceCategoryItem("s_fb_page", "Facebook পেজ ফলোয়ার", "FACEBOOK PAGE FOLLOWER", "অরগানিক", "facebook", description = "Page likes and active followers"),
        ServiceCategoryItem("s_fb_react", "Facebook রিঅ্যাক্ট", "FACEBOOK REACT", "লাভ/কেয়ার", "facebook", description = "Post custom reaction packs"),
        ServiceCategoryItem("s_fb_views", "Facebook ভিডিও ভিউস", "FACEBOOK VIDEO VIEWS", "মনোটাইজেশন", "facebook", description = "Watch time video boost"),
        ServiceCategoryItem("s_fb_id", "Facebook আইডি ফলোয়ার", "Facebook ID Followers", "নন-ড্রপ", "facebook", description = "Personal profile booster"),
        ServiceCategoryItem("s_vpn", "VPN আইটেম", "VPN ITEMS", "হাই স্পিড", "vpn", description = "ExpressVPN / NordVPN Premium Keys")
    )

    val DEFAULT_RECENT_ORDERS = listOf(
        RecentOrderFeedItem("1", "Tiyef Ahmed", "25 Diamond", "Done", "২ সেকেন্ড আগে"),
        RecentOrderFeedItem("2", "SMT SHIPON YT", "505 Diamond", "Done", "১০ সেকেন্ড আগে"),
        RecentOrderFeedItem("3", "MONMON JR", "Weekly", "Done", "২৮ সেকেন্ড আগে"),
        RecentOrderFeedItem("4", "SMT SHIPON YT", "Monthly", "Done", "৪৫ সেকেন্ড আগে"),
        RecentOrderFeedItem("5", "Md Riyad", "WEEKLY LITE", "Done", "১ মিনিট আগে"),
        RecentOrderFeedItem("6", "SK Abid GAMING", "1x Weekly Lite", "Done", "২ মিনিট আগে"),
        RecentOrderFeedItem("7", "Tiyef Ahmed", "115 Diamond", "Done", "৩ মিনিট আগে"),
        RecentOrderFeedItem("8", "Md Babul mabul miya", "Weekly 3×", "Done", "৫ মিনিট আগে"),
        RecentOrderFeedItem("9", "Ashiqur Rahman", "Weekly", "Done", "৭ মিনিট আগে"),
        RecentOrderFeedItem("10", "Al-Amin Hossain", "1090 Diamond", "Done", "৮ মিনিট আগে"),
        RecentOrderFeedItem("11", "Tanvir Ahmed", "Canva PRO", "Done", "১০ মিনিট আগে"),
        RecentOrderFeedItem("12", "Hridoy FF", "Level Up Pass", "Done", "১২ মিনিট আগে")
    )

    val PAYMENT_METHODS = listOf(
        PaymentMethodItem("wallet", "ওয়ালেট", "আপনার ব্যালেন্স থেকে কাটবে", "#0284C7"),
        PaymentMethodItem("bkash", "বিকাশ", "bKash পার্সোনাল / মার্চেন্ট", "#E2136E"),
        PaymentMethodItem("nagad", "নগদ", "নগদ সেন্ড মানি", "#F7941D"),
        PaymentMethodItem("rocket", "রকেট", "রকেট পার্সোনাল", "#8C3494"),
        PaymentMethodItem("upay", "উপায়", "Upay পেমেন্ট", "#005697")
    )
}

data class PaymentMethodItem(
    val id: String,
    val nameBangla: String,
    val description: String,
    val colorHex: String
)
