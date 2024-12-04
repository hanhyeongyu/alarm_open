package com.example.template.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "test",
)
data class TestEntity(
    @PrimaryKey
    val id: String,
    val content: String,
)