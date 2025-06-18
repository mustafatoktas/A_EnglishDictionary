package com.mustafatoktas.englishdictionary.presentation.anasayfa.viewmodel

import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mustafatoktas.englishdictionary.common.CihazDurumu
import com.mustafatoktas.englishdictionary.common.Constants
import com.mustafatoktas.englishdictionary.common.MyDataStore
import com.mustafatoktas.englishdictionary.common.Resource
import com.mustafatoktas.englishdictionary.domain.repository.DictionaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject


@HiltViewModel
class AnasayfaViewModel @Inject constructor(
    private val dictionaryRepository: DictionaryRepository,
    private val myDataStore : MyDataStore
) : ViewModel() {

    private val _state = MutableStateFlow(AnasayfaState())
    val state = _state.asStateFlow()


    private var searchJob: Job? = null


    init {
        runBlocking {
            _state.update {
                it.copy(searchWord = myDataStore.getArananSonKelime())
            }
            loadWordResult()
            cihazDurumu()
        }
    }

    fun handleEvent(event: AnasayfaEvent) {
        when (event) {
            is AnasayfaEvent.OnSearchClick -> onSearchClick()
            is AnasayfaEvent.OnSearchWordChange -> onSearchWorChange(event.newWord)
            is AnasayfaEvent.OnCheckForUpdatesClick -> checkForUpdates()
        }
    }

    private fun onSearchClick() {
        searchJob?.cancel()
        loadWordResult()
    }

    private fun onSearchWorChange(newWord: String) {
        _state.update {
            it.copy(
                searchWord = newWord.lowercase()
            )
        }
    }

    private fun loadWordResult() {
        viewModelScope.launch {
            dictionaryRepository.getWordResult(state.value.searchWord).collect { result ->
                myDataStore.setSonKelime(state.value.searchWord)
                when (result) {
                    is Resource.Error -> Unit

                    is Resource.Loading -> {
                        _state.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }

                    is Resource.Success -> {
                        result.data?.let { wordItem ->
                            _state.update {
                                it.copy(
                                    wordItem = wordItem
                                )
                            }
                        }

                    }
                }
            }
        }
    }

    private fun checkForUpdates() {
        viewModelScope.launch(Dispatchers.IO) {
            val url = URL(Constants.REPO_API_URL)
            val connection = url.openConnection() as HttpURLConnection

            connection.requestMethod = "GET"
            connection.setRequestProperty(
                "User-Agent",
                "Mozilla/5.0 (Linux; Android 10; Pixel 3 XL Build/QQ1A.191205.011) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.79 Mobile Safari/537.36"
            )

            try {
                val responseCode = connection.responseCode
                if (responseCode == 200) {
                    val responseBody = connection.inputStream.bufferedReader().use { it.readText() }
                    val jsonObject = Json.parseToJsonElement(responseBody).jsonObject
                    val sonVersiyon = jsonObject["tag_name"]?.jsonPrimitive?.content ?: ""
                    val indirmeAdresi = jsonObject["html_url"]?.jsonPrimitive?.content ?: ""

                    if (sonVersiyon > Constants.CURRENT_VERSION) {
                        _state.value = _state.value.copy(
                            guncellemeMesaji = "New version available: $sonVersiyon\n\nDownload here: $indirmeAdresi",
                            guncellemeVarMi = true,
                            guncellemeAdresi = indirmeAdresi,
                        )
                    } else {
                        _state.value = _state.value.copy(
                            guncellemeMesaji = "You are using the latest version.",
                            guncellemeVarMi = false,
                        )
                    }
                } else {
                    _state.value = _state.value.copy(
                        guncellemeMesaji = "Request error: $responseCode",
                        guncellemeVarMi = false,
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    guncellemeMesaji = "Request error: ${e.message}",
                    guncellemeVarMi = false,
                )
            } finally {
                connection.disconnect()
            }
        }
    }

    private fun cihazDurumu() {
        viewModelScope.launch {
            val isRootedDeferred = async { isRooted() }
            val isEmulatorDeferred = async { isEmulator() }

            val isRooted = isRootedDeferred.await()
            val isEmulator = isEmulatorDeferred.await()

            _state.value = _state.value.copy(
                cihazDurumu = when {
                    isRooted -> CihazDurumu.Rootlu
                    isEmulator -> CihazDurumu.Emulator
                    else -> CihazDurumu.Normal
                }
            )
        }
    }


    private suspend fun isRooted(): Boolean {
        return isRootedMethod1() || isRootedMethod2()
    }

    private suspend fun isRootedMethod1(): Boolean {
        return withContext(Dispatchers.IO) {
            val superuserApk = File("/system/app/Superuser.apk")
            val suBinary = File("/system/bin/su")
            superuserApk.exists() || suBinary.exists()
        }
    }

    private suspend fun isRootedMethod2(): Boolean {
        return withContext(Dispatchers.IO) {
            var process: Process? = null
            return@withContext try {
                process = Runtime.getRuntime().exec("su")
                true // Eğer "su" komutu başarılı olursa cihaz rootlu
            } catch (e: Exception) {
                false // Eğer bir exception fırlarsa cihaz rootlu değil
            } finally {
                process?.destroy() // Process'i kapatmayı garantiye alıyoruz
            }
        }
    }

    private suspend fun isEmulator(): Boolean {
        return withContext(Dispatchers.IO) {
            (Build.FINGERPRINT.startsWith("google/sdk_gphone_")
                    && Build.FINGERPRINT.endsWith(":user/release-keys")
                    && Build.MANUFACTURER == "Google" && Build.PRODUCT.startsWith("sdk_gphone") && Build.BRAND == "google"
                    && Build.MODEL.startsWith("sdk_gphone"))
                    || Build.FINGERPRINT.startsWith("generic")
                    || Build.FINGERPRINT.startsWith("unknown")
                    || Build.HARDWARE.contains("goldfish")
                    || Build.HARDWARE.contains("ranchu")
                    || Build.MODEL.contains("google_sdk")
                    || Build.MODEL.contains("Emulator")
                    || Build.MODEL.contains("Android SDK built for x86")
                    || Build.MANUFACTURER.contains("Genymotion")
                    || Build.HOST == "Build2" //MSI App Player
                    || Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")
                    || Build.PRODUCT.contains("sdk_google")
                    || Build.PRODUCT == "google_sdk"
                    || Build.PRODUCT.contains("sdk")
                    || Build.PRODUCT.contains("sdk_x86")
                    || Build.PRODUCT.contains("vbox86p")
                    || Build.PRODUCT.contains("emulator")
                    || Build.PRODUCT.contains("simulator")
        }
    }
}