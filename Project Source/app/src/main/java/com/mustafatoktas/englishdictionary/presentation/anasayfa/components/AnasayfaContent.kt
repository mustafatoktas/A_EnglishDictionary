package com.mustafatoktas.englishdictionary.presentation.anasayfa.components

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
import androidx.navigation.NavHostController
import com.mustafatoktas.englishdictionary.presentation.anasayfa.viewmodel.AnasayfaEvent
import com.mustafatoktas.englishdictionary.presentation.anasayfa.viewmodel.AnasayfaState
import com.mustafatoktas.englishdictionary.presentation.anasayfa.viewmodel.AnasayfaViewModel
import com.mustafatoktas.englishdictionary.presentation.navigation.Screen

@Composable
fun AnasayfaContent(
    state : AnasayfaState,
    viewModel: AnasayfaViewModel,
    navHostController: NavHostController,
) {
    var alertDialogGoster by remember { mutableStateOf(false) }
    val context = LocalContext.current

    BarColor()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AnasayfaToolbar(
                state = state,
                viewModel = viewModel,
                aramaTiklandi = {
                    viewModel.handleEvent(AnasayfaEvent.OnSearchClick)
                },
                ayarlarTiklandi = {
                    navHostController.navigate(Screen.AyarlarScreen)
                },
                checkForUpdatesTiklandi = {
                    alertDialogGoster = true
                    viewModel.handleEvent(AnasayfaEvent.OnCheckForUpdatesClick)
                }
            )
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(MaterialTheme.colorScheme.background)
                        .padding(horizontal = 30.dp),
                ) {
                    state.wordItem?.let { wordItem ->

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = wordItem.word,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = wordItem.phonetic,
                            fontSize = 17.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }

                Box(
                    modifier = Modifier
                        .padding(top = 110.dp)
                        .fillMaxSize()
                        .clip(
                            RoundedCornerShape(
                                topStart = 50.dp,
                                topEnd = 50.dp
                            )
                        )
                        .background(
                            MaterialTheme.colorScheme.secondaryContainer.copy(0.7f)
                        ),
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(80.dp)
                                .align(Alignment.Center),
                            color = MaterialTheme.colorScheme.primary
                        )
                    } else {
                        state.wordItem?.let { wordItem ->
                            WordResult(wordItem)
                        }
                    }
                }
            }
        }
        if (alertDialogGoster)
            AlertDialog(
                onDismissRequest = {},
                title = {
                    Text(text = "Update")
                },
                text = {
                    Text(text = state.guncellemeMesaji)
                },
                dismissButton = {
                    if (state.guncellemeVarMi)
                        Button(
                            onClick = {
                                alertDialogGoster = false
                            }
                        ) {
                            Text(text = "Later")
                        }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            alertDialogGoster = false
                            if (state.guncellemeVarMi) {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(state.guncellemeAdresi))
                                context.startActivity(intent)
                            }
                        }
                    ) {
                        Text(text = if (state.guncellemeVarMi) "Update" else "OK")
                    }
                },
            )
    }

}