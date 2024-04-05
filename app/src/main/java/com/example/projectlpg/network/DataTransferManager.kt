package com.example.projectlpg.network

import com.example.projectlpg.data.models.SensorData
import com.example.projectlpg.data.repository.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.Console
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import javax.inject.Inject

class DataTransferManager @Inject constructor(
    private val appRepository: AppRepository, // Injecting the repository
    private val networkManager: NetworkManager // Inject NetworkManager to access connection info

) {

    suspend fun sendAndReceiveData(ipAddress: String, port: Int, sendData: String = "WIFI_CONNECTED"): SensorData? = withContext(Dispatchers.IO) {


        var socket: Socket? = null
        var receivedData: SensorData? = null

        try {
            socket = Socket(ipAddress, port).apply {
                soTimeout = 5000 // Set a socket timeout for safety
            }

            val out = PrintWriter(socket.getOutputStream(), true)
            val input = BufferedReader(InputStreamReader(socket.getInputStream()))

            // Send data to the microcontroller
            out.println(sendData)

            // Receive the response
            val response = input.readLine() // Assuming the microcontroller sends a newline-terminated string

            // Parse the received string into the SensorData format
            val parts = response.split(",")
            if (parts.isNotEmpty()) {
                val currentSSID = networkManager.currentSSID() // This should be a function in your NetworkManager
                val deviceInfo = currentSSID.let { ssid ->
                    appRepository.getDeviceIdBySSID(ssid)
                }

                deviceInfo.let { deviceID ->
                    receivedData = SensorData(
                        deviceId = deviceID,
                        weight = parts[0],
                        timestamp = System.currentTimeMillis()
                    )
                }
                // Save the received data to the database
                receivedData?.let { appRepository.insertSensorData(it) }
            }


        } catch (e: Exception) {
            e.printStackTrace()
            // Handle exceptions like IOException, SocketTimeoutException, etc.
        } finally {
            socket?.close()
        }

        return@withContext receivedData
    }
}
