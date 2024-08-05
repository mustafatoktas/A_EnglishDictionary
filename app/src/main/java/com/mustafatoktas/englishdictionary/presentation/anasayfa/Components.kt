package com.mustafatoktas.englishdictionary.presentation.anasayfa

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Update
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mustafatoktas.englishdictionary.R
import com.mustafatoktas.englishdictionary.domain.model.Meaning
import com.mustafatoktas.englishdictionary.domain.model.WordItem
import com.mustafatoktas.englishdictionary.presentation.anasayfa.viewmodel.AnasayfaState
import com.mustafatoktas.englishdictionary.presentation.anasayfa.viewmodel.AnasayfaEvent
import com.mustafatoktas.englishdictionary.presentation.anasayfa.viewmodel.AnasayfaViewModel

@Composable
fun BarColor() {

    val systemUiController = rememberSystemUiController()
    val color = MaterialTheme.colorScheme.background

    LaunchedEffect(color) {
        systemUiController.setSystemBarsColor(color)
    }
}

@Composable
fun AnasayfaToolbar(
    viewModel: AnasayfaViewModel,
    state: AnasayfaState,
    ayarlarTiklandi : () -> Unit,
    checkForUpdatesTiklandi : () -> Unit,
    aramaTiklandi: () -> Unit,
) {

    val softwareKeyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    var dropDownGoster by remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        value = state.searchWord,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                viewModel.handleEvent(AnasayfaEvent.OnSearchClick)
                softwareKeyboardController?.hide()
            }
        ),
        onValueChange = {
            if (it.length <= 25)
                viewModel.handleEvent(AnasayfaEvent.OnSearchWordChange(it))
        },
        trailingIcon = {
            Row (
                horizontalArrangement = Arrangement.Center,
            ) {
                IconButton(
                    onClick = {
                        softwareKeyboardController?.hide()
                        aramaTiklandi.invoke()
                    }

                ) {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = context.getString(R.string.search_a_word),
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(30.dp)
                    )
                }
                IconButton(
                    onClick = {
                        dropDownGoster = !dropDownGoster
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.MoreVert,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(30.dp)
                    )
                }
                DropdownMenu(
                    expanded = dropDownGoster,
                    onDismissRequest = { dropDownGoster = false }
                ) {
                    DropdownMenuItem(
                        text = { Text(text = "Settings") },
                        onClick = {
                            dropDownGoster = false
                            ayarlarTiklandi.invoke()
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Outlined.Settings, contentDescription = "Settings")
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Check for updates") },
                        onClick = {
                            dropDownGoster = false
                            checkForUpdatesTiklandi.invoke()
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Outlined.Update, contentDescription = "Check for updates")
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "v1.0.0") },
                        onClick = {
                            dropDownGoster = false
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Outlined.Info, contentDescription = "v1.0.0")
                        }
                    )
                }
            }
        },
        label = {
            Text(
                text = context.getString(R.string.search_a_word),
                fontSize = 15.sp,
                modifier = Modifier.alpha(0.7f)
            )
        },
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 19.5.sp
        ),
        singleLine = true,
    )
}


@Composable
fun Meaning(
    meaning: Meaning,
    index: Int
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {

        Text(
            text = "${index + 1}. ${meaning.partOfSpeech}",
            fontSize = 17.sp,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(
                    brush = Brush.horizontalGradient(
                        listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.primary.copy(0.4f),
                            Color.Transparent
                        )
                    )
                )
                .padding(
                    top = 2.dp, bottom = 4.dp,
                    start = 12.dp, end = 12.dp
                )
        )

        if (meaning.definition.definition.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
            ) {

                Text(
                    text = stringResource(R.string.definition),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 19.sp,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = meaning.definition.definition,
                    fontSize = 17.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )

            }
        }

        if (meaning.definition.example.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
            ) {

                Text(
                    text = stringResource(R.string.definition),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 19.sp,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = meaning.definition.example,
                    fontSize = 17.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )

            }
        }

    }

}

@Composable
fun WordResult(wordItem: WordItem) {

    LazyColumn(
        contentPadding = PaddingValues(vertical = 32.dp),
    ) {
        items(wordItem.meanings.size) { index ->
            Meaning(
                meaning = wordItem.meanings[index],
                index = index
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}