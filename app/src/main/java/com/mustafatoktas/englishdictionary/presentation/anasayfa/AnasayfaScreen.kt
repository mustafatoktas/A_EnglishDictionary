package com.mustafatoktas.englishdictionary.presentation.anasayfa

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mustafatoktas.englishdictionary.presentation.anasayfa.viewmodel.AnasayfaEvent
import com.mustafatoktas.englishdictionary.presentation.anasayfa.viewmodel.AnasayfaViewModel
import androidx.navigation.NavHostController
import com.mustafatoktas.englishdictionary.R
import com.mustafatoktas.englishdictionary.common.CihazDurumu
import com.mustafatoktas.englishdictionary.presentation.anasayfa.components.AnasayfaContent
import com.mustafatoktas.englishdictionary.presentation.anasayfa.components.AnasayfaToolbar
import com.mustafatoktas.englishdictionary.presentation.anasayfa.components.BarColor
import com.mustafatoktas.englishdictionary.presentation.anasayfa.components.OtherContent
import com.mustafatoktas.englishdictionary.presentation.anasayfa.components.WordResult
import com.mustafatoktas.englishdictionary.presentation.navigation.Screen


@Composable
fun AnasayfaScreen(
    navHostController: NavHostController,
    context: Context,
    viewModel: AnasayfaViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    when (state.cihazDurumu) {
        CihazDurumu.Rootlu -> OtherContent(
            baslik = context.getString(R.string.root_tespit_edildi),
            icerik = context.getString(R.string.root_icerigi),
            resim = R.drawable.root,
            context = context,
        )
        CihazDurumu.Emulator -> OtherContent(
            baslik = context.getString(R.string.cihaz_bir_emulator_uzerinde_calisiyor),
            icerik = context.getString(R.string.emulator_icerigi),
            resim = R.drawable.emulator,
            context = context,
        )
        CihazDurumu.Normal -> AnasayfaContent(
            state = state,
            viewModel = viewModel,
            navHostController = navHostController
        )
    }
}