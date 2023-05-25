package com.kev.dictionary.feature_dictionary.data.remote.dto


import com.google.gson.annotations.SerializedName

data class WordInfoDTO(
    @SerializedName("license")
    val licenseDTO: LicenseDTO,
    @SerializedName("meanings")
    val meaningDTOS: List<MeaningDTO>,
    @SerializedName("phonetic")
    val phonetic: String,
    @SerializedName("phonetics")
    val phoneticDTOS: List<PhoneticDTO>,
    @SerializedName("sourceUrls")
    val sourceUrls: List<String>,
    @SerializedName("word")
    val word: String
)