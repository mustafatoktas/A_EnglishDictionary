package com.mustafatoktas.englishdictionary.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.mustafatoktas.englishdictionary.common.Constants
import com.mustafatoktas.englishdictionary.common.MyDataStore
import com.mustafatoktas.englishdictionary.presentation.navigation.EnglishDictionaryNavHost
import com.mustafatoktas.englishdictionary.presentation.navigation.Screen
import com.mustafatoktas.englishdictionary.presentation.theme.EnglishDictionaryTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var myDataStore: MyDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {
            val tema by myDataStore.temaFlow.collectAsState(initial = Constants.SISTEM_TEMASI)

            EnglishDictionaryTheme(
                theme = tema
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val navHostController = rememberNavController()
                    val startDestination = Screen.AnasayfaScreen
                    val context = LocalContext.current

                    EnglishDictionaryNavHost(
                        startDestination = startDestination,
                        navHostController = navHostController,
                        context =context,
                    )
                }
            }
        }
    }
}