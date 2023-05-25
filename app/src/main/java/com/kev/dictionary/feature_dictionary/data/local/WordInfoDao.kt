package com.kev.dictionary.feature_dictionary.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kev.dictionary.feature_dictionary.data.local.entity.WordInfoEntity

@Dao
interface WordInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWordInfos(infos: List<WordInfoEntity>)

    @Query("DELETE FROM wordinfoentity WHERE word IN (:words) ")
    suspend fun deleteWordInfos(words: List<String>)

    /*The LIKE operator is used for pattern matching.
    The % symbol is a wildcard character that matches any sequence of characters.
    So, the condition '%' || :word || '%' means that the "word" column should contain the value specified by the :word parameter,
    surrounded by any characters before and after it.*/

    @Query("SELECT * FROM wordinfoentity WHERE word LIKE '%' || :word || '%'")
    suspend fun getWordInfos(word:String): List<WordInfoEntity>

}