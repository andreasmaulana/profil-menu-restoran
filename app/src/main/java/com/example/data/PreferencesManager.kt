package com.example.data

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences = 
        context.getSharedPreferences("resto_prefs", Context.MODE_PRIVATE)

    fun getProfile(): RestaurantProfile {
        val name = sharedPreferences.getString("key_name", "Gourmet Bistro Nusantara") ?: "Gourmet Bistro Nusantara"
        val address = sharedPreferences.getString("key_address", "Jl. Kuliner Raya No. 12, Jakarta Selatan") ?: "Jl. Kuliner Raya No. 12, Jakarta Selatan"
        val description = sharedPreferences.getString("key_description", "Menghadirkan citarasa nusantara yang berpadu dengan keanggunan kuliner modern. Kami menyajikan hidangan berkualitas tinggi dengan bahan-bahan organik pilihan terbaik.") ?: "Menghadirkan citarasa nusantara yang berpadu dengan keanggunan kuliner modern. Kami menyajikan hidangan berkualitas tinggi dengan bahan-bahan organik pilihan terbaik."
        val openingHours = sharedPreferences.getString("key_opening_hours", "09:00 - 22:00 WIB") ?: "09:00 - 22:00 WIB"
        
        return RestaurantProfile(name, address, description, openingHours)
    }

    fun saveProfile(profile: RestaurantProfile) {
        sharedPreferences.edit()
            .putString("key_name", profile.name)
            .putString("key_address", profile.address)
            .putString("key_description", profile.description)
            .putString("key_opening_hours", profile.openingHours)
            .apply()
    }

    fun isDarkTheme(): Boolean {
        return sharedPreferences.getBoolean("key_dark_theme", false)
    }

    fun setDarkTheme(enabled: Boolean) {
        sharedPreferences.edit()
            .putBoolean("key_dark_theme", enabled)
            .apply()
    }
}
