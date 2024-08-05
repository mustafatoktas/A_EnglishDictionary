package com.mustafatoktas.englishdictionary.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen{

    @Serializable
    object AnasayfaScreen : Screen()

    @Serializable
    object AyarlarScreen : Screen()

}