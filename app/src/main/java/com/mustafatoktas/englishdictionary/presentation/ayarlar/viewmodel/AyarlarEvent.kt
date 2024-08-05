package com.mustafatoktas.englishdictionary.presentation.ayarlar.viewmodel

sealed class AyarlarEvent {
    data object onAyarlariSifirlaClick: AyarlarEvent()
    data object onAydinlikTemaClick : AyarlarEvent()
    data object onKaranlikTemaClick : AyarlarEvent()
    data object onSistemTemaClick : AyarlarEvent()
}