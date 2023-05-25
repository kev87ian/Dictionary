package com.kev.dictionary.feature_dictionary.data.remote.util

import java.lang.reflect.Type

interface JsonParser {

    /*
We're using an interface incase you wanna use a different library like moshi, the changes made are minimal
      */
    fun <T> fromJson(json: String, type: Type): T?
    fun <T> toJson(obj: T, type: Type): String?
}