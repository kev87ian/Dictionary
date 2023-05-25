package com.kev.dictionary.feature_dictionary.data.repository

import com.kev.dictionary.feature_dictionary.core.util.Resource
import com.kev.dictionary.feature_dictionary.data.local.WordInfoDao
import com.kev.dictionary.feature_dictionary.data.remote.DictionaryApiService
import com.kev.dictionary.feature_dictionary.domain.model.WordInfo
import com.kev.dictionary.feature_dictionary.domain.repository.WordInfoRepository
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class WordInfoRepositoryImpl @Inject constructor(
    private val api: DictionaryApiService,
    private val dao: WordInfoDao
) : WordInfoRepository {

    override fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>> = flow {
        emit(Resource.Loading())

        // Read the cached data
        val wordInfo = dao.getWordInfos(word).map { it.toWordInfo() }
        // emit the cached data with the loading status
        emit(Resource.Loading(data = wordInfo))

        /*Make the api request */
        try {
            // Make an api call
            val remoteWordInfos = api.getWordInfo(word)
            // replace the items in the database with what we get from the api
            dao.deleteWordInfos(remoteWordInfos.map { it.word })
            dao.insertWordInfos(remoteWordInfos.map { it.toWordInfoEntity() })
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(
                Resource.Error(
                    "We're unable to reach servers, please retry.",
                    data = wordInfo
                )
            )
        } catch (e: IOException) {
            e.printStackTrace()
            emit(
                Resource.Error(
                    errorMessage = "Please ensure you have an internet connection",
                    data = wordInfo
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
            emit(
                Resource.Error(
                    errorMessage = e.localizedMessage
                        ?: "Oops, an unknown error occured. Please retry.",
                    data = wordInfo
                )
            )
        }

        // get the results from database and emit them.
        val newWordInfos = dao.getWordInfos(word).map { it.toWordInfo() }
        emit(Resource.Success(newWordInfos))
    }
}
