package ru.mark.appbininfo.presentation.screen

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import ru.mark.appbininfo.data.model.BinInfo
import java.util.Collections.emptyList

class SharedPrefHelper(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("bin_history", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveBinInfo(binInfo: BinInfo) {
        val history = getBinHistory().toMutableList()
        history.add(binInfo)
        prefs.edit().putString("history", gson.toJson(history)).apply()
    }

    fun getBinHistory(): List<BinInfo> {
        val json = prefs.getString("history", null) ?: return emptyList()
        return gson.fromJson(json, Array<BinInfo>::class.java).toList()
    }
}