package com.mustafatoktas.englishdictionary.data.repositoryImpl

import android.app.Application
import com.mustafatoktas.englishdictionary.R
import com.mustafatoktas.englishdictionary.domain.model.WordItem
import com.mustafatoktas.englishdictionary.domain.repository.DictionaryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import com.mustafatoktas.englishdictionary.common.Resource
import com.mustafatoktas.englishdictionary.data.remote.DictionaryApi
import com.mustafatoktas.englishdictionary.data.remote.dto.toWordItem

class DictionaryRepositoryImpl @Inject constructor(
    private val dictionaryApi: DictionaryApi,
    private val application: Application,
) : DictionaryRepository {

    override suspend fun getWordResult(word: String): Flow<Resource<WordItem>> {

        return flow {
            emit(Resource.Loading(true))

            val remoteWordResourceDto = try {
                dictionaryApi.getWordResult(word)
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(application.getString(R.string.can_t_get_result)))
                return@flow
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(application.getString(R.string.can_t_get_result)))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                return@flow
            } finally {
                emit(Resource.Loading(false))
            }

            remoteWordResourceDto?.let { wordResultDto ->
                wordResultDto[0]?.let { wordItemDto ->
                    emit(Resource.Success(wordItemDto.toWordItem()))
                    emit(Resource.Loading(false))
                    return@flow
                }
            }

            emit(Resource.Error(application.getString(R.string.can_t_get_result)))
        }
    }
}