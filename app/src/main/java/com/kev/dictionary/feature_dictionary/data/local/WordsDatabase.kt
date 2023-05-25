package com.kev.dictionary.feature_dictionary.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kev.dictionary.feature_dictionary.data.local.entity.WordInfoEntity

@Database(
    entities = [WordInfoEntity::class],
    version = 1
)
abstract class WordsDatabase : RoomDatabase() {
    abstract val dao: WordInfoDao
}
