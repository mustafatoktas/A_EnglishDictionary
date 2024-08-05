package com.mustafatoktas.englishdictionary.presentation.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mustafatoktas.englishdictionary.presentation.anasayfa.AnasayfaScreen
import com.mustafatoktas.englishdictionary.presentation.ayarlar.AyarlarScreen

@Composable
fun EnglishDictionaryNavHost(
    startDestination: Screen,
    navHostController: NavHostController,
    application: Application,
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination,
    ) {
        composable<Screen.AnasayfaScreen> {
            AnasayfaScreen(navHostController = navHostController)
        }

        composable<Screen.AyarlarScreen> {
            AyarlarScreen(navHostController = navHostController, application = application)
        }
    }
}