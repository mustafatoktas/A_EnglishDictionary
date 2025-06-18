package com.mustafatoktas.englishdictionary.presentation.ayarlar.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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