package com.kev.dictionary.feature_dictionary.domain.model

data class WordInfo(
    val meanings: List<Meaning>,
    val phonetic: String,
    val word: String,
    val phonetics: List<Phonetic>?

)
