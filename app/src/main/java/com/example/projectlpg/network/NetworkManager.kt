package com.example.projectlpg.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiInfo
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.projectlpg.data.repository.AppRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@RequiresApi(Build.VERSION_CODES.S)
@Singleton
class NetworkManager @Inject constructor(
    context: Context,
    private val appRepository: AppRepository
) {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val _wifiConnected = MutableStateFlow(false) // Correctly defining the MutableStateFlow
    val wifiConnected = _wifiConnected.asStateFlow() // Exposing the StateFlow for observation
    lateinit var currentSSID: String
    private val _newSSIDSharedFlow = MutableSharedFlow<String>()
    val newSSIDSharedFlow = _newSSIDSharedFlow.asSharedFlow()


    init {
        registerNetworkCallback()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun registerNetworkCallback() {
        val networkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()

        val networkCallback = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MyNetworkCallback(ConnectivityManager.NetworkCallback.FLAG_INCLUDE_LOCATION_INFO)
        } else {
            MyNetworkCallback()
        }

        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private inner class MyNetworkCallback(flags: Int = 0) :
        ConnectivityManager.NetworkCallback(flags) {
        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            val wifiInfo = networkCapabilities.transportInfo as? WifiInfo
            wifiInfo?.let {
                val ssid = wifiInfo.ssid.removePrefix("\"").removeSuffix("\"")
                CoroutineScope(Dispatchers.Main).launch {
                    _wifiConnected.value = true
                    currentSSID = ssid
                    checkAndSaveNewConnection(ssid)
                }
            }
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            CoroutineScope(Dispatchers.Main).launch {
                _wifiConnected.value = false // Update the connection status
            }
        }


        private fun checkAndSaveNewConnection(ssid: String) {
            CoroutineScope(Dispatchers.IO).launch {
                val deviceKnown = appRepository.deviceKnownSSID(ssid)
                if (!deviceKnown) { // Assuming getDeviceInfoBySSID returns null if not found
                    _newSSIDSharedFlow.emit(ssid)
                    Log.d("SSIDMADE", "$ssid fun checkAndSaveNewConnection")
                    CoroutineScope(Dispatchers.Main).launch {
                        _wifiConnected.value = true
                    }
                }
            }
        }
    }

    fun currentSSID(): String = currentSSID

}
