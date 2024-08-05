package com.mustafatoktas.englishdictionary.presentation.ayarlar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Adjust
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mustafatoktas.englishdictionary.common.Constants

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
                Icon(imageVector = Icons.Outlined.Adjust, contentDescription = "Default Settings")
            }
        }
    )
}

@Composable
fun AyarlariSifirlaAlertDialog(
    dismissTiklandi : () -> Unit,
    confirmTiklandi : () -> Unit,
) {
    AlertDialog(
        onDismissRequest = dismissTiklandi,
        dismissButton = {
            Button(
                onClick = dismissTiklandi
            ) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            Button(
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

@Composable
fun TemaSecimScreen(
    selectedTheme: String,
    sistemTiklandi: () -> Unit,
    karanlikTiklandi: () -> Unit,
    aydinlikTiklandi: () -> Unit
) {
    val themeOptions = listOf(
        Constants.SISTEM_TEMASI,
        Constants.KARANLIK_TEMA,
        Constants.ACIK_TEMA,
    )

    var currentSelectedTheme by remember { mutableStateOf(selectedTheme) }

    Surface {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Select Theme",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            themeOptions.forEachIndexed { index, theme ->
                Row(
                    modifier = Modifier
                        .padding(vertical = 2.dp)
                        .fillMaxWidth()
                        .clickable {
                            currentSelectedTheme = theme
                            when (index) {
                                0 -> sistemTiklandi()
                                1 -> karanlikTiklandi()
                                2 -> aydinlikTiklandi()
                            }
                        },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    RadioButton(
                        selected = currentSelectedTheme == theme,
                        onClick = {
                            currentSelectedTheme = theme
                            when (index) {
                                0 -> sistemTiklandi()
                                1 -> karanlikTiklandi()
                                2 -> aydinlikTiklandi()
                            }
                        },
                    )
                    Text(
                        text = when (index) {
                            0 -> Constants.SISTEM_TEMASI
                            1 -> Constants.KARANLIK_TEMA
                            2 -> Constants.ACIK_TEMA
                            else -> ""
                        },
                        modifier = Modifier.padding(start = 6.dp),
                    )
                }
            }
        }
    }
}