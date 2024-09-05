package com.mustafatoktas.englishdictionary.presentation.anasayfa.viewmodel

import com.mustafatoktas.englishdictionary.common.CihazDurumu
import com.mustafatoktas.englishdictionary.domain.model.WordItem

data class AnasayfaState(
    val cihazDurumu: CihazDurumu = CihazDurumu.Normal,
    val isLoading: Boolean = false,
    val searchWord: String = "",
    val wordItem: WordItem? = null,
    val guncellemeMesaji : String = "",
    val guncellemeVarMi : Boolean = false,
    val guncellemeAdresi : String = "",
)