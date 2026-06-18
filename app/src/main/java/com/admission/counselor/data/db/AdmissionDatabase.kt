package com.admission.counselor.data.db

import androidx.room.Database
import androidx.room.Entity
import androidx.room.Fts5
import androidx.room.PrimaryKey
import androidx.room.RoomDatabase

@Entity(tableName = "courses")
data class CourseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val duration: String,
    val fee: String,
    val deadline: String,
    val eligibility: String,
    val highlights: String,
    val why_join: String
)

@Entity(tableName = "courses_fts")
@Fts5(content = "courses")
data class CourseFtsEntity(
    val name: String,
    val duration: String,
    val fee: String,
    val deadline: String,
    val eligibility: String,
    val highlights: String,
    val why_join: String
)

@Database(entities = [CourseEntity::class, CourseFtsEntity::class], version = 1, exportSchema = false)
abstract class AdmissionDatabase : RoomDatabase() {
    abstract fun courseDao(): CourseDao
}
