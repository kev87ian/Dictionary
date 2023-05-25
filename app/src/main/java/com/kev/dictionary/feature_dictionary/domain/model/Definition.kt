package com.kev.dictionary.feature_dictionary.domain.model

import com.google.gson.annotations.SerializedName

data class Definition(
    val definition: String,
    val example: String?,
    val synonyms: List<String>
)
