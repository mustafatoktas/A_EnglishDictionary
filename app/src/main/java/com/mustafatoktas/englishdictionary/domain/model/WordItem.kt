package com.mustafatoktas.englishdictionary.domain.model

data class WordItem (
    val word: String,
    val meanings: List<Meaning>,
    val phonetic: String,
)