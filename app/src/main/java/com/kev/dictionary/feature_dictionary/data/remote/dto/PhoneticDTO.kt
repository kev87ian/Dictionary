package com.kev.dictionary.feature_dictionary.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.kev.dictionary.feature_dictionary.domain.model.Phonetic

data class PhoneticDTO(
    @SerializedName("audio")
    val audio: String?,
    @SerializedName("sourceUrl")
    val sourceUrl: String?,
    @SerializedName("text")
    val text: String?
){
    fun toPhonetic(): Phonetic{
        return Phonetic(
            audio = audio,
            sourceUrl = sourceUrl,
            text = text
        )
    }
}