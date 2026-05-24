package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.data.MenuItem
import com.example.data.PreferencesManager
import com.example.data.RestaurantProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val prefsManager = PreferencesManager(application)

    private val _profile = MutableStateFlow(prefsManager.getProfile())
    val profile: StateFlow<RestaurantProfile> = _profile.asStateFlow()

    private val _isDarkTheme = MutableStateFlow(prefsManager.isDarkTheme())
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    // Daftar menu statis (minimal 5 item) makanan & minuman dengan detail lengkap
    val menuList = listOf(
        MenuItem(
            id = "nasi_goreng",
            name = "Nasi Goreng Kampung Bistro",
            price = 42000.0,
            formattedPrice = "Rp 42.000",
            description = "Nasi goreng tradisional gurih yang dimasak dengan bumbu rempah warisan, telur mata sapi, ayam suwir, acar segar, dan kerupuk udang renyah.",
            category = "Makanan",
            imageUrl = "https://images.unsplash.com/photo-1621646733956-34989587a364?w=600",
            rating = 4.8f
        ),
        MenuItem(
            id = "sop_iga",
            name = "Sop Iga Bakar Rempah",
            price = 78000.0,
            formattedPrice = "Rp 78.000",
            description = "Iga sapi pilihan yang diungkep bumbu kaya rempah, dibakar hingga karamelisasi sempurna, disajikan dengan kuah sop hangat gurih, kentang, wortel, dan sambal ijo.",
            category = "Makanan",
            imageUrl = "https://images.unsplash.com/photo-1544025162-d76694265947?w=600",
            rating = 4.9f
        ),
        MenuItem(
            id = "ayam_bakar",
            name = "Ayam Bakar Madu Kemangi",
            price = 48000.0,
            formattedPrice = "Rp 48.000",
            description = "Ayam pejantan segar yang diungkep dengan madu hutan dan rempah, dibakar perlahan hingga meresap, disajikan hangat dengan lalapan segar dan wangi kemangi.",
            category = "Makanan",
            imageUrl = "https://images.unsplash.com/photo-1598515214211-89d3e73ae83b?w=600",
            rating = 4.7f
        ),
        MenuItem(
            id = "jus_alpukat",
            name = "Jus Alpukat Creamy",
            price = 25000.0,
            formattedPrice = "Rp 25.000",
            description = "Jus buah alpukat mentega segar pilihan yang super creamy, dipadukan dengan kental manis cokelat premium dan es serut dingin yang menyegarkan dahaga.",
            category = "Minuman",
            imageUrl = "https://images.unsplash.com/photo-1553530666-ba11a7da3888?w=600",
            rating = 4.6f
        ),
        MenuItem(
            id = "es_teh_manis",
            name = "Es Teh Manis Selasih",
            price = 12000.0,
            formattedPrice = "Rp 12.000",
            description = "Seduhan daun teh melati pilihan yang harum wangi, disajikan dingin dengan es batu, gula cair murni, dan taburan biji selasih yang kaya manfaat.",
            category = "Minuman",
            imageUrl = "https://images.unsplash.com/photo-1556679343-c7306c1976bc?w=600",
            rating = 4.5f
        )
    )

    fun saveProfile(name: String, address: String, description: String, openingHours: String) {
        val newProfile = RestaurantProfile(name, address, description, openingHours)
        prefsManager.saveProfile(newProfile)
        _profile.value = newProfile
    }

    fun toggleDarkTheme() {
        val newValue = !_isDarkTheme.value
        prefsManager.setDarkTheme(newValue)
        _isDarkTheme.value = newValue
    }
}
