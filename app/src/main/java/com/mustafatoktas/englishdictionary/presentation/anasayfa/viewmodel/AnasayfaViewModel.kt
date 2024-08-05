package com.mustafatoktas.englishdictionary.presentation.anasayfa.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mustafatoktas.englishdictionary.common.Constants
import com.mustafatoktas.englishdictionary.common.MyDataStore
import com.mustafatoktas.englishdictionary.common.Resource
import com.mustafatoktas.englishdictionary.domain.repository.DictionaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
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
        }

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            loadWordResult()
        }
    }

    fun handleEvent(event: AnasayfaEvent) {
        when (event) {
            is AnasayfaEvent.OnSearchClick -> {
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    loadWordResult()
                }
            }

            is AnasayfaEvent.OnSearchWordChange -> {
                _state.update {
                    it.copy(
                        searchWord = event.newWord.lowercase()
                    )
                }
            }

            is AnasayfaEvent.OnCheckForUpdatesClick -> {
                checkForUpdates()
            }
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
}