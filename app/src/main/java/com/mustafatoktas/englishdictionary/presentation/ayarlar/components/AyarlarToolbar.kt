package com.mustafatoktas.englishdictionary.presentation.ayarlar.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SettingsBackupRestore
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AyarlarToolbar(
    geriTiklandi : () -> Unit,
    ayarlariSifirlaTiklandi : () -> Unit,
) {
    TopAppBar(
        title = {
            Text(text = "Settings")
        },
        colors = TopAppBarDefaults.topAppBarColors(
            //containerColor = MaterialTheme.colorScheme.primary,
            //actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        navigationIcon = {
            IconButton(
                onClick = geriTiklandi,
            ) {
                Icon(imageVector = Icons.Outlined.ArrowBackIosNew, contentDescription = "Back Icon")
            }
        },
        actions = {
            IconButton(
                onClick = ayarlariSifirlaTiklandi
            ) {
                Icon(imageVector = Icons.Default.SettingsBackupRestore, contentDescription = "Default Settings")
            }
        }
    )
}