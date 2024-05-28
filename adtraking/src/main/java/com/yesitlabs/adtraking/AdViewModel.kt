package com.yesitlabs.adtraking

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdViewModel(private val context: Context) : ViewModel() {

    private val _advertisingId = MutableLiveData<String?>()
    val advertisingId: LiveData<String?> get() = _advertisingId

    fun fetchAdvertisingId() {
        viewModelScope.launch {
            val adId = getAdvertisingId()
            _advertisingId.postValue(adId)
        }
    }

    private suspend fun getAdvertisingId(): String? {
        return withContext(Dispatchers.IO) {
            try {
                val adInfo = AdvertisingIdClient.getAdvertisingIdInfo(context)
                adInfo.id
            } catch (e: Exception) {
                // Handle the exception as needed
                "id not found"
            }
        }
    }
}