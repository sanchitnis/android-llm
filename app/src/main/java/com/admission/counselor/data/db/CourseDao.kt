package com.admission.counselor.data.db

import androidx.room.Dao
import androidx.room.Query

@Dao
interface CourseDao {
    @Query("""
        SELECT courses.* 
        FROM courses 
        JOIN courses_fts ON courses.id = courses_fts.rowid 
        WHERE courses_fts MATCH :query 
        LIMIT 3
    """)
    suspend fun searchCourses(query: String): List<CourseEntity>
    
    @Query("SELECT * FROM courses LIMIT 5")
    suspend fun getFallbackCourses(): List<CourseEntity>
}
