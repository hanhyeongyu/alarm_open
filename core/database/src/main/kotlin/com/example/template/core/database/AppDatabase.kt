package com.example.template.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.template.core.database.entity.TestEntity
import com.example.template.core.database.util.InstantConverter


@Database(
    entities = [
        TestEntity::class
    ],
    version = 14,
    autoMigrations = [
    ],
    exportSchema = true,
)
@TypeConverters(
    InstantConverter::class,
)
internal abstract class AppDatabase : RoomDatabase() {
    //abstract fun topicDao(): TopicDao
}
