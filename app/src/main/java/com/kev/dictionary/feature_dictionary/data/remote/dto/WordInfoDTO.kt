package com.kev.dictionary.feature_dictionary.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.kev.dictionary.feature_dictionary.data.local.entity.WordInfoEntity
import com.kev.dictionary.feature_dictionary.domain.model.WordInfo

data class WordInfoDTO(
    @SerializedName("meanings")
    val meanings: List<MeaningDTO>,
    @SerializedName("phonetic")
    val phonetic: String,
    @SerializedName("word")
    val word: String,
    @SerializedName("phonetics")
    val phonetics: List<PhoneticDTO>?
){
    fun toWordInfoEntity(): WordInfoEntity{
        return WordInfoEntity(
            meanings = meanings.map { it.toMeaning() },
            phonetic = phonetic,
            word = word,
            phonetics = phonetics?.map { it.toPhonetic() }

        )
    }
}