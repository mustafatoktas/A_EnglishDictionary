package com.mustafatoktas.englishdictionary.presentation.ayarlar

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mustafatoktas.englishdictionary.R
import com.mustafatoktas.englishdictionary.presentation.ayarlar.components.AyarlarToolbar
import com.mustafatoktas.englishdictionary.presentation.ayarlar.components.AyarlariSifirlaAlertDialog
import com.mustafatoktas.englishdictionary.presentation.ayarlar.components.TemaSecimScreen
import com.mustafatoktas.englishdictionary.presentation.ayarlar.viewmodel.AyarlarEvent
import com.mustafatoktas.englishdictionary.presentation.ayarlar.viewmodel.AyarlarViewModel

@Composable
fun AyarlarScreen(
    navHostController: NavHostController,
    viewModel: AyarlarViewModel = hiltViewModel(),
    context: Context,
) {
    val state by viewModel.state.collectAsState()
    var temaDialogGoster by remember { mutableStateOf(false) }
    var ayarSifirlaDialogGoster by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            AyarlarToolbar(
                geriTiklandi = {
                    navHostController.navigateUp()
                },
                ayarlariSifirlaTiklandi = {
                    ayarSifirlaDialogGoster = true
                },
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(top = 10.dp),
        ) {
            Box(
                modifier = Modifier
                    .clickable {
                        temaDialogGoster = true
                    }
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(vertical = 10.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(12.dp)
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = context.getString(R.string.theme),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 20.dp),
                    )

                    Text(
                        text = state.seciliTema,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 40.dp),
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.End,
                    )
                }
            }
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 30.dp)
                    .padding(bottom = 80.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = context.getString(R.string.new_settings_and_features_will_be_added_in_future_versions),
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }

    if (ayarSifirlaDialogGoster)
        AyarlariSifirlaAlertDialog(
            dismissTiklandi = {
                ayarSifirlaDialogGoster = false
            },
            confirmTiklandi = {
                ayarSifirlaDialogGoster = false
                viewModel.handleEvent(AyarlarEvent.onAyarlariSifirlaClick)
            },
        )

    if (temaDialogGoster)
        Dialog(
            onDismissRequest = {
                temaDialogGoster = false
            },
        ) {
            TemaSecimScreen(
                sistemTiklandi = {
                    temaDialogGoster = false
                    viewModel.handleEvent(AyarlarEvent.onSistemTemaClick)
                },
                karanlikTiklandi = {
                    temaDialogGoster = false
                    viewModel.handleEvent(AyarlarEvent.onKaranlikTemaClick)
                },
                aydinlikTiklandi = {
                    temaDialogGoster = false
                    viewModel.handleEvent(AyarlarEvent.onAydinlikTemaClick)
                },
                selectedTheme = state.seciliTema,
            )
        }
}