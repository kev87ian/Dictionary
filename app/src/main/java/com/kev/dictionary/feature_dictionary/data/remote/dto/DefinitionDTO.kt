package com.kev.dictionary.feature_dictionary.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.kev.dictionary.feature_dictionary.domain.model.Definition

data class DefinitionDTO(

    @SerializedName("definition")
    val definition: String,
    @SerializedName("example")
    val example: String?,
    @SerializedName("synonyms")
    val synonyms: List<String>
){
    fun toDefinition(): Definition{
        return Definition(

            definition = definition,
            example = example,
            synonyms = synonyms
        )
    }
}