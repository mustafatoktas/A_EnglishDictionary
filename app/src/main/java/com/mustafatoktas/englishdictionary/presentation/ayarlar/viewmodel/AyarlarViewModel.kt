package com.mustafatoktas.englishdictionary.presentation.ayarlar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mustafatoktas.englishdictionary.common.Constants
import com.mustafatoktas.englishdictionary.common.MyDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class AyarlarViewModel @Inject constructor(
    private val myDataStore: MyDataStore
) : ViewModel() {

    private val _state = MutableStateFlow(AyarlarState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = state.value.copy(
                seciliTema = myDataStore.getTema()
            )
        }
    }

    fun handleEvent(event: AyarlarEvent) {
        viewModelScope.launch {
            when (event) {
                AyarlarEvent.onAyarlariSifirlaClick -> {
                    myDataStore.setTema(Constants.SISTEM_TEMASI)
                    _state.update {
                        it.copy(
                            seciliTema = myDataStore.getTema()
                        )
                    }

                }
                AyarlarEvent.onAydinlikTemaClick -> {
                    myDataStore.setTema(Constants.ACIK_TEMA)
                    _state.update {
                        it.copy(
                            seciliTema = myDataStore.getTema()
                        )
                    }
                }
                AyarlarEvent.onKaranlikTemaClick -> {
                    myDataStore.setTema(Constants.KARANLIK_TEMA)
                    _state.update {
                        it.copy(
                            seciliTema = myDataStore.getTema()
                        )
                    }
                }
                AyarlarEvent.onSistemTemaClick -> {
                    myDataStore.setTema(Constants.SISTEM_TEMASI)
                    _state.update {
                        it.copy(
                            seciliTema = myDataStore.getTema()
                        )
                    }
                }
            }
        }
    }
}