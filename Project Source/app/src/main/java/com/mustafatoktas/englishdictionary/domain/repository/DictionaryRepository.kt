package com.mustafatoktas.englishdictionary.domain.repository

import com.mustafatoktas.englishdictionary.domain.model.WordItem
import kotlinx.coroutines.flow.Flow
import com.mustafatoktas.englishdictionary.common.Resource

interface DictionaryRepository {

    suspend fun getWordResult(word: String): Flow<Resource<WordItem>>
}