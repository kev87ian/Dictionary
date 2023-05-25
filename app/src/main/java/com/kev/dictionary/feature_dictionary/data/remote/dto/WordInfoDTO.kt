package com.kev.dictionary.feature_dictionary.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.kev.dictionary.feature_dictionary.domain.model.WordInfo

data class WordInfoDTO(
    @SerializedName("meanings")
    val meanings: List<MeaningDTO>,
    @SerializedName("phonetic")
    val phonetic: String,
    @SerializedName("word")
    val word: String
){
    fun toWordInfo(): WordInfo{
        return WordInfo(
            meanings = meanings.map { it.toMeaning() },
            phonetic = phonetic,
            word = word

        )
    }
}