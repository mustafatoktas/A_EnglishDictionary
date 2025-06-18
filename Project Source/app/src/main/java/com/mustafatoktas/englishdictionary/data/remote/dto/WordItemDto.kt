package com.mustafatoktas.englishdictionary.data.remote.dto

import com.mustafatoktas.englishdictionary.domain.model.WordItem

data class WordItemDto (
    val meanings: List<MeaningDto>? = null,
    val phonetic: String? = null,
    val word: String? = null,
)


fun WordItemDto.toWordItem() : WordItem {
    return WordItem(
        word = word ?: "",
        meanings = meanings?.map {
            it.toMeaning()
        } ?: emptyList(),
        phonetic = phonetic ?: "",
    )
}