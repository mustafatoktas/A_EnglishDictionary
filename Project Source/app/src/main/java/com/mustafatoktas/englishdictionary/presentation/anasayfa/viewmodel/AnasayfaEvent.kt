package com.mustafatoktas.englishdictionary.presentation.anasayfa.viewmodel

sealed class AnasayfaEvent {
    data class OnSearchWordChange(val newWord: String) : AnasayfaEvent()
    data object OnSearchClick : AnasayfaEvent()
    data object OnCheckForUpdatesClick : AnasayfaEvent()
}