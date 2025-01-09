package ru.mark.appbininfo.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

import ru.mark.appbininfo.data.api.BinRepository
import ru.mark.appbininfo.data.model.BinInfo
import ru.mark.appbininfo.presentation.screen.SharedPrefHelper

class BinViewModel(
    private val repository: BinRepository,
    private val sharedPrefHelper: SharedPrefHelper
) : ViewModel() {
    private val _binInfo = MutableLiveData<BinInfo?>()
    val binInfo: LiveData<BinInfo?> get() = _binInfo

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun fetchBinInfo(bin: String) {
        viewModelScope.launch {
            try {
                val response = repository.fetchBinInfo(bin)
                _binInfo.value = response
                response.let {
                    sharedPrefHelper.saveBinInfo(it)
                }
            } catch (e: Exception) {
                _error.postValue("Exception: ${e.localizedMessage}")
            }
        }
    }

    fun getHistory(): List<BinInfo> {
        return sharedPrefHelper.getBinHistory()
    }
}
