package com.kev.dictionary.feature_dictionary.domain.model

import com.google.gson.annotations.SerializedName
import com.kev.dictionary.feature_dictionary.data.remote.dto.DefinitionDTO

data class Meaning(
    val definitions: List<Definition>,
    val partOfSpeech: String,

)
