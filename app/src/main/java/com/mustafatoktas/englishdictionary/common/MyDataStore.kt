package com.mustafatoktas.englishdictionary.common

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

val Context.myDataStore : DataStore<Preferences> by preferencesDataStore(Constants.DATASTORE_NAME)

@Singleton
class MyDataStore @Inject constructor (
    context : Context
) {
    private val myDataStore = context.myDataStore


    private object DataStoreKeys {
        val ARANAN_SON_KELIME = stringPreferencesKey("aranan_son_kelime")
        val TEMA = stringPreferencesKey("secili_tema")
    }

    //KELİME
    suspend fun getArananSonKelime(): String {
        return myDataStore.data.map { preferences ->
            preferences[DataStoreKeys.ARANAN_SON_KELIME] ?: Constants.ILK_ARANAN_KELIME
        }.first()
    }
    fun setSonKelime (sonKelime : String) {
        CoroutineScope(Dispatchers.IO).launch {
            myDataStore.edit {
                it[DataStoreKeys.ARANAN_SON_KELIME] = sonKelime
            }
        }
    }

    //TEMA
    val temaFlow: Flow<String> = myDataStore.data.map { preferences ->
            preferences[DataStoreKeys.TEMA] ?: Constants.SISTEM_TEMASI
        }

    suspend fun getTema(): String {
        return temaFlow.first()
    }

    suspend fun setTema(tema: String) {
        myDataStore.edit {
            it[DataStoreKeys.TEMA] = tema
        }
    }
}