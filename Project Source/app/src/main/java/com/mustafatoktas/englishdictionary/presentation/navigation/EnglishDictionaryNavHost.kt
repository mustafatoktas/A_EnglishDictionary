package com.mustafatoktas.englishdictionary.presentation.navigation

import android.app.Application
import android.content.Context
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
    context: Context,
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination,
    ) {
        composable<Screen.AnasayfaScreen> {
            AnasayfaScreen(navHostController = navHostController, context = context)
        }

        composable<Screen.AyarlarScreen> {
            AyarlarScreen(navHostController = navHostController, context = context)
        }
    }
}