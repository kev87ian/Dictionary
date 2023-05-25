package com.kev.dictionary.feature_dictionary.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.kev.dictionary.feature_dictionary.domain.model.Meaning

data class MeaningDTO(
    @SerializedName("definitions")
    val definitions: List<DefinitionDTO>,
    @SerializedName("partOfSpeech")
    val partOfSpeech: String,
){
    fun toMeaning(): Meaning {
        return Meaning(
            definitions = definitions.map { it.toDefinition() },
            partOfSpeech = partOfSpeech
        )
    }
}