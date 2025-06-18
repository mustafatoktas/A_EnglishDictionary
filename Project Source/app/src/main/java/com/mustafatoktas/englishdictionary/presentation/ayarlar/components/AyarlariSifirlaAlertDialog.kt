package com.mustafatoktas.englishdictionary.presentation.ayarlar.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun AyarlariSifirlaAlertDialog(
    dismissTiklandi : () -> Unit,
    confirmTiklandi : () -> Unit,
) {
    AlertDialog(
        onDismissRequest = dismissTiklandi,
        dismissButton = {
            TextButton(
                onClick = dismissTiklandi
            ) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(
                onClick = confirmTiklandi
            ) {
                Text(text = "Confirm")
            }
        },
        title = {
            Text(text = "Are you sure you want to reset all settings?")
        }
    )
}