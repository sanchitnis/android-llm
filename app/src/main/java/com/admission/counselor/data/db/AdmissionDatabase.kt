package com.admission.counselor.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

// Placeholder entity for scaffolding
@androidx.room.Entity
data class PlaceholderEntity(
    @androidx.room.PrimaryKey val id: Int
)

@androidx.room.Dao
interface PlaceholderDao {
    @androidx.room.Query("SELECT * FROM PlaceholderEntity")
    fun getAll(): List<PlaceholderEntity>
}

@Database(entities = [PlaceholderEntity::class], version = 1, exportSchema = false)
abstract class AdmissionDatabase : RoomDatabase() {
    abstract fun placeholderDao(): PlaceholderDao
}
