package com.mustafatoktas.englishdictionary.data.remote

import com.mustafatoktas.englishdictionary.data.remote.dto.WordResultDto
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApi {

    @GET("{word}")
    suspend fun getWordResult(@Path("word") word: String): WordResultDto?
}